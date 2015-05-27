package gov.nist.hit.ds.docentryeditor.client.editor.widgets;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import gov.nist.hit.ds.docentryeditor.client.event.SelectionChangeEditorHandler;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation;

import java.util.Arrays;

/**
 * Combo box widget that enable the user to select a association type value
 * among several possible defined values.
 */
public class AssociationTypeComboBox extends ComboBox<XdsAssociation.XdsAssociationType> {
    /**
     * Creates a combo box with the different possible values for an XDS Association type
     * added to the selection list.
     */
    public AssociationTypeComboBox() {
        super(new ListStore<XdsAssociation.XdsAssociationType>(
                new ModelKeyProvider<XdsAssociation.XdsAssociationType>() {

                    @Override
                    public String getKey(XdsAssociation.XdsAssociationType item) {
                        return item.toString();
                    }
                }), new LabelProvider<XdsAssociation.XdsAssociationType>() {

            @Override
            public String getLabel(XdsAssociation.XdsAssociationType item) {
                return item.toString();
            }
        });
        getStore().clear();
        // add possible type values to the combo box widget
        getStore().addAll(Arrays.asList(XdsAssociation.XdsAssociationType.values()));
        setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        bind();
    }

    private void bind() {
        this.addSelectionHandler(new SelectionChangeEditorHandler());
    }
}
