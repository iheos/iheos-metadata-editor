package gov.nist.hit.ds.docentryeditor.client.widgets.environment;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.eventbus.events.EnvironmentNamesReloadedEvent;

import javax.inject.Inject;

/**
 * Created by onh2 on 1/24/17.
 */
public class EnvironmentSelectionWidget extends ContentPanel {
    private SimpleComboBox<String> environmentSelector=new SimpleComboBox<String>(new EnvironmentStringLabelProvider());
    private EnvironmentState environmentState;
    private MetadataEditorEventBus eventBus;

    @Inject
    public EnvironmentSelectionWidget(EnvironmentState environmentState,EventBus eventBus){
        this.eventBus=(MetadataEditorEventBus) eventBus;
        this.environmentState=environmentState;
        environmentSelector.setForceSelection(true);
        environmentSelector.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        this.setHeaderVisible(false);
        this.setBorders(false);
        this.setBodyBorder(false);
        FieldLabel envFieldLabel=new FieldLabel(environmentSelector,"Select environment");
        envFieldLabel.setLabelWidth(125);
        setWidget(envFieldLabel);
        bindWidget();
    }

    private void bindWidget() {
        environmentState.retrieveEnvironmentNames();
        eventBus.addEnvironmentNamesReloadHandler(new EnvironmentNamesReloadedEvent.EnvironmentNamesReloadedEventHandler() {
            @Override
            public void onReload(EnvironmentNamesReloadedEvent event) {
                environmentSelector.clear();
                environmentSelector.add(event.getEnvironmentNames());
                environmentSelector.select(0);
                environmentSelector.setValue(event.getEnvironmentNames().get(0));
                environmentState.setSelectedEnvironment(event.getEnvironmentNames().get(0));
            }
        });
        environmentSelector.addSelectionHandler(new SelectionHandler<String>() {
            @Override
            public void onSelection(SelectionEvent<String> event) {
                environmentState.setSelectedEnvironment(event.getSelectedItem());
            }
        });
    }
    private class EnvironmentStringLabelProvider implements LabelProvider<String> {

        @Override
        public String getLabel(String item) {
            return item;
        }
    }
}
