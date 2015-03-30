package gov.nist.hit.ds.docentryeditor.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsModelElement;

public class SaveCurrentlyEditedMetadataEvent extends GwtEvent<SaveCurrentlyEditedMetadataEvent.SaveCurrentlyEditedMetadataEventHandler> {

	public interface SaveCurrentlyEditedMetadataEventHandler extends EventHandler {
		public void onSaveCurrentlyEditedDocumentEvent(SaveCurrentlyEditedMetadataEvent event);
	}

    private XdsModelElement documentEntry;

	public static Type<SaveCurrentlyEditedMetadataEventHandler> TYPE = new Type<SaveCurrentlyEditedMetadataEventHandler>();

	public SaveCurrentlyEditedMetadataEvent(XdsModelElement documentEntry) {
        this.documentEntry=documentEntry;
	}

	@Override
	public Type<SaveCurrentlyEditedMetadataEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveCurrentlyEditedMetadataEventHandler handler) {
		handler.onSaveCurrentlyEditedDocumentEvent(this);
	}

    public XdsModelElement getDocumentEntry(){
        return documentEntry;
    }
}
