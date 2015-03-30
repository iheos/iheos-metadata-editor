package gov.nist.hit.ds.docentryeditor.client.editor.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import gov.nist.hit.ds.docentryeditor.shared.model.InternationalString;
import gov.nist.hit.ds.docentryeditor.shared.model.LanguageCode;

/**
 * Property Access interface for InternationalString entity. It handles the
 * access to InternationalString's attributes for GXT Stores. It handles the
 * access to a key for the internationalString object as well as the
 * InternationalString language code and the InternationalString Value.
 *
 *
 * @see InternationalString
 *
 * @author Olivier
 *
 */
public interface InternationalStringProperties extends PropertyAccess<InternationalString> {
	/**
	 * Returns the KeyProvider for InternationalString. It is consider that
	 * InternationalString's attribute "value" will be the key of the entity.
	 *
	 * @return a KeyProvider for InternationalString
	 */
	@Path("value")
	ModelKeyProvider<InternationalString> key();

	/**
	 * Returns ValueProvider for InternationalString languageCode. It handles
	 * the access to the languageCode InternationalString's attribute.
	 *
	 * @return InternationalString ValueProvier for languageCode
	 */
	@Path("langCode")
	ValueProvider<InternationalString, LanguageCode> langCode();

	/**
	 * Returns ValueProvider for InternationalString value. It handles the
	 * access to the value InternationalString's attribute.
	 *
	 * @return InternationalString ValueProvier for languageCode
	 */
	@Path("value.string")
	ValueProvider<InternationalString, String> value();
}
