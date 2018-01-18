package com.laky.edu.core;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by 湖之教育工作室·laky on 2017/11/21.
 */
@Controller
public class MainController {
    @GetMapping("/")
    public String  index() {
        return "index.html";
    }
}
