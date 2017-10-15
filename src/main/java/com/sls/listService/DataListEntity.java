package com.sls.listService;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "entities")
@Access(AccessType.FIELD)
@NoArgsConstructor
@EqualsAndHashCode(of = "value, reference")
public class DataListEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "list_id", nullable = false)
    @Getter
    private DataList listId;

    @Column(name = "value", nullable = false)
    @Getter
    private String value;

    @Column(name = "reference", nullable = false)
    @Getter
    private String reference;

    @Column(name = "parent_entity_id", nullable = true)
    @OneToMany(mappedBy = "id")
    @Getter
    private List<DataListEntity> subEntities;

    public DataListEntity(String value, String reference) {
        this.value = value;
        this.reference = reference;
    }

    public DataListEntity(String value, String reference, List<DataListEntity> subEntities) {
        this.value = value;
        this.reference = reference;
        this.subEntities = subEntities;
    }
}
