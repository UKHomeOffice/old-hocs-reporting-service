package com.sls.listService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@Profile("logtoconsole")
public class ListRepositoryTest {

    @Autowired
    private ListRepository repository;

    @Before
    public void setup() {
        repository.deleteAll();
        Set<DataListEntity> firstEntityList = new HashSet<>();
        firstEntityList.add(new DataListEntity("Value","ref"));
        repository.save(new DataList("Test List One", firstEntityList ));

        Set<DataListEntity> secondSubEntityList = new HashSet<>();
        secondSubEntityList.add(new DataListEntity("SubValue","sub_ref"));

        Set<DataListEntity> secondEntityList = new HashSet<>();
        secondEntityList.add(new DataListEntity("SecondValue","second_ref",secondSubEntityList));
        repository.save(new DataList("Test List Two", secondEntityList));
        repository.save(new DataList("Test List Three", null));
    }

    @Test
    public void shouldRetrieveAllAudit() {
        final Iterable<DataList> all = repository.findAll();
        assertThat(all).size().isEqualTo(3);
    }

    @Test
    public void shouldRetrieveListByName() {
        final DataList dataList = repository.findOneByReference("Test List One");
        assertThat(dataList.getReference()).isEqualTo("Test List One");
    }

    @Test
    public void shouldRetrieveListByNameNotFound() {
        final DataList dataList = repository.findOneByReference("Test List Five");
        assertThat(dataList).isNull();
    }

    @Test
    public void shouldRetrieveListEntities() {
        final DataList dataList = repository.findOneByReference("Test List One");
        assertThat(dataList.getReference()).isEqualTo("Test List One");
        assertThat(dataList.getEntities()).size().isEqualTo(1);
        assertThat(asList(dataList.getEntities()).get(0).getReference()).isEqualTo("ref");
        assertThat(asList(dataList.getEntities()).get(0).getValue()).isEqualTo("Value");
    }

    @Test
    public void shouldRetrieveListSubEntities() {
        final DataList dataList = repository.findOneByReference("Test List Two");
        assertThat(dataList.getReference()).isEqualTo("Test List Two");
        assertThat(dataList.getEntities()).size().isEqualTo(1);

        DataListEntity dataListEntityOne = asList(dataList.getEntities()).get(0);
        assertThat(dataListEntityOne.getReference()).isEqualTo("second_ref");
        assertThat(dataListEntityOne.getValue()).isEqualTo("SecondValue");

        assertThat(dataListEntityOne.getSubEntities()).size().isEqualTo(1);
        DataListEntity dataListEntitySub = asList(dataListEntityOne.getSubEntities()).get(0);
        assertThat(dataListEntitySub.getReference()).isEqualTo("sub_ref");
        assertThat(dataListEntitySub.getValue()).isEqualTo("SubValue");
    }

    @Test
    public void shouldRetrieveListEntitiesNone() {
        final DataList dataList = repository.findOneByReference("Test List Three");
        assertThat(dataList.getReference()).isEqualTo("Test List Three");
        assertThat(dataList.getEntities()).isNull();
    }

    private static List<DataListEntity> asList(Set<DataListEntity> set)
    {
        return set.stream().collect(Collectors.toList());
    }
}