package gov.nist.hit.ds.docentryeditor.client.eventbus.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

/**
 * Created by oherrmann on 9/1/15.
 */
public class SessionNamesReloadedEvent extends GwtEvent<SessionNamesReloadedEvent.SessionNamesReloadedEventHandler> {
    public static final Type<SessionNamesReloadedEventHandler> TYPE = new Type<SessionNamesReloadedEventHandler>();
    private List<String> sessionNames;

    public SessionNamesReloadedEvent(List<String> envNames){
        sessionNames=envNames;
    }

    public List<String> getSessionNames(){
        return sessionNames;
    }


    @Override
    public Type<SessionNamesReloadedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SessionNamesReloadedEventHandler handler) {
        handler.onReload(this);
    }

    public interface SessionNamesReloadedEventHandler extends EventHandler{
        void onReload(SessionNamesReloadedEvent event);
    }
}
