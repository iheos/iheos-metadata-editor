package gov.nist.hit.ds.docentryeditor.client.event;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.widget.core.client.form.Field;

/**
 * Created by onh2 on 5/20/2015.
 */
public class SelectionChangeEditorHandler<T> implements SelectionHandler<T> {

    /**
     * Called when {@link SelectionEvent} is fired.
     *
     * @param event the {@link SelectionEvent} that was fired
     */
    @Override
    public void onSelection(SelectionEvent event) {
        ((Field) event.getSource()).validate();
        ((Field) event.getSource()).finishEditing();
    }
}
