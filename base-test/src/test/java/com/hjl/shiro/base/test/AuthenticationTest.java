package com.hjl.shiro.base.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;


public class AuthenticationTest {
    private SimpleAccountRealm realm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        realm.addAccount("hjl","123","admin","user");
    }

    @Test
    public void testAuthentication(){
        //1、构建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);

        //2、构建主题提交验证信息
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken authenticationToken = new UsernamePasswordToken("hjl","123");
        subject.login(authenticationToken);
        System.out.println(subject.isAuthenticated());
//        subject.logout();
//        System.out.println(subject.isAuthenticated());

        subject.checkRoles("admin","user");
    }
}
