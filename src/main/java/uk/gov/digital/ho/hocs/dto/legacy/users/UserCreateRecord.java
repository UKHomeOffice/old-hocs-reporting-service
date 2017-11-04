package uk.gov.digital.ho.hocs.dto.legacy.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class UserCreateRecord {
    private List<UserCreateEntityRecord> users;

    public static UserCreateRecord create(List<User> list) {
        List<UserCreateEntityRecord> users = new ArrayList<>();

        if (list != null && !list.isEmpty()) {
            users = list.stream().map(UserCreateEntityRecord::createUser).collect(Collectors.toList());
        }
        return new UserCreateRecord(users);
    }
}