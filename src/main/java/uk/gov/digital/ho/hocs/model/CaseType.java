package uk.gov.digital.ho.hocs.model;

public enum CaseType {
    DCU ("DCU"),
    FOI("FOI"),
    HMPO("HMPO"),
    UKVI ("UKVI");

    private final String value;

    CaseType(String value)
    {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}