package gov.nist.hit.ds.docentryeditor.client.widgets.configuration;

import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractPresenter;
import gov.nist.hit.ds.docentryeditor.client.utils.CookiesManager;


/**
 * Created by onh2 on 2/15/17.
 */
public class ApiConfigurationPresenter extends AbstractPresenter<ApiConfigurationView>{
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

    public ApiConfigurationData retrieveConfigurationData() {
        return cookiesManager.getApiConfiguration();
    }
}
