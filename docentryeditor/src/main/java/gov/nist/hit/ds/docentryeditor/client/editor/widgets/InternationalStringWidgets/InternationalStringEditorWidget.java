package gov.nist.hit.ds.docentryeditor.client.editor.widgets.InternationalStringWidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import gov.nist.hit.ds.docentryeditor.client.editor.EditionMode;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.LanguageCodeComboBox;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.String256EditorWidget;
import gov.nist.hit.ds.docentryeditor.shared.model.InternationalString;

/**
 * This is a widget which enables to edit a single international String. It might be a title or comment for instance.
 * To use it you must call for call for it's initEditorDriver method at a higher level (in the presenter for example).
 */
public class InternationalStringEditorWidget extends Composite implements Editor<InternationalString> {

    // instance of the driver for the edition of an international string
    private final InternationalStringEditorDriver internationalStringEditorDriver = GWT.create(InternationalStringEditorDriver.class);
    // --- UI Widgets
    private final VerticalLayoutContainer widgetVContainer = new VerticalLayoutContainer();
    // fields
    LanguageCodeComboBox langCode = new LanguageCodeComboBox();
    String256EditorWidget value = new String256EditorWidget();

    // the InternationalString object being edited
    @Ignore
    private InternationalString model;
    /*
     * editionMode is an instance of EditionMode. It is used to know the state
     * of the editor, to know how to display the different fields
     * (enable/disable especially)
     */
    @Ignore
    private EditionMode editionMode = EditionMode.NODATA;

    /**
     * InternationalString editor widget default constructor
     */
    public InternationalStringEditorWidget() {
        buildUI();
        bindUI();
    }

    /**
     * Method that builds the UI.
     */
    private void buildUI() {
        initWidget(widgetVContainer);
        FieldLabel langCodeFL = new FieldLabel(langCode, "Language Code");
        FieldLabel valueFL = new FieldLabel(value, "Value");
        langCode.setWidth("auto");
        value.setWidth("auto");
        widgetVContainer.add(langCodeFL);
        widgetVContainer.add(valueFL);
    }

    /**
     * Mehtod that binds widgets of the UI with actions.
     */
    private void bindUI() {

    }

    /**
     * Method that initializes the editor driver for InternationalString (GWT Editor Framework).
     */
    public void initEditorDriver() {
        internationalStringEditorDriver.initialize(this);
    }

    /**
     * Method that starts the edition of a specific internationalString.
     * @param internationalString InternationalString object to edit.
     */
    public void edit(InternationalString internationalString) {
        resetWidgets();
        setModel(internationalString);
        internationalStringEditorDriver.edit(internationalString);
    }

    /**
     * Method that starts the edition of a new instance of InternationalString.
     */
    public void editNew() {
        model = new InternationalString();
        edit(model);
        resetWidgets();
    }

    /**
     * Method that clears all editable fields of the widget.
     */
    private void resetWidgets() {
        langCode.clear();
        value.getField().clear();
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
        internationalStringEditorDriver.edit(model);
        // editNew();
    }

    /**
     * Method that saves the changes made to an international string
     * entity in this editor widget.
     * @return saved codedTerm
     */
    public void save() {
        model = internationalStringEditorDriver.flush();
        if (internationalStringEditorDriver.hasErrors()) {
            Dialog d = new Dialog();
            d.setHeadingText("Errors dialog");
            d.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO);
            d.setBodyStyleName("pad-text");
            d.add(new Label(internationalStringEditorDriver.getErrors().toString()));
            d.getBody().addClassName("pad-text");
            d.setHideOnButtonClick(true);
            d.setWidth(300);
        }
    }

    /**
     * Use this method to enable/disable all editable fields of this widget.
     * @param enabled
     */
    protected void setWidgetEnable(boolean enabled) {
        langCode.setEnabled(enabled);
        value.getField().setEnabled(enabled);
    }

    /**
     * This method sets the default text to display in an empty field (defaults
     * to null). It is done to help and guide the user during his input.
     *
     * @param langCodeEmptyText
     * @param valueEmptyText
     */
    public void setEmptyText(String langCodeEmptyText, String valueEmptyText) {
        langCode.setEmptyText(langCodeEmptyText);
        value.setEmptyText(valueEmptyText);
    }

    /**
     * Sets the widget's tool tip with the given config
     *
     * @param langCodeConfig
     * @param valueConfig
     */
    public void setToolTipConfig(ToolTipConfig langCodeConfig, ToolTipConfig valueConfig) {
        langCode.setToolTipConfig(langCodeConfig);
        value.setToolTipConfig(valueConfig);
    }

    /**
     * Sets whether a field is valid when its value length = 0 (default to
     * true). This will warn the user through the editor widget if he didn't
     * input anything in field which does not allow blank.
     *
     * @param langCodeBlankAllowed
     */
    public void setAllowBlank(boolean langCodeBlankAllowed) {
        langCode.setAllowBlank(langCodeBlankAllowed);
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
     * Getter returning the InternationalString entity in edition in the widget.
     * @return InternationalString entity in edition.
     */
    public InternationalString getModel() {
        return model;
    }

    /**
     * Setter to change the InternationalString in edition.
     * @param internationalString InternationalString to edit.
     */
    private void setModel(InternationalString internationalString) {
        this.model = internationalString;
    }

    @Override
    public boolean isAttached() {
        return super.isAttached();
    }

    /**
     * Definition of the editor driver dedicated to InternationalString (GWT Editor Framework).
     */
    interface InternationalStringEditorDriver extends SimpleBeanEditorDriver<InternationalString, InternationalStringEditorWidget> {
    }
}
