package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TopicsServiceTest {

    private final static String TEST_LIST = "Test List One";
    private final static String UNAVAILABLE_RESOURCE = "Unavailable Resource";

    @Mock
    private TopicsRepository mockRepo;

    private TopicsService topicsService;


    @Before
    public void setUp() {
        topicsService = new TopicsService(mockRepo);
    }
/*    @Test
    public void testCollaboratorsGettingLegacyList() throws ListNotFoundException {
        when(mockRepo.findOneByName(TEST_LIST)).thenReturn(DataListServiceTest.buildValidDataList());

        TopicGroupRecord dataListRecord = topicsService.getTopicByCaseType(TEST_LIST);

        verify(mockRepo).findOneByName(TEST_LIST);

        assertThat(dataListRecord).isNotNull();
        assertThat(dataListRecord.getTopics()).hasOnlyElementsOfType(TopicRecord.class);
        assertThat(dataListRecord.getTopics()).hasSize(1);
        assertThat(dataListRecord.getTopics().get(0).getName()).isEqualTo("Text");
        assertThat(dataListRecord.getTopics().get(0).getCaseType()).isEqualTo("CaseValue");

    }

    @Test(expected = ListNotFoundException.class)
    public void testLegacyListNotFoundThrowsListNotFoundException() throws ListNotFoundException {

        TopicGroupRecord dataListRecord = topicsService.getLegacyTopicListByName(UNAVAILABLE_RESOURCE);
        verify(mockRepo).findOneByName(UNAVAILABLE_RESOURCE);
        assertThat(dataListRecord).isNull();

    }*/

}