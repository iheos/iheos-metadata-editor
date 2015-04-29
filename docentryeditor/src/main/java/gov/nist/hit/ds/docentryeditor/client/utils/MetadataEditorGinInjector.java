package gov.nist.hit.ds.docentryeditor.client.utils;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor.DocEntryEditorActivity;
import gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor.DocumentEntryEditorMVP;
import gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor.DocumentEntryEditorPresenter;
import gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor.DocumentEntryEditorView;
import gov.nist.hit.ds.docentryeditor.client.editor.submissionSetEditor.SubmissionSetEditorActivity;
import gov.nist.hit.ds.docentryeditor.client.editor.submissionSetEditor.SubmissionSetEditorMVP;
import gov.nist.hit.ds.docentryeditor.client.editor.submissionSetEditor.SubmissionSetEditorPresenter;
import gov.nist.hit.ds.docentryeditor.client.editor.submissionSetEditor.SubmissionSetEditorView;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.AuthorWidgets.AuthorsListEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.generics.ActivityDisplayer;
import gov.nist.hit.ds.docentryeditor.client.root.home.WelcomeActivity;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParser;
import gov.nist.hit.ds.docentryeditor.client.root.CenterPanel;
import gov.nist.hit.ds.docentryeditor.client.root.MetadataEditorAppView;
import gov.nist.hit.ds.docentryeditor.client.root.submission.SubmissionPanelMVP;
import gov.nist.hit.ds.docentryeditor.client.root.submission.SubmissionPanelPresenter;
import gov.nist.hit.ds.docentryeditor.client.root.submission.SubmissionPanelView;
import gov.nist.hit.ds.docentryeditor.client.widgets.EditorToolbar;
import gov.nist.hit.ds.docentryeditor.client.widgets.uploader.FileUploadMVP;

/**
 * Injection  interface with methods that return the desired types.
 *
 * Note that you only need to create injector methods for classes that
 * you would directly access in your top-level initialization code,
 * such as the UI classes to install in your RootPanel.
 * You don't need to create injector methods for lower-level classes that
 * will be automatically injected.
 */
// Associating the module with the injector
@GinModules(MetadataEditorGinModule.class)
public interface MetadataEditorGinInjector extends Ginjector {

    MetadataEditorGinInjector INSTANCE = GWT.create(MetadataEditorGinInjector.class);

    // -----------------------------------------
    // ~ Utils
    MetadataEditorEventBus getEventBus();
    PlaceController getPlaceController();
    MetadataEditorRequestFactory getRequestFactory();
    ActivityDisplayer getActivityDisplayer();

    // ------------------------------------------
    // ~ Root
    MetadataEditorAppView getMetadataEditorAppView();
    CenterPanel getCenterPanel();

    // ------------------------------------------
    // ~ Various widgets
    FileUploadMVP getFileUploadMVP();
    AuthorsListEditorWidget getAuthorsListEditorWidget();
    EditorToolbar getToolbar();

    // ------------------------------------------
    // ~ ACTIVITIES
    WelcomeActivity getWelcomeActivity();
    SubmissionSetEditorActivity getSubmissionSetEditorActivity();
    DocEntryEditorActivity getDocEntryEditorActivity();

    // SubmissionSet editor
    SubmissionSetEditorMVP getSubmissionSetEditorMVP();
    SubmissionSetEditorView getSubmissionSetEditorView();
    SubmissionSetEditorPresenter getSubmissionSetEditorPresenter();

    // DocEntry editor
    DocumentEntryEditorMVP getDocumentEntryEditorMVP();
    DocumentEntryEditorView getDocumentEntryEditorView();
    DocumentEntryEditorPresenter getDocumentEntryEditorPresenter();

    // Submission panel
    SubmissionPanelMVP getSubmissionPanelMVP();
    SubmissionPanelView getSubmissionPanelView();
    SubmissionPanelPresenter getSubmissionPanelPresenter();

    // Client parser
    XdsParser getXdsParser();

}
