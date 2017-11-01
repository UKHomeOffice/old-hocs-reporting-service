package uk.gov.digital.ho.hocs.dto.legacy.users;

import uk.gov.digital.ho.hocs.DataList;
import uk.gov.digital.ho.hocs.DataListEntity;
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
        return UserCreateEntityRecord.createUser(user);
    }

    private static UserCreateEntityRecord createTestUser(DataListEntity user) {
        return UserCreateEntityRecord.createTestUser(user);
    }
}