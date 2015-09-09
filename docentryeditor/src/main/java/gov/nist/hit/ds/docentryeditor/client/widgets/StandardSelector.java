package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.utils.StandardPropertiesServices;
import gov.nist.hit.ds.docentryeditor.client.utils.StandardPropertiesServicesAsync;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by oherrmann on 8/26/15.
 */
public class StandardSelector extends SimpleComboBox<String> {
    private final static Logger LOGGER=Logger.getLogger(StandardSelector.class.getName());
    private String selectedStandard;
    private Map<String, String> stdPropertiesMap;
    // RPC services declaration
    private final StandardPropertiesServicesAsync stdPropertiesServices = GWT.create(StandardPropertiesServices.class);
    @Inject
    private MetadataEditorEventBus eventBus;

    public StandardSelector() {
        super(new LabelProvider<String>() {
            @Override
            public String getLabel(String s) {
                return s;
            }
        });
        initValues();
        this.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        this.setForceSelection(true);
        this.setValue(this.getStore().get(0));
        this.setTypeAhead(true);
        this.setEmptyText("Select a standard...");
        bindUI();
    }

    private void bindUI() {
        this.addSelectionHandler(new SelectionHandler<String>() {
            @Override
            public void onSelection(SelectionEvent<String> selectionEvent) {
                selectedStandard = selectionEvent.getSelectedItem();
                stdPropertiesServices.getStandardProperties(selectedStandard, new AsyncCallback<Map<String, String>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        LOGGER.warning(throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(Map<String, String> result) {
                        stdPropertiesMap = result;
                        eventBus.fireSelectedStandardChangedEvent(stdPropertiesMap);
                    }
                });
            }
        });
    }

    private void initValues() {
        this.add("XDS.b");
        this.add("XDR");
        this.add("XDR-minimum metadata");
        this.add("XDM");
        this.add("XDM-minimum metadata");
        stdPropertiesServices.getStandardProperties("XDS.b", new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LOGGER.warning(throwable.getMessage());
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                stdPropertiesMap = result;
            }
        });
    }

    public String getSelectedStandard(){
        return selectedStandard;
    }

    public Map<String,String> getStdPropertiesMap(){
        return stdPropertiesMap;
    }
}