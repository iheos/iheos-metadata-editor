package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;

/**
 * Created by onh2 on 6/26/2014.
 */
public class FieldEmptyValidator<T> extends EmptyValidator<T> {
    public FieldEmptyValidator(final String fieldName){
        super();
        setMessages(new EmptyMessages() {
            @Override
            public String blankText() {
                return "The field "+fieldName+" is required. It can not be null.";
            }
        });
    }
}
