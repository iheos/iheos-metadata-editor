package gov.nist.hit.ds.docentryeditor.client.root.submission;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.info.Info;
import gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor.DocEntryEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.editor.submissionSetEditor.SubmissionSetEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.event.*;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.client.home.WelcomePlace;
import gov.nist.hit.ds.docentryeditor.client.parser.PreParse;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParser;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParserServices;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParserServicesAsync;
import gov.nist.hit.ds.docentryeditor.client.resources.AppResources;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorRequestFactory;
import gov.nist.hit.ds.docentryeditor.shared.model.String256;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsSubmissionSet;

import javax.inject.Inject;

/**
 * This class presents the submission panel. It handles the mechanic of the submission set tree of the SubmissionPanelView.
 *
 * @see gov.nist.hit.ds.docentryeditor.client.root.submission.SubmissionPanelView
 * @see gov.nist.hit.ds.docentryeditor.client.root.submission.SubmissionMenuData
 * Created by onh2 on 7/11/2014.
 */
public class SubmissionPanelPresenter extends AbstractPresenter<SubmissionPanelView> {
    @Inject
    private PlaceController placeController;
    @Inject
    private XdsParser xdsParser;
    @Inject
    private MetadataEditorRequestFactory requestFactory;

    private final SubmissionMenuData submissionSetTreeNode = new SubmissionMenuData("subSet", "Submission set",new XdsSubmissionSet());

    private SubmissionMenuData currentlyEdited;
    private int nextIndex = 1;

    // RPC services declaration
    private final XdsParserServicesAsync xdsParserServices = GWT.create(XdsParserServices.class);

    @Override
    public void init() {
        bind();
        requestFactory.initialize(eventBus);
    }

    /**
     * Method that handles the different event fired in the event bus.
     */
    private void bind() {
        // this event catches handle the navigation back to the home page.
        ((MetadataEditorEventBus) getEventBus()).addBackToHomePageEventHandler(new BackToHomePageEvent.BackToHomePageEventHandler() {
            @Override
            public void onBackToHomePage(BackToHomePageEvent event) {
                goToHomePage();
            }
        });
        // this event catches that a Document entry has been loaded from the user's file system.
        ((MetadataEditorEventBus) getEventBus()).addNewFileLoadedHandler(new NewFileLoadedEvent.NewFileLoadedHandler() {
            @Override
            public void onNewFileLoaded(NewFileLoadedEvent event) {
                clearSubmissionSet();
                view.getTreeStore().getRootItems().get(0).setModel(event.getMetadata().getSubmissionSet());
                for (XdsDocumentEntry docEntry : event.getMetadata().getDocumentEntries()) {
                    currentlyEdited = new SubmissionMenuData("DocEntry" + nextIndex, "Document Entry " + nextIndex, docEntry);
                    nextIndex++;
                    view.getTreeStore().add(view.getTreeStore().getRootItems().get(0), currentlyEdited);
                }
                currentlyEdited=submissionSetTreeNode;
                view.getTree().expandAll();
                view.getTree().getSelectionModel().select(submissionSetTreeNode, false);
            }
        });
        // this event catches that a Document entry has been loaded from the user's file system.
        ((MetadataEditorEventBus) getEventBus()).addCreateNewDocEntryEventHandler(new CreateNewDocEntryEvent.CreateNewDocEntryEventHandler() {
            @Override
            public void onCreateNewDocumentEntry(CreateNewDocEntryEvent event) {
                currentlyEdited = new SubmissionMenuData("DocEntry" + nextIndex, "Document Entry " + nextIndex, event.getDocument());
                nextIndex++;
                view.getTreeStore().add(view.getTreeStore().getRootItems().get(0), currentlyEdited);
                view.getTree().expandAll();
                view.getTree().getSelectionModel().select(currentlyEdited, false);
            }
        });
        // this catches that the XDS Document entry editor view has loaded.
        ((MetadataEditorEventBus) getEventBus()).addXdsEditorLoadedEventtHandler(new XdsEditorLoadedEvent.XdsEditorLoadedEventHandler() {
            @Override
            public void onXdsEditorLoaded(XdsEditorLoadedEvent event) {
                logger.info("... receive Editor loaded event.");
                if (currentlyEdited != null) {
                    // if a doc. entry is currently under edition, an event is fired to transfer it to the editor.
                    if (currentlyEdited.getModel() instanceof XdsDocumentEntry) {
                        logger.info("A document is already selected. Loading it...");
                        ((MetadataEditorEventBus) getEventBus()).fireStartEditXdsDocumentEvent((XdsDocumentEntry) currentlyEdited.getModel());
                    }else if (currentlyEdited.getModel() instanceof XdsSubmissionSet){
                        ((MetadataEditorEventBus) getEventBus()).fireStartEditXdsSubmissionSetEvent((XdsSubmissionSet) currentlyEdited.getModel());
                    }
                } else {
                    if (placeController.getWhere() instanceof SubmissionSetEditorPlace){
                        view.getTree().getSelectionModel().select(submissionSetTreeNode,false);
                    }else {
                        // if no doc. entry is currently under edition, it means the app (editor view) has been loaded from
                        // by its URL from the browser navigation bar (external link).
                        logger.info("No Document Entry in Submission Set");
                        // a new doc. entry is create in the submission tree.
                        createNewDocumentEntry();
                    }
                }
            }
        });
        // this catches that a new pre-filled doc. entry creation has been required
        // from another place than the submission panel.
        ((MetadataEditorEventBus) getEventBus()).addLoadPreFilledDocEntryEventHandler(new LoadPrefilledDocEntryEvent.LoadPrefilledDocEntryEventHandler() {
            @Override
            public void onLoadPrefilledDocEntryHandler(LoadPrefilledDocEntryEvent event) {
                createPreFilledDocumentEntry();
            }
        });
        // handle auto-save
        ((MetadataEditorEventBus) getEventBus()).addSaveFileEventHandler(new SaveFileEvent.SaveFileEventHandler() {
            @Override
            public void onFileSave(SaveFileEvent event) {
                doSave();
            }
        });
        // start edit submission set from home page
        eventBus.addHandler(SelectSubmissionSetEvent.TYPE, new SelectSubmissionSetEvent.SelectSubmissionSetEventHandler() {
            @Override
            public void onSelectSubmissionSet(SelectSubmissionSetEvent event) {
                view.getTree().getSelectionModel().select(submissionSetTreeNode,false);
            }
        });
    }

