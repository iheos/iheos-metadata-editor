package gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParserServices;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParserServicesAsync;
import gov.nist.hit.ds.docentryeditor.client.utils.MetadataEditorRequestFactory;
import gov.nist.hit.ds.docentryeditor.client.event.*;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * This class is the presenter that handles all the actions and events related to the Document Entry Editor view.
 * It works with the DocumentEntryView to handle the XdsDocumentEntry model.
 *
 * @see gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor.DocumentEntryEditorView
 * @see gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry
 */
public class DocumentEntryEditorPresenter extends AbstractPresenter<DocumentEntryEditorView> {

    protected static Logger logger = Logger.getLogger(DocumentEntryEditorPresenter.class.getName());
    protected XdsDocumentEntry model=new XdsDocumentEntry();
    private DocEntryEditorDriver editorDriver = GWT.create(DocEntryEditorDriver.class);
    // private final static XdsParserServicesAsync xdsParserServicesAsync=GWT.create(XdsParserServices.class);

    /**
     * Method that initializes the editor and the request factory on document entry editor view start.
     */
    @Override
    public void init() {
        // model = new XdsDocumentEntry();
        bind();
        initDriver(model);
        // requestFactory.initialize(eventBus);
    }

    /**
     * Method that initializes the editor with a document entry object.
     * @param model
     */
    private void initDriver(XdsDocumentEntry model) {
        this.model = model;
        editorDriver.initialize(view);
        getView().authors.getAuthorWidget().initEditorDriver();
        logger.info("Init driver with: "+model.toString());
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
                logger.info("... receive Start Edit Event");
                model = event.getDocument();
                initDriver(event.getDocument());
                getView().authors.editNewAuthor();
            }
        });
        /*((MetadataEditorEventBus) getEventBus()).addSaveFileEventHandler(new SaveFileEvent.SaveFileEventHandler() {

            @Override
            public void onFileSave(SaveFileEvent event) {
                doSave();
            }
        });*/
    }

    /**
     * Method to handle edited metadata file download with editor's validation check.
     */
    public void doSave() {
        if (editorDriver.isDirty()) {
            final XdsDocumentEntry tmp = editorDriver.flush();
            logger.info("Saving document entry: ");
            logger.info(model.toXML());

            if (editorDriver.hasErrors()) {
                final ConfirmMessageBox cmb = new ConfirmMessageBox("Error", "There are errors in your editor. Are you sure you want to download a copy of these data? They may not be usable.");
                cmb.show();
                cmb.addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {
                    public void onDialogHide(DialogHideEvent event) {
                        if (event.getHideButton() == PredefinedButton.YES) {
                            // perform YES action
                            model=tmp;
                            save();
                            cmb.hide();
                        } else if (event.getHideButton() == PredefinedButton.NO) {
                            // perform NO action
                        }
                    }
                });
            } else {
                model=tmp;
                save();
            }
        } else {
            final ConfirmMessageBox cmb = new ConfirmMessageBox("", "Data has not changed. Are you sure you want to download a copy of this metadata entry?");
            cmb.show();
            cmb.addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {
                public void onDialogHide(DialogHideEvent event) {
                    if (event.getHideButton() == PredefinedButton.YES) {
                        // perform YES action
                        save();
                        cmb.hide();
                    } else if (event.getHideButton() == PredefinedButton.NO) {
                        // perform NO action
                    }
                }
            });
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
        logger.info("Cancel doc. entry changes.");
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
     * Interface of a XDS Document Entry Editor Driver
     */
    interface DocEntryEditorDriver extends SimpleBeanEditorDriver<XdsDocumentEntry, DocumentEntryEditorView> {

    }

}
