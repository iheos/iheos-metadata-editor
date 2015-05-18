package gov.nist.hit.ds.docentryeditor.shared.model;

/**
 * Enumerated type that lists the possible values for attributes Availability Status.
 */
public enum AvailabilityStatus {
    APPROVED("Approved"),DEPRECATED("Deprecated");
    private final String text;

    /**
     * @param text
     */
    AvailabilityStatus(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
