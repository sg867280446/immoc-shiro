package com.hjl.shiro.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
            if(!subject.hasRole("admin")){
                return "无admin权限";
            }
            subject.checkPermissions("user:login");
        }catch (Exception e){
            if(e instanceof UnknownAccountException){
                return "不存在此用户名";
            }else if(e instanceof IncorrectCredentialsException){
                return "密码错误";
            }else if(e instanceof AuthorizationException){
                return "无user:login权限";
            }
            throw e;
        }
        return "登录成功";
    }

    @RequiresRoles("admin")
    @RequestMapping(value = "/testRole",produces = "application/json;charset=utf-8")
    public @ResponseBody String testRole(){
        return "拥有admin角色";
    }

    @RequiresRoles("user")
    @RequestMapping(value = "/testRole1",produces = "application/json;charset=utf-8")
    public @ResponseBody String testRole1(){
        return "拥有user角色";
    }

    @RequestMapping(value = "/testRole2",produces = "application/json;charset=utf-8")
    public @ResponseBody String testRole2(){
        return "拥有admin角色";
    }

    @RequestMapping(value = "/testRole3",produces = "application/json;charset=utf-8")
    public @ResponseBody String testRole3(){
        return "拥有admin与user角色";
    }

    @RequestMapping(value = "/testRole4",produces = "application/json;charset=utf-8")
    public @ResponseBody String testRole4(){
        return "拥有admin或user角色";
    }


    @RequiresPermissions("user:login")
    @RequestMapping(value = "/testPermissions",produces = "application/json;charset=utf-8")
    public @ResponseBody String testPerm(){
        return "拥有user:login权限";
    }


}
