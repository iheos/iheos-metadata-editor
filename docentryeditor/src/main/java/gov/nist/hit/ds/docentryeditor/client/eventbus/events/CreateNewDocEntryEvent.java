package gov.nist.hit.ds.docentryeditor.client.eventbus.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

/**
 * This is a GwtEvent to request the creation of a new empty document entry.
 * Created by onh2 on 3/9/2015.
 */
public class CreateNewDocEntryEvent extends GwtEvent<CreateNewDocEntryEvent.CreateNewDocEntryEventHandler> {
    public static final GwtEvent.Type<CreateNewDocEntryEventHandler> TYPE = new Type<CreateNewDocEntryEventHandler>();
    private XdsDocumentEntry documentEntry;

    public CreateNewDocEntryEvent(XdsDocumentEntry documentEntry) {
        this.documentEntry = documentEntry;
    }

    public XdsDocumentEntry getDocument() {
        return documentEntry;
    }

    @Override
    public Type<CreateNewDocEntryEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CreateNewDocEntryEventHandler handler) {
        handler.onCreateNewDocumentEntry(this);
    }

    public interface CreateNewDocEntryEventHandler extends EventHandler {
        public void onCreateNewDocumentEntry(CreateNewDocEntryEvent event);
    }
}
