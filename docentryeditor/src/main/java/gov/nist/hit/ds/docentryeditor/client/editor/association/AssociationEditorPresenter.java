package gov.nist.hit.ds.docentryeditor.client.editor.association;

import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation;

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

    private AssociationEditorDriver editorDriver;

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
