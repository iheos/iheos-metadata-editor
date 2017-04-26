package gov.nist.hit.ds.docentryeditor.client.widgets.uploader;

import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.eventbus.events.NewFileLoadedEvent;

import javax.inject.Inject;

/**
 * This class is an extend of Dialog. It is a Dialog widget to upload
 * a ebRim xml file to the application.
 */
public class FileUploadDialog extends Dialog {

    @Inject
    private FileUploadMVP fileUploadMVP;
    @Inject
    private ExtCacheFileLoaderMVP extCacheLoaderMVP;
    @Inject
    private MetadataEditorEventBus eventBus;


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
        extCacheLoaderMVP.init();

        // tab panel to choose from file upload from user's machine or file loading from ext. cache.
        TabPanel tabPanel = new TabPanel();
        tabPanel.setWidth(500);
        TabItemConfig fileUploadTab=new TabItemConfig("Upload from computer");
        tabPanel.add(fileUploadMVP.getDisplay(),fileUploadTab);
        TabItemConfig extCacheLoaderTab=new TabItemConfig("Load from external cache");
        tabPanel.add(extCacheLoaderMVP.getDisplay(),extCacheLoaderTab);
        add(tabPanel);
        bind();
    }

    private void bind() {
        fileUploadMVP.getView().getBtnCancel().addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                hide();
            }
        });
        extCacheLoaderMVP.getView().getBtnCancel().addSelectHandler(new SelectEvent.SelectHandler() {
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
