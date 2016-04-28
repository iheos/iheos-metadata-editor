package gov.nist.hit.ds.docentryeditor.client.editor.widgets;


import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SpinnerField;

/**
 * <p>
 * <b>This class represents the widget which matches Integer model
 * type for edition.</b> <br>
 * </p>
 */
public class IntegerEditorWidget extends SpinnerField<Integer> implements Editor<Integer> {
    @Ignore
    private static final int SPINNER_INCREMENT = 5;

    /**
     * Integer editor constructor.
     */
    public IntegerEditorWidget() {
        super(new NumberPropertyEditor.IntegerPropertyEditor());
        buildUI();
    }

    /**
     * This method configures the parameters of the widget.
     */
    private void buildUI() {
        this.setAllowBlank(false);
        this.setWidth("auto");
        this.setMinValue(1);
        this.setIncrement(SPINNER_INCREMENT);
    }

}
