package gov.nist.hit.ds.docentryeditor.client.editor.submissionSetEditor;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.AuthorWidgets.AuthorsListEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.CodedTermWidgets.CodedTermEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.CodedTermWidgets.PredefinedCodedTermComboBox;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.EditorFieldLabel;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.EditorToolbar;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.IdentifierWidgets.IdentifierOIDEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.IdentifierWidgets.IdentifierString256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.InternationalStringWidgets.InternationalStringEditableGrid;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.NameValueWidgets.NameValueDTMEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.NameValueWidgets.NameValueString256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.String256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.UuidFormatClientValidator;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;
import gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodes;
import gov.nist.hit.ds.docentryeditor.shared.model.InternationalString;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsSubmissionSet;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the view of the Submission Set editor.
 * It only handles the different widgets used to build the final
 * complete view of the editor.
 * It work with a presenter to handle the XdsSubmissionSet model.
 *
 * @see gov.nist.hit.ds.docentryeditor.shared.model.XdsSubmissionSet
 * @see gov.nist.hit.ds.docentryeditor.client.editor.submissionSetEditor.SubmissionSetEditorPresenter
 * Created by onh2 on 3/2/2015.
 */
public class SubmissionSetEditorView extends AbstractView<SubmissionSetEditorPresenter> implements Editor<XdsSubmissionSet>{
    private final VerticalLayoutContainer mainContainer = new VerticalLayoutContainer();

    VerticalLayoutContainer requiredFields = new VerticalLayoutContainer();
    VerticalLayoutContainer optionalFields = new VerticalLayoutContainer();

    // Required R in XDS (DS and DR) and in XDS DS.
    @Inject
    String256EditorWidget entryUUID;
    @Inject
    IdentifierOIDEditorWidget uniqueId;
    @Inject
    IdentifierOIDEditorWidget sourceId;
    NameValueDTMEditorWidget submissionTime=new NameValueDTMEditorWidget("Submission Time",false,1);
    @Inject
    IdentifierString256EditorWidget patientId;
    PredefinedCodedTermComboBox contentTypeCode=new PredefinedCodedTermComboBox(PredefinedCodes.CONTENT_TYPE_CODE);
    // Required if known R2 in XDS (DS and DR) and in XDS DS.
    @Inject
    AuthorsListEditorWidget authors;
    // Optional O in XDS (DS and DR) and in XDS DS.
    NameValueString256EditorWidget intendedRecipient=new NameValueString256EditorWidget("Intended Recipients");
    // ---- Titles Widgets
    ListStoreEditor<InternationalString> title;
    InternationalStringEditableGrid titleGrid;
    // ---- Comments Widgets
    ListStoreEditor<InternationalString> comments;
    InternationalStringEditableGrid commentsGrid;
    @Inject
    String256EditorWidget availabilityStatus;
    @Inject
    String256EditorWidget homeCommunityId; // maybe OIDEditorWidget

    // -- Button toolbars
    @Inject
    private EditorToolbar editorTopToolbar;
    @Inject
    private EditorToolbar editorBottomToolbar;

    /**
     * This is the abstract method implementation that builds a collection of objects
     * mapping a String key to a Widget for the Submission Set editor view.
     * @return Map of widgets for the Submission Set editor view.
     */
    @Override
    protected Map<String, Widget> getPathToWidgetsMap() {
        Map<String,Widget> map=new HashMap<String,Widget>();
        map.put("entryUUID",entryUUID);
        map.put("uniqueId",uniqueId);
        map.put("sourceId",sourceId);
        map.put("submissionTime",submissionTime);
        map.put("patientId",patientId);
        map.put("contentTypeCode",contentTypeCode);
        map.put("authors",authors.asWidget());
        map.put("intendedRecipient",intendedRecipient);
        map.put("title",titleGrid);
        map.put("comments",commentsGrid);
        map.put("availabilityStatus",availabilityStatus);
        map.put("homeCommunityId",homeCommunityId);
        return map;
    }

