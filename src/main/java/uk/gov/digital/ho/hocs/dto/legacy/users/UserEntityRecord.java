package uk.gov.digital.ho.hocs.dto.legacy.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.User;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class UserEntityRecord {

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    public static UserEntityRecord createUser(User user) {
        return new UserEntityRecord(user.getUserName(), user.getFirstName(), user.getLastName(), user.getEmailAddress());
    }
}
