package gov.nist.hit.ds.docentryeditor.client.widgets.configuration;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.info.Info;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.CookiesManager;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.EnvironmentServices;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.EnvironmentServicesAsync;


/**
 * Created by onh2 on 2/15/17.
 */
public class ApiConfigurationPresenter extends AbstractPresenter<ApiConfigurationView>{
    private final EnvironmentServicesAsync services = GWT.create(EnvironmentServices.class);
    private CookiesManager cookiesManager;
    private ApiConfigurationData config;

    @Override
    public void init() {
        // don't need to do anything in that particular case
    }

    public void saveApiConfiguration(String hostName, String contextName, String portNumber) {
        config=new ApiConfigurationData(hostName,contextName,portNumber);
        cookiesManager.saveApiConfiguration(config);
    }

    public void saveExtCachePath(String extCachePathText) {
        services.saveExternalCachePath(extCachePathText, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Can't save ext. cache path property.");
            }

            @Override
            public void onSuccess(Void result) {
            }
        });
    }

    public void retrieveConfigurationData() {
        config=cookiesManager.getApiConfiguration();
        view.setApiConfigurationFieldValues(config);
    }

    public void retrieveExtCachePathProperty(){
        services.retrieveExternalCachePathProperty(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Can't retrieve external cache path from server.");
            }

            @Override
            public void onSuccess(String result) {
                view.setExtCacheFieldValue(result);
            }
        });
    }

}
