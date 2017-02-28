package gov.nist.hit.ds.docentryeditor.client.widgets.session;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.EnvironmentServices;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.EnvironmentServicesAsync;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onh2 on 2/15/17.
 */
public class SessionState {
    private final EnvironmentServicesAsync services = GWT.create(EnvironmentServices.class);
    private final EventBus eventBus;
    private List<String> sessionNames=new ArrayList<>();
    private String selectedSession;

    @Inject
    public SessionState(EventBus eventBus){
        this.eventBus=eventBus;
    }

    public void retrieveSessionNames() {
        services.retrieveSessionNames(new AsyncCallback<List<String>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failure! Can't retrieve session names from EC.");
            }

            @Override
            public void onSuccess(List<String> result) {
                sessionNames=result;
                ((MetadataEditorEventBus) eventBus).fireSessionNamesReloaded(sessionNames);
            }
        });
    }

    public String getSelectedSession(){
        return selectedSession;
    }

    public void setSelectedSession(String selectedSession) {
        this.selectedSession = selectedSession;
    }
}
