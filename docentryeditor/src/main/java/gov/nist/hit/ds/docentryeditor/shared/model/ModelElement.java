package gov.nist.hit.ds.docentryeditor.shared.model;

/**
 * <p>
 * This class is an interface for each element from the model.
 * </p>
 * <p>
 *
 */
public interface ModelElement {
    static final int HASHING_KEY = 31;
	boolean verify() throws String256Exception;
}
