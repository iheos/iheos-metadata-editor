package gov.nist.hit.ds.docentryeditor.client.editor.subset;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.EditorFieldLabel;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.String256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.UuidFormatClientValidator;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.author.AuthorsListEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.codedterm.CodedTermsEditableGridWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.codedterm.PredefinedCodedTermComboBox;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.identifier.IdentifierOIDEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.identifier.IdentifierString256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.internatinationalstring.InternationalStringEditableGrid;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.namevalue.NameValueDTMEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.namevalue.NameValueString256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;
import gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodes;
import gov.nist.hit.ds.docentryeditor.client.resources.ClientFormatValidationResource;
import gov.nist.hit.ds.docentryeditor.client.resources.ToolTipResources;
import gov.nist.hit.ds.docentryeditor.client.widgets.EditorToolbar;
import gov.nist.hit.ds.docentryeditor.client.widgets.StandardSelector;
import gov.nist.hit.ds.docentryeditor.shared.model.CodedTerm;
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
 * @see gov.nist.hit.ds.docentryeditor.client.editor.subset.SubmissionSetEditorPresenter
 * Created by onh2 on 3/2/2015.
 */
public class SubmissionSetEditorView extends AbstractView<SubmissionSetEditorPresenter> implements Editor<XdsSubmissionSet>{
    public static final int FIELD_BOTTOM_MARGIN = 10;

    private final VerticalLayoutContainer mainContainer = new VerticalLayoutContainer();
    private VerticalLayoutContainer requiredFields = new VerticalLayoutContainer();
    private VerticalLayoutContainer optionalFields = new VerticalLayoutContainer();

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
    private InternationalStringEditableGrid titleGrid;
    // ---- Comments Widgets
    ListStoreEditor<InternationalString> comments;
    private InternationalStringEditableGrid commentsGrid;
    @Inject
    String256EditorWidget availabilityStatus;
    @Inject
    String256EditorWidget homeCommunityId; // OIDEditorWidget?

    // -- Button toolbars
    @Inject
    private EditorToolbar editorTopToolbar;
    @Inject
    private EditorToolbar editorBottomToolbar;

