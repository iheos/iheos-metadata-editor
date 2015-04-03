package gov.nist.hit.ds.docentryeditor.client.editor.widgets.InternationalStringWidgets;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import gov.nist.hit.ds.docentryeditor.client.editor.properties.InternationalStringProperties;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.LanguageCodeComboBox;
import gov.nist.hit.ds.docentryeditor.client.generics.GenericEditableGrid;
import gov.nist.hit.ds.docentryeditor.client.generics.GridModelFactory;
import gov.nist.hit.ds.docentryeditor.client.widgets.BoundedTextField;
import gov.nist.hit.ds.docentryeditor.shared.model.InternationalString;
import gov.nist.hit.ds.docentryeditor.shared.model.LanguageCode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the widget made for the edition of a list of InternationalString.
 * It creates a editable grid of InternationalString.
 *
 * @see gov.nist.hit.ds.docentryeditor.client.generics.GenericEditableGrid
 * @see gov.nist.hit.ds.docentryeditor.client.editor.widgets.InternationalStringWidgets.InternationalStringFactory
 */
public class InternationalStringEditableGrid extends GenericEditableGrid<InternationalString> {
    // instance of the property access for the InternationalString entity attributes (for the GXT Store).
    private final static InternationalStringProperties isprops= GWT.create(InternationalStringProperties.class);

    // --- UI Widgets
    private static ColumnConfig<InternationalString, LanguageCode> languageCodeColumnConfig;
    private static ColumnConfig<InternationalString, String> titleColumnConfig;

    /**
     * InternationalString editable grid default constructor.
     *
     * @param gridTitle title of the widget (for the grid's panel header).
     */
    public InternationalStringEditableGrid(String gridTitle) {
        super(gridTitle, new ListStore<InternationalString>(isprops.key())/*, buildColumnModel()*/);
//        setCheckBoxSelectionModel();
    }

    /**
     * Implementation of the abstract method that builds the grid
     * column model for InternationalString.
     */
    @Override
    protected ColumnModel<InternationalString> buildColumnModel() {
        List<ColumnConfig<InternationalString, ?>> columnsConfigs = new ArrayList<ColumnConfig<InternationalString, ?>>();

        languageCodeColumnConfig = new ColumnConfig<InternationalString, LanguageCode>(
                isprops.langCode(), 15, "Language Code");
        titleColumnConfig = new ColumnConfig<InternationalString, String>(
                isprops.value(), 85, "Title");
        columnsConfigs.add(languageCodeColumnConfig);
        columnsConfigs.add(titleColumnConfig);
        return new ColumnModel<InternationalString>(
                columnsConfigs);
    }

    /**
     * Implementation of the abstract method that creates the editing
     * fields used when the grid is in edition mode.
     */
    @Override
    protected void buildEditingFields() {
        LanguageCodeComboBox languageCodeComboBox = new LanguageCodeComboBox();
        languageCodeComboBox.setAllowBlank(false);
        languageCodeComboBox.setToolTip("The translation language's code is required. It can not be null. Please select one or delete the row.");
        BoundedTextField tf = new BoundedTextField();
        tf.setMaxLength(256);
        tf.setToolTip("This is the translation corresponding to the selected language. This field is required. It can not be null.");
        tf.setAllowBlank(false);
        addColumnEditorConfig(languageCodeColumnConfig, languageCodeComboBox);
        addColumnEditorConfig(titleColumnConfig, tf);
    }

    /**
     * Implementation of the abstract method that specifies the ModelFactory
     * for the grid (used to create a new instance of codedterm in the grid w/o using reflection).
     * @return InternationalStringGridFactory
     */
    @Override
    protected GridModelFactory<InternationalString> getModelFactory() {
        return InternationalStringFactory.instance;
    }
}
