package gov.nist.hit.ds.docentryeditor.client.widgets.configuration;

import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractMVP;

import javax.inject.Inject;

/**
 * Created by onh2 on 2/15/17.
 */
public class ApiConfigurationMVP extends AbstractMVP<ApiConfigurationData,ApiConfigurationView,ApiConfigurationPresenter>{
    @Inject
    private ApiConfigurationView view;
    @Inject
    private ApiConfigurationPresenter presenter;

    @Override
    public ApiConfigurationView buildView() {
        return view;
    }

    @Override
    public ApiConfigurationPresenter buildPresenter() {
        return presenter;
    }
}
