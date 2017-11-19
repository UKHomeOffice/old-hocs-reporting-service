package uk.gov.digital.ho.hocs.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "events")
@Access(AccessType.FIELD)
@NoArgsConstructor
public class ContactDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @Getter
    private String title;

    @Column(name = "forename")
    @Getter
    private String forename;

    @Column(name = "surname")
    @Getter
    private String surname;

    @Column(name = "organisation")
    @Getter
    private String organisation;

    @Column(name = "telephone")
    @Getter
    private String telephone;

    @Column(name = "email")
    @Getter
    private String email;

    @Column(name = "postcode")
    @Getter
    private String postcode;

    @Column(name = "addressLine1")
    @Getter
    private String addressLine1;

    @Column(name = "addressLine2")
    @Getter
    private String addressLine2;

    @Column(name = "addressLine3")
    @Getter
    private String addressLine3;

    @Column(name = "country")
    @Getter
    private String country;

}
