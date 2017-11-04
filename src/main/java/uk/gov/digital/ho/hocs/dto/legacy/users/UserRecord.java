package uk.gov.digital.ho.hocs.dto.legacy.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.DataListEntity;
import uk.gov.digital.ho.hocs.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserRecord {
    private List<UserEntityRecord> users;

    public static UserRecord create(List<User> list) {
        List<UserEntityRecord> users = new ArrayList<>();

        if (list != null && !list.isEmpty()) {
            users = list.stream().map(UserEntityRecord::createUser).collect(Collectors.toList());
        }
        return new UserRecord(users);
    }
}