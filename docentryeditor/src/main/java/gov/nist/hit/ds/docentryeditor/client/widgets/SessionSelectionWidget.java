package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;

/**
 * Created by onh2 on 1/24/17.
 */
public class SessionSelectionWidget extends ContentPanel {
    private SimpleComboBox sessionSelector=new SimpleComboBox(new StringLabelProvider());

    public SessionSelectionWidget(){
        this.setHeaderVisible(false);
        this.setBorders(false);
        this.setBodyBorder(false);
        setWidget(new FieldLabel(sessionSelector,"Select session"));
    }

    public class StringLabelProvider implements LabelProvider {
        @Override
        public String getLabel(Object item) {
            return item.toString();
        }
    }
}
