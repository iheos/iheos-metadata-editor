package gov.nist.hit.ds.docentryeditor.client;

/* Imports */

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.logging.Logger;

/**
 * This is the XDS Metadata Editor Application EntryPoint. That's the first
 * class loaded, which instantiate the different object global to the
 * application.
 */
public class MetadataEditorAppStandaloneEP implements EntryPoint {
    protected static Logger logger = Logger.getLogger(MetadataEditorAppStandaloneEP.class.getName());

    @SuppressWarnings("deprecation")
    @Override
    public void onModuleLoad() {
        RootLayoutPanel.get(/*"editorAppContainer"*/).add(new MetadataEditorApp().asWidget());
        //  RootPanel.get("editorAppContainer").add(new MetadataEditorApp().asWidget());
        //  RootLayoutPanel.get().add(activityPanel);
        logger.info("Application Started!");
    }


}
