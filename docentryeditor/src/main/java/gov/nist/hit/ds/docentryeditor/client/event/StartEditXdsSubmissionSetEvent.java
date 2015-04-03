package gov.nist.hit.ds.docentryeditor.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsSubmissionSet;

/**
 * This is a GwtEvent to request a Xds Submission set to be edited in the Submission Set editor.
 * Created by onh2 on 7/18/2014.
 */
public class StartEditXdsSubmissionSetEvent extends GwtEvent<StartEditXdsSubmissionSetEvent.StartEditXdsSubmissionSetHandler> {

    public static Type<StartEditXdsSubmissionSetHandler> TYPE = new Type<StartEditXdsSubmissionSetHandler>();
    private XdsSubmissionSet submissionSet;

    public StartEditXdsSubmissionSetEvent(XdsSubmissionSet submissionSet) {
        this.submissionSet = submissionSet;
    }

    @Override
    public Type<StartEditXdsSubmissionSetHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StartEditXdsSubmissionSetHandler handler) {
        handler.onStartEdit(this);
    }

    public XdsSubmissionSet getSumissionSet() {
        return submissionSet;
    }

    public interface StartEditXdsSubmissionSetHandler extends EventHandler {
        public void onStartEdit(StartEditXdsSubmissionSetEvent event);
    }
}
