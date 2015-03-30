package gov.nist.hit.ds.docentryeditor.client.widgets.uploader;

import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.event.NewFileLoadedEvent;

import javax.inject.Inject;

/**
 * This class is an extend of Dialog. It is a Dialog widget to upload
 * a ebRim xml file to the application.
 */
public class FileUploadDialog extends Dialog {

    @Inject
    FileUploadMVP fileUploadMVP;
    @Inject
    MetadataEditorEventBus eventBus;

    public FileUploadDialog() {
        super();
        setBodyBorder(false);
        setHeadingText("File Upload");
        setHideOnButtonClick(true);

        // delete the default button
        getButtonBar().remove(0);
        setModal(true);
    }

    public void init() {
        fileUploadMVP.init();
        add(fileUploadMVP.getDisplay());
        bind();
    }

    private void bind() {
        fileUploadMVP.getView().getBtnCancel().addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                hide();
            }
        });
        eventBus.addNewFileLoadedHandler(new NewFileLoadedEvent.NewFileLoadedHandler() {

            @Override
            public void onNewFileLoaded(NewFileLoadedEvent event) {
                hide();
            }
        });
    }
}
