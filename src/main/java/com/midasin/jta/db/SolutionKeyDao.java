package com.midasin.jta.db;

/**
 * 키가 존재하는 Dao는 해당 클래스를 상속받아 사용한다.
 *
 * @param <Mapper> Mapper
 * @param <Key> Key
 * @param <Record> Record
 * @author jsh1026
 * @date 2018-12-11 17:19
 */
public abstract class SolutionKeyDao<Mapper, Key, Record> extends SolutionMapperSqlSession<Mapper> implements IKeyDao<Key, Record> {

}