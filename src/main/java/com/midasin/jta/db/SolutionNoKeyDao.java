package com.midasin.jta.db;

/**
 * 키가 없는 DAO일 경우 해당 클래스를 상속받아 구현한다
 *
 * @param <Mapper> Mapper
 * @param <Record> Record
 * @author jsh1026
 * @date 2018-12-11 17:19
 */
public abstract class SolutionNoKeyDao<Mapper, Record> extends SolutionMapperSqlSession<Mapper> implements IDao<Record> {

}
