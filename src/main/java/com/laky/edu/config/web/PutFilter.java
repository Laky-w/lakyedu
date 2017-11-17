package com.laky.edu.config.web;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.HttpPutFormContentFilter;

/**
 * 支持put参数传递
 * Created by 95 on 2017/11/15.
 */
@Component
public class PutFilter extends HttpPutFormContentFilter {

}
