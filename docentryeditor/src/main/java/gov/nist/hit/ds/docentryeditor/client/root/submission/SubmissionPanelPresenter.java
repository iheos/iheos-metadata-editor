package gov.nist.hit.ds.docentryeditor.client.root.submission;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.sencha.gxt.widget.core.client.info.Info;
import gov.nist.hit.ds.docentryeditor.client.editor.association.AssociationEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.editor.docentry.DocEntryEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.editor.subset.SubmissionSetEditorPlace;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.eventbus.events.*;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParser;
import gov.nist.hit.ds.docentryeditor.client.root.home.WelcomePlace;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.MetadataEditorRequestFactory;
import gov.nist.hit.ds.docentryeditor.client.widgets.SaveDialog;
import gov.nist.hit.ds.docentryeditor.client.widgets.environment.EnvironmentState;
import gov.nist.hit.ds.docentryeditor.client.widgets.session.SessionState;
import gov.nist.hit.ds.docentryeditor.shared.SaveInExtCacheRequest;
import gov.nist.hit.ds.docentryeditor.shared.model.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
    @Inject
    private SaveDialog saveDialog;
    @Inject
    private EnvironmentState environmentState;
    @Inject
    private SessionState sessionState;
    static String metadataFileInEditionPath=null;

    private final SubmissionMenuData submissionSetTreeNode = new SubmissionMenuData("subSet", "Submission set",new XdsSubmissionSet());

    private SubmissionMenuData currentlyEdited;
    private int nextIndex = 1;
    private int associationIndex=1;

    @Override
    public void init() {
        bind();
        requestFactory.initialize(eventBus);
    }

    /**
     * Method that handles the different eventbus fired in the eventbus bus.
     */
    private void bind() {
        // this eventbus catches handle the navigation back to the home page.
        ((MetadataEditorEventBus) getEventBus()).addBackToHomePageEventHandler(new BackToHomePageEvent.BackToHomePageEventHandler() {
            @Override
            public void onBackToHomePage(BackToHomePageEvent event) {
                goToHomePage();
            }
        });
        // this eventbus catches that a Document entry has been loaded from the user's file system.
        ((MetadataEditorEventBus) getEventBus()).addNewFileLoadedHandler(new NewFileLoadedEvent.NewFileLoadedHandler() {
            @Override
            public void onNewFileLoaded(NewFileLoadedEvent event) {
                clearSubmissionSet();
                XdsMetadata xdsMetadata=event.getMetadata();
                metadataFileInEditionPath=event.getMetadata().getFilePath();
                view.getSubmissionTreeStore().getRootItems().get(0).setModel(event.getMetadata().getSubmissionSet());
                for (XdsDocumentEntry docEntry : event.getMetadata().getDocumentEntries()) {
                    currentlyEdited = new SubmissionMenuData("DocEntry" + nextIndex, "Document Entry " + nextIndex, docEntry);
                    nextIndex++;
                    view.getSubmissionTreeStore().add(view.getSubmissionTreeStore().getRootItems().get(0), currentlyEdited);
                }
                for (XdsAssociation asso : event.getMetadata().getAssociations()) {
                    view.getAssociationStore().add(asso);
                }
                currentlyEdited = submissionSetTreeNode;
                view.getSubmissionTree().expandAll();
                view.getSubmissionTree().getSelectionModel().select(submissionSetTreeNode, false);
            }
        });
        // this eventbus catches that a Document entry has been loaded from the user's file system.
        ((MetadataEditorEventBus) getEventBus()).addCreateNewDocEntryEventHandler(new CreateNewDocEntryEvent.CreateNewDocEntryEventHandler() {
            @Override
            public void onCreateNewDocumentEntry(CreateNewDocEntryEvent event) {
                createNewDocumentEntry();
            }
        });
        // this catches that the XDS Document entry editor view has loaded.
        ((MetadataEditorEventBus) getEventBus()).addXdsEditorLoadedEventtHandler(new XdsEditorLoadedEvent.XdsEditorLoadedEventHandler() {
            @Override
            public void onXdsEditorLoaded(XdsEditorLoadedEvent event) {
                logger.info("... receive Editor loaded eventbus.");
                if (currentlyEdited != null) {
                    // if a doc. entry is currently under edition, an eventbus is fired to transfer it to the editor.
                    if (currentlyEdited.getModel() instanceof XdsDocumentEntry) {
                        logger.info("A document is already selected. Loading it...");
                        ((MetadataEditorEventBus) getEventBus()).fireStartEditXdsDocumentEvent((XdsDocumentEntry) currentlyEdited.getModel());
                    } else if (currentlyEdited.getModel() instanceof XdsSubmissionSet) {
                        ((MetadataEditorEventBus) getEventBus()).fireStartEditXdsSubmissionSetEvent((XdsSubmissionSet) currentlyEdited.getModel());
                    }
                } else {
                    if (placeController.getWhere() instanceof SubmissionSetEditorPlace) {
                        view.getSubmissionTree().getSelectionModel().select(submissionSetTreeNode, false);
                    } else if (placeController.getWhere() instanceof DocEntryEditorPlace) {
                        // if no doc. entry is currently under edition, it means the app (editor view) has been loaded from
                        // by its URL from the browser navigation bar (external link).
                        logger.info("No Document Entry in Submission Set");
                        // a new doc. entry is create in the submission tree.
                        createNewDocumentEntry();
                    } else {
                        // if no association is currently under edition, it means the app (asso. editor view) has been loaded from
                        // its URL from the browser navigation bar (external link).
                        logger.info("No association");
                        // a new doc. entry is create in the submission tree.
                        createNewAssociation();
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
        // signal that associated node with the association being edited has changed
        ((MetadataEditorEventBus) getEventBus()).addAssociatedElementsChangedHandler(new AssociatedElementsChangedEvent.AssociatedElementsChangedHandler() {
            @Override
            public void onAssociatedElementsChange(AssociatedElementsChangedEvent event) {
                changeAssociatedNodesFontColor(new String256(event.getSourceElement()),new String256(event.getTargetElement()));
            }
        });
        ((MetadataEditorEventBus) getEventBus()).addSelectedStandardChangedEventHandler(new SelectedStandardChangedEvent.SelectedStandardChangedEventHandler() {
            @Override
            public void onSelectedStandardChange(SelectedStandardChangedEvent event) {
                if (event.getSelectedStandard()!=null){
                    view.stdSelector.setStandard(event.getSelectedStandard());
                }
            }
        });
        // start edit submission set from home page
        eventBus.addHandler(SelectSubmissionSetEvent.TYPE, new SelectSubmissionSetEvent.SelectSubmissionSetEventHandler() {
            @Override
            public void onSelectSubmissionSet(SelectSubmissionSetEvent event) {
                view.getSubmissionTree().getSelectionModel().select(submissionSetTreeNode, false);
            }
        });
    }

    /**
     * This method initialize the submission set tree by adding
     * a root node (an empty submission set) to the tree.
     */
    public void initSubmissionSet() {
        submissionSetTreeNode.setModel(new XdsSubmissionSet());
        if (view.getSubmissionTreeStore().getAll().isEmpty()) {
            view.getSubmissionTreeStore().add(submissionSetTreeNode);
        }
    }


    /**
     * This method loads the adequate editor interface with the selected entry
     * from the submission tree. It can be a XdsSubmissionSet, a XdsDocumentEntry.
     *
     * @param selectedItem selected tree node
     */
    public void loadSelectedTreeEntryEditor(SubmissionMenuData selectedItem) {
        // restore nodes default font color.
        resetFontColor();
        fireChangePlaceEvent();
        // deselect all associations.
        view.getAssociationList().getSelectionModel().deselectAll();
        currentlyEdited = selectedItem;
        startEditing();
    }

    /**
     * This method loads the association editor interface with the selected entry
     * from the list of association objects.
     *
     * @param selectedItem selected association
     */
    public void loadSelectedAssociationEditor(XdsAssociation selectedItem) {
        // restore nodes default font color.
        resetFontColor();
        fireChangePlaceEvent();
        // deselect all element in submission set.
        view.getSubmissionTree().getSelectionModel().deselectAll();
        currentlyEdited=new SubmissionMenuData(selectedItem.getId().toString(),selectedItem.getId().toString(),selectedItem);
        changeAssociatedNodesFontColor(selectedItem.getSource(),selectedItem.getTarget());
        startEditing();
    }

    /**
     * This method creates a new Document Entry and adds it to the submissionSet tree.
     */
    public void createNewDocumentEntry() {
        logger.info("Create new document entry");
        XdsDocumentEntry newDocEntry=new XdsDocumentEntry();
        newDocEntry.setId(new String256(new String("DocumentEntry"+(nextIndex<10?"0"+nextIndex:nextIndex))));
        currentlyEdited = new SubmissionMenuData("DocEntry" + nextIndex, newDocEntry.getId().getString(), newDocEntry);
        nextIndex++;
        view.getSubmissionTreeStore().add(view.getSubmissionTreeStore().getRootItems().get(0), currentlyEdited);
        view.getSubmissionTree().expandAll();
        view.getSubmissionTree().getSelectionModel().select(currentlyEdited, false);
        // add an association to link the new doc entry to the submission set.
        XdsAssociation asso=new XdsAssociation();
        asso.setSource(submissionSetTreeNode.getModel().getId());
        asso.setTarget(currentlyEdited.getModel().getId());
        asso.setId(new String256("HasMember " + associationIndex));
        associationIndex++;
        view.getAssociationStore().add(asso);
    }

    /**
     * This method creates a new Association and adds it to the list of association in the Submission panel.
     */
    public void createNewAssociation() {
        logger.info("Create new association");
        XdsAssociation association=new XdsAssociation();
        association.setId(new String256(association.getType().toString().split(":")[association.getType().toString().split(":").length-1]+" "+associationIndex));
        associationIndex++;
        view.getAssociationStore().add(association);
        view.getAssociationList().getSelectionModel().select(association, false);
    }

    /**
     * This method load the editor user interface with a pre-filled document entry which is added to the submission set tree.
     */
    public void createPreFilledDocumentEntry() {
        XdsDocumentEntry prefilledDocEntry = xdsParser.getPrefilledDocumentEntry();
        //------------------------------------------- MIGHT CHANGE
        logger.info("Create new pre-filled document entry");
        XdsDocumentEntry newDoc=prefilledDocEntry.copy();
        newDoc.setId(new String256(new String("DocumentEntry"+(nextIndex<10?"0"+nextIndex:nextIndex))));
        currentlyEdited = new SubmissionMenuData("DocEntry" + nextIndex, newDoc.getId().getString(), newDoc);
        nextIndex++;
        view.getSubmissionTreeStore().add(view.getSubmissionTreeStore().getRootItems().get(0), currentlyEdited);
        view.getSubmissionTree().expandAll();
        view.getSubmissionTree().getSelectionModel().select(currentlyEdited, false);
        // add an association to link the new doc entry to the submission set.
        XdsAssociation asso=new XdsAssociation();
        asso.setSource(submissionSetTreeNode.getModel().getId());
        asso.setTarget(currentlyEdited.getModel().getId());
        asso.setId(new String256("HasMember " + associationIndex));
        associationIndex++;
        view.getAssociationStore().add(asso);
    }

    /**
     * Clear the submission set from all its data.
     */
    public void clearSubmissionSet() {
        view.getSubmissionTreeStore().clear();
        nextIndex=1;
        initSubmissionSet();
        clearAssociationStore();
    }

    /**
     * Clear the association list from all its data.
     */
    public void clearAssociationStore() {
        view.getAssociationStore().clear();
        associationIndex=1;
    }

    /**
     * This method save file on the server and enable the user to download it by displaying it in its browser.
     */
    public void doSave() {
        // set XdsMetadata object from the submission tree data.
        XdsMetadata m=new XdsMetadata();
        m.setSubmissionSet((XdsSubmissionSet) getSubmissionSetTreeNode().getModel());
        for (SubmissionMenuData subData:view.getSubmissionTreeStore().getChildren(getSubmissionSetTreeNode())){
            if (subData.getModel() instanceof XdsDocumentEntry) {
                m.getDocumentEntries().add((XdsDocumentEntry) subData.getModel());
            }
        }
        for (XdsAssociation association:view.getAssociationStore().getAll()){
            m.getAssociations().add(association);
        }
        SaveInExtCacheRequest saveRequest=new SaveInExtCacheRequest();
        saveRequest.setEnvironmentName(environmentState.getSelectedEnvironment());
        saveRequest.setSessionName(sessionState.getSelectedSession());
        saveRequest.setMetadata(m);
        if (metadataFileInEditionPath!=null) {
            saveRequest.setFilePath(metadataFileInEditionPath);
        }
        saveDialog.setSaveRequest(saveRequest);
        saveDialog.show();
    }

    /**
     * This method loads a document entry into the editor user interface, which is loaded if not already.
     */
    private void startEditing() {
        if(currentlyEdited.getModel() instanceof XdsDocumentEntry) {
            if (!(placeController.getWhere() instanceof DocEntryEditorPlace)) {
                placeController.goTo(new DocEntryEditorPlace());
            }
            logger.info("Fire Start Edit selected (" + currentlyEdited.getValue() + ") document entry eventbus...");
            ((MetadataEditorEventBus) getEventBus()).fireStartEditXdsDocumentEvent((XdsDocumentEntry) currentlyEdited.getModel());
        }else if(currentlyEdited.getModel() instanceof XdsSubmissionSet){
            if(!(placeController.getWhere() instanceof SubmissionSetEditorPlace)){
                placeController.goTo(new SubmissionSetEditorPlace());
            }
            logger.info("Fire Start Edit selected (" + currentlyEdited.getValue() + ") submission set eventbus...");
            logger.info(currentlyEdited.getModel().toString());
            ((MetadataEditorEventBus) getEventBus()).fireStartEditXdsSubmissionSetEvent((XdsSubmissionSet) currentlyEdited.getModel());
        }else if(currentlyEdited.getModel() instanceof XdsAssociation){
            if (!(placeController.getWhere() instanceof AssociationEditorPlace)){
                placeController.goTo(new AssociationEditorPlace());
            }
            logger.info("Fire Start Edit selected (" + currentlyEdited.getValue() + ") association eventbus...");
            logger.info(currentlyEdited.getModel().toString());
            List<XdsModelElement> l=new ArrayList<XdsModelElement>();
            for (SubmissionMenuData s:view.getSubmissionTreeStore().getAll()){
                l.add(s.getModel());
            }
            ((MetadataEditorEventBus) getEventBus()).fireStartEditXdsAssociationEvent((XdsAssociation) currentlyEdited.getModel(),l);
        }
    }

    /**
     * This method handle the navigation back to the home page of the application.
     */
    public void goToHomePage() {
        // restore all nodes default font color.
        resetFontColor();
        getView().getSubmissionTree().getSelectionModel().deselectAll();
        getView().getAssociationList().getSelectionModel().deselectAll();
        fireChangePlaceEvent();
        placeController.goTo(new WelcomePlace());
    }

    public void fireChangePlaceEvent(){
        String type="Metadata ";
        if (currentlyEdited!=null) {
            if (currentlyEdited.getModel() instanceof XdsDocumentEntry) {
                type = "Document entry ";
            } else if (currentlyEdited.getModel() instanceof XdsSubmissionSet) {
                type = "Submission set ";
            } else if (currentlyEdited.getModel() instanceof XdsAssociation) {
                type = "Association ";
            }
        }
        Info.display("Auto save",
                type + "automatically saved when changing page.");
        ((MetadataEditorEventBus) eventBus).firePlaceChangeEvent();
    }

    /**
     * This method change the font color the different nodes involved in the association
     * under edition as source and target objects.
     *
     * @param source id of the source object, whose font color has to be changed.
     * @param target id of the target object, whose font color has to be changed.
     */
    private void changeAssociatedNodesFontColor(String256 source,String256 target) {
        // restore default font color
        resetFontColor();
        // change the font color of the target item involved in the association
        if (source!=null && !source.toString().isEmpty()) {
            SubmissionMenuData sourceSubData = searchForSubmissionData(source);
            if (sourceSubData != null) {
                sourceSubData.setIsActiveLink(true);
            }
            getView().getSubmissionTree().refresh(sourceSubData);
        }
        // change the font color of the target item involved in the association
        if (target!=null && !target.toString().isEmpty()) {
            SubmissionMenuData targetSubData = searchForSubmissionData(target);
            if (targetSubData != null) {
                targetSubData.setIsActiveLink(true);
            }
            getView().getSubmissionTree().refresh(targetSubData);
        }
    }

    /**
     * Switch back to black the color of the font for the submission tree items.
     */
    private void resetFontColor() {
        for (SubmissionMenuData subData:this.getView().getSubmissionTreeStore().getAll()){
            if (subData.isActiveLink()){
                subData.setIsActiveLink(false);
                this.getView().getSubmissionTree().refresh(subData);
            }
        }
    }

    /**
     * This method search a submission tree item by its model id.
     * @param id - ID of the XdsModel to find.
     * @return SubmissionMenuData whose model id is the id given as parameter if found.
     *          null otherwise.
     */
    public SubmissionMenuData searchForSubmissionData(String256 id) {
        for (SubmissionMenuData subData:this.getView().getSubmissionTreeStore().getAll()){
            if (id.equals(subData.getModel().getId())){
                return subData;
            }
        }
        return null;
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

    /**
     * This method return the Place currently loaded.
     * @return place currently displayed.
     */
    public Place getCurrentPlace() {
        return placeController.getWhere();
    }

}
