package gov.nist.hit.ds.docentryeditor.client.eventbus.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsModelElement;

import java.util.List;

/**
 * This is a GwtEvent to request a Xds Submission set to be edited in the Submission Set editor.
 * Created by onh2 on 7/18/2014.
 */
public class StartEditXdsAssociationEvent extends GwtEvent<StartEditXdsAssociationEvent.StartEditXdsAssociationHandler> {

    public static final Type<StartEditXdsAssociationHandler> TYPE = new Type<StartEditXdsAssociationHandler>();
    private XdsAssociation association;
    private List<XdsModelElement> objectInSubmission;

    public StartEditXdsAssociationEvent(XdsAssociation association,List<XdsModelElement> objectsInSubmission) {
        this.association = association;
        this.objectInSubmission=objectsInSubmission;
    }

    @Override
    public Type<StartEditXdsAssociationHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StartEditXdsAssociationHandler handler) {
        handler.onStartEdit(this);
    }

    public XdsAssociation getAssociation() {
        return association;
    }

    public List<XdsModelElement> getObjectInSubmission() {
        return objectInSubmission;
    }

    public interface StartEditXdsAssociationHandler extends EventHandler {
        public void onStartEdit(StartEditXdsAssociationEvent event);
    }
}
