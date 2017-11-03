package uk.gov.digital.ho.hocs.dto.legacy.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.DataList;
import uk.gov.digital.ho.hocs.model.DataListEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserRecord {
    private List<UserEntityRecord> users;

    public static UserRecord create(DataList list) {
        List<UserEntityRecord> users = new ArrayList<>();

        if (list.getEntities() != null && !list.getEntities().isEmpty()) {
            users = list.getEntities().stream().map(UserRecord::createUser).collect(Collectors.toList());
        }
        return new UserRecord(users);
    }

    private static UserEntityRecord createUser(DataListEntity user) {
        return UserEntityRecord.createUser(user);
    }
}