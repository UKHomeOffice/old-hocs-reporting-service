package uk.gov.digital.ho.hocs.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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
    @Setter
    private Set<DataListEntity> subEntities = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name ="entity_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Set<DataListEntityProperty> properties = new HashSet<>();

    public DataListEntity(String text) {
        this(text, text);
    }

    public DataListEntity(String text, String value){
        this.text = toListText(text);
        this.value = toListValue(value);
    }

    private static String toListText(String text) {
        text = text.startsWith("\"") ? text.substring(1) : text;
        text = text.endsWith("\"") ? text.substring(0, text.length() - 1) : text;
        return text;
    }

    private static String toListValue(String value) {
        return value.replaceAll(" ", "_")
                .replaceAll("[^a-zA-Z0-9_]+", "")
                .replaceAll("__", "_")
                .toUpperCase();
    }
}