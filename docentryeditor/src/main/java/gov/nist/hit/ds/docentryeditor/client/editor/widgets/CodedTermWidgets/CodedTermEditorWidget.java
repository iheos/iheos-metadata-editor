package gov.nist.hit.ds.docentryeditor.client.editor.widgets.CodedTermWidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import gov.nist.hit.ds.docentryeditor.client.editor.EditionMode;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.String256EditorWidget;
import gov.nist.hit.ds.docentryeditor.shared.model.CodedTerm;

/**
 * <p>
 * <b>This class represents the widget which matches CodedTerm model type</b>
 * </p>
 */
// FIXME for resize purpose I think I need to extends AbstractView in every classes that implements Editor to use pathToWidgetMap
public class CodedTermEditorWidget extends Composite implements Editor<CodedTerm> {
    // instance of the driver for the edition of a coded term
    @Ignore
    CodedTermEditorDriver codedTermEditorDriver = GWT.create(CodedTermEditorDriver.class);
    /*
     * editionMode is an instance of EditionMode. It is used to know the state
     * of the editor, to know how to display the different fields
     * (enable/disable especially)
     */
    @Ignore
    EditionMode editionMode = EditionMode.NODATA;
    // the CodedTerm object being edited
    @Ignore
    CodedTerm model;
    // --- UI Widgets
    VerticalLayoutContainer codedTermEditorVContainer = new VerticalLayoutContainer();
    // Fields
    String256EditorWidget displayName = new String256EditorWidget();
    String256EditorWidget code = new String256EditorWidget();
    CodingSchemeEditorWidget codingScheme = new CodingSchemeEditorWidget();

    /**
     * CodedTerm Editor widget default constructor.
     */
    public CodedTermEditorWidget() {
        buildUI();
    }

    /**
     * Method that builds the UI.
     */
    private void buildUI() {
        initWidget(codedTermEditorVContainer);

        FieldLabel displayNameLabel = new FieldLabel(displayName, "Display Name");
        displayNameLabel.setLabelWidth(125);

        FieldLabel codeLabel = new FieldLabel(code, "Code");
        codeLabel.setLabelWidth(125);

        FieldLabel codingSchemeLabel = new FieldLabel(codingScheme, "Coding Scheme");
        codingSchemeLabel.setLabelWidth(125);

        // setAllowBlanks(false, false, false);
        setEmptyTexts("ex: General Medicine", "ex: Outpatient", "ex: urn:uuid:vvvh6285-8b07-6s88-f36d-df545z654jgf");
        setToolTipConfigs(new ToolTipConfig("Display Name is a string", "it should contain less than 256 characters"), new ToolTipConfig(
                "Code is a string", "it should contain less than 256 characters"), new ToolTipConfig("Coding Scheme is a string",
                "it should contain less than 256 characters"));
        codedTermEditorVContainer.add(displayNameLabel, new VerticalLayoutData(1, -1));
        codedTermEditorVContainer.add(codeLabel, new VerticalLayoutData(1, -1));
        codedTermEditorVContainer.add(codingSchemeLabel, new VerticalLayoutData(1, -1));
    }

    /**
     * Method that initializes the editor driver for CodedCodedTerm (GWT Editor Framework).
     */
    public void initEditorDriver() {
        codedTermEditorDriver.initialize(this);
    }

    /**
     * Method that starts the edition of a specific codedTerm.
     * @param codedTerm
     */
    public void edit(CodedTerm codedTerm) {
        resetWidgets();
        setModel(codedTerm);
        codedTermEditorDriver.edit(codedTerm);
    }

    /**
     * Method that starts the edition of a new instance of CodedTerm.
     */
    public void editNew() {
        model = new CodedTerm();
        edit(model);
        resetWidgets();
    }

    /**
     * Method that clears all editable fields of the widget.
     */
    private void resetWidgets() {
        displayName.getField().clear();
        code.getField().clear();
        codingScheme.codingScheme.getField().clear();
    }

    /**
     * Method to rollback every changes. Cancel changes.
     */
    public void rollbackChanges() {
        switch (editionMode) {
            case NEW:
                resetEditor();
                break;
            case EDIT:
                resetEditor();
                break;
            default:
                break;
        }

    }

