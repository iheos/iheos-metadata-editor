package gov.nist.hit.ds.docentryeditor.client.editor.association;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.AssociationTypeComboBox;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.String256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the view of the XDS Association editor.
 * It only handles the different widgets used to build the final
 * complete view of the editor.
 * It works with a presenter to handle the XdsAssociation model class.
 *
 * @see gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation
 * @see gov.nist.hit.ds.docentryeditor.client.editor.association.AssociationEditorPresenter
 */
public class AssociationEditorView extends AbstractView<AssociationEditorPresenter> implements Editor<XdsAssociation> {
    private static final int FIELD_BOTTOM_MARGIN = 10;

    private final VerticalLayoutContainer form = new VerticalLayoutContainer();
    private final VerticalLayoutContainer requiredFields = new VerticalLayoutContainer();
    private final VerticalLayoutContainer optionalFields = new VerticalLayoutContainer();

    @Inject
    String256EditorWidget id;
    @Inject
    AssociationTypeComboBox type;
    // TODO Create a combobox widget for enum XdsAssociationTypes?

    /**
     * This is the abstract method implementation that builds a collection of objects
     * mapping a String key to a Widget for the Association editor view.
     * (Can be useful for final validation and/or resize purposes)
     * @return Map of widgets for the association editor view.
     */
    @Override
    protected Map<String, Widget> getPathToWidgetsMap() {
        Map<String,Widget> map=new HashMap<String,Widget>();
        map.put("id",id);
        map.put("type",type);
        return map;
    }

    /**
     * This is the implementation of an abstract method supposed to construct
     * the Association editor view as a widget.
     * @return Association editor view as a Widget.
     */
    @Override
    protected Widget buildUI() {
        return null;
    }

    /**
     * Implementation of the abstract method that binds together
     * widgets actions (such as a button click) with presenter actions.
     */
    @Override
    protected void bindUI() {

    }
}
