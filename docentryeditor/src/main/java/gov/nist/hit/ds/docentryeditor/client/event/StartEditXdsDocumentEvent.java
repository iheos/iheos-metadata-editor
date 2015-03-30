package gov.nist.hit.ds.docentryeditor.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

/**
 * This is a GwtEvent to request a Document entry to be edited in the Xds Document Entry editor.
 */
public class StartEditXdsDocumentEvent extends GwtEvent<StartEditXdsDocumentEvent.StartEditXdsDocumentHandler> {

    public static Type<StartEditXdsDocumentHandler> TYPE = new Type<StartEditXdsDocumentHandler>();
    private XdsDocumentEntry fileContent;

    public StartEditXdsDocumentEvent(XdsDocumentEntry fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public Type<StartEditXdsDocumentHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StartEditXdsDocumentHandler handler) {
        handler.onStartEditXdsDocument(this);
    }

    public XdsDocumentEntry getDocument() {
        return fileContent;
    }

    public interface StartEditXdsDocumentHandler extends EventHandler {
        public void onStartEditXdsDocument(StartEditXdsDocumentEvent event);
    }
}
