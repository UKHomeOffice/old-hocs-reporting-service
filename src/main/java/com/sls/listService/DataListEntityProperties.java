package com.sls.listService;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "list_properties")
@Access(AccessType.FIELD)
@NoArgsConstructor
@EqualsAndHashCode(of = {"property", "value"})
public class DataListEntityProperties {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "entity_id")
    @Getter
    private long dataListEntity;

    @Column(name = "property")
    @Getter
    private String property;

    @Column(name = "value")
    @Getter
    private String value;

    public DataListEntityProperties(String property, String value)
    {
        this.property = property;
        this.value = value;
    }

}
