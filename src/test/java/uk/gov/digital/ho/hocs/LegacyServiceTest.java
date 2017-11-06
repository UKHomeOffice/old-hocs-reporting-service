package uk.gov.digital.ho.hocs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.digital.ho.hocs.dto.legacy.topics.TopicEntityRecord;
import uk.gov.digital.ho.hocs.dto.legacy.topics.TopicRecord;
import uk.gov.digital.ho.hocs.exception.ListNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LegacyServiceTest {

    private final static String TEST_LIST = "Test List One";
    private final static String UNAVAILABLE_RESOURCE = "Unavailable Resource";

    @Mock
    private DataListRepository mockRepo;

    private LegacyService legacyService;


    @Before
    public void setUp() {
        legacyService = new LegacyService(mockRepo);
    }
    @Test
    public void testCollaboratorsGettingLegacyList() throws ListNotFoundException {
        when(mockRepo.findOneByName(TEST_LIST)).thenReturn(DataListServiceTest.buildValidDataList());

        TopicRecord dataListRecord = legacyService.getLegacyTopicListByName(TEST_LIST);

        verify(mockRepo).findOneByName(TEST_LIST);

        assertThat(dataListRecord).isNotNull();
        assertThat(dataListRecord.getTopics()).hasOnlyElementsOfType(TopicEntityRecord.class);
        assertThat(dataListRecord.getTopics()).hasSize(1);
        assertThat(dataListRecord.getTopics().get(0).getName()).isEqualTo("Text");
        assertThat(dataListRecord.getTopics().get(0).getCaseType()).isEqualTo("CaseValue");

    }

    @Test(expected = ListNotFoundException.class)
    public void testLegacyListNotFoundThrowsListNotFoundException() throws ListNotFoundException {

        TopicRecord dataListRecord = legacyService.getLegacyTopicListByName(UNAVAILABLE_RESOURCE);
        verify(mockRepo).findOneByName(UNAVAILABLE_RESOURCE);
        assertThat(dataListRecord).isNull();

    }

}