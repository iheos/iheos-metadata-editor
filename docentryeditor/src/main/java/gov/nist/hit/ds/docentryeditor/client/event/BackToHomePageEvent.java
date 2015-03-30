package gov.nist.hit.ds.docentryeditor.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * GWT Event to request to move back to the application Home page.
 * Created by onh2 on 1/20/2015.
 */
public class BackToHomePageEvent extends GwtEvent<BackToHomePageEvent.BackToHomePageEventHandler> {
    public static Type<BackToHomePageEventHandler> TYPE = new Type<BackToHomePageEventHandler>();

    public BackToHomePageEvent(){
    }

    @Override
    public Type<BackToHomePageEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(BackToHomePageEventHandler handler) {
        handler.onBackToHomePage(this);
    }

    public interface BackToHomePageEventHandler extends EventHandler {
        public void onBackToHomePage(BackToHomePageEvent event);
    }
}
