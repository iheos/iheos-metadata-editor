package gov.nist.hit.ds.docentryeditor.client.editor.widgets.identifier;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.String256EditorWidget;
import gov.nist.hit.ds.docentryeditor.shared.model.IdentifierString256;

/**
 * <p>
 * <b>This class represents the editor widget which matches IdentifierString256 model type</b>
 * </p>
 */
public class IdentifierString256EditorWidget extends Composite implements Editor<IdentifierString256> {
    private VerticalLayoutContainer vcontainer = new VerticalLayoutContainer();
    String256EditorWidget value = new String256EditorWidget();
    String256EditorWidget idType = new String256EditorWidget();

    /**
     * Default constructor.
     */
    public IdentifierString256EditorWidget() {
        initWidget(vcontainer);
        vcontainer.add(value, new VerticalLayoutData(1, -1));
    }

    /**
     * Method that add a validator to the IdentifierString256 field.
     * @param validator input identifier String256 value validator.
     */
    public void addValueFieldValidator(Validator<String> validator) {
        value.addValidator(validator);
    }

    /**
     * This method sets the default text to display in an empty field (defaults
     * to null). It is done to help and guide the user during his input.
     *
     * @param valueEmptyText  Default text displayed in an empty value field
     * @param idTypeEmptyText Default text displayed in an empty id type field
     */
    public void setEmptyTexts(String valueEmptyText, String idTypeEmptyText) {
        value.setEmptyText(valueEmptyText);
        idType.setEmptyText(idTypeEmptyText);
    }

    /**
     * Sets the widget's tool tip with the given config
     *
     * @param valueConfig
     * @param idTypeConfig
     */
    public void setToolTipConfigs(ToolTipConfig valueConfig, ToolTipConfig idTypeConfig) {
        value.setToolTipConfig(valueConfig);
        idType.setToolTipConfig(idTypeConfig);
    }

    /**
     * Sets whether a field is valid when its value length = 0 (default to
     * true). This will warn the user through the editor widget if he didn't
     * input anything in field which does not allow blank.
     *
     * @param isValueBlank  true to allow blank to the value field, false otherwise
     * @param isTypeBlank true to allow blank to the idType field, false otherwise
     */
    public void setAllowBlanks(boolean isValueBlank, boolean isTypeBlank) {
        value.setAllowBlank(isValueBlank);
        idType.setAllowBlank(isTypeBlank);
    }
}
