package gov.nist.hit.ds.docentryeditor.client.editor.properties;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

import gov.nist.hit.ds.docentryeditor.shared.model.CodedTerm;

/**
 * Property Access interface for CodedTerm entities. It handles the access to
 * codedTerm's attributes for GXT Stores. It handles the access to a key for the
 * codedTerm object as well as is displayName, its code and its codingScheme.
 *
 * @see CodedTerm
 *
 * @author Olivier
 *
 */
public interface CodedTermProperties extends PropertyAccess<CodedTerm> {
	/**
	 * Returns the KeyProvider for CodedTerm. It is consider that CodedTerm's
	 * attribute "code" will be the key of the entity.
	 *
	 * @return a KeyProvider for CodedTerm
	 */
	@Path("code.string")
	ModelKeyProvider<CodedTerm> key();

	/**
	 * Returns ValueProvider for CodedTerm displayName attribute.
	 *
	 * @return CodedTerm ValueProvier for displayName
	 */
	@Path("displayName.string")
	ValueProvider<CodedTerm, String> displayName();

	/**
	 * Returns ValueProvider for CodedTerm code attribute.
	 *
	 * @return CodedTerm ValueProvier for code
	 */
	@Path("code.string")
	ValueProvider<CodedTerm, String> code();

	/**
	 * Returns ValueProvider for CodedTerm codingScheme attribute.
	 *
	 * @return CodedTerm ValueProvier for codingScheme
	 */
	@Path("codingScheme.codingScheme.string")
	ValueProvider<CodedTerm, String> codingScheme();
}
