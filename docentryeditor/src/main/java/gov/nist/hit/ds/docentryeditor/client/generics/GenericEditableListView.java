package gov.nist.hit.ds.docentryeditor.client.generics;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.Converter;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.editing.ClicksToEdit;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic widget for Row list grid editing on double click.
 * Created by onh2 on 6/11/2014.
 */
public abstract class GenericEditableListView<M, N> extends GenericEditableGrid<M> {
    protected ColumnConfig<M, N> cc1;
    // - constants
    private static final int COLUMN_WIDTH = 1000;

    /**
     * Abstract constructor
     * @param listTitle header for the editable list widget
     * @param listStore
     */
    public GenericEditableListView(String listTitle, ListStore<M> listStore) {
        super(listTitle, listStore);
        this.setHideHeaders(true);
    }

    protected abstract ValueProvider<? super M, N> getValueProvider();

    @Override
    protected ColumnModel<M> buildColumnModel() {
        List<ColumnConfig<M, ?>> columnsConfigs = new ArrayList<ColumnConfig<M, ?>>();
        cc1 = new ColumnConfig<M, N>(getValueProvider(), COLUMN_WIDTH);
        columnsConfigs.add(cc1);

        return new ColumnModel<M>(columnsConfigs);
    }

    public void addEditorConfig(Field<N> field) {
        // TODO consider exit edition on ENTER key press
        editing.addEditor(cc1, field);
    }

    public <O> void addEditorConfig(Converter<N, O> converter, final Field<O> field) {
        // TODO consider exit edition on ENTER key press
        editing.addEditor(cc1, converter, field);
    }

    /**
     * Should not be used, use addEditorConfig(Field<N> field) instead.
     *
     * @param columnConfig Grid's Column Configuration which will be associated to a type of editable field
     * @param field        Editable field which will be used to edit the grid's Column
     */
    @Deprecated
    @Override
    public <N> void addColumnEditorConfig(ColumnConfig<M, N> columnConfig, final Field<N> field) {
        super.addColumnEditorConfig(columnConfig, field);
    }

    public void setOneClickToEdit() {
        setEditable(ClicksToEdit.ONE);
    }
}
