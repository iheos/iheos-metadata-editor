package gov.nist.hit.ds.docentryeditor.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import java.util.Map;

/**
 * Created by oherrmann on 9/1/15.
 */
public class SelectedStandardChangedEvent extends GwtEvent<SelectedStandardChangedEvent.SelectedStandardChangedEventHandler> {
    public static final GwtEvent.Type<SelectedStandardChangedEventHandler> TYPE = new GwtEvent.Type<SelectedStandardChangedEventHandler>();
    private String standard;
    private Map<String,String> selectedStandardProperties;

    public SelectedStandardChangedEvent(Map<String,String> stdPropertiesMap){
        selectedStandardProperties=stdPropertiesMap;
    }

    public SelectedStandardChangedEvent(String std){
        standard=std;
    }

    public Map<String,String> getSelectedStandardProperties(){
        return selectedStandardProperties;
    }

    public String getSelectedStandard(){return standard;}

    @Override
    public Type<SelectedStandardChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SelectedStandardChangedEventHandler handler) {
        handler.onSelectedStandardChange(this);
    }

    public interface SelectedStandardChangedEventHandler extends EventHandler{
        public void onSelectedStandardChange(SelectedStandardChangedEvent event);
    }
}
