package com.hdfclife.desk.service;

import com.hdfclife.desk.exception.DuplicatePolicyException;
import com.hdfclife.desk.exception.PolicyNotFoundException;
import com.hdfclife.desk.model.Policy;
import com.hdfclife.desk.store.PolicyStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {
    private final PolicyStore policyStore;

    public PolicyService(PolicyStore policyStore) {
        this.policyStore = policyStore;
    }

    public List<Policy> getAllPolicies() {
        return policyStore.findAll();
    }

    public Policy getPolicy(String policyNo) {

        return policyStore.findByPolicyNo(policyNo)
                .orElseThrow(() ->
                        new PolicyNotFoundException(
                                "Policy not found: " + policyNo
                        )
                );
    }

    public List<Policy> findPolicies(String status, String type) {

        return policyStore.findAll()
                .stream()
                .filter(policy ->
                        status == null ||
                                policy.getStatus().equals(status)
                )
                .filter(policy ->
                        type == null ||
                                policy.getType().equals(type)
                )
                .toList();
    }

    public Policy createPolicy(Policy policy) {

        if (policyStore.findByPolicyNo(
                policy.getPolicyNo()
        ).isPresent()) {

            throw new DuplicatePolicyException(
                    "Policy already exists: " +
                            policy.getPolicyNo()
            );
        }

        policyStore.add(policy);

        return policy;
    }

    public Policy updatePolicy(
            String policyNo,
            Policy policy) {

        getPolicy(policyNo);

        Policy updatedPolicy = new Policy(
                policyNo,
                policy.getCustomer(),
                policy.getType(),
                policy.getBasePremium(),
                policy.getStatus()
        );

        policyStore.update(updatedPolicy);

        return updatedPolicy;
    }

    public void deletePolicy(String policyNo) {

        getPolicy(policyNo);

        policyStore.delete(policyNo);
    }

    public long countActivePolicies() {

        return policyStore.findAll()
                .stream()
                .filter(policy ->
                        policy.getStatus().equals("Active")
                )
                .count();
    }

    public long countByType(String type) {

        return policyStore.findAll()
                .stream()
                .filter(policy ->
                        policy.getType().equals(type)
                )
                .count();
    }

    public long countUniqueCustomers() {

        return policyStore.findAll()
                .stream()
                .map(Policy::getCustomer)
                .distinct()
                .count();
    }
}
