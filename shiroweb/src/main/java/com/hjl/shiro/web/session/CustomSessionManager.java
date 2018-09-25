package com.hjl.shiro.web.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

public class CustomSessionManager extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if(sessionKey instanceof WebSessionKey){
            request = ((WebSessionKey)sessionKey).getServletRequest();
        }

        Session session = null;
        if(request != null && sessionId != null){
            session = (Session) request.getAttribute(sessionId.toString());
            if(session == null){
                session = super.retrieveSession(sessionKey);
                request.setAttribute(sessionId.toString() , session);
            }
        }
        return (Session) request.getAttribute(sessionId.toString());

        /**
         * 当前的做法是把session放到request内，减少在同一个request时redis的访问次数，但也存在一个问题:
         * 在当前request生命周期内，如果我修改了session的数据，不能立刻生效，而要等下一个request才能生效
         */
    }
}
