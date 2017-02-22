package gov.nist.hit.ds.docentryeditor.client.eventbus.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by onh2 on 4/2/2015.
 */
public class ChangePlaceEvent extends GwtEvent<ChangePlaceEvent.ChangePlaceEventHandler> {
    public static final Type<ChangePlaceEventHandler> TYPE = new Type<ChangePlaceEventHandler>();

    public ChangePlaceEvent(){
    }

    @Override
    public Type<ChangePlaceEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ChangePlaceEventHandler handler) {
        handler.onPlaceChange(this);
    }

    public interface ChangePlaceEventHandler extends EventHandler {
        public void onPlaceChange(ChangePlaceEvent event);
    }
}
