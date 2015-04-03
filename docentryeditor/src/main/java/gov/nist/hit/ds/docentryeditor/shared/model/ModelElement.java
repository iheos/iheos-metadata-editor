package gov.nist.hit.ds.docentryeditor.shared.model;

//Ajouter: import gov.nist.hit.ds.eventLog.assertion.AssertionGroup;
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
	// AssertionGroup validate();
	public boolean verify() throws String256Exception;
}