    /**
     * This method initialize the submission set tree by adding
     * a root node (an empty submission set) to the tree.
     */
    public void initSubmissionSet() {
        submissionSetTreeNode.setModel(new XdsSubmissionSet());
        if (view.getTreeStore().getAll().isEmpty()) {
            view.getTreeStore().add(submissionSetTreeNode);
        }
    }

    /**
     * This method creates a new Document Entry and adds it to the submissionSet tree.
     */
    public void createNewDocumentEntry() {
        logger.info("Create new document entry");
        currentlyEdited = new SubmissionMenuData("DocEntry" + nextIndex, "Document Entry " + nextIndex, new XdsDocumentEntry());
        nextIndex++;
        view.getTreeStore().add(view.getTreeStore().getRootItems().get(0), currentlyEdited);
        view.getTree().expandAll();
        view.getTree().getSelectionModel().select(currentlyEdited, false);
    }

    /**
     * This method loads the adequate editor interface with the selected entry
     * from the submission tree. It can be a XdsSubmissionSet, a XdsDocumentEntry
     * or a XdsAssociation.
     *
     * @param selectedItem selected tree node
     */
    public void loadSelectedEntryEditor(SubmissionMenuData selectedItem) {
        ((MetadataEditorEventBus) eventBus).firePlaceChangeEvent();
        currentlyEdited = selectedItem;
        startEditing();
    }

    /**
     * This method load the editor user interface with a pre-filled document entry which is added to the submission set tree.
     */
    public void createPreFilledDocumentEntry() {
        XdsDocumentEntry prefilledDocEntry = xdsParser.getPrefilledDocumentEntry();
        //------------------------------------------- MIGHT CHANGE
        logger.info("Create new pre-filled document entry");
        XdsDocumentEntry newDoc=prefilledDocEntry.copy();
        currentlyEdited = new SubmissionMenuData("DocEntry" + nextIndex, "Document Entry " + nextIndex, newDoc);
        nextIndex++;
        view.getTreeStore().add(view.getTreeStore().getRootItems().get(0), currentlyEdited);
        view.getTree().expandAll();
        view.getTree().getSelectionModel().select(currentlyEdited, false);
    }

