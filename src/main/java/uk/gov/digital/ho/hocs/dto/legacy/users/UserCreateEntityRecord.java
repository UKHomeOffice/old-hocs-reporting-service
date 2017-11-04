package uk.gov.digital.ho.hocs.dto.legacy.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.User;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class UserCreateEntityRecord {

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    // Passwords are expired when they are created
    private String password;

    private List<String> groupNameArray;

    public static UserCreateEntityRecord createUser(User user) {
        List<String> groups = user.getGroups().stream().map(g -> g.getReferenceName()).collect(Collectors.toList());
        return new UserCreateEntityRecord(user.getFirstName(), user.getLastName(), user.getUserName(), user.getEmailAddress(), "Password1", groups);
    }
}
