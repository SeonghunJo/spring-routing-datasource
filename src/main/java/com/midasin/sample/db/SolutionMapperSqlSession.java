package com.midasin.sample.db;

import org.apache.ibatis.session.SqlSession;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;

/**
 * Solution DB에 대해 sqlSession 과 mapper 를 설정해주는 Dao
 * 
 * @author jsh1026
 * @date 2018-12-11 17:19
 */
public class SolutionMapperSqlSession<Mapper> {
    @Resource(name="sqlSession")
    protected SqlSession sqlSession;

    protected Mapper mapper = null;

    public static ParameterizedType getSuperParameterizedType(Object obj) {
        Class c = obj.getClass();
        while (!(c.getGenericSuperclass() instanceof ParameterizedType)) {
            c = c.getSuperclass();
            if (c == null) return null;
        }
        return (ParameterizedType)c.getGenericSuperclass();
    }

    @SuppressWarnings("unchecked")
    private Class<Mapper> getMapperClass() {
        return (Class<Mapper>) getSuperParameterizedType(this).getActualTypeArguments()[0];
    }

    @PostConstruct
    protected void initializeMapper() {
        mapper = sqlSession.getMapper(getMapperClass());
    }
}
