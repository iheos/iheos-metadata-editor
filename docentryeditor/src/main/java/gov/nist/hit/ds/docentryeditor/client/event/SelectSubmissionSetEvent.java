package gov.nist.hit.ds.docentryeditor.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by onh2 on 4/7/2015.
 */
public class SelectSubmissionSetEvent extends GwtEvent<SelectSubmissionSetEvent.SelectSubmissionSetEventHandler> {

    public static final Type<SelectSubmissionSetEventHandler> TYPE = new Type<SelectSubmissionSetEventHandler>();

    @Override
    public Type<SelectSubmissionSetEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SelectSubmissionSetEventHandler handler) {
        handler.onSelectSubmissionSet(this);
    }

    public interface SelectSubmissionSetEventHandler extends EventHandler {
        public void onSelectSubmissionSet(SelectSubmissionSetEvent event);
    }

}
