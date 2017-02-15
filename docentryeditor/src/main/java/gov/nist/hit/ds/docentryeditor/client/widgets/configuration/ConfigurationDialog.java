package gov.nist.hit.ds.docentryeditor.client.widgets.configuration;

import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.Dialog;

/**
 * Created by onh2 on 1/24/17.
 */
public class ConfigurationDialog extends Dialog {
//    private ConfigurationView configurationPanel=new ConfigurationView();
    public ConfigurationDialog(){
        setHeadingText("Editor configuration dialog");
        setWidth(300);
        setResizable(false);
        setHideOnButtonClick(true);
        setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO);
        setBodyStyleName("pad-text");
        getBody().addClassName("pad-text");
        add(new Label("Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s."));
    }
}
