package com.hdfclife.desk.store;

import com.hdfclife.desk.model.Policy;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryPolicyStore implements PolicyStore{
    private final List<Policy> policies = new ArrayList<>();

    @Override
    public void add(Policy policy) {
        policies.add(policy);
    }

    @Override
    public List<Policy> findAll() {
        return new ArrayList<>(policies);
    }

    @Override
    public Optional<Policy> findByPolicyNo(String policyNo) {
        return policies.stream()
                .filter(policy -> policy.getPolicyNo().equals(policyNo))
                .findFirst();
    }

    @Override
    public void update(Policy policy) {

        for (int i = 0; i < policies.size(); i++) {

            if (policies.get(i)
                    .getPolicyNo()
                    .equals(policy.getPolicyNo())) {

                policies.set(i, policy);
                return;
            }
        }
    }

    @Override
    public void delete(String policyNo) {
        policies.removeIf(
                policy -> policy.getPolicyNo().equals(policyNo)
        );
    }

    @Override
    public long count() {
        return policies.size();
    }
}