    @Inject
    private StandardSelector selector;
    private Map<String, String> selectedStandardProperties;
    private VerticalLayoutContainer container;

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
        map.put("authors", authors.asWidget());
        map.put("intendedRecipient", intendedRecipient);
        map.put("title", titleGrid);
        map.put("comments", commentsGrid);
        map.put("availabilityStatus", availabilityStatus);
        map.put("homeCommunityId", homeCommunityId);
        return map;
    }

    /**
     * This is the implementation of the abstract method supposed to construct
     * the Submission Set editor view as a widget.
     * @return Submission Set editor view as a Widget.
     */
    @Override
    protected Widget buildUI() {
        container = new VerticalLayoutContainer();
        container.setBorders(false);

        selectedStandardProperties = selector.getStdPropertiesMap();
        if (selectedStandardProperties==null){
            presenter.retrieveDefaultStandardProperties();
        }else{
            updateEditorUI(selectedStandardProperties);
        }
        updateEditorUI(selector.getStdPropertiesMap());

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
        SelectEvent.SelectHandler populateHandler = new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                presenter.populate();
            }
        };
        editorTopToolbar.addPopulateHandler(populateHandler);
        editorBottomToolbar.addPopulateHandler(populateHandler);
        SelectEvent.SelectHandler expandHandler= new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                expandAll();
            }
        };
        editorBottomToolbar.addExpandHandler(expandHandler);
        editorTopToolbar.addExpandHandler(expandHandler);
        SelectEvent.SelectHandler collapseHandler=new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                collapseAll();
            }
        };
        editorTopToolbar.addCollapseHandler(collapseHandler);
        editorBottomToolbar.addCollapseHandler(collapseHandler);
    }

    /**
     * This method collapses all the collapsible panel in the Sbumission Set editor view.
     */
    public void collapseAll() {
        submissionTime.collapse();
        authors.collapse();
        intendedRecipient.collapse();
        if (titleGrid!=null) {
            titleGrid.collapse();
        }
        if (commentsGrid!=null) {
            commentsGrid.collapse();
        }
    }

    /**
     * This method collapses all the collapsible panel in the Sbumission Set editor view.
     */
    public void expandAll() {
        submissionTime.expand();
        authors.expand();
        intendedRecipient.expand();
        titleGrid.expand();
        commentsGrid.expand();
    }

    public void refreshGridButtonsDisplay() {
        submissionTime.refreshNewButton();
        if (commentsGrid!=null) {
            commentsGrid.refreshNewButton();
        }
        if (intendedRecipient != null) {
            intendedRecipient.refreshNewButton();
        }
        if (titleGrid != null){
            titleGrid.refreshNewButton();
        }
    }

    /**
     * Configures information about each widgets to guide the user as well as
     * client side validations (Tooltips, empty texts and client validators).
     */
    private void setWidgetsInfo() {
        // availability status
        availabilityStatus.setAllowBlank(!isRequired("subSetAvailabilityStatus"));
        availabilityStatus.setEmptyText("ex: urn:oasis:names:tc:ebxml-regrep:StatusType:Approved");
        availabilityStatus.setToolTipConfig(ToolTipResources.INSTANCE.getAvailabilityStatusToolTipConfig());
        // comments
        commentsGrid.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getSubSetCommentsTooltipConfig());
        // content type code
        contentTypeCode.setEmptyText("Select a content type...");
        contentTypeCode.clear();
        contentTypeCode.setAllowBlank(!isRequired("subSetContentTypeCode"));
        // entry uuid
        entryUUID.setToolTipConfig(new ToolTipConfig("Entry UUID is a string", ToolTipResources.INSTANCE.getString256ToolTip()));
        entryUUID.setAllowBlank(!isRequired("subSetEntryUUID"));
        entryUUID.addValidator(new UuidFormatClientValidator());
        // home community id
        homeCommunityId.setAllowBlank(!isRequired("subSetHomeCommunityId"));
        homeCommunityId.setEmptyText("ex: urn:oid:1.2.3");
        homeCommunityId.addValidator(ClientFormatValidationResource.INSTANCE.getHomeCommunityIdRegExpValidator());
        homeCommunityId.setToolTipConfig(ToolTipResources.INSTANCE.getHomeCommunityIdTooltipConfig());
        // intended recipents
        // TODO Add regex validator
        intendedRecipient.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getIntendedRecipientHelpTooltipConfig());
        intendedRecipient.setEmptyTexts("ex: \"Some Hospital^^^^^^^^^1.2.3.9.1789.45|^Wel^Marcus^^^Dr^MD|^^Internet^mwel@healthcare.example.org\"");
        intendedRecipient.setEditingFieldToolTip(ToolTipResources.INSTANCE.getIntendedRecipientEditorTooltip());
        // patient id
        patientId.setEmptyTexts("ex: 76cc^^1.3.6367.2005.3.7&ISO", "ex: urn:uuid:6b5aea1a-625s-5631-v4se-96a0a7b38446");
        patientId.setToolTipConfigs(ToolTipResources.INSTANCE.getPatientIdTooltipConfig());
        patientId.setAllowBlanks(!isRequired("subSetPatientID"));
        patientId.addValueFieldValidator(ClientFormatValidationResource.INSTANCE.getPatientIDRegExpValidator());
        // source id
        sourceId.setAllowBlanks(!isRequired("subSetSourceID"));
        sourceId.addValueFieldValidator(ClientFormatValidationResource.INSTANCE.getSourceIdRegExpValidator());
        sourceId.setEmptyTexts("ex: 1.3.6.1.4.1.21367.2005.3.7", "ex: 1.3.6.1.4.1.21367.2005.3.7");
        sourceId.setToolTipConfigs(ToolTipResources.INSTANCE.getSourceIdTooltipConfig());
        // submission time
        submissionTime.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getSubmissionTimeToolTipConfig());
        // title
        titleGrid.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getSubSetTitleTooltipConfig());
        // Unique ID
        uniqueId.setAllowBlanks(!isRequired("subSetUniqueID"));
        uniqueId.addValueFieldValidator(ClientFormatValidationResource.INSTANCE.getUniqueIdRegExpValidator());
        uniqueId.setEmptyTexts("ex: 2008.8.1.35447^5846", "ex: 2008.8.1.35447");
        uniqueId.setToolTipConfigs(ToolTipResources.INSTANCE.getUniqueIdTooltipConfig());
    }

    public void updateEditorUI(Map<String, String> selectedStandardProperties) {
        this.selectedStandardProperties = selectedStandardProperties;
        requiredFields.clear();
        optionalFields.clear();
        container.clear();

        container.setBorders(false);

        SimpleContainer requiredFieldsContainer = new SimpleContainer();
        requiredFieldsContainer.setTitle("Required fields");
        SimpleContainer optionalFieldsContainer = new SimpleContainer();
        optionalFieldsContainer.setTitle("Optional fields");

        requiredFieldsContainer.add(requiredFields);
        optionalFieldsContainer.add(optionalFields);

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
        VerticalLayoutContainer simpleOptionalFieldsContainer = new VerticalLayoutContainer();
        if (isRequired("subSetEntryUUID")) {
            simpleRequiredFieldsContainer.add(entryUUIDLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }else{
            simpleOptionalFieldsContainer.add(entryUUIDLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }
        if (isRequired("subSetUniqueID")) {
            simpleRequiredFieldsContainer.add(uniqueIdLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }else{
            simpleOptionalFieldsContainer.add(uniqueIdLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }
        if (isRequired("subSetSourceID")) {
            simpleRequiredFieldsContainer.add(sourceIdlabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }else{
            simpleOptionalFieldsContainer.add(sourceIdlabel,new VerticalLayoutContainer.VerticalLayoutData(1,-1));
        }
        if (isRequired("subSetPatientID")) {
            simpleRequiredFieldsContainer.add(patientIdLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }else{
            simpleOptionalFieldsContainer.add(patientIdLabel, new VerticalLayoutContainer.VerticalLayoutData(1,-1));
        }
        if (isRequired("subSetContentTypeCode")) {
            simpleRequiredFieldsContainer.add(contentTypeCodeLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }else{
            simpleOptionalFieldsContainer.add(contentTypeCodeLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }
        if (isRequired("subSetAvailabilityStatus")) {
            simpleRequiredFieldsContainer.add(availabilityStatusLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }else{
            simpleOptionalFieldsContainer.add(availabilityStatusLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }
        if (isRequired("subSetHomeCommunityId")) {
            simpleRequiredFieldsContainer.add(homeCommunityIdLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }else{
            simpleOptionalFieldsContainer.add(homeCommunityIdLabel, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        }

        // Add every required fields to the required fields panel.
        requiredFields.add(simpleRequiredFieldsContainer,new VerticalLayoutContainer.VerticalLayoutData(1,-1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        if (isRequired("subSetSubmissionTime")) {
            requiredFields.add(submissionTime.getDisplay(), new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(submissionTime.getDisplay(),new VerticalLayoutContainer.VerticalLayoutData(1,-1,new Margins(0,0,FIELD_BOTTOM_MARGIN,0)));
        }
        // Add every optional fields to the optional fields panel.
        optionalFields.add(simpleOptionalFieldsContainer,new VerticalLayoutContainer.VerticalLayoutData(1,-1,new Margins(0,0,FIELD_BOTTOM_MARGIN,0)));
        if (isRequired("subSetAuthors")) {
            requiredFields.add(authors.asWidget(), new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(authors.asWidget(), new VerticalLayoutContainer.VerticalLayoutData(1,-1,new Margins(0,0,FIELD_BOTTOM_MARGIN,0)));
        }
        if (isRequired("subSetIntendedRecipient")) {
            requiredFields.add(intendedRecipient.getDisplay(), new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(intendedRecipient.getDisplay(), new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("subSetTitles")) {
            requiredFields.add(titleGrid.getDisplay(), new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(titleGrid.getDisplay(), new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("subSetComments")) {
            requiredFields.add(commentsGrid.getDisplay(), new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(commentsGrid.getDisplay(), new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }

        // configure help information and client validations for the entire editor.
        setWidgetsInfo();

        // Bottom toolbar container.
        SimpleContainer bottomToolbarContainer = new SimpleContainer();
        bottomToolbarContainer.add(editorBottomToolbar);
        optionalFields.add(bottomToolbarContainer);

        // Adding required and optional fields panels to the main container of editor view.
        container.add(editorTopToolbar, new VerticalLayoutContainer.VerticalLayoutData(-1, -1,new Margins(FIELD_BOTTOM_MARGIN)));
        container.add(new HtmlLayoutContainer("<h2>Submission Set Editor</h2>"),new VerticalLayoutContainer.VerticalLayoutData(-1,-1,new Margins(0,0,0,FIELD_BOTTOM_MARGIN)));
        container.add(new HtmlLayoutContainer("<h3>Required fields</h3>"),new VerticalLayoutContainer.VerticalLayoutData(-1,-1,new Margins(0,0,0,FIELD_BOTTOM_MARGIN)));
        container.add(requiredFieldsContainer, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 20, FIELD_BOTTOM_MARGIN, FIELD_BOTTOM_MARGIN)));
        container.add(new HtmlLayoutContainer("<h3>Optional fields</h3>"), new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0,0,0,FIELD_BOTTOM_MARGIN)));
        container.add(optionalFieldsContainer, new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(0, 20, FIELD_BOTTOM_MARGIN, FIELD_BOTTOM_MARGIN)));

        collapseAll();

        setWidgetsInfo();

        mainContainer.setScrollMode(ScrollMode.AUTO);
        mainContainer.add(container);
        mainContainer.forceLayout();

    }

    private boolean isRequired(String attributeId) {
        return "R".equals(selectedStandardProperties.get(attributeId));
    }
}
