package gov.nist.hit.ds.docentryeditor.client.editor.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import gov.nist.hit.ds.docentryeditor.shared.model.String256;

/**
 * Property Access interface for String256 entity. It handles the access to
 * String256 attributes for GXT Stores. It handles the access to a key for the
 * string256 object as well as the String256 value.
 *
 *
 * @see String256
 *
 * @author Olivier
 *
 */
public interface String256Properties extends PropertyAccess<String256> {
	/**
	 * Returns the KeyProvider for String256. It is consider that String256's
	 * attribute "string" will be the key of the entity.
	 *
	 * @return a KeyProvider for String256
	 */
	@Path("string")
	ModelKeyProvider<String256> key();

	/**
	 * Returns ValueProvider for String256 value. It handles the access to the
	 * string String256's attribute.
	 *
	 * @return String256 ValueProvier for string
	 */
	ValueProvider<String256, String> string();
}
