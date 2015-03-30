package gov.nist.hit.ds.docentryeditor.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import gov.nist.hit.ds.docentryeditor.client.event.LoadPrefilledDocEntryEvent.LoadPrefilledDocEntryEventHandler;
/**
 * Gwt Event to request a pre-filled document entry to be loaded.
 * Created by onh2 on 1/16/2015.
 */
public class LoadPrefilledDocEntryEvent extends GwtEvent<LoadPrefilledDocEntryEventHandler>{
    public static Type<LoadPrefilledDocEntryEventHandler> TYPE = new Type<LoadPrefilledDocEntryEventHandler>();

    public LoadPrefilledDocEntryEvent(){
    }

    @Override
    public Type<LoadPrefilledDocEntryEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(LoadPrefilledDocEntryEventHandler handler) {
        handler.onLoadPrefilledDocEntryHandler(this);
    }

    public interface LoadPrefilledDocEntryEventHandler extends EventHandler {
        public void onLoadPrefilledDocEntryHandler(LoadPrefilledDocEntryEvent event);
    }
}
