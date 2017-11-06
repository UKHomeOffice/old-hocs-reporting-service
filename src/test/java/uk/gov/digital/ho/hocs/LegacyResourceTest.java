package uk.gov.digital.ho.hocs;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.digital.ho.hocs.dto.legacy.topics.TopicEntityRecord;
import uk.gov.digital.ho.hocs.dto.legacy.topics.TopicRecord;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;
import uk.gov.digital.ho.hocs.model.DataList;
import uk.gov.digital.ho.hocs.model.DataListEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LegacyResourceTest {

    @Mock
    private DataListResource dataListResource;

    @Mock
    private LegacyService legacyService;

    private LegacyResource legacyResource;

    @Before
    public void setUp() {
        legacyResource = new LegacyResource(dataListResource, legacyService);
    }

    @Test
    public void shouldRetrieveAllEntitiesLegacy() throws IOException, JSONException, ListNotFoundException {
        Set<DataListEntity> topicList = new HashSet<>();
        topicList.add(new DataListEntity());
        DataList dataList = new DataList("TEST List", topicList);
        TopicRecord record = TopicRecord.create(dataList);

        when(legacyService.getLegacyTopicListByName("DCU_Topics")).thenReturn(record);
        when(legacyService.getLegacyTopicListByName("UKVI_Topics")).thenReturn(record);
        ResponseEntity<List<TopicEntityRecord>> httpResponse = legacyResource.getLegacyListByReference();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).hasSize(2);

    }

    @Test
    public void shouldReturnNotFoundWhenUnableToFindLegacyUKVIEntity() throws ListNotFoundException {
        TopicRecord dataList = new TopicRecord(new ArrayList<>());

        when(legacyService.getLegacyTopicListByName("UKVI_Topics")).thenThrow(new ListNotFoundException());
        when(legacyService.getLegacyTopicListByName("DCU_Topics")).thenReturn(dataList);
        ResponseEntity<List<TopicEntityRecord>> httpResponse = legacyResource.getLegacyListByReference();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();

    }

    @Test
    public void shouldReturnNotFoundWhenUnableToFindLegacyDCUEntity() throws ListNotFoundException {
        TopicRecord dataList = new TopicRecord(new ArrayList<>());

        when(legacyService.getLegacyTopicListByName("UKVI_Topics")).thenReturn(dataList);
        when(legacyService.getLegacyTopicListByName("DCU_Topics")).thenThrow(new ListNotFoundException());
        ResponseEntity<List<TopicEntityRecord>> httpResponse = legacyResource.getLegacyListByReference();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();

    }
}