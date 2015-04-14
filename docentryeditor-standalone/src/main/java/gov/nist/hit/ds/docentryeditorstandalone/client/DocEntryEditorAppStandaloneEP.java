package gov.nist.hit.ds.docentryeditorstandalone.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import gov.nist.hit.ds.docentryeditor.client.MetadataEditorApp;

/**
 * Created by onh2 on 2/3/2015.
 */
public class DocEntryEditorAppStandaloneEP implements EntryPoint {
    @Override
    public void onModuleLoad() {
        MetadataEditorApp app = new MetadataEditorApp();
        app.getAppView().setMarginTop(80);
        RootPanel.get("editorAppContainer").add(app);
    }
}
