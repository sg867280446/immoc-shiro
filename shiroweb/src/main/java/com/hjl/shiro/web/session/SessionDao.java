package com.hjl.shiro.web.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 模拟redis的操作
 */
public class SessionDao extends AbstractSessionDAO {
    //模拟Redis的Map
    private Map<String,byte[]> redisMap = new HashMap<>();


    private final String SHIRO_SESSION_PREFIX = "shiro-session:";

    private String getRealKey(String key){
        return (SHIRO_SESSION_PREFIX+key);
    }

    private void saveSession(Session session){
        if(session != null && session.getId()!=null){
            String realKey = getRealKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            redisMap.put(realKey,value);
        }
    }

    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessionId = generateSessionId(session);
        assignSessionId(session , sessionId);
        saveSession(session);
        System.out.println("创建session : "+sessionId.toString());
        return session.getId();
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            return null;
        }
        String realKey = getRealKey(sessionId.toString());
        byte[] value =redisMap.get(realKey);
        System.out.println("##################读取session : "+sessionId.toString());
        return (Session)SerializationUtils.deserialize(value);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
        System.out.println("更新session : "+session.getId().toString());
    }

    @Override
    public void delete(Session session) {
        if(session != null && session.getId()!=null){
            String realKey = getRealKey(session.getId().toString());
            redisMap.remove(realKey);
            System.out.println("删除session : "+session.getId().toString());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> set = new HashSet<>();
        for(byte [] value : redisMap.values()){
            set.add((Session)SerializationUtils.deserialize(value));
        }
        System.out.println("获取所有session");
        return set;
    }
}
