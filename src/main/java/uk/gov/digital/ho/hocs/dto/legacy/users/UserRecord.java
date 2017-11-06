package uk.gov.digital.ho.hocs.dto.legacy.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.digital.ho.hocs.model.User;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class UserRecord {
    private List<UserEntityRecord> users;

    public static UserRecord create(List<User> list) {
        List<UserEntityRecord> users = list.stream().map(UserEntityRecord::create).collect(Collectors.toList());
        return new UserRecord(users);
    }
}