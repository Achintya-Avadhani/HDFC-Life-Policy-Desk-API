package com.hdfclife.desk.store;

import com.hdfclife.desk.model.Policy;

import java.util.List;
import java.util.Optional;

public interface PolicyStore {
    void add(Policy policy);

    List<Policy> findAll();

    Optional<Policy> findByPolicyNo(String policyNo);

    void update(Policy policy);

    void delete(String policyNo);

    long count();
}
