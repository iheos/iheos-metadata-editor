package gov.nist.hit.ds.docentryeditor.client.widgets.session;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import gov.nist.hit.ds.docentryeditor.client.eventbus.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.eventbus.events.SessionNamesReloadedEvent;

import javax.inject.Inject;

/**
 * Created by onh2 on 1/24/17.
 */
public class SessionSelectionWidget extends ContentPanel {
    private SimpleComboBox sessionSelector=new SimpleComboBox(new SessionStringLabelProvider());
    private SessionState sessionState;
    private MetadataEditorEventBus eventBus;

    @Inject
    public SessionSelectionWidget(SessionState sessionState,EventBus eventBus){
        this.eventBus=(MetadataEditorEventBus) eventBus;
        this.sessionState=sessionState;
        sessionSelector.setForceSelection(true);
        sessionSelector.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        this.setHeaderVisible(false);
        this.setBorders(false);
        this.setBodyBorder(false);
        setWidget(new FieldLabel(sessionSelector,"Select session"));
        bindWidget();
    }

    private void bindWidget() {
        sessionState.retrieveSessionNames();
        eventBus.addSessionNamesReloadHandler(new SessionNamesReloadedEvent.SessionNamesReloadedEventHandler() {
            @Override
            public void onReload(SessionNamesReloadedEvent event) {
                sessionSelector.clear();
                sessionSelector.add(event.getSessionNames());
                sessionSelector.select(0);
                sessionSelector.setValue(event.getSessionNames().get(0));
                sessionState.setSelectedSession(event.getSessionNames().get(0));
            }
        });
        sessionSelector.addSelectionHandler(new SelectionHandler<String>() {
            @Override
            public void onSelection(SelectionEvent<String> event) {
                sessionState.setSelectedSession(event.getSelectedItem());
            }
        });
    }

    private class SessionStringLabelProvider extends StringLabelProvider<String>{

    }
}
