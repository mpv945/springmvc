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

    //设置过期时间
    private static final long REDIS_EXPIRE_TIME = 1800;// redis 过期是按秒计算
    private static final long SESSION_EXPIRE_TIME = REDIS_EXPIRE_TIME*1000; //session是按毫秒过时的

    // 创建session，保存到数据库
    // 当有请求过来的时候，会有一个线程来对应响应，每次请求的对应的线程是不一样的，因为subject与线程绑定的，有的线程没有subject，会再次登录，并生成session，
    // 所以sessionid不一样，但是连接池是循环使用的，当我们多次请求的时候，你会发现sessionid有重复的，原因就是这个线程的subject之前有登录过。
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

        session.setTimeout(SESSION_EXPIRE_TIME);// 设置的最大时间，正负都可以，为负数时表示永不超时。时间单位是:ms(毫秒)，但是Shiro会把这个时间转成:s
        redisManager.setEx(KEY_PREFIX + sessionId, session, REDIS_EXPIRE_TIME);
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
