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

    @Test
    public void testCollaboratorsGettingList() throws ListNotFoundException {
        when(mockRepo.findOneByReference("Test List One")).thenReturn(buildDataList());

        DataListRecord dataListRecord = service.getListByReference("Test List One");

        verify(mockRepo).findOneByReference("Test List One");

        assertThat(dataListRecord.getReference()).isEqualTo("Test List One");
        assertThat(dataListRecord.getEntities()).size().isEqualTo(1);
        assertThat(dataListRecord.getEntities().get(0).getReference()).isEqualTo("ref");
        assertThat(dataListRecord.getEntities().get(0).getValue()).isEqualTo("Value");
    }

    private static DataList buildDataList() {
        Set<DataListEntity> dle = new HashSet<>();
        dle.add(new DataListEntity("Value","ref"));
        return new DataList("Test List One", dle);
    }

}