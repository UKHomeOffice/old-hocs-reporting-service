package com.sls.listService;

import com.sls.listService.dto.DataListEntityRecordProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "entities")
@Access(AccessType.FIELD)
@NoArgsConstructor
@EqualsAndHashCode(of = {"value", "reference"})
public class DataListEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "list_id")
    @Getter
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
    private List<DataListEntity> subEntities;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="entity_id", referencedColumnName = "id")
    @Getter
    private List<DataListEntityProperties> properties;

    public DataListEntity(String value, String reference) {
        this.value = value;
        this.reference = reference;
    }

    public DataListEntity(String value, String reference, List<DataListEntity> subEntities, List<DataListEntityProperties> properties) {
        this.value = value;
        this.reference = reference;
        this.subEntities = subEntities;
        this.properties = properties;

   }
}
