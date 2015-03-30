package gov.nist.hit.ds.docentryeditor.client.utils;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor.DocEntryEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.editor.submissionSetEditor.SubmissionSetEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.home.WelcomePlace;

/**
 * Monitors PlaceChangeEvents and History events and keep them in sync.
 * It must know all the different Places used in the application.
 */
@WithTokenizers({ WelcomePlace.Tokenizer.class, SubmissionSetEditorPlace.Tokenizer.class, DocEntryEditorPlace.Tokenizer.class })
public interface MetadataEditorAppPlaceHistoryMapper extends PlaceHistoryMapper {

}
