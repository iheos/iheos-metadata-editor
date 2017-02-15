package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;

import javax.inject.Inject;

/**
 * Created by onh2 on 1/24/17.
 */
public class EnvironmentSelectionWidget extends ContentPanel {
    private SimpleComboBox<String> environmentSelector=new SimpleComboBox<String>(new StringLabelProvider<String>());
    @Inject
    EnvironmentState environmentState;

    public EnvironmentSelectionWidget(){
        this.setHeaderVisible(false);
        this.setBorders(false);
        this.setBodyBorder(false);
        FieldLabel envFieldLabel=new FieldLabel(environmentSelector,"Select environment");
        envFieldLabel.setLabelWidth(125);
        setWidget(envFieldLabel);
    }

}
