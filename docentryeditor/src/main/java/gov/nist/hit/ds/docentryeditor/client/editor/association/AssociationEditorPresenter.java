package gov.nist.hit.ds.docentryeditor.client.editor.association;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import gov.nist.hit.ds.docentryeditor.client.event.ChangePlaceEvent;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.event.StartEditXdsAssociationEvent;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.client.root.submission.SubmissionMenuData;
import gov.nist.hit.ds.docentryeditor.shared.model.String256;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation;

import java.util.List;
import java.util.logging.Logger;

/**
 * This class is the presenter that handles all the actions and events related to the Association Editor view.
 * It works with the AssociationEditorView to handle the XdsAssociation model class.
 *
 * @see AssociationEditorView
 * @see gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation
 */
public class AssociationEditorPresenter extends AbstractPresenter<AssociationEditorView> {
    private static final Logger LOGGER = Logger.getLogger(AssociationEditorPresenter.class.getName());

    protected XdsAssociation model = new XdsAssociation();

    private AssociationEditorDriver editorDriver= GWT.create(AssociationEditorDriver.class);

    /**
     * Method that initializes the editor on association editor view start.
     */
    @Override
    public void init() {
        bind();
        initDriver(model);
    }

    /**
     * Method that initializes the editor with a XDS Association object.
     * @param model instance of XdsAssociation to display in the editor.
     */
    private void initDriver(XdsAssociation model) {
        this.model=model;
        editorDriver.initialize(view);
        LOGGER.info("Init association editor driver with: " + model.toString());
        editorDriver.edit(model);
        editorDriver.flush();
    }

    /**
     * Method that ties actions and view together. It mostly handles gwt event form the event bus.
     */
    private void bind() {
        // this event provides the presenter a association to edit and triggers its display in the association editor view.
        ((MetadataEditorEventBus) getEventBus()).addStartEditXdsAssociationHandler(new StartEditXdsAssociationEvent.StartEditXdsAssociationHandler() {

            @Override
            public void onStartEdit(StartEditXdsAssociationEvent event) {
                logger.info("... receive Start Edit Event (association)");
                initSourceAndTargetComboBoxes(event.getObjectInSubmission());
                logger.info(event.getAssociation().toString());
                initDriver(event.getAssociation());
            }
        });
        // this event tells the presenter the application Place is about to change.
        // TODO redesign this
        getEventBus().addHandler(ChangePlaceEvent.TYPE, new ChangePlaceEvent.ChangePlaceEventHandler() {
            @Override
            public void onPlaceChange(ChangePlaceEvent event) {
                // auto save on Place change.
                logger.info("Association metadata automatically saved on Place change.");
                final XdsAssociation tmp = editorDriver.flush();
                model = tmp;
            }
        });
    }

    private void initSourceAndTargetComboBoxes(List<SubmissionMenuData> availableObjectInSubmission) {
        view.source.clear();
        view.target.clear();
        for (int i=0;i<availableObjectInSubmission.size();i++) {
            if (!view.source.getStore().getAll().contains(availableObjectInSubmission.get(i).getModel().getId())) {
                view.source.add(availableObjectInSubmission.get(i).getModel().getId());
            }
            if (!view.target.getStore().getAll().contains(availableObjectInSubmission.get(i).getModel().getId())) {
                view.target.add(availableObjectInSubmission.get(i).getModel().getId());
            }
        }
    }

    /**
     * This method fires an event to notify that the associated elements in the association have changed.
     */
    public void fireAssociatedNodesChangedEvent() {
        ((MetadataEditorEventBus) getEventBus()).fireAssociatedElementsChanged(view.source.getText(), view.target.getText());
    }

    /**
     * Getter that returns the XDS Association object in edition.
     * @return XdsAssociation object being edited.
     */
    public XdsAssociation getModel() {
        return model;
    }

    /**
     * Method that cancels the changes made to the association object in edition since the last save.
     */
    public void rollbackChanges() {
        LOGGER.info("Cancel association changes.");
        initDriver(model);
        fireAssociatedNodesChangedEvent();
    }

    /**
     * Interface of a XDS Document Entry Editor Driver
     */
    interface AssociationEditorDriver extends SimpleBeanEditorDriver<XdsAssociation, AssociationEditorView> {
    }
}
