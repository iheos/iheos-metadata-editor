package gov.nist.hit.ds.docentryeditorinteg.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import gov.nist.hit.ds.docentryeditor.client.MetadataEditorApp;

/**
 * Created by onh2 on 2/3/2015.
 */
public class DocEntryEditorAppIntegEP implements EntryPoint {
    @Override
    public void onModuleLoad() {
        RootLayoutPanel.get(/*"editorAppContainer"*/).add(new MetadataEditorApp().asWidget());
    }
}
