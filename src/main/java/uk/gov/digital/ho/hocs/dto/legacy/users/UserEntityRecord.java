package uk.gov.digital.ho.hocs.dto.legacy.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.gov.digital.ho.hocs.model.DataListEntity;
import uk.gov.digital.ho.hocs.model.DataListEntityProperty;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class UserEntityRecord {

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    public static UserEntityRecord createUser(DataListEntity user) {

        Set<DataListEntityProperty> userProperties = user.getProperties();
        String userName =  user.getValue();
        String email = user.getValue();

        DataListEntityProperty firstName = userProperties.stream()
                .filter(p -> p.getKey().equals("firstName"))
                .findFirst().orElse(new DataListEntityProperty());

        DataListEntityProperty lastName = userProperties.stream()
                .filter(p -> p.getKey().equals("lastName"))
                .findFirst().orElse(new DataListEntityProperty());

        userProperties.remove(firstName);
        userProperties.remove(lastName);

        return new UserEntityRecord(userName, firstName.getValue(), lastName.getValue(), email);
    }
}
