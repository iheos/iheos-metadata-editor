package gov.nist.hit.ds.docentryeditor.client.editor.submissionSetEditor;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Place for the Submission Set editor.
 * This enables the browser navigation (w/ URLs) through the application thanks to the Activity-place design.
 * Created by onh2 on 3/2/2015.
 */
public class SubmissionSetEditorPlace extends Place {
    private String placeName;

    public SubmissionSetEditorPlace(String token){this.placeName=token;}

    public SubmissionSetEditorPlace(){super();}

    public String getPlaceName(){return this.placeName;}

    public static class Tokenizer implements PlaceTokenizer<SubmissionSetEditorPlace> {

        @Override
        public SubmissionSetEditorPlace getPlace(String token) {
            return new SubmissionSetEditorPlace(token);
        }

        @Override
        public String getToken(SubmissionSetEditorPlace place) {
            return place.getPlaceName();
        }
    }
}
