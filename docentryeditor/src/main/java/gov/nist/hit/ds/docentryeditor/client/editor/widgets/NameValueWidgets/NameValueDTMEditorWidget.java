package gov.nist.hit.ds.docentryeditor.client.editor.widgets.NameValueWidgets;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import gov.nist.hit.ds.docentryeditor.client.editor.properties.DTMProperties;
import gov.nist.hit.ds.docentryeditor.client.generics.GenericEditableListView;
import gov.nist.hit.ds.docentryeditor.client.generics.GridModelFactory;
import gov.nist.hit.ds.docentryeditor.shared.model.DTM;
import gov.nist.hit.ds.docentryeditor.shared.model.NameValueDTM;

import java.util.Date;

/**
 * <p>
 * <b>This class represents the widget which matches the NameValueDTM model type</b> <br>
 * </p>
 *
 * @see gov.nist.hit.ds.docentryeditor.client.generics.GenericEditableListView
 */
public class NameValueDTMEditorWidget extends GenericEditableListView<DTM, Date> implements Editor<NameValueDTM> {
    // instance of the property access for the DTM entity attributes (for the GXT Store).
    private final static DTMProperties props = GWT.create(DTMProperties.class);

    // --- UI Widgets
    ListStoreEditor<DTM> values;
    @Ignore
    private DateField df;


    /**
     * NameValueDTM editable list constructor.
     * @param widgetTitle title of the widget (for the grid's panel header).
     */
    public NameValueDTMEditorWidget(String widgetTitle) {
        super(widgetTitle, new ListStore<DTM>(props.key()), props.dtm());
        // init list store that will handle the list of DTM objects
        values = new ListStoreEditor<DTM>(getStore());
        bind();
    }

    /**
     * NameValueDTM editable list constructor.
     * @param widgetTitle title of the widget (for the grid's panel header).
     * @param listSize number of DTM objects that can be stored in the widget (list).
     */
    public NameValueDTMEditorWidget(String widgetTitle,int listSize) {
        this(widgetTitle);
        setStoreMaxLength(listSize);
    }

    public NameValueDTMEditorWidget(String widgetTitle, boolean isToolbarVisible, int listSize) {
        this(widgetTitle);
        if(!isToolbarVisible){
            disableToolbar();
        }
        setStoreMaxLength(listSize);
    }

    /**
     * Method that constructs the widget itself.
     */
    private void buildUI() {

    }

    /**
     * Method binding the view with actions.
     */
    private void bind() {
        // FIXME not working asked a question on stackoverflow waiting for answers
//        editing.addCompleteEditHandler(new CompleteEditEvent.CompleteEditHandler<DTM>() {
//            @Override
//            public void onCompleteEdit(CompleteEditEvent<DTM> event) {
//                df.clearInvalid();
//                df.validate();
//                if (df.getValue() == null || !df.isValid()) {
//                    getStore().clear();
//                    DTM e = GWT.create(DTM.class);
//                    getStore().add(e);
//                    editing.startEditing(event.getEditCell());
//                    df.forceInvalid("This date does not respect the expeted format");
//                } else {
////                    editing.cancelEditing();
////                    editing.completeEditing();
//                }
//            }
//        });
    }


    /**
     * This method assigns a specific field editor to the editable list widget for its edition mode.
     */
    @Override
    protected void buildEditingFields() {
        DateTimeFormat dtf = DateTimeFormat.getFormat("yyyyMMddHHmmss");

        df = new DateField();
        df.setPropertyEditor(new DateTimePropertyEditor(dtf));
        df.setToolTip("This value is required. The format of these values is defined as following: YYYY[MM[DD[hh[mm[ss]]]]]; YYYY is the four digit year (ex: 2014); MM is the two digit month 01-12, where January is 01, December is 12; DD is the two digit day of the month 01-31; HH is the two digit hour, 00-23, where 00 is midnight, 01 is 1 am, 12 is noon, 13 is 1 pm; mm is the two digit minute, 00-59; ss is the two digit seconds, 00-59");
        df.setEmptyText("YYYY[MM[DD[hh[mm[ss]]]]] (ex: 201103160830)");

        addEditorConfig(df);

        // Modifying grid cell render for a Date
        Cell c = new DateCell(DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss"));
        getColumnModel().getColumn(0).setCell(c);
    }

    /**
     * This method return the Model factory that must be used for this list editor.
     * @return DTM factory.
     */
    @Override
    protected GridModelFactory<DTM> getModelFactory() {
        return DTMFactory.instance;
    }

    /**
     * Defines the maximum number of element that can be stored in the widget.
     * @param maxSize maximum number of element.
     */
    public void setListMaxSize(int maxSize) {
        setStoreMaxLength(maxSize);
    }

    /**
     * This method returns the list value provider.
     * @return DTM value provider.
     */
    @Override
    protected ValueProvider<DTM, Date> getValueProvider() {
        return props.dtm();
    }
}
