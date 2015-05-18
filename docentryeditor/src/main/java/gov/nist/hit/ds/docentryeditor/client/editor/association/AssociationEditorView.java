package gov.nist.hit.ds.docentryeditor.client.editor.association;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.*;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;
import gov.nist.hit.ds.docentryeditor.client.widgets.EditorToolbar;
import gov.nist.hit.ds.docentryeditor.shared.model.AvailabilityStatus;
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

    private final VerticalLayoutContainer mainContainer = new VerticalLayoutContainer();
    private final VerticalLayoutContainer requiredFields = new VerticalLayoutContainer();
    private final VerticalLayoutContainer optionalFields = new VerticalLayoutContainer();

    @Inject
    String256EditorWidget id;
    @Inject
    AssociationTypeComboBox type;
    @Inject
    AvailabilityStatusComboBox availabilityStatus;
    @Inject
    SubmissionSetStatusComboBox submissionSetStatus;
    @Inject
    String256EditorWidget source;
    @Inject
    String256EditorWidget target;

    @Inject
    private EditorToolbar editorTopToolbar;
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
        map.put("source",source);
        map.put("target",target);
        map.put("availabilityStatus",availabilityStatus);
        map.put("submissionSetStatus",submissionSetStatus);
        return map;
    }

    /**
     * This is the implementation of an abstract method supposed to construct
     * the Association editor view as a widget.
     * @return Association editor view as a Widget.
     */
    @Override
    protected Widget buildUI() {
        VerticalLayoutContainer container=new VerticalLayoutContainer();
        container.getElement().setMargins(10);
        container.setBorders(false);

        SimpleContainer requiredFieldsContainer = new SimpleContainer();
        requiredFieldsContainer.setTitle("Required fields");
        SimpleContainer optionalFieldsContainer = new SimpleContainer();
        optionalFieldsContainer.setTitle("Optional fields");

        requiredFieldsContainer.add(requiredFields);
        optionalFieldsContainer.add(optionalFields);

        editorTopToolbar.removeButton(false,false,false,true);

        // Adding required and optional fields panels to the main container of editor view.
        container.add(editorTopToolbar, new VerticalLayoutContainer.VerticalLayoutData(-1, -1));
        container.add(new HtmlLayoutContainer("<h2>Association Editor</h2>"));
        container.add(new HtmlLayoutContainer("<h3>Required fields</h3>"));
        container.add(requiredFieldsContainer, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        container.add(new HtmlLayoutContainer("<h3>Optional fields</h3>"));
        container.add(optionalFieldsContainer, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));

        // /////////////////////////////////////// //
        // - Required fields.
        EditorFieldLabel idLabel = new EditorFieldLabel(id,"Association ID");
        EditorFieldLabel typeLabel = new EditorFieldLabel(type,"Association type");
        EditorFieldLabel sourceLabel = new EditorFieldLabel(source,"Source object");
        EditorFieldLabel targetLabel = new EditorFieldLabel(target,"Target object");

        // - Optional fields.
        EditorFieldLabel availabilityStatusLabel = new EditorFieldLabel(availabilityStatus,"Availability status");
        EditorFieldLabel statusLabel = new EditorFieldLabel(submissionSetStatus,"Submission set status");

        // - simple required fields container.
        VerticalLayoutContainer simpleRequiredFieldsContainer = new VerticalLayoutContainer();
        simpleRequiredFieldsContainer.add(idLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(typeLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(sourceLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(targetLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        // - simple optional fields container.
        VerticalLayoutContainer simpleOptionalFieldsContainer = new VerticalLayoutContainer();
        simpleOptionalFieldsContainer.add(availabilityStatusLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        simpleOptionalFieldsContainer.add(statusLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        // - Required fields set.
        FieldSet simpleRequiredFieldSet = new FieldSet();
        simpleRequiredFieldSet.setHeadingText("General");
        simpleRequiredFieldSet.setCollapsible(true);
        simpleRequiredFieldSet.add(simpleRequiredFieldsContainer);

        // - Optional fields set.
        FieldSet statusOptionalFieldSet = new FieldSet();
        statusOptionalFieldSet.setHeadingText("Status");
        statusOptionalFieldSet.setCollapsible(true);
        statusOptionalFieldSet.add(simpleOptionalFieldsContainer);

        // Add every required fields to the required fields panel.
        requiredFields.add(simpleRequiredFieldSet, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        // Add every optional fields to the optional fields panel.
        optionalFields.add(statusOptionalFieldSet, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));

        // configure help information and client validations for the entire editor.
//        setWidgetsInfo();

        mainContainer.setScrollMode(ScrollSupport.ScrollMode.AUTO);
        mainContainer.add(container);
        mainContainer.forceLayout();

        return mainContainer;
    }

    /**
     * Implementation of the abstract method that binds together
     * widgets actions (such as a button click) with presenter actions.
     */
    @Override
    protected void bindUI() {

    }
}
