package com.midasin.sample.db;

public interface IDao<Record> {

    boolean create(Record data);

    boolean createSelective(Record data);

    boolean updateSelective(Record data);
}
