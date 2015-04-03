package gov.nist.hit.ds.docentryeditor.client.editor.widgets;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.widget.core.client.Composite;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.Validator;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import gov.nist.hit.ds.docentryeditor.shared.model.OID;

/**
 * <p>
 * <b>This class represents the widget which matches OID model type</b> <br>
 * </p>
 */
public class OIDEditorWidget extends Composite implements Editor<OID> {
    private final VerticalLayoutContainer vcontainer = new VerticalLayoutContainer();
    String256EditorWidget oid = new String256EditorWidget();

    public OIDEditorWidget(boolean displayLabel) {
        initWidget(vcontainer);

        if (displayLabel) {
            FieldLabel oidLabel = new FieldLabel(oid, "OID");
            oidLabel.setLabelWidth(125);
            vcontainer.add(oidLabel, new VerticalLayoutData(1, -1));
        } else {
            vcontainer.add(oid, new VerticalLayoutData(1, -1));
        }
    }

    /**
     * Adds a field validator that is meants to check the input oid.
     * @param validator
     */
    public void addOIDValidator(Validator<String> validator) {
        oid.addValidator(validator);
    }

    /**
     * This method sets the default text to display in an empty field (defaults
     * to null). It is done to help and guide the user during his input.
     *
     * @param oidEmptyText Default text displayed in an empty field
     */
    public void setEmptyText(String oidEmptyText) {
        oid.setEmptyText(oidEmptyText);
    }

    /**
     * Sets the widget's tool tip with the given config
     *
     * @param config
     */
    public void setToolTipConfig(ToolTipConfig config) {
        oid.setToolTipConfig(config);
    }

    /**
     * Sets whether a field is valid when its value length = 0 (default to
     * true). This will warn the user through the editor widget if he didn't
     * input anything in field which does not allow blank.
     *
     * @param allowBlank true to allow blanks, false otherwise
     */
    public void setAllowBlank(boolean allowBlank) {
        oid.setAllowBlank(allowBlank);
    }
}
