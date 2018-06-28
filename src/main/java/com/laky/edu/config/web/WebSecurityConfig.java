package com.laky.edu.config.web;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * web配置
 * Created by 湖之教育工作室·laky on 2017/11/21.
 */
@Configuration
//@EnableWebMvc
public class WebSecurityConfig extends WebMvcConfigurerAdapter {


    /**
     *  格式化日期
     * @return
     */
    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    if(!StringUtils.isEmpty(source)){
                        if(source.length() == 10){
                            date = sdf1.parse(source);
                        } else {
                            date = sdf.parse(source);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return date;
            }
        };
    }

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(securityInterceptor());

        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        addInterceptor.excludePathPatterns("/organization/login");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    /**
     * 添加跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS");
    }

   /* @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        // 1.需要先定义一个convert 转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 2.添加fastJson的配置信息,比如，是否需要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 空值特别处理
        // WriteNullListAsEmpty 将Collection类型字段的字段空值输出为[]
        // WriteNullStringAsEmpty 将字符串类型字段的空值输出为空字符串 ""
        // WriteNullNumberAsZero 将数值类型字段的空值输出为0
        // WriteNullBooleanAsFalse 将Boolean类型字段的空值输出为false
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty);
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
//        fastJsonConfig.set
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        // 3.在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 4.将convert添加到converters当中
        converters.add(fastConverter);
    }*/
}
