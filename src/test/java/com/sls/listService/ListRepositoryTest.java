package com.sls.listService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@Profile("logtoconsole")
public class ListRepositoryTest {

    @Autowired
    private ListRepository repository;

    private static List<DataListEntity> asList(Set<DataListEntity> set) {
        return new ArrayList<>(set);
    }

    @Test
    public void shouldRetrieveAllAudit() {
        final Iterable<DataList> all = repository.findAll();
        assertThat(all).size().isEqualTo(3);
    }

    @Test
    public void shouldRetrieveListByName() {
        final DataList dataList = repository.findOneByName("Test List One");
        assertThat(dataList.getName()).isEqualTo("Test List One");
    }

    @Test
    public void shouldRetrieveListByNameNotFound() {
        final DataList dataList = repository.findOneByName("Test List Five");
        assertThat(dataList).isNull();
    }

    @Test
    public void shouldRetrieveListEntities() {
        final DataList dataList = repository.findOneByName("Test List One");
        assertThat(dataList.getName()).isEqualTo("Test List One");
        assertThat(dataList.getEntities()).size().isEqualTo(1);
        assertThat(asList(dataList.getEntities()).get(0).getText()).isEqualTo("Text");
        assertThat(asList(dataList.getEntities()).get(0).getValue()).isEqualTo("Value");
    }

    @Test
    public void shouldRetrieveListSubEntities() {
        final DataList dataList = repository.findOneByName("Test List Two");
        assertThat(dataList.getName()).isEqualTo("Test List Two");
        assertThat(dataList.getEntities()).size().isEqualTo(1);

        DataListEntity dataListEntityOne = asList(dataList.getEntities()).get(0);
        assertThat(dataListEntityOne.getText()).isEqualTo("SecondText");
        assertThat(dataListEntityOne.getValue()).isEqualTo("second_val");

        assertThat(dataListEntityOne.getSubEntities()).size().isEqualTo(1);
        DataListEntity dataListEntitySub = asList(dataListEntityOne.getSubEntities()).get(0);
        assertThat(dataListEntitySub.getText()).isEqualTo("SubText");
        assertThat(dataListEntitySub.getValue()).isEqualTo("sub_val");
    }

    @Test
    public void shouldRetrieveListEntitiesNone() {
        final DataList dataList = repository.findOneByName("Test List Three");
        assertThat(dataList.getName()).isEqualTo("Test List Three");
        assertThat(dataList.getEntities()).isNull();
    }

    @Before
    public void setup() {
        repository.deleteAll();

        Set<DataListEntity> firstEntityList = new HashSet<>();
        firstEntityList.add(new DataListEntity("Text", "Value"));
        repository.save(new DataList("Test List One", firstEntityList));

        Set<DataListEntity> secondSubEntityList = new HashSet<>();
        secondSubEntityList.add(new DataListEntity("SubText", "sub_val"));

        Set<DataListEntity> secondEntityList = new HashSet<>();
        secondEntityList.add(new DataListEntity("SecondText", "second_val", secondSubEntityList));
        repository.save(new DataList("Test List Two", secondEntityList));
        repository.save(new DataList("Test List Three", null));
    }
}