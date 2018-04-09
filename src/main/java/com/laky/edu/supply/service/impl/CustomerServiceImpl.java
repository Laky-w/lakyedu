package com.laky.edu.supply.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.organization.bean.ImportDetail;
import com.laky.edu.organization.bean.ImportRecord;
import com.laky.edu.organization.dao.ImportDetailDao;
import com.laky.edu.organization.dao.ImportRecordDao;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.dao.CustomerDao;
import com.laky.edu.supply.service.CustomerService;
import com.laky.edu.util.ImportExcelUtil;
import com.laky.edu.util.PinYinUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ImportRecordDao importRecordDao;

    @Autowired
    private ImportDetailDao importDetailDao;



    @Transactional
    @Override
    public Customer createCustomer(Customer customer,Integer [] intentionIds) throws Exception {
        customer.setTheStatus(1);
        customer.setTheType(1);
        customer.setInviteStatus(1);
        customer.setContactStatus(1);
        if(!StringUtils.isEmpty(customer.getOwnerId())){
            customer.setAllotStatus(2);//已分配
        } else {
            customer.setAllotStatus(1);//未分配
        }
        customer.setCreateTime(new Date());
        //PinyinHelper.
        customer.setPinyin(PinYinUtil.parsePinYin(customer.getName()));
        int rows=customerDao.insertCustomer(customer);
        if(rows==0) throw new Exception("创建生源失败！");
        if(null !=intentionIds){ //意向课程添加
            customerDao.insertIntentionCourse(getIntentionCourseList(intentionIds,customer.getId()));
        }
        return customer;

    }

    @Transactional
    @Override
    public Customer updateCustomer(Customer customer, Integer[] intentionIds) throws Exception {
        if(!StringUtils.isEmpty(customer.getOwnerId())){
            customer.setAllotStatus(2);//已分配
        } else {
            customer.setAllotStatus(1);//未分配
        }
        customer.setPinyin(PinYinUtil.parsePinYin(customer.getName()));
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        int rows=customerDao.updateByPrimaryKeySelective(customerList);
        if(rows==0) throw new Exception("修改生源失败！");
        if(null !=intentionIds){ //意向课程添加
            //删除历史意向课程
            customerDao.deleteIntentionCourseByStudentId(customer.getId());
            customerDao.insertIntentionCourse(getIntentionCourseList(intentionIds,customer.getId()));
        }
        return customer;
    }

    @Transactional
    @Override
    public boolean deleteCustomer(Integer customerId) throws Exception {
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setTheStatus(0);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        int rowCount = customerDao.updateByPrimaryKeySelective(customerList);
        return !(rowCount==0);
    }

    private  List<Map> getIntentionCourseList(Integer[] intentionIds, Integer customerId){
        List<Map> intentionCourseList = new ArrayList<>();
        for (Integer intentionId: intentionIds) {
            Map map = new HashMap<>();
            map.put("courseId",intentionId);
            map.put("studentId",customerId);
            intentionCourseList.add(map);
        }
        return intentionCourseList;
    }

    @Override
    public Map findCustomer(LinkedHashMap parameterMap) throws Exception {
        return customerDao.selectCustomer(parameterMap);
    }

    @Override
    public PageBean<Customer> findCustomerAll(LinkedHashMap parameterMap) throws Exception {

        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(customerDao.selectByParameterMap(parameterMap));
    }

    @Transactional
    @Override
    public int updateUserOwner(Integer ownerId, Integer[] students) throws Exception {
        List<Customer> customerList = new ArrayList<>();
        for (Integer id:students){
            Customer customer = new Customer();
            customer.setId(id);
            customer.setOwnerId(ownerId);
            customer.setAllotStatus(2);
            customerList.add(customer);
        }

        return  customerDao.updateByPrimaryKeySelective(customerList);
    }

    @Transactional
    @Override
    public void importCustomer(Integer schoolId,Integer ownerId, MultipartFile file) throws Exception {
        InputStream is = file.getInputStream();
        try {
            Date currentDate = new Date();
            ImportRecord importRecord = new ImportRecord();
            importRecord.setTheType(1);
            importRecord.setTheTime(currentDate);
            importRecord.setOperationId(ownerId);
            importRecord.setFileName(file.getName());
            importRecord.setSchoolZoneId(schoolId);
            importRecordDao.insertSelective(importRecord);
            Workbook wb = new HSSFWorkbook(is);
            //得到第一个shell
            Sheet sheet=wb.getSheetAt(0);
            //得到Excel的行数
            int totalRows=sheet.getPhysicalNumberOfRows();

            List customerList = new ArrayList<>();
            List importDetailList = new ArrayList<>();
            for(int i=1;i<totalRows;i++){
                Row row = sheet.getRow(i);
                Map map = doExcelCustomer(importRecord,row,schoolId,ownerId,currentDate);
                customerList.add(map.get("customer"));
                importDetailList.add(map.get("importDetail"));
            }
            if(customerList.size()>0){
                customerDao.batchInsertCustomer(customerList);
            }
            if(importDetailList.size()>0){
                importDetailDao.batchInsert(importDetailList);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            if(is!=null){
                is.close();
            }
        }
    }

    private Map doExcelCustomer(ImportRecord importRecord,Row row,Integer schoolId,Integer ownerId,Date theTime) throws Exception{
        Map map = new HashMap<>();
        Customer customer = new Customer();
        customer.setSchoolZoneId(schoolId);
        customer.setName(ImportExcelUtil.getCellValue(row.getCell(0)));
        customer.setPhone(ImportExcelUtil.getCellValue(row.getCell(1)));
        customer.setOwnerId(ownerId);
        customer.setTheStatus(1);
        customer.setTheType(1);
        customer.setInviteStatus(1);
        customer.setContactStatus(1);
        customer.setAllotStatus(2);//已分配
        customer.setCreateTime(theTime);
        customer.setPinyin(PinYinUtil.parsePinYin(customer.getName()));
        //导入详情
        ImportDetail importDetail = new ImportDetail();
        importDetail.setImportId(importRecord.getId());
        importDetail.setTheType(1);// 1 成功， 2 失败
        importDetail.setContent("第"+(row.getRowNum()+1)+"行：姓名："+customer.getName()+"手机："+customer.getPhone()+"，导入成功。");
        map.put("customer",customer);
        map.put("importDetail",importDetail);
        return map;
    }
}
