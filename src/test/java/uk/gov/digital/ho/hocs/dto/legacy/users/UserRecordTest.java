package uk.gov.digital.ho.hocs.dto.legacy.users;

import org.junit.Test;
import uk.gov.digital.ho.hocs.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRecordTest {

    @Test
    public void createWithEntities() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        UserRecord record = UserRecord.create(userList);
        assertThat(record.getUsers()).hasSize(1);
    }

    @Test
    public void createWithoutEntities() throws Exception {
        List<User> userList = new ArrayList<>();
        UserRecord record = UserRecord.create(userList);
        assertThat(record.getUsers()).hasSize(0);
    }

}