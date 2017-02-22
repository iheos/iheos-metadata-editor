package gov.nist.hit.ds.docentryeditor.client.widgets;


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
public class EnvironmentState {
    private final EnvironmentServicesAsync services = GWT.create(EnvironmentServices.class);
    private final EventBus eventBus;
    private List<String> environmentNames=new ArrayList<>();

    @Inject
    public EnvironmentState(EventBus eventBus){
        this.eventBus=eventBus;
    }

    public void retrieveEnvironmentNames() {
        services.retrieveEnvironmentNames(new AsyncCallback<List<String>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failure! Can't retrieve environment names from EC.");
            }

            @Override
            public void onSuccess(List<String> result) {
                environmentNames=result;
                ((MetadataEditorEventBus) eventBus).fireEnvironmentNamesReloaded(environmentNames);
            }
        });
    }

}
