package com.hlebon.personio_challenge.service;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;

public interface CompanyService {

    Member getRoot(Collection<Pair<Member, Member>> pairs) throws ServiceException;

    List<Member> getHierarchyByName(String name);
}
