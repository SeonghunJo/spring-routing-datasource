package com.midasin.sample.db;

/**
 * Record 타입 외에 RecordWithBlobs 타입을 따로 가지는 DAO는 해당 인터페이스를 사용하여 구현한다.
 *
 * select
 * update
 * delete
 *
 *
 * @author jsh1026
 * @date 2018-12-11 17:19
 */
public interface IKeyWithBlobsDao<Key, Record, RecordWithBlobs> extends IDao<RecordWithBlobs> {
    RecordWithBlobs find(Key key);

    boolean update(Record data);

    boolean updateWithBlobs(RecordWithBlobs data);

    default boolean isExist(Key key) {
        return find(key) != null;
    }

    boolean delete(Key key);
}
