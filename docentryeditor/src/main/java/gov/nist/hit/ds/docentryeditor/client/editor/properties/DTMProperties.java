package gov.nist.hit.ds.docentryeditor.client.editor.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import gov.nist.hit.ds.docentryeditor.shared.model.DTM;

import java.util.Date;

/**
 * Property Access interface for DTM entity. It handles the access to dtm's
 * attributes for GXT Stores. It handles the access to a key for the dtm object
 * as well as the dtm value.
 *
 * This Property access only handles the DTM's attribute dtm.
 *
 * @see DTM
 *
 * @author Olivier
 *
 */
public interface DTMProperties extends PropertyAccess<DTM> {
	/**
	 * Returns the KeyProvider for DTM. It is consider that DTM's attribute
	 * "dtm" will be the key of the entity.
	 *
	 * @return a KeyProvider for DTM
	 */
	@Path("dtm")
	ModelKeyProvider<DTM> key();

	/**
	 * Returns ValueProvider for DTM dtm (value) attribute.
	 *
	 * @return CodedTerm ValueProvier for displayName
	 */
	@Path("dtm")//.string
	ValueProvider<DTM, Date> dtm();

}
