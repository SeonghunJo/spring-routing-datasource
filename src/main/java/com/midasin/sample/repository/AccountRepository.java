package com.midasin.sample.repository;

import com.midasin.sample.db.SolutionKeyDao;
import com.midasin.sample.db.generate.mapper.AccountMapper;
import com.midasin.sample.db.generate.model.Account;
import com.midasin.sample.db.generate.model.AccountExample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository extends SolutionKeyDao<AccountMapper, Long, Account> {

    @Override
    public Account find(Long key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public boolean update(Account data) {
        return mapper.updateByPrimaryKey(data) > 0;
    }

    @Override
    public boolean delete(Long key) {
        return mapper.deleteByPrimaryKey(key) > 0;
    }

    @Override
    public boolean create(Account data) {
        return mapper.insert(data) > 0;
    }

    @Override
    public boolean createSelective(Account data) {
        return mapper.insertSelective(data) > 0;
    }

    @Override
    public boolean updateSelective(Account data) {
        return mapper.updateByPrimaryKeySelective(data) > 0;
    }

    public List<Account> findAll() {
        AccountExample example = new AccountExample();
        return mapper.selectByExample(example);
    }
}
