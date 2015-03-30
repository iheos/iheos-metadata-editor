package gov.nist.hit.ds.docentryeditor.shared.model;

/**
 * <p>
 * <b>This class represents a String256Exception</b> <br>
 * It throws when a String256 has more than 256 characters.</br>
 * </p>
 *
 * @see String256 class String256
 * @see XdsDocumentEntry class DocumentModel
 */
@SuppressWarnings("serial")
public class String256Exception extends Exception {

    public String256Exception() {
        super("This String shouldn't be larger than 256 characters.\nCheck your xml document!");
    }
}
