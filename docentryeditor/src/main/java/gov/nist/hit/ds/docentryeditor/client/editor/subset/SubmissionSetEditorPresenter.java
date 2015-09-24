package gov.nist.hit.ds.docentryeditor.client.editor.subset;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import gov.nist.hit.ds.docentryeditor.client.event.ChangePlaceEvent;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.event.SelectedStandardChangedEvent;
import gov.nist.hit.ds.docentryeditor.client.event.StartEditXdsSubmissionSetEvent;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.client.utils.StandardPropertiesServices;
import gov.nist.hit.ds.docentryeditor.client.utils.StandardPropertiesServicesAsync;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsSubmissionSet;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is the presenter that handles all the actions and events related to the Submission Set Editor view.
 * Created by onh2 on 3/2/2015.
 */
public class SubmissionSetEditorPresenter extends AbstractPresenter<SubmissionSetEditorView> {
    protected XdsSubmissionSet model=new XdsSubmissionSet();
    Map<String, String> map = new HashMap<String,String>();
    private SubmissionSetEditorDriver editorDriver = GWT.create(SubmissionSetEditorDriver.class);
    private StandardPropertiesServicesAsync stdPropertiesServices= GWT.create(StandardPropertiesServices.class);
    private String standard;

    /**
     * Method that initializes the editor and the request factory on submission set activity start.
     */
    @Override
    public void init() {
        bind();
        initDriver(model);
        // init request factory w/ requestFactory.initialize(eventBus)
    }

    /**
     * Method that initializes the editor with a document entry object.
     * @param model
     */
    public void initDriver(XdsSubmissionSet model) {
        this.model=model;
        editorDriver.initialize(view);
        getView().authors.getAuthorWidget().initEditorDriver();
        logger.info("Init driver with: " + model.toString());
        editorDriver.edit(model);
        editorDriver.flush();
        getView().refreshGridButtonsDisplay();
        getView().collapseAll();
    }

    /**
     * Method that ties actions and view together. It mostly handles gwt event form the event bus.
     */
    private void bind() {
        // this event provides the presenter a submission set to edit and triggers its display in the submission set editor view.
        ((MetadataEditorEventBus) getEventBus()).addStartEditXdsSubmissionSetHandler(new StartEditXdsSubmissionSetEvent.StartEditXdsSubmissionSetHandler() {

            @Override
            public void onStartEdit(StartEditXdsSubmissionSetEvent event) {
                logger.info("... receive Start Edit Event");
                initDriver(event.getSumissionSet());
                getView().authors.editNewAuthor();
            }
        });
        // this event tells the presenter the application Place is about to change.
        getEventBus().addHandler(ChangePlaceEvent.TYPE, new ChangePlaceEvent.ChangePlaceEventHandler() {
            @Override
            public void onPlaceChange(ChangePlaceEvent event) {
                // auto save on Place change.
                logger.info("Submission set metadata automatically saved on Place change.");
                final XdsSubmissionSet tmp = editorDriver.flush();
                model = tmp;
            }
        });
        ((MetadataEditorEventBus) getEventBus()).addSelectedStandardChangedEventHandler(new SelectedStandardChangedEvent.SelectedStandardChangedEventHandler() {
            @Override
            public void onSelectedStandardChange(SelectedStandardChangedEvent event) {
                if (event.getSelectedStandard()!=null){
                    standard=event.getSelectedStandard();
                    retrieveDefaultStandardProperties();
                }
                if (event.getSelectedStandardProperties()!=null) {
                    view.updateEditorUI(event.getSelectedStandardProperties());
                }
            }
        });
    }

    /**
     * Method to save modified metadata (submission set) with editor's validation check.
     */
    public void doSave() {
        if (editorDriver.isDirty()) {
            model = editorDriver.flush();
            logger.info("Saving submission set modifications...");
            if (editorDriver.hasErrors()) {
                final ConfirmMessageBox cmb = new ConfirmMessageBox("Are you sure?", "There are errors in your editor. Are you sure you want to download a copy of these data? They may not be usable.");
                cmb.show();
                cmb.addDialogHideHandler(new SaveDialogHideHandler());
            } else {
                save();
            }
        } else {
            final ConfirmMessageBox cmb = new ConfirmMessageBox("Are you sure?", "Data has not changed. Are you sure you want to download a copy of this metadata entry?");
            cmb.show();
            cmb.addDialogHideHandler(new SaveDialogHideHandler());
        }
    }
    /**
     * Method which actually handle saving (on server) and download for the edited metadata file.
     */
    private void save() {
        ((MetadataEditorEventBus) eventBus).fireSaveFileEvent();
    }

    /**
     * Method that cancels the last unsaved changes made to the submission set currently in edition.
     */
    public void rollbackChanges() {
        logger.info("Cancel last unsaved submission set changes...");
        initDriver(model);
    }

    /**
     * Getter that returns the XDS Submission Set object in edition.
     * @return Xds Submission Set object being edited.
     */
    public XdsSubmissionSet getModel(){
        return model;
    }

    public Map<String,String> getStdProp(){
        return map;
    }

    /**
     * This method populates the submission set editor form with test data.
     */
    public void populate() {
        model.populate();
        initDriver(model);
    }

    public void retrieveDefaultStandardProperties() {
        if (standard==null){
            standard="XDS.b-DS";
        }
        stdPropertiesServices.getStandardProperties(standard, new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.warning(caught.getMessage());
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                getView().updateEditorUI(result);
            }
        });
    }

    /**
     * Handler for the Save Dialogs.
     */
    private class SaveDialogHideHandler implements DialogHideEvent.DialogHideHandler {

        @Override
        public void onDialogHide(DialogHideEvent dialogHideEvent) {
            if (dialogHideEvent.getHideButton() == Dialog.PredefinedButton.YES) {
                // perform YES action
                save();
            } else if (dialogHideEvent.getHideButton() == Dialog.PredefinedButton.NO) {
                // perform NO action
            }
        }
    }

    /**
     * Interface of a XDS Submission Set Editor Driver
     */
    interface SubmissionSetEditorDriver extends SimpleBeanEditorDriver<XdsSubmissionSet, SubmissionSetEditorView> {

    }
}
