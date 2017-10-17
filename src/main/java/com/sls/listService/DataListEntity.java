package com.sls.listService;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "entities")
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"value", "reference"})
public class DataListEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "list_id")
    private Long listId;

    @Column(name = "value", nullable = false)
    @Getter
    private String value;

    @Column(name = "reference", nullable = false)
    @Getter
    private String reference;

    @Column(name = "parent_entity_id")
    @Getter
    private Long parentId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="parent_entity_id", referencedColumnName = "id")
    @Getter
    private Set<DataListEntity> subEntities;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="entity_id", referencedColumnName = "id")
    @Getter
    private Set<DataListEntityProperties> properties;

    public DataListEntity(String value, String reference, Set<DataListEntity> subEntities, Set<DataListEntityProperties> properties) {
        this.value = value;
        this.reference = reference;
        this.subEntities = subEntities;
        this.properties = properties;
    }

    public DataListEntity(String value, String reference, Set<DataListEntity> subEntities) {
        this.value = value;
        this.reference = reference;
        this.subEntities = subEntities;
    }

    public DataListEntity(String value, String reference) {
        this.value = value;
        this.reference = reference;
    }
}
