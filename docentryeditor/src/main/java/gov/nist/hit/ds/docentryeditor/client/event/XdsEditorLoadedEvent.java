package gov.nist.hit.ds.docentryeditor.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * This is a GwtEvent to signal that the Xds Document Entry editor has been loaded.
 */
public class XdsEditorLoadedEvent extends GwtEvent<XdsEditorLoadedEvent.XdsEditorLoadedEventHandler> {

    public static Type<XdsEditorLoadedEventHandler> TYPE = new Type<XdsEditorLoadedEventHandler>();

    public XdsEditorLoadedEvent() {
    }

    @Override
    public Type<XdsEditorLoadedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(XdsEditorLoadedEventHandler handler) {
        handler.onXdsEditorLoaded(this);
    }

    public interface XdsEditorLoadedEventHandler extends EventHandler {
        public void onXdsEditorLoaded(XdsEditorLoadedEvent event);
    }

}
