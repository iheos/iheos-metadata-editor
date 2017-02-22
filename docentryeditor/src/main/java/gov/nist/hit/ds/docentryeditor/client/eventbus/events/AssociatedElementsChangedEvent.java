package gov.nist.hit.ds.docentryeditor.client.eventbus.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by onh2 on 5/20/2015.
 */
public class AssociatedElementsChangedEvent extends GwtEvent<AssociatedElementsChangedEvent.AssociatedElementsChangedHandler> {
    public static final GwtEvent.Type<AssociatedElementsChangedHandler> TYPE = new GwtEvent.Type<AssociatedElementsChangedHandler>();
    private String sourceElement;
    private String targetElement;

    public AssociatedElementsChangedEvent(String source,String target) {
        sourceElement=source;
        targetElement=target;
    }

    public String getSourceElement() {
        return sourceElement;
    }

    public String getTargetElement() {
        return targetElement;
    }

    @Override
    public GwtEvent.Type<AssociatedElementsChangedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AssociatedElementsChangedHandler handler) {
        handler.onAssociatedElementsChange(this);
    }

    public interface AssociatedElementsChangedHandler extends EventHandler {
        public void onAssociatedElementsChange(AssociatedElementsChangedEvent event);
    }
}
