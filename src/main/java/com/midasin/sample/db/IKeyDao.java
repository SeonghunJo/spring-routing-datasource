package com.midasin.sample.db;

public interface IKeyDao<Key, Record> extends IDao<Record> {
    Record find(Key key);

    boolean update(Record data);

    default boolean isExist(Key key) {
        return find(key) != null;
    }

    boolean delete(Key key);
}
