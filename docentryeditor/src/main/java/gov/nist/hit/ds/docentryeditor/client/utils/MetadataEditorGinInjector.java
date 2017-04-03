package gov.nist.hit.ds.docentryeditor.client.utils;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import gov.nist.hit.ds.docentryeditor.client.editor.association.*;
import gov.nist.hit.ds.docentryeditor.client.editor.docentry.*;
import gov.nist.hit.ds.docentryeditor.client.editor.subset.*;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.author.AuthorsListEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.generics.ActivityDisplayer;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParser;
import gov.nist.hit.ds.docentryeditor.client.root.CenterPanel;
import gov.nist.hit.ds.docentryeditor.client.root.MetadataEditorAppView;
import gov.nist.hit.ds.docentryeditor.client.root.EditorConfigurationHeader;
import gov.nist.hit.ds.docentryeditor.client.root.home.WelcomeActivity;
import gov.nist.hit.ds.docentryeditor.client.root.submission.*;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.MetadataEditorRequestFactory;
import gov.nist.hit.ds.docentryeditor.client.widgets.EditorToolbar;
import gov.nist.hit.ds.docentryeditor.client.widgets.environment.EnvironmentSelectionWidget;
import gov.nist.hit.ds.docentryeditor.client.widgets.StandardSelector;
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
    EditorConfigurationHeader getNorthPanel();

    // ------------------------------------------
    // ~ Various widgets
    FileUploadMVP getFileUploadMVP();
    AuthorsListEditorWidget getAuthorsListEditorWidget();
    EditorToolbar getToolbar();
    StandardSelector getStandardSelector();
    EnvironmentSelectionWidget getEnvironmentSelectionWidget();

    // ------------------------------------------
    // ~ ACTIVITIES
    WelcomeActivity getWelcomeActivity();
    SubmissionSetEditorActivity getSubmissionSetEditorActivity();
    DocEntryEditorActivity getDocEntryEditorActivity();
    AssociationEditorActivity getAssociationEditorActivity();

    // SubmissionSet editor
    SubmissionSetEditorMVP getSubmissionSetEditorMVP();
    SubmissionSetEditorView getSubmissionSetEditorView();
    SubmissionSetEditorPresenter getSubmissionSetEditorPresenter();

    // DocEntry editor
    DocumentEntryEditorMVP getDocumentEntryEditorMVP();
    DocumentEntryEditorView getDocumentEntryEditorView();
    DocumentEntryEditorPresenter getDocumentEntryEditorPresenter();

    // Association editor
    AssociationEditorMVP getAssociationEditorMVP();
    AssociationEditorView getAssociationEditorView();
    AssociationEditorPresenter getAssociationEditorPresenter();

    // Submission panel
    SubmissionPanelMVP getSubmissionPanelMVP();
    SubmissionPanelView getSubmissionPanelView();
    SubmissionPanelPresenter getSubmissionPanelPresenter();

    // Client parser
    XdsParser getXdsParser();

}
