package gov.nist.hit.ds.docentryeditor.client.eventbus.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import java.util.List;

/**
 * Created by oherrmann on 9/1/15.
 */
public class EnvironmentNamesReloadedEvent extends GwtEvent<EnvironmentNamesReloadedEvent.EnvironmentNamesReloadedEventHandler> {
    public static final Type<EnvironmentNamesReloadedEventHandler> TYPE = new Type<EnvironmentNamesReloadedEventHandler>();
    private List<String> environmentNames;

    public EnvironmentNamesReloadedEvent(List<String> envNames){
        environmentNames=envNames;
    }

    public List<String> getEnvironmentNames(){
        return environmentNames;
    }


    @Override
    public Type<EnvironmentNamesReloadedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EnvironmentNamesReloadedEventHandler handler) {
        handler.onReload(this);
    }

    public interface EnvironmentNamesReloadedEventHandler extends EventHandler{
        public void onReload(EnvironmentNamesReloadedEvent event);
    }
}
