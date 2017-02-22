package gov.nist.hit.ds.docentryeditor.client.editor.widgets;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import gov.nist.hit.ds.docentryeditor.shared.model.SubmissionSetStatus;

import java.util.Arrays;

/**
 * Combo box widget that enable the user to select a association type value
 * among several possible defined values.
 */
public class SubmissionSetStatusComboBox extends ComboBox<SubmissionSetStatus> {
    /**
     * Creates a combo box with the different possible values for an XDS Association type
     * added to the selection list.
     */
    public SubmissionSetStatusComboBox() {
        super(new ListStore<SubmissionSetStatus>(
                new ModelKeyProvider<SubmissionSetStatus>() {

                    @Override
                    public String getKey(SubmissionSetStatus item) {
                        return item.toString();
                    }
                }), new LabelProvider<SubmissionSetStatus>() {

            @Override
            public String getLabel(SubmissionSetStatus item) {
                return item.toString();
            }
        });
        getStore().clear();
        // add possible status values to the combo box widget
        getStore().addAll(Arrays.asList(SubmissionSetStatus.values()));
        setTriggerAction(ComboBoxCell.TriggerAction.ALL);

        bind();
    }

    private void bind() {
        this.addSelectionHandler(new SelectionChangeEditorHandler());
    }
}
