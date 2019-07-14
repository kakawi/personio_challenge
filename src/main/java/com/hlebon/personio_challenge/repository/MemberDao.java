package com.hlebon.personio_challenge.repository;

import com.hlebon.personio_challenge.repository.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<MemberEntity, Integer> {

    MemberEntity findByName(String name);

}
