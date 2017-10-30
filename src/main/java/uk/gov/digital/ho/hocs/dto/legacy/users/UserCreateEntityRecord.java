package uk.gov.digital.ho.hocs.dto.legacy.users;

import uk.gov.digital.ho.hocs.DataListEntityProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class UserCreateEntityRecord {

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    // Passwords are expired when they are created
    private String password;

    private List<String> groupNameArray;

    public static UserCreateEntityRecord createUser(String userName, String email, Set<DataListEntityProperty> userProperties) {
        DataListEntityProperty firstName = userProperties.stream()
                .filter(p -> p.getKey().equals("firstName"))
                .findFirst().orElse(new DataListEntityProperty());

        DataListEntityProperty lastName = userProperties.stream()
                .filter(p -> p.getKey().equals("lastName"))
                .findFirst().orElse(new DataListEntityProperty());

        userProperties.remove(firstName);
        userProperties.remove(lastName);

        List<String> groups = userProperties.stream().map(p -> p.getValue()).collect(Collectors.toList());

        return new UserCreateEntityRecord(userName, firstName.getValue(), lastName.getValue(), email, "Password1", groups);
    }

    public static UserCreateEntityRecord createTestUser(String email, Set<DataListEntityProperty> userProperties) {
        DataListEntityProperty firstName = userProperties.stream()
                .filter(p -> p.getKey().equals("firstName"))
                .findFirst().orElse(new DataListEntityProperty());

        DataListEntityProperty lastName = userProperties.stream()
                .filter(p -> p.getKey().equals("lastName"))
                .findFirst().orElse(new DataListEntityProperty());

        userProperties.remove(firstName);
        userProperties.remove(lastName);

        String testUserName = String.format("%s %s", firstName.getValue(), lastName.getValue());

        List<String> groups = userProperties.stream().map(p -> p.getValue()).collect(Collectors.toList());

        return new UserCreateEntityRecord(testUserName, firstName.getValue(), lastName.getValue(), email, "Password1", groups);
    }
}
