package gov.nist.hit.ds.docentryeditor.client.root.submission;

import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

/**
 * Created by onh2 on 5/18/2015.
 */
public interface SubmissionMenuDataProperties extends PropertyAccess<SubmissionMenuData> {

    ModelKeyProvider<SubmissionMenuData> key();

    @Editor.Path("value")
    LabelProvider<SubmissionMenuData> valueLabel();

    ValueProvider<SubmissionMenuData, String> value();

    class SubmissionValueProvider implements ValueProvider<SubmissionMenuData,SubmissionMenuData>{

        /**
         * Returns the property value of the given object.
         *
         * @param object the target object
         * @return the property value
         */
        public SubmissionMenuData getValue(SubmissionMenuData object) {
            return object;
        }

        /**
         * Sets the value of the given object
         *
         * @param object target object, whose values needs to be changed.
         * @param value
         */
        @Override
        public void setValue(SubmissionMenuData object, SubmissionMenuData value) {
            object.setValue(value.getValue());
            object.setModel(value.getModel());
            object.setIsActiveLink(value.isActiveLink());
            object.setKey(value.getKey());
        }

        /**
         * Returns the path that this ValueProvider makes available, from the object,
         * to the value.
         *
         * @return the path from the object to the value
         */
        @Override
        public String getPath() {
            return "this";
        }
    }
}
