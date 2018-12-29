package com.cc.wx.szy.index;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String index(HttpServletRequest request) {
        log.info("-->index");
        return "index";
    }
}
