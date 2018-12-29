package com.cc.wx.szy.core;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class SzyControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {}

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Error exceptionHandler(Exception ex) {
        Error err = new Error();
        err.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        err.setMsg(ex.getCause().getMessage());
        return err;
    }

    @Getter
    @Setter
    private class Error {
        private int code;
        private String msg;
    }
}
