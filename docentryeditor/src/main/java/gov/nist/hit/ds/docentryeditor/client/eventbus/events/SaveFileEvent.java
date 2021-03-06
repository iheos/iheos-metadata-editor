package gov.nist.hit.ds.docentryeditor.client.eventbus.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import gov.nist.hit.ds.docentryeditor.client.eventbus.events.SaveFileEvent.SaveFileEventHandler;

public class SaveFileEvent extends GwtEvent<SaveFileEventHandler> {

	public interface SaveFileEventHandler extends EventHandler {
		public void onFileSave(SaveFileEvent event);
	}

	public static final Type<SaveFileEventHandler> TYPE = new Type<SaveFileEventHandler>();

	public SaveFileEvent() {
	}

	@Override
	public Type<SaveFileEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(SaveFileEventHandler handler) {
		handler.onFileSave(this);
	}

}