    /**
     * This is the implementation of the abstract method supposed to construct
     * the Submission Set editor view as a widget.
     * @return Submission Set editor view as a Widget.
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

        // Adding required and optional fields panels to the main container of editor view.
        container.add(editorTopToolbar, new VerticalLayoutContainer.VerticalLayoutData(-1,30));
        container.add(new HtmlLayoutContainer("<h2>Submission Set Editor</h2>"));
        container.add(new HtmlLayoutContainer("<h3>Required fields</h3>"));
        container.add(requiredFieldsContainer, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        container.add(new HtmlLayoutContainer("<h3>Optional fields</h3>"));
        container.add(optionalFieldsContainer, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));

        // /////////////////////////////////////// //
        // - Required "simple" fields.
        EditorFieldLabel entryUUIDLabel = new EditorFieldLabel(entryUUID,"Entry UUID");
        EditorFieldLabel uniqueIdLabel = new EditorFieldLabel(uniqueId,"Unique ID");
        EditorFieldLabel sourceIdlabel = new EditorFieldLabel(sourceId,"Source ID");
        EditorFieldLabel patientIdLabel = new EditorFieldLabel(patientId,"Patient ID");
        EditorFieldLabel contentTypeCodeLabel = new EditorFieldLabel(contentTypeCode.getDisplay(),"Content type code");

        // - Optional "simple" fields.
        EditorFieldLabel availabilityStatusLabel = new EditorFieldLabel(availabilityStatus,"Availability status");
        EditorFieldLabel homeCommunityIdLabel = new EditorFieldLabel(homeCommunityId,"Home community ID");

        // - Optional widget fields.
        titleGrid = new InternationalStringEditableGrid("Titles");
        title = new ListStoreEditor<InternationalString>(titleGrid.getStore());
        commentsGrid = new InternationalStringEditableGrid("Comments");
        comments = new ListStoreEditor<InternationalString>(commentsGrid.getStore());

        // - simple required fields container.
        VerticalLayoutContainer simpleRequiredFieldsContainer = new VerticalLayoutContainer();
        simpleRequiredFieldsContainer.add(entryUUIDLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(uniqueIdLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(sourceIdlabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(patientIdLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(contentTypeCodeLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));

        // - simple optional fields container.
        VerticalLayoutContainer simpleOptionalFieldsContainer = new VerticalLayoutContainer();
        simpleOptionalFieldsContainer.add(availabilityStatusLabel, new VerticalLayoutContainer.VerticalLayoutData(1,-1));
        simpleOptionalFieldsContainer.add(homeCommunityIdLabel, new VerticalLayoutContainer.VerticalLayoutData(1,-1));

        // - Required field set.
        FieldSet fieldSet_simpleRequired = new FieldSet();
        fieldSet_simpleRequired.setHeadingText("General");
        fieldSet_simpleRequired.setCollapsible(true);
        fieldSet_simpleRequired.add(simpleRequiredFieldsContainer);

        // - Optional field sets.
        FieldSet fieldSet_simpleOptional = new FieldSet();
        fieldSet_simpleOptional.setHeadingText("General");
        fieldSet_simpleOptional.setCollapsible(true);
        fieldSet_simpleOptional.add(simpleOptionalFieldsContainer);

        FieldSet fieldSet_authors = new FieldSet();
        fieldSet_authors.setHeadingText("Authors");
        fieldSet_authors.setCollapsible(true);
        fieldSet_authors.add(authors.asWidget());

        // Add every required fields to the required fields panel.
        requiredFields.add(fieldSet_simpleRequired,new VerticalLayoutContainer.VerticalLayoutData(1,-1, new Margins(0, 0, 10, 0)));
        requiredFields.add(submissionTime.getDisplay(),new VerticalLayoutContainer.VerticalLayoutData(1,-1, new Margins(0, 0, 10, 0)));
        // Add every optional fields to the optional fields panel.
        optionalFields.add(fieldSet_simpleOptional,new VerticalLayoutContainer.VerticalLayoutData(1,-1,new Margins(0,0,10,0)));
        optionalFields.add(fieldSet_authors,new VerticalLayoutContainer.VerticalLayoutData(1,-1,new Margins(0,0,10,0)));
        optionalFields.add(intendedRecipient.getDisplay(),new VerticalLayoutContainer.VerticalLayoutData(1,-1,new Margins(0,0,10,0)));
        optionalFields.add(titleGrid.getDisplay(),new VerticalLayoutContainer.VerticalLayoutData(1,-1,new Margins(0,0,10,0)));
        optionalFields.add(commentsGrid.getDisplay(),new VerticalLayoutContainer.VerticalLayoutData(1,-1,new Margins(0,0,10,0)));

        // configure help information and client validations for the entire editor.
        setWidgetsInfo();

        // Bottom toolbar container.
        SimpleContainer bottomToolbarContainer = new SimpleContainer();
        bottomToolbarContainer.setHeight(35);
        bottomToolbarContainer.add(editorBottomToolbar);
        optionalFields.add(bottomToolbarContainer);

        mainContainer.setScrollMode(ScrollMode.AUTO);
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
        mainContainer.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                mainContainer.forceLayout();
            }
        });
        SelectEvent.SelectHandler saveHandler = new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                presenter.doSave();
            }
        };
        editorTopToolbar.addSaveHandler(saveHandler);
        editorBottomToolbar.addSaveHandler(saveHandler);
        SelectEvent.SelectHandler cancelHandler = new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                presenter.rollbackChanges();
            }
        };
        editorTopToolbar.addCancelHandler(cancelHandler);
        editorBottomToolbar.addCancelHandler(cancelHandler);
    }

    public void refreshGridButtonsDisplay() {
        if (submissionTime.getStoreMaxSize() != 0 && submissionTime.getStore().size() >= submissionTime.getStoreMaxSize()) {
            submissionTime.disableNewButton();
        } else {
            submissionTime.enableNewButton();
        }
        if (commentsGrid != null) {
            if (commentsGrid.getStoreMaxSize() != 0 && commentsGrid.getStore().size() >= commentsGrid.getStoreMaxSize()) {
                commentsGrid.disableNewButton();
            } else {
                commentsGrid.enableNewButton();
            }
        }
        if (intendedRecipient != null) {
            if (intendedRecipient.getStoreMaxSize() != 0 && intendedRecipient.getStore().size() >= intendedRecipient.getStoreMaxSize()) {
                intendedRecipient.disableNewButton();
            } else {
                intendedRecipient.enableNewButton();
            }
        }
        if (titleGrid != null)
            if (titleGrid.getStoreMaxSize() != 0 && titleGrid.getStore().size() >= titleGrid.getStoreMaxSize()) {
                titleGrid.disableNewButton();
            } else {
                titleGrid.enableNewButton();
            }
    }

    /**
     * Configures information about each widgets to guide the user as well as
     * client side validations (Tooltips, empty texts and client validators).
     */
    private void setWidgetsInfo() {
        // availability status
        availabilityStatus.setAllowBlank(true);
        availabilityStatus.setEmptyText("ex: urn:oasis:names:tc:ebxml-regrep:StatusType:Approved");
        availabilityStatus.setToolTipConfig(new ToolTipConfig("It represents the status of the SubmissionSet. Since the deprecation of SubmissionSets is not\n" +
                "allowed, this value shall always be Approved.\n\nThe availabilityStatus value shall be \"urn:oasis:names:tc:ebxml-regrep:StatusType:Approved\""));
        // comments
        commentsGrid.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on comments","It contains comments associated with the SubmissionSet (Max length is unbounded)."));
        // content type code
        contentTypeCode.setEmptyText("Select a content type...");
        contentTypeCode.clear();
        contentTypeCode.setAllowBlank(false);
        // entry uuid
        entryUUID.setToolTipConfig(new ToolTipConfig("ID is a string", "It should contain less than 256 characters"));
        entryUUID.setAllowBlank(false);
        entryUUID.addValidator(new UuidFormatClientValidator());
        // home community id
        homeCommunityId.setAllowBlank(true);
        homeCommunityId.setEmptyText("ex: urn:oid:1.2.3");
        homeCommunityId.addValidator(new RegExValidator("^urn:oid:[1-9][0-9]*(\\.[1-9][0-9]*)+(\\^[1-9][0-9]+)?$", "Value's format is not a correct. It is supposed to be a suite of figures separated by periods preceded by \"urn:oid:\"."));
        homeCommunityId.setToolTipConfig(new ToolTipConfig("It is an OID URN.","It is a globally unique identifier for a community. It should be  an OID URN (ex: \"urn:oid:1.2.3\")."));
        // intended recipents
        // TODO Add regex validator
        intendedRecipient.setToolbarHelpButtonTooltip(new ToolTipConfig("Help of intended recipients","It represents the organization(s) or person(s) for whom the SubmissionSet is intended at time of\n" +
                "submission. Each slot value shall include at least one of the organization, person, or\n" +
                "telecommunications address fields described below. It is highly recommended to define the\n" +
                "organization for all the persons, avoiding errors in the transmission of the documents. " +
                "Values shall be formatted as <b>XON|XCN|XTN</b>."));
        intendedRecipient.setEmptyTexts("ex: \"Some Hospital^^^^^^^^^1.2.3.9.1789.45|^Wel^Marcus^^^Dr^MD|^^Internet^mwel@healthcare.example.org\"");
        intendedRecipient.setEditingFieldToolTip("The value shall be a <b>XON|XCN|XTN</b>\n" +
                "                where: <ul><li><b>XON</b> identifies the organization,</li> <li><b>XCN</b> identifies a person</li> <li><b>XTN</b> identifies the" +
                "                telecommunications.</li></ul>" +
                "                There is a \"<b>|</b>\" character separator between the organization and the person, and between the person and" +
                "                the telecommunications address, which is required when the person or the telecommunications" +
                "                address information is present.");
        // patient id
        patientId.setEmptyTexts("ex: 76cc^^1.3.6367.2005.3.7&ISO", "ex: urn:uuid:6b5aea1a-625s-5631-v4se-96a0a7b38446");
        patientId.setToolTipConfigs(new ToolTipConfig("Patient ID is a String256 in HL7 CX format", "The required format is:" +
                "IDNumber^^^&OIDofAssigningAuthority&ISO"), new ToolTipConfig(
                "idType is a string256 in HL7 CX format", "The required format is: " +
                "IDNumber^^^&OIDofAssigningAuthority&ISO"));
        patientId.setAllowBlanks(false, false);
        patientId.addValueFieldValidator(new RegExValidator("^(([A-Za-z])|([1-9]))*[0-9A-z]+\\^{3}&[1-9][0-9]*(\\.[1-9][0-9]*)+(&ISO)$", "Value's format is not a correct. \nIt should be like this: 6578946^^^&1.3.6.1.4.1.21367.2005.3.7&ISO."));
        // source id
        sourceId.setAllowBlanks(false,false);
        sourceId.addValueFieldValidator(new RegExValidator("^[1-9][0-9]*(\\.[1-9][0-9]*)+(\\^[1-9][0-9]+)?$", "Value's format is not a correct. It is supposed to be a suite of figures separated by periods."));
        sourceId.setEmptyTexts("ex: 1.3.6.1.4.1.21367.2005.3.7", "ex: 1.3.6.1.4.1.21367.2005.3.7");
        sourceId.setToolTipConfigs(new ToolTipConfig("Source ID is an OID", "Source ID is the globally unique, immutable, identifier of the entity that contributed the SubmissionSet. It is an OID, as defined in the HL7 implementation for OID (http://www.hl7.org/implement/standards/product_brief.cfm?product_id=210)<br/>Unique ID format is \"[1-9](\\.[0-9]+)*]\""),
                new ToolTipConfig(
                        "idType is an OID",
                        "As defined in the HL7 implementation for OID (http://www.hl7.org/implement/standards/product_brief.cfm?product_id=210)<br/>Unique ID format is \"[1-9](\\.[0-9]+)*]\""));
        // submission time
        submissionTime.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on Submission time", "Represents the point in time at the creating entity when the Submission Set was submitted.\n" +
                "This shall be provided by the submitting system."));
        // title
        titleGrid.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on Titles", "Represents the title of the Submission Set. The format of a title shall be any string of length less than 256 characters.\n"));
        // Unique ID
        uniqueId.setAllowBlanks(false, false);
        uniqueId.addValueFieldValidator(new RegExValidator("^[1-9][0-9]*(\\.[1-9][0-9]*)+(\\^[1-9][0-9]+)?$", "Value's format is not a correct. It is supposed to be a suite of figures separated by periods."));
        uniqueId.setEmptyTexts("ex: 2008.8.1.35447^5846", "ex: 2008.8.1.35447");
        uniqueId.setToolTipConfigs(
                new ToolTipConfig("Unique ID is an OID", "As defined in the HL7 implementation for OID (http://www.hl7.org/implement/standards/product_brief.cfm?product_id=210)<br/>Unique ID format is \"[1-9](\\.[0-9]+)*]\""),
                new ToolTipConfig(
                        "idType is an OID",
                        "As defined in the HL7 implementation for OID (http://www.hl7.org/implement/standards/product_brief.cfm?product_id=210)<br/>Unique ID format is \"[1-9](\\.[0-9]+)*]\""));
    }
}
