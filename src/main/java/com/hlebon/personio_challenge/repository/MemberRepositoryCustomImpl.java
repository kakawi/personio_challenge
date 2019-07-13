package com.hlebon.personio_challenge.repository;

import com.hlebon.personio_challenge.repository.entity.ConnectionEntity;
import com.hlebon.personio_challenge.repository.entity.MemberEntity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

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

    @Override
    public ConnectionEntity getConnectionByEmployeeName(String name) {
        MemberEntity employee = entityManager.createQuery("SELECT m FROM MemberEntity m WHERE m.name = :name", MemberEntity.class)
                .setParameter("name", name)
                .getSingleResult();

        List<ConnectionEntity> connectionEntity = entityManager.createQuery("SELECT c FROM ConnectionEntity c WHERE c.employee = :employee", ConnectionEntity.class)
                    .setParameter("employee", employee)
                    .getResultList();
        if (connectionEntity.isEmpty()) {
            return null;
        }
        return connectionEntity.get(0);
    }
}
