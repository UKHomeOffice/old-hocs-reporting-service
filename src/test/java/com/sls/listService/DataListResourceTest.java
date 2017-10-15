package com.sls.listService;

import com.sls.listService.dto.DataListRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataListResourceTest {

    @Mock
    private ListRepository mockRepo;
    private ListResource resource;

    @Before
    public void setUp() {
        resource = new ListResource(mockRepo);
    }

    @Test
    public void testCollaboratorsGettingAudit() {
        when(mockRepo.findOneByReference("Test List One")).thenReturn(buildDataList());

        DataListRecord dataListRecord = resource.getListByReference("Test List One");

        verify(mockRepo).findOneByReference("Test List One");

        assertThat(dataListRecord.getReference()).isEqualTo("Test List One");
        assertThat(dataListRecord.getEntities()).size().isEqualTo(1);
        assertThat(dataListRecord.getEntities().get(0).getReference()).isEqualTo("ref");
        assertThat(dataListRecord.getEntities().get(0).getValue()).isEqualTo("Value");
    }

    private static DataList buildDataList() {
        List<DataListEntity> dle = new ArrayList<>();
        dle.add(new DataListEntity("Value", "ref"));
        return createList("Test List One", dle);
    }

    private static DataList createList(String reference, List<DataListEntity> listEntities) {
        DataList dataList = new DataList(reference, listEntities);
        return dataList;
    }
}