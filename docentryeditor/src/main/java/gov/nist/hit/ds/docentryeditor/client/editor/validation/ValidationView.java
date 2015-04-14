package gov.nist.hit.ds.docentryeditor.client.editor.validation;

import com.google.gwt.user.client.ui.Widget;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents the view for Validation result panel.
 */
public class ValidationView extends AbstractView<ValidationPresenter> {

    /**
     * Implementation of the abstract getter that returns a map of
     * paths to widgets from IDs.
     * @return
     */
    @Override
    protected Map<String, Widget> getPathToWidgetsMap() {
        return new HashMap<String, Widget>();
    }

    /**
     * Implementation of the abstract method that builds
     * the UI widget for the Validation result panel.
     * @return Validation result panel widget
     */
    @Override
    protected Widget buildUI() {
        // TODO implement validation view
        return null;
    }

    /**
     * Implementation of the abstract method that binds the UI
     * widgets of the Validation result panel with their actions.
     */
    @Override
    protected void bindUI() {
        // TODO bind validation view actions

    }

}
