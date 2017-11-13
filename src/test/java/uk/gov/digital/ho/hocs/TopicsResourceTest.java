package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TopicsResourceTest {

    @Mock
    private DataListResource dataListResource;

    @Mock
    private TopicsService topicsService;

    private TopicsResource topicsResource;

    @Before
    public void setUp() {
        topicsResource = new TopicsResource(topicsService);
    }

/*    @Test
    public void shouldRetrieveAllEntitiesLegacy() throws IOException, JSONException, ListNotFoundException {
        Set<DataListEntity> topicList = new HashSet<>();
        topicList.add(new DataListEntity());
        DataList dataList = new DataList("TEST List", topicList);
        TopicGroupRecord record = TopicGroupRecord.create(dataList);

        when(topicsService.getLegacyTopicListByName("DCU_Topics")).thenReturn(record);
        when(topicsService.getLegacyTopicListByName("UKVI_Topics")).thenReturn(record);
        ResponseEntity<List<TopicRecord>> httpResponse = topicsResource.getLegacyListByReference();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).hasSize(2);

    }

    @Test
    public void shouldReturnNotFoundWhenUnableToFindLegacyUKVIEntity() throws ListNotFoundException {
        TopicGroupRecord dataList = new TopicGroupRecord(new ArrayList<>());

        when(topicsService.getLegacyTopicListByName("UKVI_Topics")).thenThrow(new ListNotFoundException());
        when(topicsService.getLegacyTopicListByName("DCU_Topics")).thenReturn(dataList);
        ResponseEntity<List<TopicRecord>> httpResponse = topicsResource.getLegacyListByReference();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();

    }

    @Test
    public void shouldReturnNotFoundWhenUnableToFindLegacyDCUEntity() throws ListNotFoundException {
        TopicGroupRecord dataList = new TopicGroupRecord(new ArrayList<>());

        when(topicsService.getLegacyTopicListByName("UKVI_Topics")).thenReturn(dataList);
        when(topicsService.getLegacyTopicListByName("DCU_Topics")).thenThrow(new ListNotFoundException());
        ResponseEntity<List<TopicRecord>> httpResponse = topicsResource.getLegacyListByReference();

        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(httpResponse.getBody()).isNull();

    }*/
}