package gov.nist.hit.ds.docentryeditor.client.editor.widgets.NameValueWidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.form.Validator;
import gov.nist.hit.ds.docentryeditor.client.editor.properties.String256Properties;
import gov.nist.hit.ds.docentryeditor.client.generics.GenericEditableListView;
import gov.nist.hit.ds.docentryeditor.client.generics.GridModelFactory;
import gov.nist.hit.ds.docentryeditor.client.widgets.BoundedTextField;
import gov.nist.hit.ds.docentryeditor.shared.model.DTM;
import gov.nist.hit.ds.docentryeditor.shared.model.NameValueString256;
import gov.nist.hit.ds.docentryeditor.shared.model.String256;

/**
 * <p>
 * <b>This class represents the widget which matches NameValueString256 model type</b>
 * </p>
 * @see gov.nist.hit.ds.docentryeditor.client.generics.GenericEditableListView
 */
public class NameValueString256EditorWidget extends GenericEditableListView<String256, String> implements Editor<NameValueString256> {
    // instance of the property access for the String256 entity attributes (for the GXT Store).
    private final static String256Properties props = GWT.create(String256Properties.class);

    // --- UI Widgets
    ListStoreEditor<String256> values;
    @Ignore
    BoundedTextField tf;

    /**
     * NameValueString256 editable list widget default constructor.
     * @param widgetTitle title of the widget (for the grid's panel header).
     */
    public NameValueString256EditorWidget(String widgetTitle) {
        super(widgetTitle, new ListStore<String256>(props.key()), props.string());
        buildUI();
    }

    /**
     * NameValueString256 editable list constructor.
     * @param widgetTitle title of the widget (for the grid's panel header).
     * @param listSize number of String256 objects that can be stored in the widget (list).
     */
    public NameValueString256EditorWidget(String widgetTitle,int listSize) {
        super(widgetTitle, new ListStore<String256>(props.key()), props.string());
        buildUI();
        setStoreMaxLength(listSize);
    }

    /**
     * Method that builds the widgets itself
     */
    private void buildUI() {
        values = new ListStoreEditor<String256>(getStore());
        bindUI();
    }

    /**
     * Method binding the UI with actions.
     */
    private void bindUI() {
//        editing.addCompleteEditHandler(new CompleteEditEvent.CompleteEditHandler<String256>() {
//            @Override
//            public void onCompleteEdit(CompleteEditEvent<String256> event) {
//                tf.validate();
//                if (tf.getValue() == null || !tf.isValid()) {
//                    tf.clear();
//                    editing.startEditing(event.getEditCell());
////                    editing.cancelEditing();
////                    view.refresh(false);
//                }
//            }
//        });
    }

    /**
     * This method assigns a specific field editor to the editable list widget for its edition mode.
     */
    @Override
    protected void buildEditingFields() {
        tf = new BoundedTextField();
        tf.setAllowBlank(false);
        tf.setToolTip("This value is required and must unique.");
        tf.setEmptyText("ex: 58642j65s^^^5.8.4");
        addEditorConfig(tf);
    }

    @Override
    protected GridModelFactory<String256> getModelFactory() {
        return String256Factory.instance;
    }

    /**
     * Sets the widget's tool tip with the given config
     * @param toolTip tips about the tool.
     */
    public void setEditingFieldToolTip(String toolTip) {
        tf.setToolTip(toolTip);
    }

    /**
     * This method sets the default text to display in an empty field (defaults
     * to null). It is done to help and guide the user during his input.
     * @param emptyText Default text displayed in an empty value field when editing
     */
    public void setEmptyTexts(String emptyText) {
        tf.setEmptyText(emptyText);
    }

    /**
     * Defines the maximum number of element that can be stored in the widget.
     * @param maxSize maximum number of element.
     */
    public void setListMaxSize(int maxSize) {
        setStoreMaxLength(maxSize);
    }

    /**
     * This method add a Validator to the editable list entry editing widget.
     * @param validator
     */
    public void addFieldValidator(Validator<String> validator) {
        tf.addValidator(validator);
    }

    /**
     * This method returns the list value provider.
     * @return String256 ValueProvider.
     */
    @Override
    protected ValueProvider<? super String256, String> getValueProvider() {
        return props.string();
    }
    //	/**
//	 * Sets whether a field is valid when its value length = 0 (default to
//	 * true). This will warn the user through the editor widget if he didn't
//	 * input anything in field which does not allow blank.
//	 *
//	 * @param nameAllowsBlank
//	 *            true to allow blank to the name field, false otherwise
//	 * @param valueAllowsBlank
//	 *            true to allow blank to the value field, false otherwise
//	 *
//	 */
//	public void setAllowBlanks(boolean nameAllowsBlank, boolean valueAllowsBlank) {
//		name.setAllowBlank(nameAllowsBlank);
//		value.setAllowBlank(valueAllowsBlank);
//	}
}
