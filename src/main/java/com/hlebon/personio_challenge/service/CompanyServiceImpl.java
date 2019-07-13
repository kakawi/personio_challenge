package com.hlebon.personio_challenge.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Override
    public Member getRoot(Collection<Pair<Member, Member>> pairs) throws ServiceException {
        Company company = new Company();
        for (Pair<Member, Member> pair : pairs) {
            company.unitMembers(pair.getLeft(), pair.getRight());
        }
        return company.getRoot();
    }
}