    /**
     * Clear the submission set from all its data.
     */
    public void clearSubmissionSet() {
        view.getTreeStore().clear();
        nextIndex=1;
        initSubmissionSet();
    }

    /**
     * This method save file on the server and enable the user to download it by displaying it in its browser.
     */
    public void doSave() {
        // set XdsMetadata object from the submission tree data.
        XdsMetadata m=new XdsMetadata();
        m.setSubmissionSet((XdsSubmissionSet) getSubmissionSetTreeNode().getModel());
        for (SubmissionMenuData subData:view.getTreeStore().getChildren(getSubmissionSetTreeNode())){
            if (subData.getModel() instanceof XdsDocumentEntry) {
                m.getDocumentEntries().add((XdsDocumentEntry) subData.getModel());
            }
        }
        // rpc server call to translate metadata java object into an ebRim xml String.
        xdsParserServices.toEbRim(m, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.warning(throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                // request factory server call to physically save the file on the server.
                requestFactory.saveFileRequestContext().saveAsXMLFile(s).fire(new Receiver<String>() {
                    @Override
                    public void onSuccess(String response) {
                        logger.info("saveAsXMLFile call succeed");
                        logger.info("Generated filename sent to the client \n" + response);
                        logger.info("File's URL: " + GWT.getHostPageBaseURL() + "files/" + response);
                        Window.open(GWT.getHostPageBaseURL() + "files/" + response, response + " Metadata File", "enabled");
                        Dialog d = new Dialog();
                        HTMLPanel htmlP = new HTMLPanel("<a href='" + GWT.getHostPageBaseURL() + "files/" + response + "'>"
                                + GWT.getHostPageBaseURL() + "files/" + response + "</a>");
                        VerticalLayoutContainer vp = new VerticalLayoutContainer();
                        vp.add(new Label("Your download is in progress, please allow this application to open popups with your browser..."),
                                new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(10, 5, 10, 5)));
                        vp.add(htmlP, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(10, 5, 10, 5)));
                        d.add(vp);
                        d.setPredefinedButtons(Dialog.PredefinedButton.OK);
                        d.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
                        d.setHideOnButtonClick(true);
                        d.setHeadingText("XML Metadata File Download");
                        d.show();
                    }
                });
            }
        });
    }

    /**
     * This method loads a document entry into the editor user interface, which is loaded if not already.
     */
    private void startEditing() {
        if(currentlyEdited.getModel() instanceof XdsDocumentEntry) {
            if (!(placeController.getWhere() instanceof DocEntryEditorPlace)) {
                placeController.goTo(new DocEntryEditorPlace());
            }
            logger.info("Fire Start Edit selected (" + currentlyEdited.getValue() + ") document entry event...");
            ((MetadataEditorEventBus) getEventBus()).fireStartEditXdsDocumentEvent((XdsDocumentEntry) currentlyEdited.getModel());
        }else if(currentlyEdited.getModel() instanceof XdsSubmissionSet){
            if(!(placeController.getWhere() instanceof SubmissionSetEditorPlace)){
                placeController.goTo(new SubmissionSetEditorPlace());
            }
            logger.info("Fire Start Edit selected (" + currentlyEdited.getValue() + ") submission set event...");
            logger.info(currentlyEdited.getModel().toString());
            ((MetadataEditorEventBus) getEventBus()).fireStartEditXdsSubmissionSetEvent((XdsSubmissionSet) currentlyEdited.getModel());
        }
    }

    /**
     * This method handle the navigation back to the home page of the application.
     */
    public void goToHomePage() {
        getView().getTree().getSelectionModel().deselectAll();
        placeController.goTo(new WelcomePlace());
    }

    /**
     * Getter that return the entity currently under edition.
     * @return SubmissionMenuData.
     */
    public SubmissionMenuData getCurrentlyEditedEntry(){
        return currentlyEdited;
    }

    /**
     * This method returns the root node of the submissions set tree.
     * It is actually the Submission Set Node.
     *
     * @return Submission Set tree node data
     */
    public SubmissionMenuData getSubmissionSetTreeNode() {
        return submissionSetTreeNode;
    }
}
