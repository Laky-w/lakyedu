package com.laky.edu.reception.service.impl;

import com.github.pagehelper.PageHelper;
import com.laky.edu.core.PageBean;
import com.laky.edu.finance.bean.FinanceAccount;
import com.laky.edu.finance.bean.MoneyRecord;
import com.laky.edu.finance.bean.MoneyRecordAccount;
import com.laky.edu.finance.dao.FinanceAccountDao;
import com.laky.edu.finance.dao.MoneyRecordAccountDao;
import com.laky.edu.finance.dao.MoneyRecordDao;
import com.laky.edu.reception.ReceptionConst;
import com.laky.edu.reception.bean.Student;
import com.laky.edu.reception.bean.StudentClass;
import com.laky.edu.reception.bean.StudentOrder;
import com.laky.edu.reception.bean.StudentOrderDetail;
import com.laky.edu.reception.dao.StudentClassDao;
import com.laky.edu.reception.dao.StudentDao;
import com.laky.edu.reception.dao.StudentOrderDao;
import com.laky.edu.reception.dao.StudentOrderDetailDao;
import com.laky.edu.reception.service.StudentService;
import com.laky.edu.supply.SupplyConst;
import com.laky.edu.supply.bean.Customer;
import com.laky.edu.supply.dao.CustomerDao;
import com.laky.edu.teach.bean.Course;
import com.laky.edu.teach.bean.SchoolClass;
import com.laky.edu.teach.dao.CourseDao;
import com.laky.edu.teach.dao.SchoolClassDao;
import com.laky.edu.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 湖之教育工作室·laky on 2017/12/23.
 */
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private StudentClassDao studentClassDao;

    @Autowired
    private SchoolClassDao schoolClassDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentOrderDao studentOrderDao;

    @Autowired
    private StudentOrderDetailDao studentOrderDetailDao;

    @Autowired
    private MoneyRecordDao moneyRecordDao;

    @Autowired
    private MoneyRecordAccountDao moneyRecordAccountDao;

    @Autowired
    private FinanceAccountDao financeAccountDao;

    @Transactional
    @Override
    public Student createStudent(Student student,Customer customer) throws Exception {
        //创建生源记录
        customer.setTheStatus(1);
        customer.setTheType(SupplyConst.CUSTOMER_TYPE_STUDENT);//生源类型处理 正式学员
        customer.setCreateTime(new Date());
        //PinyinHelper.
//        customer.set
        customer.setPinyin(PinYinUtil.parsePinYin(customer.getName()));
        customerDao.insertCustomer(customer);
        //创建正式学员记录 报班状态（未报班）
        student.setTheStatus(1);//正常
        student.setTheType(1);
        student.setCreateTime(customer.getCreateTime());
        student.setPinyin(customer.getPinyin());
        student.setCustomerId(customer.getId());
//        student.
        student.setClassStatus(ReceptionConst.STUDENT_CLASS_STATUS_NO);//1未报班 2已报班，其他状态待定
        int rowIndex = studentDao.insert(student);
        if(rowIndex==0) throw new Exception("创建学员失败！");
        return student;
    }

    @Override
    public PageBean<Student> findStudentAll(LinkedHashMap parameterMap) throws Exception {
        PageHelper.startPage((int)parameterMap.get("pageNum"),(int)parameterMap.get("pageSize"));
        return new PageBean<>(studentDao.selectByParameterMap(parameterMap));
    }



    @Transactional
    @Override
    public StudentOrder studentApply(Integer studentId, StudentOrder order, List<StudentOrderDetail> orderDetails,
                             List<MoneyRecordAccount> financeAccount,Integer userId) throws Exception {
        //获取生源信息
        Customer customer = customerDao.selectByPrimaryKey(studentId);
        Student student = cloneStudent(customer);
        student.setCreateTime(new Date());
        student.setTheStatus(1);
        student.setTheType(1);
        student.setClassStatus(ReceptionConst.STUDENT_CLASS_STATUS_YES);
        //生成正式学员信息
        int rowCount = studentDao.insert(student);
        if(rowCount ==0) throw  new Exception("报名失败");
        //生成报名订单记录
        order = doOrder(order,student.getId());
        order.setUserId(userId);
        rowCount=studentOrderDao.insert(order);
        if(rowCount ==0) throw  new Exception("报名失败");
        List<StudentClass> studentClassList=doOrderDetailAndClass(order,orderDetails);  //生成订单明细信息和报班记录
        if(studentClassList.size()>0){
            studentClassDao.batchInsert(studentClassList);
        }
        doMoneyRecord(order,userId,financeAccount);//收支记录
        customer.setTheType(SupplyConst.CUSTOMER_TYPE_STUDENT);//更新生源类型为正式学员
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        customerDao.updateByPrimaryKeySelective(customerList);
        return order;
    }

    @Override
    public Map queryStudent(LinkedHashMap parameterMap) throws Exception {
        return studentDao.selectStudent(parameterMap);
    }

    @Transactional
    @Override
    public boolean deleteStudent(Map studentMap) throws Exception {
        Student student = new Student();
        student.setId((Integer) studentMap.get("id"));
        student.setTheStatus(0);
        int rows = studentDao.updateStudent(student);
        Customer customer = new Customer();
        customer.setId((Integer) studentMap.get("customerId"));
        customer.setTheStatus(0);
        customerDao.updateByPrimaryKey(customer);
        return !(rows==0);
    }
    @Transactional
    @Override
    public Student updateStudent(Student student,Customer customer) throws Exception {
        //拼音处理
        if(!StringUtils.isEmpty(student.getName())){
            customer.setPinyin(PinYinUtil.parsePinYin(customer.getName()));
            student.setPinyin(customer.getPinyin());
            customer.setId(student.getCustomerId());
        }
        customer.setTheType(SupplyConst.CUSTOMER_TYPE_STUDENT);//生源类型处理 正式学员
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        int rows1 = customerDao.updateByPrimaryKeySelective(customerList);
       int rows =  studentDao.updateStudent(student);
       if (rows ==0 || rows1 ==0) throw new RuntimeException("更新正式学员失败");
        return student;
    }

    @Override
    public int updateStudentManage(Integer ownerId, Integer[] students) throws Exception {
        List<Student> studentList = new ArrayList<>();
        for (Integer id:students){
            Student student = new Student();
            student.setId(id);
            student.setOwnerId(ownerId);
            studentList.add(student);
        }

        return  studentDao.batchUpdateByPrimaryKey(studentList);
    }

    /**
     * 克隆客户信息到正式学员
     * @param customer
     */
    private Student cloneStudent(Customer customer){
        Student student = new Student();
        student.setName(customer.getName());
        student.setPinyin(customer.getPinyin());
        student.setPhone(customer.getPhone());
        student.setSourceId(customer.getSourceId());//活动
        student.setContactId(customer.getContactId());
        student.setRemarks(customer.getRemarks());
        student.setSex(customer.getSex());
        student.setBirthday(customer.getBirthday());
        student.setIntroducerId(customer.getIntroducerId());
        student.setCustomerId(customer.getId());
        student.setSchoolZoneId(customer.getSchoolZoneId());
        return student;
    }

    /**
     * 处理订单
     * @param order
     * @param studentId
     * @return
     */
    private StudentOrder doOrder(StudentOrder order,Integer studentId){
        order.setCreateTime(new Date());
        order.setStudentId(studentId);
        order.setTheStatus(1);
        order.setTheType(ReceptionConst.ORDER_TYPE_CHARGE);//收费
        if(order.getSubtractMoney() == null)
            order.setSubtractMoney(BigDecimal.ZERO);
        if(order.getReceivable() == null)
            order.setReceivable(BigDecimal.ZERO);
        if(order.getTotal().subtract(order.getReceivable()).subtract(order.getSubtractMoney()).compareTo(BigDecimal.ZERO)==0){
            order.setCostStatus(ReceptionConst.ORDER_COST_STATUS_COMPLETE); //已收齐
        } else {
            order.setCostStatus(ReceptionConst.ORDER_COST_STATUS_ARREARS); //未收齐
        }
        return order;
    }



    /**
     * 处理订单详情和报班信息
     * @param order
     * @param orderDetails
     * @return
     */
    private List<StudentClass> doOrderDetailAndClass(StudentOrder order, List<StudentOrderDetail> orderDetails){
        List<StudentClass> studentClassList = new ArrayList<>();
        if(orderDetails!=null){
            // 生成学员班级信息
            for(StudentOrderDetail orderDetail:orderDetails){
                StudentClass studentClass = new StudentClass();
                studentClass.setCreateTime(order.getCreateTime());
                studentClass.setTheStatus(1);
                studentClass.setClassId(orderDetail.getClassId());
                studentClass.setOrderId(order.getId());
                studentClass.setSchoolZoneId(orderDetail.getSchoolId());//班级或者课程所在校区
                studentClass.setStudentId(order.getStudentId());
                studentClass.setCourseId(orderDetail.getCourseId());
                if(orderDetail.getClassId()!=null){ //学员已报班
                    studentClass.setClassStatus(ReceptionConst.STUDENT_CLASS_STATUS_STUDYING);//在读状态
                } else {
                    studentClass.setClassStatus(ReceptionConst.STUDENT_CLASS_STATUS_WAIT);//待分班状态
                }
                studentClassList.add(studentClass);
                orderDetail.setOrderId(order.getId());
            }
            studentOrderDetailDao.batchInsert(orderDetails);
        }
        return studentClassList;
    }

    /**
     * @处理收支记录和支付账户详情
     * @param order
     * @param userId
     * @param financeAccount
     */
    private void doMoneyRecord(StudentOrder order,Integer userId,List<MoneyRecordAccount> financeAccount){
        //生成收支记录
        MoneyRecord record = new MoneyRecord();
        record.setMoney(order.getReceivable());
        record.setSalesmanId(userId);
        record.setCreateTime(order.getCreateTime());
        record.setTheStatus(1);
        record.setCheckStatus(1); //审核状态
        record.setTheType(1);//收支类型
        record.setSchoolZoneId(order.getSchoolZoneId());
        record.setOrderId(order.getId());
        record.setSubtractMoney(order.getSubtractMoney());
        moneyRecordDao.insert(record);
        //生成收支账户金额信息
        if(financeAccount !=null){
//            List<FinanceAccount> accountList = new ArrayList<>();//回填账户信息（审核通过在加）
            List<MoneyRecordAccount> newFinanceAccount = new ArrayList<>();
            for(MoneyRecordAccount recordAccount :financeAccount){
                recordAccount.setRecordId(record.getId());
//                FinanceAccount account = financeAccountDao.selectByPrimaryKey(recordAccount.getAccountId());
//                if (account.getMoney()==null){
//                    account.setMoney(BigDecimal.ZERO);
//                }
                if(recordAccount.getMoney()!=null && recordAccount.getMoney().compareTo(BigDecimal.ZERO) !=0){
                    newFinanceAccount.add(recordAccount);
                }
//                account.setMoney(account.getMoney().add(recordAccount.getMoney()));
//                accountList.add(account);
            }
            moneyRecordAccountDao.batchInsert(newFinanceAccount);
//            financeAccountDao.batchUpdateByPrimaryKeySelective(accountList);
        }
    }
}
