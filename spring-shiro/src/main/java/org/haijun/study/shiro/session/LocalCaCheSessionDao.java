package org.haijun.study.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import java.io.Serializable;

/**
 * 在redis基础上增加本地cache缓存
 */
public class LocalCaCheSessionDao extends CachingSessionDAO {

    private RedisSessionDao redisSessionDao;

    @Override
    protected void doUpdate(Session session) {
        redisSessionDao.doUpdate(session);
    }

    @Override
    protected void doDelete(Session session) {
        redisSessionDao.doDelete(session);
    }

    @Override
    protected Serializable doCreate(Session session) {
        return redisSessionDao.doCreate(session);
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        return redisSessionDao.doReadSession(serializable);
    }


    public RedisSessionDao getRedisSessionDao() {
        return redisSessionDao;
    }

    public void setRedisSessionDao(RedisSessionDao redisSessionDao) {
        this.redisSessionDao = redisSessionDao;
    }
}
