package gov.nist.hit.ds.docentryeditor.client.editor.widgets;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodesParser;
import gov.nist.hit.ds.docentryeditor.shared.model.String256;

import java.util.List;

/**
 * <p>
 * <b>This class represents the widget to edit a document mime type
 * using a list of existing values (mime types).</b>
 * It enables to edit a mime type by selecting an value in a list existing mime types.
 * <br/>
 * </p>
 */
public class MimeTypeComboBox extends ComboBox<String256> {
    /**
     * Widget default constructor.
     */
    public MimeTypeComboBox(){
        super(new ListStore<String256>(
                new ModelKeyProvider<String256>() {

                    @Override
                    public String getKey(String256 item) {
                        if (item == null) {
                            return "NULL";
                        }
                        return item.toString();
                    }
                }), new LabelProvider<String256>() {

            @Override
            public String getLabel(String256 item) {
                return item.toString();
            }
        });
        getStore().clear();

        // retrieve the list of existing mime types
        List<String256> l = PredefinedCodesParser.INSTANCE
                .getMimeTypes();

        // init the widget with the list of mime types.
        getStore().addAll(l);

        setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        setForceSelection(true);
        setTypeAhead(true);
    }
}
