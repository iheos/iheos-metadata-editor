package gov.nist.hit.ds.docentryeditor.client.editor.docentry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.info.Info;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.event.StartEditXdsDocumentEvent;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParser;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * This class is the presenter that handles all the actions and events related to the Document Entry Editor view.
 * It works with the DocumentEntryView to handle the XdsDocumentEntry model.
 *
 * @see gov.nist.hit.ds.docentryeditor.client.editor.docentry.DocumentEntryEditorView
 * @see gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry
 */
public class DocumentEntryEditorPresenter extends AbstractPresenter<DocumentEntryEditorView> {

    private static final Logger LOGGER = Logger.getLogger(DocumentEntryEditorPresenter.class.getName());
    protected XdsDocumentEntry model=new XdsDocumentEntry();
    private DocEntryEditorDriver editorDriver = GWT.create(DocEntryEditorDriver.class);
    @Inject
    private XdsParser xdsParser;

    /**
     * Method that initializes the editor and the request factory on document entry editor view start.
     */
    @Override
    public void init() {
        bind();
        initDriver(model);
        // to initialize the request factory : requestFactory.initialize(eventBus)
    }

    /**
     * Method that initializes the editor with a document entry object.
     * @param model
     */
    private void initDriver(XdsDocumentEntry model) {
        this.model = model;
        editorDriver.initialize(view);
        getView().authors.getAuthorWidget().initEditorDriver();
        LOGGER.info("Init driver with: " + model.toString());
        editorDriver.edit(model);
        getView().refreshGridButtonsDisplay();
    }

    /**
     * Method that ties actions and view together. It mostly handles gwt event form the event bus.
     */
    private void bind() {
        // this event provides the presenter a document entry to edit and triggers its display in doc entry editor view.
        ((MetadataEditorEventBus) getEventBus()).addStartEditXdsDocumentHandler(new StartEditXdsDocumentEvent.StartEditXdsDocumentHandler() {

            @Override
            public void onStartEditXdsDocument(StartEditXdsDocumentEvent event) {
                LOGGER.info("... receive Start Edit Event");
                model = event.getDocument();
                initDriver(event.getDocument());
                getView().authors.editNewAuthor();
            }
        });
        // this event tells the presenter the application Place is about to change.
        getEventBus().addHandler(PlaceChangeEvent.TYPE, new PlaceChangeEvent.Handler() {
            @Override
            public void onPlaceChange(PlaceChangeEvent placeChangeEvent) {
                // auto save on Place change.
                LOGGER.info("Document Entry auto save on Place change.");
                Info.display("Auto save",
                        "Document entry automatically saved when changing page.");
                final XdsDocumentEntry tmp = editorDriver.flush();
                model = tmp;
            }
        });
    }

    /**
     * Method to handle edited metadata file download with editor's validation check.
     */
    public void doSave() {
        if (editorDriver.isDirty()) {
            final XdsDocumentEntry tmp = editorDriver.flush();
            LOGGER.info("Saving document entry: ");
            LOGGER.info(model.toString());

            if (editorDriver.hasErrors()) {
                final ConfirmMessageBox cmb = new ConfirmMessageBox("Error", "There are errors in your editor. Are you sure you want to download a copy of these data? They may not be usable.");
                cmb.show();
                cmb.addDialogHideHandler(new SaveDialogHandler(tmp));
            } else {
                model=tmp;
                save();
            }
        } else {
            final ConfirmMessageBox cmb = new ConfirmMessageBox("", "Data has not changed. Are you sure you want to download a copy of this metadata entry?");
            cmb.show();
            cmb.addDialogHideHandler(new SaveDialogHandler(model));
        }
    }

    /**
     * Method which actually handle saving (on server) and download for the edited metadata file.
     */
    private void save() {
        ((MetadataEditorEventBus) eventBus).fireSaveFileEvent();
    }

    /**
     * Method that cancels the changes made to the document entry object since the last save.
     */
    public void rollbackChanges() {
        LOGGER.info("Cancel doc. entry changes.");
        ((MetadataEditorEventBus) eventBus).fireXdsEditorLoadedEvent();
    }

    /**
     * Getter that returns the XDS Document Entry object in edition.
     * @return XDS Document Entry object being edited.
     */
    public XdsDocumentEntry getModel() {
        return model;
    }

    /**
     * Method that fills the Document entry editor form with test data.
     */
    public void populate() {
        model=xdsParser.getPrefilledDocumentEntry();
        initDriver(model);
        getView().authors.editNewAuthor();
    }

    /**
     * Interface of a XDS Document Entry Editor Driver
     */
    interface DocEntryEditorDriver extends SimpleBeanEditorDriver<XdsDocumentEntry, DocumentEntryEditorView> {

    }

    /**
     * Handler for save dialog.
     */
    private class SaveDialogHandler implements DialogHideEvent.DialogHideHandler{
        XdsDocumentEntry documentEntry;
        public SaveDialogHandler(XdsDocumentEntry docentry){
            documentEntry=docentry;
        }
        @Override
        public void onDialogHide(DialogHideEvent event) {
            if (event.getHideButton() == PredefinedButton.YES) {
                // perform YES action
                model=documentEntry;
                save();
            } else if (event.getHideButton() == PredefinedButton.NO) {
                // perform NO action
            }
        }
    }
}
