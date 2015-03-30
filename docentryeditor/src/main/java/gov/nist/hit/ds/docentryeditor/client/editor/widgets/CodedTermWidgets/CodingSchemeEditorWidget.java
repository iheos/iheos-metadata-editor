package gov.nist.hit.ds.docentryeditor.client.editor.widgets.CodedTermWidgets;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.Composite;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.String256EditorWidget;
import gov.nist.hit.ds.docentryeditor.shared.model.CodingScheme;

/**
 * <p>
 * <b>This class represents the widget which matches CodingScheme model type</b>
 * </p>
 */
public class CodingSchemeEditorWidget extends Composite implements Editor<CodingScheme> {
    VerticalLayoutContainer vcontainer = new VerticalLayoutContainer();
    String256EditorWidget codingScheme = new String256EditorWidget();

    /**
     * Default Constructor.
     */
    public CodingSchemeEditorWidget() {
        initWidget(vcontainer);
        vcontainer.add(codingScheme);
    }

    /**
     * This method sets the default text to display in an empty field (defaults
     * to null). It is done to help and guide the user during his input.
     *
     * @param codingSchemeEmptyText Default text displayed in an empty coding scheme field
     */
    public void setEmptyText(String codingSchemeEmptyText) {
        codingScheme.setEmptyText(codingSchemeEmptyText);
    }

    /**
     * Sets the widget's tool tip with the given config
     *
     * @param config
     */
    public void setToolTipConfig(ToolTipConfig config) {
        codingScheme.setToolTipConfig(config);
    }

    /**
     * Sets whether a field is valid when its value length = 0 (default to
     * true). This will warn the user through the editor widget if he didn't
     * input anything in field which does not allow blank.
     *
     * @param allowBlank true to allow blanks, false otherwise
     */
    public void setAllowBlank(boolean allowBlank) {
        codingScheme.setAllowBlank(allowBlank);
    }
}
