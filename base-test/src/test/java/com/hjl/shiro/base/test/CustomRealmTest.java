package com.hjl.shiro.base.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {


    @Test
    public void testCustomRealm(){
        //1、构建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        CustomRealm realm = new CustomRealm();
        securityManager.setRealm(realm);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        realm.setCredentialsMatcher(matcher);

        //2、构建主题提交验证信息
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken authenticationToken = new UsernamePasswordToken("hjl","123");
        subject.login(authenticationToken);
        System.out.println(subject.isAuthenticated());


        subject.checkRoles("admin");
        subject.checkPermissions("user:add");
    }


    public static void main(String[] args) {
//        Md5Hash md5Hash = new Md5Hash("123","huang");
//        System.out.println(md5Hash.toString());
        System.out.println(1/2);
    }

}
