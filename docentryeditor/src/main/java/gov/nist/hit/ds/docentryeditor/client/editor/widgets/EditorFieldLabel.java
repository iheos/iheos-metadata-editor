package gov.nist.hit.ds.docentryeditor.client.editor.widgets;

import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

/**
 * Created by onh2 on 3/3/2015.
 */
public class EditorFieldLabel extends FieldLabel {
    public EditorFieldLabel(IsWidget widget, String label){
        super(widget,label);
        setLabelWidth(135);
    }
}
