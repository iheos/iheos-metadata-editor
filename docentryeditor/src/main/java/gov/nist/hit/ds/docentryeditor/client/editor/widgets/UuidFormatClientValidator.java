package gov.nist.hit.ds.docentryeditor.client.editor.widgets;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.validator.AbstractValidator;

import java.util.List;
import java.util.logging.Logger;

/**
 * Bill's UUID format validator adapted for client side validation in the editor.
 * Created by onh2 on 8/28/2014.
 */
public class UuidFormatClientValidator extends AbstractValidator<String> {
    Logger logger = Logger.getLogger(this.getClass().getName());

    static private boolean isLCHexString(String value) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                    continue;
                default:
                    return false;
            }
        }
        return true;
    }

    @Override
    public List<EditorError> validate(Editor<String> editor, String value) {
        if (value == null)
            return createError(editor, "Can not be null", value);
        else if (!value.startsWith("urn:uuid:")) {
            if (value.length() == 36) {
                String tmp = value;
                tmp.replaceAll("-", "");
                if (isLCHexString(tmp)) {
                    return createError(editor, "Value is not a correct UUID. It does not have the right number of characters. It should be 45 and start with 'urn:uuid:'.", value);
                }
            }
//            return createError(editor, "Value does not start with 'urn:uuid:'. It should.", value);
        } else {
            if (value.length() != 45) {
                String tmp = value;
                if (isLCHexString(tmp.replaceAll("-", ""))) {
                    return createError(editor, "Value is not a correct UUID. It does not have the right number of characters. It should be 45 and start with 'urn:uuid:'.", value);
                }
            } else {
                logger.info("else");
                String rest;
                rest = value.substring(9);

                if (!isLCHexString(rest.substring(0, 8)))
                    return createError(editor, "Value should be Hexadecimal.", value);
                rest = rest.substring(8);

                if (!(rest.charAt(0) == '-'))
                    return createError(editor, "Value's 10th character should be a dash (-).", value);
                rest = rest.substring(1);

                if (!isLCHexString(rest.substring(0, 4)))
                    return createError(editor, "Value should be Hexadecimal.", value);
                rest = rest.substring(4);

                if (!(rest.charAt(0) == '-'))
                    return createError(editor, "Value's 15th character should be a dash (-).", value);

                rest = rest.substring(1);
                if (!isLCHexString(rest.substring(0, 4)))
                    return createError(editor, "Value should be Hexadecimal.", value);
                rest = rest.substring(4);

                if (!(rest.charAt(0) == '-'))
                    return createError(editor, "Value's 20th character should be a dash (-).", value);

                rest = rest.substring(1);
                if (!isLCHexString(rest.substring(0, 4)))
                    return createError(editor, "Value should be Hexadecimal.", value);
                rest = rest.substring(4);

                if (!(rest.charAt(0) == '-'))
                    return createError(editor, "Value's 25th character should be a dash (-).", value);

                rest = rest.substring(1);
                if (!isLCHexString(rest.substring(0, 12)))
                    return createError(editor, "Value should be Hexadecimal.", value);
            }
        }
        return null;
    }

}
