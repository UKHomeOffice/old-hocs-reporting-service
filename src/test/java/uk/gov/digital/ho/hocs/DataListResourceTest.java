package uk.gov.digital.ho.hocs;

import uk.gov.digital.ho.hocs.dto.DataListRecord;
import uk.gov.digital.ho.hocs.dto.legacy.topics.TopicEntityRecord;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.digital.ho.hocs.exception.EntityCreationException;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;
import uk.gov.digital.ho.hocs.model.DataList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataListResourceTest {

    private static final String TEST_REFERENCE = "Reference";

    @Mock
    private DataListService dataListService;
    @Mock
    private LegacyService legacyService;

    private DataListResource dataListResource;

    @Before
    public void setUp() {
        dataListResource = new DataListResource(dataListService, legacyService);
    }

    @Test
    public void shouldRetrieveAllEntities() throws IOException, JSONException, ListNotFoundException {

        DataListRecord dataList = new DataListRecord(TEST_REFERENCE, new ArrayList<>());

        when(dataListService.getListByName(TEST_REFERENCE)).thenReturn(dataList);
        ResponseEntity<DataListRecord> httpResponse = dataListResource.getListByReference(TEST_REFERENCE);

        assertThat(httpResponse.getBody()).isEqualTo(dataList);
        assertThat(httpResponse.getBody().getName()).isEqualTo(TEST_REFERENCE);
        assertThat(httpResponse.getBody().getEntities()).isEmpty();

    }

    @Test
    public void shouldReturnNotFoundWhenUnableToFindEntity() throws ListNotFoundException {

        when(dataListService.getListByName(TEST_REFERENCE)).thenThrow(new ListNotFoundException());
        ResponseEntity<DataListRecord> httpResponse = dataListResource.getListByReference(TEST_REFERENCE);

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();

    }

    @Test
    public void shouldReturnNotFoundWhenUnableToFindLegacyUKVIEntity() throws ListNotFoundException {

        when(legacyService.getLegacyTopicListByName("UKVI_Topics")).thenThrow(new ListNotFoundException());
        ResponseEntity<TopicEntityRecord[]> httpResponse = dataListResource.getLegacyListByReference();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();

    }

    @Test
    public void shouldReturnNotFoundWhenUnableToFindLegacyDCUEntity() throws ListNotFoundException {

        when(legacyService.getLegacyTopicListByName("DCU_Topics")).thenThrow(new ListNotFoundException());
        ResponseEntity<TopicEntityRecord[]> httpResponse = dataListResource.getLegacyListByReference();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();

    }

    @Test
    public void shouldReturnBadRequestWhenUnableCreate() throws EntityCreationException {

        DataList emptyDataList = new DataList("", new HashSet<>());

        doThrow(new EntityCreationException("")).when(dataListService).createList(emptyDataList);
        ResponseEntity httpResponse = dataListResource.createList(emptyDataList);

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }
}
