package gov.nist.hit.ds.docentryeditor.client.editor.association;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.event.StartEditXdsAssociationEvent;
import gov.nist.hit.ds.docentryeditor.client.event.StartEditXdsSubmissionSetEvent;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsSubmissionSet;

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
    }

    /**
     * Method that ties actions and view together. It mostly handles gwt event form the event bus.
     */
    private void bind() {
        // this event provides the presenter a association to edit and triggers its display in the association editor view.
        ((MetadataEditorEventBus) getEventBus()).addStartEditXdsAssociationHandler(new StartEditXdsAssociationEvent.StartEditXdsAssociationHandler() {

            @Override
            public void onStartEdit(StartEditXdsAssociationEvent event) {
                logger.info("... receive Start Edit Event");
                logger.info(event.getAssociation().toString());
                initDriver(event.getAssociation());
            }
        });
        // this event tells the presenter the application Place is about to change.
        // TODO redesign this
        getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
            @Override
            public void onPlaceChange(PlaceChangeEvent placeChangeEvent) {
                // auto save on Place change.
                logger.info("Association metadata auto save on Place change.");
                Info.display("Auto save",
                        "Association automatically saved when changing page.");
                final XdsAssociation tmp = editorDriver.flush();
                model=tmp;
            }
        });
    }

    /**
     * Getter that returns the XDS Association object in edition.
     * @return XdsAssociation object being edited.
     */
    public XdsAssociation getModel() {
        return model;
    }

    /**
     * Interface of a XDS Document Entry Editor Driver
     */
    interface AssociationEditorDriver extends SimpleBeanEditorDriver<XdsAssociation, AssociationEditorView> {
    }
}
