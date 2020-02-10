package com.midasin.sample.db;

/**
 * 키가 존재하는 Dao는 해당 클래스를 상속받아 사용한다.
 * 키가 존재하면서 Record와 별도로 RecordWithBlobs 타입이 있을 때 이 추상클래스를 사용한다
 *
 * @param <M> Mapper
 * @param <K> Key
 * @param <R> Record
 * @author jsh1026
 * @date 2018-12-11 17:19
 */
public abstract class SolutionKeyWithBlobsDao<M, K, R, RB> extends SolutionMapperSqlSession<M> implements IKeyWithBlobsDao<K, R, RB> {

}