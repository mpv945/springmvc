package org.haijun.study.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/*我们就可以在此基础上进行session的共享了，其实只需要继承EnterpriseCacheSessionDAO（其实继承CachingSessionDAO就可以了，
        但是这里考虑到集群中每次都访问数据库导致开销过大，这里在本地使用ehcache进行缓存，
        每次读取session的时候都先尝试读取本地ehcache缓存，没有的话再去远程redis数据库中读取），
        然后覆盖原来的增删改查操作，这样多个服务器就共享了session，*/
public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    private RedisManager redisManager;

    private static final String KEY_PREFIX = "shiro_redis_session:";

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        this.saveSession(session, sessionId);
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if(session == null) {
            session = (Session)redisManager.get(KEY_PREFIX + sessionId);
        }
        return session;
    }

    //更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        Serializable sessionId = this.generateSessionId(session);
        this.saveSession(session, sessionId);
    }

    // 删除session
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        redisManager.del(KEY_PREFIX + session.getId());
    }

    // 保存Session
    private void saveSession(Session session,Serializable sessionId) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            //logger.error("session or session id is null");
            return;
        }
        //设置过期时间
        long expireTime = 1800000l;
        session.setTimeout(expireTime);
        redisManager.setEx(KEY_PREFIX + sessionId, session, expireTime);
    }

/*    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<>();
        Set<byte[]> keys = redisManager.keys(KEY_PREFIX + "*");
        if(keys != null && keys.size()>0){
            for(byte[] key : keys){
                Session s = (Session)SerializerUtil.deserialize(redisManager.get(SerializerUtil.deserialize(key)));
                sessions.add(s);
            }
        }
        return sessions;
    }*/

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }
}
