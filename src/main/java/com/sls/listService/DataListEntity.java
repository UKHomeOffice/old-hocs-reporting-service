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
@EqualsAndHashCode(of = {"text", "value"})
public class DataListEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "list_id")
    private Long listId;

    @Column(name = "text", nullable = false)
    @Getter
    private String text;

    @Column(name = "value", nullable = false)
    @Getter
    private String value;

    @Column(name = "parent_entity_id")
    private Long parentId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="parent_entity_id", referencedColumnName = "id")
    @Getter
    private Set<DataListEntity> subEntities;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="entity_id", referencedColumnName = "id")
    @Getter
    private Set<DataListEntityProperty> properties;


    public DataListEntity(String text, String value, Set<DataListEntity> subEntities, Set<DataListEntityProperty> properties) {
        this.text = text;
        this.value = value;
        this.subEntities = subEntities;
        this.properties = properties;
    }

    public DataListEntity(String text, String value, Set<DataListEntity> subEntities) {
        this.text = text;
        this.value = value;
        this.subEntities = subEntities;
    }

    public DataListEntity(String text, String value) {
        this.text = text;
        this.value = value;
    }
}