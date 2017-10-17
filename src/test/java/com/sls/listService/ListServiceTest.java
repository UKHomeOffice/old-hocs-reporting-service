package com.sls.listService;

import com.sls.listService.dto.DataListRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListServiceTest {

    @Mock
    private ListRepository mockRepo;
    private ListService service;

    @Before
    public void setUp() {
        service = new ListService(mockRepo);
    }

    private static DataList buildDataList() {
        Set<DataListEntity> dle = new HashSet<>();
        dle.add(new DataListEntity("Text", "Value"));
        return new DataList("Test List One", dle);
    }

    @Test
    public void testCollaboratorsGettingList() throws ListNotFoundException {
        when(mockRepo.findOneByName("Test List One")).thenReturn(buildDataList());

        DataListRecord dataListRecord = service.getListByName("Test List One");

        verify(mockRepo).findOneByName("Test List One");

        assertThat(dataListRecord.getName()).isEqualTo("Test List One");
        assertThat(dataListRecord.getEntities()).size().isEqualTo(1);
        assertThat(dataListRecord.getEntities().get(0).getText()).isEqualTo("Text");
        assertThat(dataListRecord.getEntities().get(0).getValue()).isEqualTo("Value");
    }

}