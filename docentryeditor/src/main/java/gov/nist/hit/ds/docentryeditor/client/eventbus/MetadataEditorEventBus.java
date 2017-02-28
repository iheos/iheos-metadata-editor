package gov.nist.hit.ds.docentryeditor.client.eventbus;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.SimpleEventBus;
import gov.nist.hit.ds.docentryeditor.client.eventbus.events.NewFileLoadedEvent.NewFileLoadedHandler;
import gov.nist.hit.ds.docentryeditor.client.eventbus.events.SaveFileEvent.SaveFileEventHandler;
import gov.nist.hit.ds.docentryeditor.client.eventbus.events.*;
import gov.nist.hit.ds.docentryeditor.shared.model.*;

import java.util.List;
import java.util.Map;

/**
 * This is the application eventbus bus. It is used through the entire application.
 * It has its own personalized handlers.
 */
public class MetadataEditorEventBus extends SimpleEventBus {
    // TODO personalized handlers

    /**
     * Method that handle actions that must be triggered when a new file is loaded.
     * @param handler
     * @return
     */
    public HandlerRegistration addNewFileLoadedHandler(NewFileLoadedHandler handler) {
        return addHandler(NewFileLoadedEvent.TYPE, handler);
    }

    /**
     * Method that signals to the eventbus bus to the application that a new file has been loaded into the system.
     * @param xdsMetadata
     */
    public void fireNewFileLoadedEvent(XdsMetadata xdsMetadata) {
        fireEvent(new NewFileLoadedEvent(xdsMetadata));
    }

    /**
     * Method that adds an handler for when a request to save a file is made.
     * @param handler
     * @return
     */
    public HandlerRegistration addSaveFileEventHandler(SaveFileEventHandler handler) {
        return addHandler(SaveFileEvent.TYPE, handler);
    }

    /**
     * This method signals to the eventbus bus to the entire application that request to save a file has been made.
     */
    public void fireSaveFileEvent() {
        fireEvent(new SaveFileEvent());
    }

    /**
     * This method adds an handler that will enable to trigger actions when a Document Entry starts to be edited.
     * @param handler
     * @return
     */
    public HandlerRegistration addStartEditXdsDocumentHandler(StartEditXdsDocumentEvent.StartEditXdsDocumentHandler handler) {
        return addHandler(StartEditXdsDocumentEvent.TYPE,handler);
    }

    /**
     * This methods signals to the eventbus bus a request to start editing a specific document entry.
     * @param documentEntry
     */
    public void fireStartEditXdsDocumentEvent(XdsDocumentEntry documentEntry) {
        fireEvent(new StartEditXdsDocumentEvent(documentEntry));
    }

    /**
     * This method adds an handler that will enable to trigger actions when the Submission set starts to be edited.
     * @param handler
     * @return
     */
    public HandlerRegistration addStartEditXdsSubmissionSetHandler(StartEditXdsSubmissionSetEvent.StartEditXdsSubmissionSetHandler handler) {
        return addHandler(StartEditXdsSubmissionSetEvent.TYPE,handler);
    }

    /**
     * This methods signals to the eventbus bus a request to start editing a specific submission set.
     * @param submissionSet
     */
    public void fireStartEditXdsSubmissionSetEvent(XdsSubmissionSet submissionSet) {
        fireEvent(new StartEditXdsSubmissionSetEvent(submissionSet));
    }

    public HandlerRegistration addSaveCurrentlyEditedMetadataHandler(SaveCurrentlyEditedMetadataEvent.SaveCurrentlyEditedMetadataEventHandler handler) {
        return addHandler(SaveCurrentlyEditedMetadataEvent.TYPE, handler);
    }

    public void fireSaveCurrentlyEditedMetadataEvent(XdsModelElement documentEntry) {
        fireEvent(new SaveCurrentlyEditedMetadataEvent(documentEntry));
    }

    /**
     * This method adds an handler that will take of the actions that must be triggered
     * when the document entry editor is loaded.
     * @param handler
     * @return
     */
    public HandlerRegistration addXdsEditorLoadedEventtHandler(XdsEditorLoadedEvent.XdsEditorLoadedEventHandler handler) {
        return addHandler(XdsEditorLoadedEvent.TYPE, handler);
    }

    /**
     * This methods signals to the eventbus bus that the Xds Document Entry Editor has loaded.
     */
    public void fireXdsEditorLoadedEvent() {
        fireEvent(new XdsEditorLoadedEvent());
    }

    public HandlerRegistration addLoadPreFilledDocEntryEventHandler(LoadPrefilledDocEntryEvent.LoadPrefilledDocEntryEventHandler handler) {
        return addHandler(LoadPrefilledDocEntryEvent.TYPE, handler);
    }
    public void fireLoadPreFilledDocEntryEvent() {
        fireEvent(new LoadPrefilledDocEntryEvent());
    }

