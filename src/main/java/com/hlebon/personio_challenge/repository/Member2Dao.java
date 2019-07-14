package com.hlebon.personio_challenge.repository;

import com.hlebon.personio_challenge.repository.entity.Member2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Member2Dao extends JpaRepository<Member2, Integer>, Member2RepositoryCustom {
    Member2 findByName(String name);
}
