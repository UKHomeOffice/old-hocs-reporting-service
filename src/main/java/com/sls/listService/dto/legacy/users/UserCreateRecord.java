package com.sls.listService.dto.legacy.users;

import com.sls.listService.DataList;
import com.sls.listService.DataListEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserCreateRecord {
    private List<UserCreateEntityRecord> users;

    public static UserCreateRecord create(DataList list) {
        List<UserCreateEntityRecord> users = new ArrayList<>();

        if (list.getEntities() != null && !list.getEntities().isEmpty()) {
            users = list.getEntities().stream().map(UserCreateRecord::createUser).collect(Collectors.toList());
        }
        return new UserCreateRecord(users);
    }

    public static UserCreateRecord createTest(DataList list) {
        List<UserCreateEntityRecord> users = new ArrayList<>();

        if (list.getEntities() != null && !list.getEntities().isEmpty()) {
            users = list.getEntities().stream().map(UserCreateRecord::createTestUser).collect(Collectors.toList());
        }
        return new UserCreateRecord(users);
    }

    private static UserCreateEntityRecord createUser(DataListEntity user) {
        return UserCreateEntityRecord.createUser(user.getValue(), user.getValue(), user.getProperties());
    }

    private static UserCreateEntityRecord createTestUser(DataListEntity user) {
        return UserCreateEntityRecord.createTestUser(user.getValue(), user.getProperties());
    }
}