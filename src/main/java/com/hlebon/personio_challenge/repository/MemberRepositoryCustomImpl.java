package com.hlebon.personio_challenge.repository;

import com.hlebon.personio_challenge.repository.entity.MemberEntity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final EntityManager entityManager;

    public MemberRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveConnection(MemberEntity employee, MemberEntity supervisor) {
        entityManager
                .createNativeQuery("INSERT INTO company (employee_id, supervisor_id) VALUES (?, ?)")
                .setParameter(1, employee.getId())
                .setParameter(2, supervisor.getId())
                .executeUpdate();
    }

    @Override
    @Transactional
    public void clearConnections() {
        entityManager
                .createNativeQuery("DELETE FROM company")
                .executeUpdate();
    }
}
