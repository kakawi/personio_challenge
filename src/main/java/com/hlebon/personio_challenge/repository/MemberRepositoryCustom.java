package com.hlebon.personio_challenge.repository;

import com.hlebon.personio_challenge.repository.entity.MemberEntity;

public interface MemberRepositoryCustom {
    void saveConnection(MemberEntity employee, MemberEntity supervisor);

    void clearConnections();
}
