package com.hlebon.personio_challenge.repository;

import com.hlebon.personio_challenge.repository.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao extends JpaRepository<MemberEntity, Integer>, MemberRepositoryCustom {
    MemberEntity findByName(String name);
}
