package gov.nist.hit.ds.docentryeditor.client.editor.widgets.namevalue;


import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.form.SpinnerField;
import gov.nist.hit.ds.docentryeditor.client.generics.GenericEditableListView;
import gov.nist.hit.ds.docentryeditor.client.generics.GridModelFactory;
import gov.nist.hit.ds.docentryeditor.shared.model.NameValueInteger;

/**
 * <p>
 * <b>This class represents the widget which matches NameValueInteger model
 * type</b> <br>
 * </p>
 * @see gov.nist.hit.ds.docentryeditor.client.generics.GenericEditableListView
 */
public class NameValueIntegerEditorWidget extends GenericEditableListView<Integer, String> implements Editor<NameValueInteger> {
    @Ignore
    private static final int SPINNER_INCREMENT = 5;
    ListStoreEditor<Integer> values;

    @Ignore
    private SpinnerField<Integer> value;

    /**
     * NameValueInteger editable list default constructor.
     * @param widgetTitle title of the widget (for the grid's panel header).
     */
    public NameValueIntegerEditorWidget(String widgetTitle) {
        super(widgetTitle, new ListStore<Integer>(new IntegerKeyProvider()));
        values = new ListStoreEditor<Integer>(getStore());
    }

    /**
     * This method assigns a specific field editor to the editable list widget for its edition mode.
     */
    @Override
    protected void buildEditingFields() {
        value = new SpinnerField<Integer>(new NumberPropertyEditor.IntegerPropertyEditor());
        value.setAllowBlank(false);
        value.setWidth("auto");
        value.setMinValue(1);
        value.setIncrement(SPINNER_INCREMENT);

        Converter<String, Integer> converter = new Converter<String, Integer>() {
            @Override
            public String convertFieldValue(Integer object) {
                String v = "";
                if (object != null) {
                    v = object.toString();
                }
                return v;
            }

            @Override
            public Integer convertModelValue(String object) {
                Integer v = 0;
                if (object != null && object.trim().length() > 0) {
                    v = Integer.parseInt(object);
                }
                return v;
            }
        };

        addEditorConfig(converter, value);
    }

    /**
     * This method return the Model factory that must be used for this list editor.
     * @return Integer factory.
     */
    @Override
    protected GridModelFactory<Integer> getModelFactory() {
        return IntegerFactory.INSTANCE;
    }

    /**
     * Defines the maximum number of element that can be stored in the widget.
     * @param listMaxSize maximum number of element.
     */
    public void setListMaxSize(int listMaxSize) {
        this.setStoreMaxLength(listMaxSize);
    }

    /**
     * This method returns the list value provider.
     * @return Integer ValueProvider.
     */
    @Override
    protected ValueProvider<Integer, String> getValueProvider() {
        return new IntegerValueProvider();
    }

    // Custom KeyProvider to deal with Integer
    public static class IntegerKeyProvider implements ModelKeyProvider<Integer> {
        @Override
        public String getKey(Integer item) {
            return item.toString();
        }
    }

    /**
     * Class that defines an Integer ValueProvider.
     * It is a custom ValueProvider to deal with Integer.
     */
    public static class IntegerValueProvider implements ValueProvider<Integer, String> {

        @Override
        public String getValue(Integer object) {
            return object.toString();
        }

        @Override
        public void setValue(Integer object, String value) {
            object = Integer.parseInt(value);
        }

        @Override
        public String getPath() {
            return null;
        }

    }
}
