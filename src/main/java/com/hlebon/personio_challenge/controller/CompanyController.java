package com.hlebon.personio_challenge.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hlebon.personio_challenge.service.CompanyService;
import com.hlebon.personio_challenge.service.Member;
import com.hlebon.personio_challenge.service.ServiceException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

@RestController("/")
public class CompanyController {

    private final static JsonObject EMPTY_OBJECT = new JsonObject();

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping(value = "saveHierarchy", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String saveHierarchy(HttpEntity<String> httpEntity) throws ControllerException {
        String json = httpEntity.getBody();
        if (json == null) {
            throw new ControllerException("Request can't be null");
        }

        JsonObject data = new JsonParser().parse(json).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = data.entrySet();
        Collection<Pair<Member, Member>> pairs = new LinkedList<>();
        for (Map.Entry<String, JsonElement> currentEntry : entries) {
            String employeeName = currentEntry.getKey();
            String supervisorName = currentEntry.getValue().getAsString();
            Member employee = new Member(employeeName);
            Member supervisor = new Member(supervisorName);
            pairs.add(Pair.of(employee, supervisor));
        }
        try {
            Member root = companyService.getRoot(pairs);

            JsonObject jsonObject = new JsonObject();
            jsonObject.add(root.getName(), generateEmployees(root));
            return jsonObject.toString();
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        }
    }

    private JsonElement generateEmployees(Member supervisor) {
        if (supervisor.getEmployees().isEmpty()) {
            return EMPTY_OBJECT;
        }
        JsonObject result = new JsonObject();
        for (Member employee : supervisor.getEmployees()) {
            result.add(employee.getName(), generateEmployees(employee));
        }
        return result;
    }
}
