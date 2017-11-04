package uk.gov.digital.ho.hocs.dto.legacy.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.digital.ho.hocs.model.User;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class UserCreateRecord {
    private List<UserCreateEntityRecord> users;

    public static UserCreateRecord create(List<User> list) {
        List<UserCreateEntityRecord> users = list.stream().map(UserCreateEntityRecord::createUser).collect(Collectors.toList());
        return new UserCreateRecord(users);
    }
}