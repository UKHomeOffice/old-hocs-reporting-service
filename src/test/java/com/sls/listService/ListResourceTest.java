package com.sls.listService;

import com.sls.listService.dto.DataListRecord;
import com.sls.listService.dto.legacy.TopicListEntityRecord;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListResourceTest {

    private static final String TEST_REFERENCE = "Reference";

    @Mock
    private ListService listService;
    @Mock
    private LegacyService legacyService;

    private ListResource listResource;

    @Before
    public void setUp() {
        listResource = new ListResource(listService, legacyService);
    }

    @Test
    public void shouldRetrieveAllEntities() throws IOException, JSONException, ListNotFoundException {

        DataListRecord dataList = new DataListRecord(TEST_REFERENCE, new ArrayList<>());

        when(listService.getListByName(TEST_REFERENCE)).thenReturn(dataList);
        ResponseEntity<DataListRecord> httpResponse = listResource.getListByReference(TEST_REFERENCE);

        assertThat(httpResponse.getBody()).isEqualTo(dataList);
        assertThat(httpResponse.getBody().getName()).isEqualTo(TEST_REFERENCE);
        assertThat(httpResponse.getBody().getEntities()).isEmpty();

    }

    @Test
    public void shouldReturnNotFoundWhenUnableToFindEntity() throws ListNotFoundException {

        when(listService.getListByName(TEST_REFERENCE)).thenThrow(new ListNotFoundException());
        ResponseEntity<DataListRecord> httpResponse = listResource.getListByReference(TEST_REFERENCE);

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();

    }

    @Test
    public void shouldReturnNotFoundWhenUnableToFindLegacyUKVIEntity() throws ListNotFoundException {

        when(listService.getLegacyTopicListByName("TopicListUKVI")).thenThrow(new ListNotFoundException());
        ResponseEntity<TopicListEntityRecord[]> httpResponse = listResource.getLegacyListByReference();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();

    }

    @Test
    public void shouldReturnNotFoundWhenUnableToFindLegacyDCUEntity() throws ListNotFoundException {

        when(listService.getLegacyTopicListByName("TopicListDCU")).thenThrow(new ListNotFoundException());
        ResponseEntity<TopicListEntityRecord[]> httpResponse = listResource.getLegacyListByReference();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();

    }

    @Test
    public void shouldReturnBadRequestWhenUnableCreate() throws EntityCreationException {

        DataList emptyDataList = new DataList();

        doThrow(new EntityCreationException("")).when(listService).createList(emptyDataList);
        ResponseEntity httpResponse = listResource.createList(emptyDataList);

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }
}
