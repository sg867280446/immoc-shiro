package com.hjl.shiro.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController {

    @RequestMapping(value = "/sublogin",produces = "application/json;charset=utf-8")
    public @ResponseBody String login(String username,String password){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try{
            subject.login(token);
        }catch (Exception e){
            if(e instanceof UnknownAccountException){
                return "不存在此用户名";
            }else if(e instanceof IncorrectCredentialsException){
                return "密码错误";
            }
            throw e;
        }
        return "登录成功";
    }
}
