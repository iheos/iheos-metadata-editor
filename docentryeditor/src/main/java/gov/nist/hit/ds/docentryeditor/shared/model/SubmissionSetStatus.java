package gov.nist.hit.ds.docentryeditor.shared.model;

/**
 * Enumerated type that lists all the possible values for Submission Set Status attribute
 * in Xds Association type.
 */
public enum SubmissionSetStatus {
    ORIGINAL("Original"),REFERENCED("Referenced");
    private final String text;

    /**
     * @param text
     */
    SubmissionSetStatus(final String text) {
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
