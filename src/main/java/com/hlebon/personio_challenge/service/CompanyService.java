package com.hlebon.personio_challenge.service;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;

public interface CompanyService {

    Member getRoot(Collection<Pair<Member, Member>> pairs) throws ServiceException;

    Member getHierarchyByName(String name);
}
