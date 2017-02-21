package gov.nist.hit.ds.docentryeditor.client.widgets.configuration;

import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

import javax.inject.Inject;

/**
 * Created by onh2 on 1/24/17.
 */
public class ConfigurationDialog extends Dialog {
    @Inject
    private ApiConfigurationMVP apiConfigurationMVP;

    public ConfigurationDialog(){
        setHeadingText("Editor configuration dialog");
        setWidth(400);
        setBodyBorder(false);
        setHideOnButtonClick(true);
        getButtonBar().remove(0);
        setModal(true);
        getBody().addClassName("pad-text");
    }

    private void bind() {
        apiConfigurationMVP.getView().getSaveButton().addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                hide();
            }
        });
        apiConfigurationMVP.getView().getCancelButton().addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                hide();
            }
        });
    }

    @Override
    public void show() {
        apiConfigurationMVP.init();
        add(apiConfigurationMVP.getDisplay());
        bind();
        super.show();
    }
}
