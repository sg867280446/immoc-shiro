package com.hjl.shiro.web.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm{
    private final String name = "customReaml";
    private final String key = "huang";
    private Map<String,String> userMap = new HashMap();
    private Map<String,Set> roleMap = new HashMap();
    private Map<String,Set> permMap = new HashMap();

    {
        userMap.put("hjl","21e7f4ea9b773045b4a0fef86fe3b4d2");

        Set<String> roleSet = new HashSet<>();
        roleSet.add("admin");
        roleMap.put("hjl",roleSet);

        Set<String> permSet = new HashSet<>();
        permSet.add("user:add");
        permMap.put("hjl",permSet);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        Set<String> roles = getRolesByUsername(username);
        Set<String> perms = getPermissionsByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(perms);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        String password = getPasswordByUsername(username);
        if(password == null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,name);
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(key));
        return simpleAuthenticationInfo;
    }

    public String getPasswordByUsername(String username){
        return userMap.get(username);
    }

    public Set<String> getRolesByUsername(String username){
        return roleMap.get(username);
    }

    public Set<String> getPermissionsByUsername(String username){
        return permMap.get(username);
    }

}
