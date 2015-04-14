package gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Place for the Document Entry editor.
 * This enables the navigation within the application thanks to
 * the Activity-place design.
 */
public class DocEntryEditorPlace extends Place {
	private String editorPlaceName;

	public DocEntryEditorPlace(String token) {
		this.editorPlaceName = token;
	}

	public DocEntryEditorPlace() {
		super();
	}

	public String getActivityEditorPlaceName() {
		return this.editorPlaceName;
	}

	public static class Tokenizer implements PlaceTokenizer<DocEntryEditorPlace> {
		@Override
		public String getToken(DocEntryEditorPlace place) {
			return place.getActivityEditorPlaceName();
		}

		@Override
		public DocEntryEditorPlace getPlace(String token) {
			return new DocEntryEditorPlace(token);
		}
	}

}
