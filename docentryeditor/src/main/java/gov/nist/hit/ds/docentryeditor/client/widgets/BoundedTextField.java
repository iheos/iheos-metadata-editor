package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.google.gwt.dom.client.InputElement;
import com.sencha.gxt.widget.core.client.form.TextField;

/**
 * This class is an extend of Textfield with an limit of input character.
 * Its default limit is 256 characters.
 */
public class BoundedTextField extends TextField {

    /**
     * Default constructor, with default max length=256
     */
    public BoundedTextField() {
        super();
        setWidth("auto");
        setMaxLength(256);
    }

    public void setMaxLength(int length) {
        InputElement ie = this.getInputEl().cast();
        ie.setMaxLength(length);
    }

}
