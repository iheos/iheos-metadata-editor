package gov.nist.hit.ds.docentryeditor.shared.model;

/**
 * <p>
 * This class is an interface for each element from the model.
 * </p>
 * <p>
 *
 * <p>
 * A ModelElement contains ... TODO
 *
 *
 */
public interface ModelElement {
    int HASHING_KEY = 31;
	boolean verify() throws String256Exception;
}
