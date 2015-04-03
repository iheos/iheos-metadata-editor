package gov.nist.hit.ds.docentryeditor.client.editor.widgets.CodedTermWidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import gov.nist.hit.ds.docentryeditor.client.editor.properties.CodedTermProperties;
import gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodes;
import gov.nist.hit.ds.docentryeditor.client.generics.GenericEditableGrid;
import gov.nist.hit.ds.docentryeditor.client.generics.GridModelFactory;
import gov.nist.hit.ds.docentryeditor.shared.model.CodedTerm;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the widget made for the edition of a list of CodedTerms.
 * It creates a editable grid of Coded Term.
 *
 * @see gov.nist.hit.ds.docentryeditor.client.generics.GenericEditableGrid
 * @see gov.nist.hit.ds.docentryeditor.client.editor.widgets.CodedTermWidgets.CodedTermFactory
 */
public class CodedTermsEditableGridWidget extends GenericEditableGrid<CodedTerm> {
    // instance of the property access for the Author entity attributes (for the GXT Store).
    private final static CodedTermProperties isprops = GWT.create(CodedTermProperties.class);

    // --- UI Widgets
    private static ColumnConfig<CodedTerm, String> displayNameColumnConfig;
    private static ColumnConfig<CodedTerm, String> codeColumnConfig;
    private static ColumnConfig<CodedTerm, String> codingSchemeColumnConfig;

    private PredefinedCodedTermComboBox predefinedCodedTermComboBox;

    /**
     * CodedTerms editable grid default constructor.
     *
     * @param gridTitle title of the widget (for the grid's panel header).
     * @param predefinedCode type of predefined codes used in the widget (enum PedefinedCodes).
     * @see gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodes
     */
    public CodedTermsEditableGridWidget(String gridTitle, PredefinedCodes predefinedCode) {
        // use of the super constructor from GenericEditableGrid
        super(gridTitle, new ListStore<CodedTerm>(isprops.key())/*, buildColumnModel()*/);

        // change default tooltip for the toolbar add button.
        ((TextButton) getToolbar().getWidget(0)).setToolTip("Add a custom value");

        predefinedCodedTermComboBox = new PredefinedCodedTermComboBox(predefinedCode);
        // remove combobox value "Custom value"
        predefinedCodedTermComboBox.getStore().remove(0);

//        setCheckBoxSelectionModel();

        bindUI();
    }

    /**
     * Method that binds the UI widgets with actions.
     */
    private void bindUI() {
        predefinedCodedTermComboBox.addSelectionHandler(new SelectionHandler<CodedTerm>() {
            @Override
            public void onSelection(SelectionEvent<CodedTerm> event) {
                if (!getStore().getAll().contains(event.getSelectedItem()))
                    getStore().add(event.getSelectedItem());
                predefinedCodedTermComboBox.getStore().remove(event.getSelectedItem());
            }
        });
    }

    /**
     * Implementation of the abstract method that builds the grid
     * column model for CodedTerms.
     */
    @Override
    protected ColumnModel<CodedTerm> buildColumnModel() {
        List<ColumnConfig<CodedTerm, ?>> columnsConfigs = new ArrayList<ColumnConfig<CodedTerm, ?>>();

        displayNameColumnConfig = new ColumnConfig<CodedTerm, String>(
                isprops.displayName(), 20, "Display Name");
        codeColumnConfig = new ColumnConfig<CodedTerm, String>(
                isprops.code(), 30, "Code");
        codingSchemeColumnConfig = new ColumnConfig<CodedTerm, String>(
                isprops.codingScheme(), 50, "CodingScheme");

        columnsConfigs.add(displayNameColumnConfig);
        columnsConfigs.add(codeColumnConfig);
        columnsConfigs.add(codingSchemeColumnConfig);

        return new ColumnModel<CodedTerm>(columnsConfigs);
    }

    /**
     * Implementation of the abstract method that creates the editing
     * fields used when the grid is in edition mode.
     */
    @Override
    protected void buildEditingFields() {
        // Editing widgets
        TextField displayNameTF = new TextField();
        displayNameTF.setAllowBlank(false);
//        displayNameTF.setToolTip("This fields is required.");
        TextField codeTF = new TextField();
        codeTF.setAllowBlank(false);
        codeTF.setToolTip("This field is required.");
        TextField codingSchemeTF = new TextField();
        codingSchemeTF.setAllowBlank(false);
//        codingSchemeTF.setToolTip("This is required.");
        addColumnEditorConfig(displayNameColumnConfig, displayNameTF);
        addColumnEditorConfig(codeColumnConfig, codeTF);
        addColumnEditorConfig(codingSchemeColumnConfig, codingSchemeTF);
    }

    /**
     * Implementation of the abstract method that specifies the ModelFactory
     * for the grid (used to create a new instance of codedterm in the grid w/o using reflection).
     * @return CodedTermGridFactory
     */
    @Override
    protected GridModelFactory<CodedTerm> getModelFactory() {
        return CodedTermFactory.instance;
    }

    /**
     * This method returns the CodedTerm editable grid as a widget w/ the combobox to add
     * predefined codedterm to the grid.
     * @return CodedTermEditableGrid as a widget.
     */
    @Override
    public Widget getDisplay() {
        FieldLabel codedTermFL = new FieldLabel(predefinedCodedTermComboBox, "Select a coded term to add");
        codedTermFL.setLabelWidth(200);
        addWidget(codedTermFL);
        return super.getDisplay();
    }

    /**
     * Method that clears the entire grid from all values and add theses values
     * to the list of predefined coded terms in the combobox.
     */
    @Override
    protected void clearStoreAction() {
        for (CodedTerm c : getStore().getAll()) {
            if (!predefinedCodedTermComboBox.getStore().getAll().contains(c)) {
                predefinedCodedTermComboBox.getStore().add(c);
            }
        }
        super.clearStoreAction();
        view.refresh(false);
    }

    /**
     * Method that removes a codedterm value from the grid and add it to the list
     * of predefined coded terms in the combobox.
     */
    @Override
    protected void deleteSelectedItemAction() {
        for (CodedTerm e : getSelectionModel().getSelectedItems()) {
            getStore().remove(e);
            getStore().commitChanges();
            if (!predefinedCodedTermComboBox.getStore().getAll().contains(e)) {
                predefinedCodedTermComboBox.getStore().add(e);
            }
        }
        view.refresh(false);
    }

}