    /**
     * This method reset this widget to its initial state
     * w/ no data selected.
     */
    private void resetEditor() {
        setEditionMode(EditionMode.NODATA);
        model = null;
        codedTermEditorDriver.edit(model);
    }

    /**
     * Method that saves the changes made to a coded term
     * entity in this editor widget.
     * @return saved codedTerm
     */
    public CodedTerm save() {
        model = codedTermEditorDriver.flush();
        if (codedTermEditorDriver.hasErrors()) {
            Dialog d = new Dialog();
            d.setHeadingText("Errors dialog");
            d.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO);
            d.setBodyStyleName("pad-text");
            d.add(new Label(codedTermEditorDriver.getErrors().toString()));
            d.getBody().addClassName("pad-text");
            d.setHideOnButtonClick(true);
            d.setWidth(300);
        }
        return model;
    }

    /**
     * Use this method to enable/disable all editable fields of this widget.
     * @param enabled
     */
    protected void setWidgetEnable(boolean enabled) {
        displayName.getField().setEnabled(enabled);
        code.getField().setEnabled(enabled);
        codingScheme.codingScheme.getField().setEnabled(enabled);
    }

    /**
     * This method sets the default text to display in an empty field (defaults
     * to null). It is done to help and guide the user during his input.
     *
     * @param displayNameEmptytext Default text displayed in an empty name field
     * @param codeEmptyText        Default text displayed in an empty code field
     * @param codingEmptyText      Default text displayed in an empty coding scheme text field
     */
    public void setEmptyTexts(String displayNameEmptytext, String codeEmptyText, String codingEmptyText) {
        displayName.setEmptyText(displayNameEmptytext);
        code.setEmptyText(codeEmptyText);
        codingScheme.setEmptyText(codingEmptyText);
    }

    /**
     * Sets the widget's tool tip with the given config
     *
     * @param configDisplayName
     * @param configCode
     * @param configCodingScheme
     */
    public void setToolTipConfigs(ToolTipConfig configDisplayName, ToolTipConfig configCode, ToolTipConfig configCodingScheme) {
        displayName.setToolTipConfig(configDisplayName);
        code.setToolTipConfig(configCode);
        codingScheme.setToolTipConfig(configCodingScheme);
    }

    /**
     * Sets whether a field is valid when its value length = 0 (default to
     * true). This will warn the user through the editor widget if he didn't
     * input anything in field which does not allow blank.
     *
     * @param b_displayName  true to allow blank to the name field, false otherwise
     * @param b_code         true to allow blank to the code field, false otherwise
     * @param b_codingScheme true to allow blank to the coding scheme field, false
     *                       otherwise
     */
    public void setAllowBlanks(boolean b_displayName, boolean b_code, boolean b_codingScheme) {
        displayName.setAllowBlank(b_displayName);
        code.setAllowBlank(b_code);
        codingScheme.setAllowBlank(b_codingScheme);
    }

    /**
     * Methods that the current edition mode state of the widget.
     * @return current edition mode state.
     */
    public EditionMode getEditionMode() {
        return editionMode;
    }

    /**
     * Method that sets an edition mode for the widget.
     * @param editionMode edition mode to set
     */
    public void setEditionMode(EditionMode editionMode) {
        this.editionMode = editionMode;
        if (editionMode.equals(EditionMode.DISPLAY) || editionMode.equals(EditionMode.NODATA)) {
            setWidgetEnable(false);
        } else if (editionMode.equals(EditionMode.NEW) || editionMode.equals(EditionMode.EDIT)) {
            setWidgetEnable(true);
        }
    }

    /**
     * Getter returning the codedTerm entity in edition in the widget.
     * @return CodedTerm entity in edition.
     */
    public CodedTerm getModel() {
        return save();
    }

    /**
     * Setter to change the coded term in edition.
     * @param codedTerm coded term to edit.
     */
    public void setModel(CodedTerm codedTerm) {
        this.model = codedTerm;
    }

    /**
     * This methods set the focus to the first field of the widget (displayName).
     */
    public void setFocus() {
        displayName.focus();
    }

    /**
     * Definition of the editor driver dedicated to CodedTerm (GWT Editor Framework).
     */
    interface CodedTermEditorDriver extends SimpleBeanEditorDriver<CodedTerm, CodedTermEditorWidget> {
    }

}
