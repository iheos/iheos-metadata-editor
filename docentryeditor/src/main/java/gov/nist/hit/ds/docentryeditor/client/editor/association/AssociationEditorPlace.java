package gov.nist.hit.ds.docentryeditor.client.editor.association;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Place for the Association editor.
 * This enables the navigation within the application thanks to
 * the Activity-place design.
 */
public class AssociationEditorPlace extends Place {
    private String editorPlaceName;

    public AssociationEditorPlace(String token) {
        this.editorPlaceName = token;
    }

    public AssociationEditorPlace() {
        super();
    }

    public String getActivityEditorPlaceName() {
        return this.editorPlaceName;
    }

    public static class Tokenizer implements PlaceTokenizer<AssociationEditorPlace> {
        @Override
        public String getToken(AssociationEditorPlace place) {
            return place.getActivityEditorPlaceName();
        }

        @Override
        public AssociationEditorPlace getPlace(String token) {
            return new AssociationEditorPlace(token);
        }
    }

}
