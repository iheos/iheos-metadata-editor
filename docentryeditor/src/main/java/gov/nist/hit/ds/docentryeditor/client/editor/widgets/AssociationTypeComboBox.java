package gov.nist.hit.ds.docentryeditor.client.editor.widgets;

import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation;

import java.util.Arrays;

/**
 * Combo box widget that enable the user to select a association type value
 * among several possible defined values.
 */
public class AssociationTypeComboBox extends SimpleComboBox<XdsAssociation.XdsAssociationType>{
    /**
     * Creates a combo box with the different possible values for an XDS Association type
     * added to the selection list.
     */
    public AssociationTypeComboBox() {
        super(new LabelProvider<XdsAssociation.XdsAssociationType>() {
            @Override
            public String getLabel(XdsAssociation.XdsAssociationType item) {
                return item.toString();
            }
        });
        // add possible type values to the combo box widget
        add(Arrays.asList(XdsAssociation.XdsAssociationType.values()));
    }
}