    /**
     * This method adds an eventbus handler that will do the navigation to the home page.
     * @param handler
     * @return
     */
    public HandlerRegistration addBackToHomePageEventHandler(BackToHomePageEvent.BackToHomePageEventHandler handler) {
        return addHandler(BackToHomePageEvent.TYPE, handler);
    }

    /**
     * This method signals to the eventbus bus a request to navigate back to the home page of the application.
     */
    public void fireBackToHomePageEvent() {
        fireEvent(new BackToHomePageEvent());
    }

    /**
     * This method adds an eventbus handler that will add the new document entry to the submission set tree.
     * @param handler
     * @return
     */
    public HandlerRegistration addCreateNewDocEntryEventHandler(CreateNewDocEntryEvent.CreateNewDocEntryEventHandler handler){
        return addHandler(CreateNewDocEntryEvent.TYPE,handler);
    }

    /**
     * This method signals to the eventbus bus a request to create a new empty document entry.
     */
    public void fireCreateNewDocEntryEvent(XdsDocumentEntry documentEntry) {
        fireEvent(new CreateNewDocEntryEvent(documentEntry));
    }

    /**
     * Notify the view is about to change Place.
     */
    public void firePlaceChangeEvent() {
        fireEvent(new ChangePlaceEvent());
    }

    /**
     * This method adds an handler that will handle actions required before a Place Change.
     * @param handler
     * @return
     */
    public HandlerRegistration addPlaceChangeEventHandler(ChangePlaceEvent.ChangePlaceEventHandler handler){
        return addHandler(ChangePlaceEvent.TYPE,handler);
    }

    public void fireSelectSubmissionSetEvent() {
        fireEvent(new SelectSubmissionSetEvent());
    }

    /**
     * This method adds an handler that will enable to trigger actions when a association starts to be edited.
     * @param handler
     * @return
     */
    public HandlerRegistration addStartEditXdsAssociationHandler(StartEditXdsAssociationEvent.StartEditXdsAssociationHandler handler) {
        return addHandler(StartEditXdsAssociationEvent.TYPE,handler);
    }

    /**
     * This methods signals to the eventbus bus a request to start editing a specific association.
     * @param association
     */
    public void fireStartEditXdsAssociationEvent(XdsAssociation association,List<XdsModelElement> objectsAvailableInSubmission) {
        fireEvent(new StartEditXdsAssociationEvent(association,objectsAvailableInSubmission));
    }

    public void fireAssociatedElementsChanged(String source, String target) {
        fireEvent(new AssociatedElementsChangedEvent(source,target));
    }

    public HandlerRegistration addAssociatedElementsChangedHandler(AssociatedElementsChangedEvent.AssociatedElementsChangedHandler handler) {
        return addHandler(AssociatedElementsChangedEvent.TYPE,handler);
    }

    /**
     * This method fire an eventbus to notify that the selected standard for the editor has changed.
     * @param
     */
    public void fireSelectedStandardChangedEvent(Map<String,String> selectedStandardPropertiesMap) {
        fireEvent(new SelectedStandardChangedEvent(selectedStandardPropertiesMap));
    }

    /**
     * This method adds an handler that will handle this actions required when the selected standard changes.
     * @param handler
     * @return
     */
    public HandlerRegistration addSelectedStandardChangedEventHandler(SelectedStandardChangedEvent.SelectedStandardChangedEventHandler handler) {
        return addHandler(SelectedStandardChangedEvent.TYPE,handler);
    }

    public void fireSelectedStandardChangedEvent(String std) {
        fireEvent(new SelectedStandardChangedEvent(std));
    }

    /**
     * Fire an event that notify the entire app that the list of environment names has been reloaded.
     * @param envNames - new list of environment names
     */
    public void fireEnvironmentNamesReloaded(List<String> envNames) {
        fireEvent(new EnvironmentNamesReloadedEvent(envNames));
    }

    public HandlerRegistration addEnvironmentNamesReloadHandler(EnvironmentNamesReloadedEvent.EnvironmentNamesReloadedEventHandler handler) {
        return addHandler(EnvironmentNamesReloadedEvent.TYPE,handler);
    }

    public void fireSessionNamesReloaded(List<String> sessionNames) {
        fireEvent(new SessionNamesReloadedEvent(sessionNames));
    }

    public HandlerRegistration addSessionNamesReloadHandler(SessionNamesReloadedEvent.SessionNamesReloadedEventHandler handler) {
        return addHandler(SessionNamesReloadedEvent.TYPE,handler);
    }
}
