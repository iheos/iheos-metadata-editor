package gov.nist.hit.ds.docentryeditorstandalone.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import gov.nist.hit.ds.docentryeditor.client.MetadataEditorApp;

/**
 * Created by onh2 on 2/3/2015.
 */
public class DocEntryEditorAppStandaloneEP implements EntryPoint {
    // Constant to compensate the size of the html header for resize purposes
    private static final int HEADER_HEIGHT = 75;

    @Override
    public void onModuleLoad() {
        MetadataEditorApp app = new MetadataEditorApp();
        app.getAppView().setMarginTop(HEADER_HEIGHT);
        RootPanel.get("editorAppContainer").add(app);
    }
}
