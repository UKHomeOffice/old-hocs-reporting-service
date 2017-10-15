package com.sls.listService;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lists")
@Access(AccessType.FIELD)
@NoArgsConstructor
@EqualsAndHashCode(of = "reference")
public class DataList {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference", nullable = false)
    @Getter
    private String reference;

    @OneToMany(mappedBy = "listId")
    @Getter
    private List<DataListEntity> entities;


    public DataList(String reference, List<DataListEntity> listEntities) {
        this.reference = reference;
        this.entities = listEntities;
    }
}
