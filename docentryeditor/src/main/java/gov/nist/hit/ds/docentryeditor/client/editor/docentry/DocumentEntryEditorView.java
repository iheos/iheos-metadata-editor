package gov.nist.hit.ds.docentryeditor.client.editor.docentry;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.*;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.author.AuthorsListEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.codedterm.CodedTermsEditableGridWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.codedterm.PredefinedCodedTermComboBox;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.identifier.IdentifierOIDEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.identifier.IdentifierString256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.internatinationalstring.InternationalStringEditableGrid;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.namevalue.NameValueDTMEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.namevalue.NameValueIntegerEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.namevalue.NameValueString256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;
import gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodes;
import gov.nist.hit.ds.docentryeditor.client.resources.ClientFormatValidationResource;
import gov.nist.hit.ds.docentryeditor.client.resources.ToolTipResources;
import gov.nist.hit.ds.docentryeditor.client.widgets.EditorToolbar;
import gov.nist.hit.ds.docentryeditor.client.widgets.StandardSelector;
import gov.nist.hit.ds.docentryeditor.shared.model.CodedTerm;
import gov.nist.hit.ds.docentryeditor.shared.model.InternationalString;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the view of the Document Entry editor.
 * It only handles the different widgets used to build the final
 * complete view of the editor.
 * It works with a presenter to handle the XdsDocumentEntry model.
 *
 * @see gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry
 * @see gov.nist.hit.ds.docentryeditor.client.editor.docentry.DocumentEntryEditorPresenter
 */
public class DocumentEntryEditorView extends AbstractView<DocumentEntryEditorPresenter> implements Editor<XdsDocumentEntry> {
    private static final int FIELD_BOTTOM_MARGIN = 10;
    private final VerticalLayoutContainer form = new VerticalLayoutContainer();

    private VerticalLayoutContainer requiredFields = new VerticalLayoutContainer();
    private VerticalLayoutContainer optionalFields = new VerticalLayoutContainer();

    /* simple fields declaration */
    @Inject
    String256EditorWidget id;
    @Inject
    String256EditorWidget hash;
    @Inject
    String256EditorWidget uri;
    @Inject
    LanguageCodeComboBox languageCode;
    @Inject
    MimeTypeComboBox mimeType;

    // Could be injected using FactoryProvider assisted inject
    OIDEditorWidget repoUId = new OIDEditorWidget(false);

    /* Identifiers declaration */
    @Inject
    IdentifierOIDEditorWidget uniqueId;
    @Inject
    IdentifierString256EditorWidget patientID;
    @Inject
    @Ignore
    StandardSelector standardSelector;

    /* coded terms declaration */
    // Could be injected using FactoryProvider assisted inject
    PredefinedCodedTermComboBox classCode = new PredefinedCodedTermComboBox(PredefinedCodes.CLASS_CODES);
    PredefinedCodedTermComboBox formatCode = new PredefinedCodedTermComboBox(PredefinedCodes.FORMAT_CODES);
    PredefinedCodedTermComboBox healthcareFacilityType = new PredefinedCodedTermComboBox(PredefinedCodes.HEALTHCARE_FACILITY_TYPE_CODES);
    PredefinedCodedTermComboBox practiceSettingCode = new PredefinedCodedTermComboBox(PredefinedCodes.PRACTICE_SETTING_CODES);
    PredefinedCodedTermComboBox typeCode = new PredefinedCodedTermComboBox(PredefinedCodes.TYPE_CODES);

    /* name values declaration */
    // Could be injected using FactoryProvider assisted inject
    NameValueString256EditorWidget legalAuthenticator = new NameValueString256EditorWidget("Legal Authenticator");
    NameValueString256EditorWidget sourcePatientId = new NameValueString256EditorWidget("Source Patient ID");
    NameValueString256EditorWidget sourcePatientInfo = new NameValueString256EditorWidget("Source Patient Info");
    NameValueDTMEditorWidget creationTime = new NameValueDTMEditorWidget("Creation Time");
    NameValueDTMEditorWidget serviceStartTime = new NameValueDTMEditorWidget("Service Start Time");
    NameValueDTMEditorWidget serviceStopTime = new NameValueDTMEditorWidget("Service Stop Time");
    NameValueIntegerEditorWidget size = new NameValueIntegerEditorWidget("File Size");

    /* Authors widget */
    @Inject
    AuthorsListEditorWidget authors;

    // ---- Titles WIDGETS
    ListStoreEditor<InternationalString> titles;
    InternationalStringEditableGrid titlesGrid;

    // ---- Comments WIDGETS
    ListStoreEditor<InternationalString> comments;
    InternationalStringEditableGrid commentsGrid;

    // ---- ConfidentialityCodes WIDGETS
    ListStoreEditor<CodedTerm> confidentialityCodes;
    CodedTermsEditableGridWidget confidentialityCodesGrid;

    // ---- EventCodes WIDGETS
    ListStoreEditor<CodedTerm> eventCode;
    CodedTermsEditableGridWidget eventCodesGrid;

    // -- Button toolbars
    @Inject
    private EditorToolbar editorTopToolbar;
    @Inject
    private EditorToolbar editorBottomToolbar;

    private ContentPanel filePropertiesRPanel;
    private ContentPanel filePropertiesOPanel;
    private ContentPanel repositoryAttributesOPanel;
    private ContentPanel repositoryAttributesRPanel;

    private VerticalLayoutContainer container;

    @Inject
    private StandardSelector selector;
    Map<String, String> selectedStandardProperties;

    /**
     * This is the abstract method implementation that builds a collection of objects
     * mapping a String key to a Widget for the Document Entry editor view.
     * @return Map of widgets for the Document entry editor view.
     */
    @Override
    protected Map<String, Widget> getPathToWidgetsMap() {
        Map<String, Widget> map = new HashMap<String, Widget>();
        map.put("id", id);
        map.put("hash", hash);
        map.put("patientID", patientID);
        map.put("uniqueId", uniqueId);
        map.put("uri", uri);
        map.put("repoUId", repoUId);
        return map;
    }

    /**
     * This is the implementation of an abstract method supposed to construct
     * the Document Entry editor view as a widget.
     * @return Document entry editor view as a Widget.
     */
    @Override
    protected Widget buildUI() {
        container=new VerticalLayoutContainer();
        container.setBorders(false);
        selectedStandardProperties=selector.getStdPropertiesMap();
        if (selectedStandardProperties==null){
            presenter.retrieveDefaultStandardProperties();
        }else {
            updateEditorUI(selectedStandardProperties);
        }


        return form;
    }

    /**
     * Implementation of the abstract method that binds together
     * widgets actions (such as a button click) with presenter actions.
     */
    @Override
    protected void bindUI() {
        form.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                form.forceLayout();
            }
        });
        SelectHandler saveHandler = new SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                presenter.doSave();
            }
        };
        editorTopToolbar.addSaveHandler(saveHandler);
        editorBottomToolbar.addSaveHandler(saveHandler);
        SelectHandler cancelHandler = new SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                presenter.rollbackChanges();
            }
        };
        editorTopToolbar.addCancelHandler(cancelHandler);
        editorBottomToolbar.addCancelHandler(cancelHandler);
        SelectHandler populateHandler = new SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                presenter.populate();
            }
        };
        editorTopToolbar.addPopulateHandler(populateHandler);
        editorBottomToolbar.addPopulateHandler(populateHandler);
        SelectHandler expandHandler=new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                expandAll();
            }
        };
        editorTopToolbar.addExpandHandler(expandHandler);
        editorBottomToolbar.addExpandHandler(expandHandler);
        SelectHandler collapseHandler=new SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                collapseAll();
            }
        };
        editorTopToolbar.addCollapseHandler(collapseHandler);
        editorBottomToolbar.addCollapseHandler(collapseHandler);
    }

    public void refreshGridButtonsDisplay() {
        serviceStartTime.refreshNewButton();
        serviceStopTime.refreshNewButton();
        if (commentsGrid!=null) {
            commentsGrid.refreshNewButton();
        }
        if (confidentialityCodes!=null) {
            confidentialityCodesGrid.refreshNewButton();
        }
        if (eventCodesGrid!=null) {
            eventCodesGrid.refreshNewButton();
        }
        if (legalAuthenticator!=null) {
            legalAuthenticator.refreshNewButton();
        }
        if(sourcePatientId!=null) {
            sourcePatientId.refreshNewButton();
        }
        if (sourcePatientInfo!=null) {
            sourcePatientInfo.refreshNewButton();
        }
        if (titlesGrid!=null) {
            titlesGrid.refreshNewButton();
        }
    }

    /**
     * This method update the header of the panel for file properties on collapse.
     */
    private void updateFilePropertiesPanelHeader() {
        if (filePropertiesOPanel !=null) {
            String s = new String();
            if (!hash.getField().getText().isEmpty()) {
                s += "Hash: " + hash.getField().getText();
            }
            s += ", Size: " + size.getStore().get(0).toString();
            filePropertiesOPanel.setHeadingText("File properties (" + s + ")");
        }
        if (filePropertiesRPanel !=null) {
            String s = new String();
            if (!hash.getField().getText().isEmpty()) {
                s += "Hash: " + hash.getField().getText();
            }
            s += ", Size: " + size.getStore().get(0).toString();
            filePropertiesRPanel.setHeadingText("File properties (" + s + ")");
        }
    }

    /**
     * This method update the header of the panel for repository attributes on collapse.
     */
    private void updateRepositoryAttributesPanelHeader() {
        if (repositoryAttributesOPanel !=null) {
            String s = new String();
            if (!uri.getField().getText().isEmpty()) {
                s += "URI: " + uri.getField().getText();
                if (!repoUId.getText().isEmpty()) {
                    s += ", Repository Unique ID: " + repoUId.getText();
                }
            } else if (!repoUId.getText().isEmpty()) {
                s += "Repository Unique ID: " + repoUId.getText();
            }
            repositoryAttributesOPanel.setHeadingText("Repository attributes (" + s + ")");
        }
        if (repositoryAttributesRPanel !=null) {
            String s = new String();
            if (!uri.getField().getText().isEmpty()) {
                s += "URI: " + uri.getField().getText();
                if (!repoUId.getText().isEmpty()) {
                    s += ", Repository Unique ID: " + repoUId.getText();
                }
            } else if (!repoUId.getText().isEmpty()) {
                s += "Repository Unique ID: " + repoUId.getText();
            }
            repositoryAttributesRPanel.setHeadingText("Repository attributes (" + s + ")");
        }
    }

    /**
     * This method collapses all the collapsible panel in the Document entry editor view.
     */
    public void collapseAll(){
        creationTime.collapse();
        size.collapse();
        if (titlesGrid!=null) {
            titlesGrid.collapse();
        }
        if(commentsGrid!=null) {
            commentsGrid.collapse();
        }
        authors.collapse();
        legalAuthenticator.collapse();
        sourcePatientId.collapse();
        sourcePatientInfo.collapse();
        if (confidentialityCodesGrid!=null) {
            confidentialityCodesGrid.collapse();
        }
        if (eventCodesGrid!=null) {
            eventCodesGrid.collapse();
        }
        serviceStartTime.collapse();
        serviceStopTime.collapse();
        if(filePropertiesOPanel !=null) {
            filePropertiesOPanel.collapse();
        }
        if(filePropertiesRPanel !=null) {
            filePropertiesRPanel.collapse();
        }
        if (repositoryAttributesOPanel !=null){
            repositoryAttributesOPanel.collapse();
        }
        if (repositoryAttributesRPanel !=null){
            repositoryAttributesRPanel.collapse();
        }
        updateFilePropertiesPanelHeader();
        updateRepositoryAttributesPanelHeader();
    }

    /**
     * This method collapses all the collapsible panel in the Sbumission Set editor view.
     */
    public void expandAll() {
        creationTime.expand();
        size.expand();
        titlesGrid.expand();
        commentsGrid.expand();
        authors.expand();
        legalAuthenticator.expand();
        sourcePatientId.expand();
        sourcePatientInfo.expand();
        confidentialityCodesGrid.expand();
        eventCodesGrid.expand();
        serviceStartTime.expand();
        serviceStopTime.expand();
        filePropertiesOPanel.expand();
        repositoryAttributesOPanel.expand();
    }

    /**
     * Sets information about each widgets to guide the user. Tooltips, empty texts and validators
     */
    public void setWidgetsInfo() {
        // class code
        classCode.setEmptyText("Select a class...");
        classCode.clear();
        classCode.setAllowBlank(!isRequired("docEntryClassCode"));
        // comments
        commentsGrid.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getDocEntryCommentsTooltipConfig());
        // confidentiality codes
        confidentialityCodesGrid.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getConfidentialityCodesToolTipConfig());
        // event codes
        eventCodesGrid.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getEventCodesTooltipConfig());
        // format code
        formatCode.setEmptyText("Select a format...");
        formatCode.clear();
        formatCode.setAllowBlank(!isRequired("docEntryFormatCode"));
        // hash code
        hash.setEmptyText("ex: Hex456");
        hash.setToolTipConfig(new ToolTipConfig("Hash is a string", ToolTipResources.INSTANCE.getString256ToolTip()));
        hash.setAllowBlank(!isRequired("docEntryHash"));
        hash.addValidator(ClientFormatValidationResource.INSTANCE.getHashCodeRegExpValidator());
        // healthcare facility
        healthcareFacilityType.setEmptyText("Select an healthcare facility...");
        healthcareFacilityType.clear();
        healthcareFacilityType.setAllowBlank(!isRequired("docEntryHealthcareFacilityCode"));
        // entry uuid
        id.setEmptyText("ex: 123456789");
        id.setToolTipConfig(new ToolTipConfig("ID is a string", ToolTipResources.INSTANCE.getString256ToolTip()));
        id.setAllowBlank(!isRequired("docEntryEntryUUID"));
        id.addValidator(new UuidFormatClientValidator());
        // language code
        languageCode.setAllowBlank(!isRequired("docEntryLanguageCode"));
        languageCode.setEmptyText("Select a language...");
        languageCode.setToolTipConfig(new ToolTipConfig("LanguageCode from RFC3066", "Language code format is \"[a-z](2)-[A-Z](2)\""));
        // legal authenticator
        legalAuthenticator.setEditingFieldToolTip("A legal authenticator is a string256 in XCN format. It should be formatted as follow: \n<b>Identifier^LastName^FirstName[^SecondName[^FurtherGivenNames]][^Suffix][^Prefix]^AssigningAuthority</b>.");
        // Empty Texts : "ex: 11375^Welby^Marcus^J^Jr. MD^Dr^^^&1.2.840.113619.6.197&ISO"
        legalAuthenticator.addFieldValidator(ClientFormatValidationResource.INSTANCE.getLegalAuthenticatorRegExpValidator());
        legalAuthenticator.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getLegalAuthenticatorToolTipConfig());
        // mime type
        mimeType.setAllowBlank(!isRequired("docEntryMimeType"));
        mimeType.setEmptyText("Select a mime type...");
        mimeType.setToolTipConfig(new ToolTipConfig("Mime Type is a string", ToolTipResources.INSTANCE.getString256ToolTip()));
        // patient id
        patientID.setEmptyTexts("ex: 76cc^^^&1.3.6367.2005.3.7&ISO", "ex: urn:uuid:6b5aea1a-625s-5631-v4se-96a0a7b38446");
        patientID.setToolTipConfigs(ToolTipResources.INSTANCE.getPatientIdTooltipConfig());
        patientID.setAllowBlanks(!isRequired("docEntryPatientID"));
        patientID.addValueFieldValidator(ClientFormatValidationResource.INSTANCE.getPatientIDRegExpValidator());
        // pratice setting code
        practiceSettingCode.setEmptyText("Select a practice setting...");
        practiceSettingCode.clear();
        practiceSettingCode.setAllowBlank(!isRequired("docEntryPracticeSettingCode"));
        // repository unique id
        repoUId.setEmptyText("ex: 1.2.7.0.3.2.37768.2007.2.2");
        repoUId.setToolTipConfig(ToolTipResources.INSTANCE.getRepoUniqueIdToolTipConfig());
        repoUId.setAllowBlank(!isRequired("docEntryRepositoryUniqueID"));
        repoUId.addOIDValidator(ClientFormatValidationResource.INSTANCE.getRepoOIDValidation());
        // service start time
        serviceStartTime.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getServiceStartTimeTooltipConfig());
        // service stop time
        serviceStopTime.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getServiceStopTimeTooltipConfig());
        // source patient id
        sourcePatientId.addFieldValidator(ClientFormatValidationResource.INSTANCE.getSourcePatientIDRegExpValidator());
        // Empty Text: "j98789^^^&1.2.3.4.343.1&ISO"
        sourcePatientId.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getSourcePatientIdTooltipConfig());
        // source patient info
        sourcePatientInfo.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getSourcePatientInfoToolTipConfig());
        sourcePatientInfo.addFieldValidator(ClientFormatValidationResource.INSTANCE.getSourcePatientInfoRegExpValidator());
        // title
        titlesGrid.setToolbarHelpButtonTooltip(ToolTipResources.INSTANCE.getDocEntryTitleTooltipConfig());
        // type code
        typeCode.setEmptyText("Select a type...");
        typeCode.clear();
        typeCode.setAllowBlank(!isRequired("docEntryTypeCode"));
        // unique id
        uniqueId.setAllowBlanks(!isRequired("docEntryUniqueID"));
        uniqueId.addValueFieldValidator(ClientFormatValidationResource.INSTANCE.getUniqueIdRegExpValidator());
        uniqueId.setEmptyTexts("ex: 2008.8.1.35447^5846", "ex: 2008.8.1.35447");
        uniqueId.setToolTipConfigs(ToolTipResources.INSTANCE.getUniqueIdTooltipConfig());
        // uri
        uri.setAllowBlank(!isRequired("docEntryURI"));
        uri.setEmptyText("ex: uriO");
        uri.setToolTipConfig(new ToolTipConfig("URI is a string", ToolTipResources.INSTANCE.getString256ToolTip()));
        uri.addValidator(ClientFormatValidationResource.INSTANCE.getUriRegExpValidator());
    }

    public void updateEditorUI(Map<String, String> selectedStandardProperties) {
        this.selectedStandardProperties = selectedStandardProperties;
        requiredFields.clear();
        optionalFields.clear();
        container.clear();

        container.setBorders(false);

        SimpleContainer fp1 = new SimpleContainer();
        SimpleContainer fp2 = new SimpleContainer();

        fp1.add(requiredFields);
        fp2.add(optionalFields);

        fp1.setTitle("Required fields");
        fp2.setTitle("Optional fields");
        // //////////////////////////////////////
        // Simple fields label and options (init)
        // /////////////////////////////////////
        // ID Field (required)
        EditorFieldLabel idLabel = new EditorFieldLabel(id, "Entry UUID");
        // Hash Field (required)
        EditorFieldLabel hashLabel = new EditorFieldLabel(hash, "Hash");
        // Language Code Field (required)
        EditorFieldLabel languageCodeLabel = new EditorFieldLabel(languageCode, "Language Code");
        // Mime Type Field (required)
        EditorFieldLabel mimeTypeLabel = new EditorFieldLabel(mimeType, "Mime Type");
        // Class Code Field (required)
        EditorFieldLabel classCodeLabel = new EditorFieldLabel(classCode.getDisplay(), "Class Code");
        // Format Code Field (required)
        EditorFieldLabel formatCodeLabel = new EditorFieldLabel(formatCode.getDisplay(), "Format Code");
        // healthcare facility Code Field (required)
        EditorFieldLabel healthcareFacilityTypeLabel = new EditorFieldLabel(healthcareFacilityType.getDisplay(), "Healthcare Facility");
        // practiceSettingCode Field (required)
        EditorFieldLabel practiceSettingCodeLabel = new EditorFieldLabel(practiceSettingCode.getDisplay(), "Practice Setting Code");
        // type Code Field (required)
        EditorFieldLabel typeCodeLabel = new EditorFieldLabel(typeCode.getDisplay(), "Type Code");
        // Repository Unique ID Field (optional)
        EditorFieldLabel repositoryLabel = new EditorFieldLabel(repoUId, "Repository Unique ID");
        // URI Field (optional)
        EditorFieldLabel uriLabel = new EditorFieldLabel(uri, "URI");

		/* ********************************* */
        /* identifiers options and fieldset */
        /* ********************************* */
        // Patient ID Fields (required)
        EditorFieldLabel patientIdLabel = new EditorFieldLabel(patientID, "Patient ID");
        // Unique ID Fieds (required)
        EditorFieldLabel uniqueIdLabel = new EditorFieldLabel(uniqueId, "Unique ID");

        // ////////////////////////////////////////////////////
        // --- Adding REQUIRED simple fields labels to containers
        // ////////////////////////////////////////////////////
        VerticalLayoutContainer simpleRequiredFieldsContainer = new VerticalLayoutContainer();
        VerticalLayoutContainer simpleOptionalFieldsContainer = new VerticalLayoutContainer();
        if (isRequired("docEntryEntryUUID")) {
            simpleRequiredFieldsContainer.add(idLabel, new VerticalLayoutData(1, -1));
        } else {
            simpleOptionalFieldsContainer.add(idLabel, new VerticalLayoutData(1, -1));
        }
        if (isRequired("docEntryUniqueID")) {
            simpleRequiredFieldsContainer.add(uniqueIdLabel, new VerticalLayoutData(1, -1));
        } else {
            simpleOptionalFieldsContainer.add(uniqueIdLabel, new VerticalLayoutData(1, -1));
        }
        if (isRequired("docEntryPatientID")) {
            simpleRequiredFieldsContainer.add(patientIdLabel, new VerticalLayoutData(1, -1));
        } else {
            simpleOptionalFieldsContainer.add(patientIdLabel, new VerticalLayoutData(1, -1));
        }
        if (isRequired("docEntryLanguageCode")) {
            simpleRequiredFieldsContainer.add(languageCodeLabel, new VerticalLayoutData(1, -1));
        } else {
            simpleOptionalFieldsContainer.add(languageCodeLabel, new VerticalLayoutData(1, -1));
        }
        if (isRequired("docEntryMimeType")) {
            simpleRequiredFieldsContainer.add(mimeTypeLabel, new VerticalLayoutData(1, -1));
        } else {
            simpleOptionalFieldsContainer.add(mimeTypeLabel, new VerticalLayoutData(1, -1));
        }
        if (isRequired("docEntryClassCode")) {
            simpleRequiredFieldsContainer.add(classCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        } else {
            simpleOptionalFieldsContainer.add(classCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        }
        if (isRequired("docEntryTypeCode")) {
            simpleRequiredFieldsContainer.add(typeCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        } else {
            simpleOptionalFieldsContainer.add(typeCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        }
        if (isRequired("docEntryFormatCode")) {
            simpleRequiredFieldsContainer.add(formatCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        } else {
            simpleOptionalFieldsContainer.add(formatCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        }
        if (isRequired("docEntryHealthcareFacilityCode")) {
            simpleRequiredFieldsContainer.add(healthcareFacilityTypeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        } else {
            simpleOptionalFieldsContainer.add(healthcareFacilityTypeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        }
        if (isRequired("docEntryPracticeSettingCode")) {
            simpleRequiredFieldsContainer.add(practiceSettingCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        }else{
            simpleOptionalFieldsContainer.add(practiceSettingCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        }

		/* REQUIRED container added to a fieldset */
        FieldSet generalRequiredFieldSet = new FieldSet();
        generalRequiredFieldSet.setHeadingText("General");
        generalRequiredFieldSet.setCollapsible(true);
        generalRequiredFieldSet.add(simpleRequiredFieldsContainer);

        // //////////////////////////////////////////////////////////
        // --- Adding OPTIONAL simple fields labels to containers
        // //////////////////////////////////////////////////////////
        VerticalLayoutContainer filePropertiesFieldsRContainer = new VerticalLayoutContainer();
        VerticalLayoutContainer filePropertiesFieldsOContainer = new VerticalLayoutContainer();
        if (isRequired("docEntryHash")) {
            filePropertiesFieldsRContainer.add(hashLabel, new VerticalLayoutData(1, -1, new Margins(5, 5, 0, 5)));
        }else{
            filePropertiesFieldsOContainer.add(hashLabel, new VerticalLayoutData(1, -1, new Margins(5, 5, 0, 5)));
        }
        if (isRequired("docEntrySize")) {
            filePropertiesFieldsRContainer.add(size.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 5, 0, 5)));
        }else {
            filePropertiesFieldsOContainer.add(size.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 5, 0, 5)));
        }

        VerticalLayoutContainer repositoryAttributesFieldsRContainer = new VerticalLayoutContainer();
        VerticalLayoutContainer repositoryAttributesFieldsOContainer = new VerticalLayoutContainer();
        if (isRequired("docEntryURI")) {
            repositoryAttributesFieldsRContainer.add(uriLabel, new VerticalLayoutData(1, -1, new Margins(5, 5, 0, 5)));
        }else{
            repositoryAttributesFieldsOContainer.add(uriLabel, new VerticalLayoutData(1, -1, new Margins(5, 5, 0, 5)));
        }
        if (isRequired("docEntryRepositoryUniqueID")) {
            repositoryAttributesFieldsRContainer.add(repositoryLabel, new VerticalLayoutData(1, -1, new Margins(0, 5, 5, 5)));
        }else{
            repositoryAttributesFieldsOContainer.add(repositoryLabel, new VerticalLayoutData(1, -1, new Margins(0, 5, 5, 5)));
        }

		/* OPTIONAL container added to a fieldset */
        filePropertiesRPanel = new ContentPanel(){
            @Override
            public void onCollapse(){
                super.onCollapse();
                updateFilePropertiesPanelHeader();
            }
            @Override
            public void onExpand(){
                super.onExpand();
                this.setHeadingText("File properties");
            }
        };
        filePropertiesRPanel.setCollapsible(true);
        filePropertiesRPanel.setHeadingText("File properties");
        filePropertiesRPanel.add(filePropertiesFieldsRContainer, new VerticalLayoutData(1, -1, new Margins(0, 0, 5, 0)));

        filePropertiesOPanel = new ContentPanel(){
            @Override
            public void onCollapse(){
                super.onCollapse();
                updateFilePropertiesPanelHeader();
            }
            @Override
            public void onExpand(){
                super.onExpand();
                this.setHeadingText("File properties");
            }
        };
        filePropertiesOPanel.setCollapsible(true);
        filePropertiesOPanel.setHeadingText("File properties");
        filePropertiesOPanel.add(filePropertiesFieldsOContainer, new VerticalLayoutData(1, -1, new Margins(0, 0, 5, 0)));

        repositoryAttributesRPanel = new ContentPanel(){
            @Override
            public void onCollapse(){
                super.onCollapse();
                updateRepositoryAttributesPanelHeader();
            }
            @Override
            public void onExpand(){
                super.onExpand();
                this.setHeadingText("Repository attributes");
            }
        };
        repositoryAttributesRPanel.setHeadingText("Repository attributes");
        repositoryAttributesRPanel.setCollapsible(true);
        repositoryAttributesRPanel.add(repositoryAttributesFieldsRContainer, new VerticalLayoutData(1, -1, new Margins(0, 0, 5, 0)));

        repositoryAttributesOPanel = new ContentPanel(){
            @Override
            public void onCollapse(){
                super.onCollapse();
                updateRepositoryAttributesPanelHeader();
            }
            @Override
            public void onExpand(){
                super.onExpand();
                this.setHeadingText("Repository attributes");
            }
        };
        repositoryAttributesOPanel.setHeadingText("Repository attributes");
        repositoryAttributesOPanel.setCollapsible(true);
        repositoryAttributesOPanel.add(repositoryAttributesFieldsOContainer, new VerticalLayoutData(1, -1, new Margins(0, 0, 5, 0)));

        // //////////////////////////////////////////////////////
        // Other fields and options (init)
        // //////////////////////////////////////////////////////


		/* ********************************** */
        /* coded terms options and fieldset */
        /* ********************************** */

		/* ****************************** */
        /* name values options and fields */
        /* ****************************** */
        // Legal Authenticator (optional)
        legalAuthenticator.setListMaxSize(1);

        // Source Patient ID (optional)
        sourcePatientId.setListMaxSize(1);

        // Source Patient Info
        sourcePatientInfo.setListMaxSize(10);

        // Service Start Time (optional)
        serviceStartTime.setListMaxSize(1);

        // Service Stop Time (optional)
        serviceStopTime.setListMaxSize(1);

        // Size (optional)
        size.disableToolbar();
        size.setListMaxSize(1);

        // Creation Time (required)
        creationTime.disableToolbar();
        creationTime.setListMaxSize(1);

        // TITLES (Optional)
        titlesGrid = new InternationalStringEditableGrid("Titles");
        titles = new ListStoreEditor<InternationalString>(titlesGrid.getStore());


        // COMMENTS (Optional)
        commentsGrid = new InternationalStringEditableGrid("Comments");
        comments = new ListStoreEditor<InternationalString>(commentsGrid.getStore());

        // CONFIDENTIALITY CODE (Optional)
        confidentialityCodesGrid = new CodedTermsEditableGridWidget("Confidentiality Codes", PredefinedCodes.CONFIDENTIALITY_CODES);
        confidentialityCodes = new ListStoreEditor<CodedTerm>(confidentialityCodesGrid.getStore());

        // CONFIDENTIALITY CODE (Optional)
        eventCodesGrid = new CodedTermsEditableGridWidget("Event Codes", PredefinedCodes.EVENT_CODES);
        eventCode = new ListStoreEditor<CodedTerm>(eventCodesGrid.getStore());

        // /////////////////////////////////////////////////////////
        // Adding and ordering fieldsets in REQUIRED panel
        // /////////////////////////////////////////////////////////
        /* simple required fields added to FramedPanel container */
        requiredFields.add(generalRequiredFieldSet, new VerticalLayoutData(1,-1,new Margins(0,0,FIELD_BOTTOM_MARGIN,0)));
        requiredFields.add(creationTime.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));

        // /////////////////////////////////////////////////////////
        // Adding and ordering fieldsets in OPTIONAL fields panel
        // /////////////////////////////////////////////////////////
        /* simple optional fields added to FramedPanel container */
        optionalFields.add(simpleOptionalFieldsContainer);
        if (isRequired("docEntryHash") || isRequired("docEntrySize")){
            requiredFields.add(filePropertiesRPanel, new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (!(isRequired("docEntryHash") && isRequired("docEntrySize"))) {
            optionalFields.add(filePropertiesOPanel, new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntryURI") || isRequired("docEntryRepositoryUniqueID")) {
            requiredFields.add(repositoryAttributesRPanel, new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (!(isRequired("docEntryURI") && isRequired("docEntryRepositoryUniqueID"))) {
            optionalFields.add(repositoryAttributesOPanel, new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntryTitles")) {
            requiredFields.add(titlesGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(titlesGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntryComments")) {
            requiredFields.add(commentsGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(commentsGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntryAuthors")) {
            requiredFields.add(authors.asWidget(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(authors.asWidget(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntryLegalAuthenticator")) {
            requiredFields.add(legalAuthenticator.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(legalAuthenticator.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntrySourcePatientID")) {
            requiredFields.add(sourcePatientId.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(sourcePatientId.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntrySourcePatientInfo")) {
            requiredFields.add(sourcePatientInfo.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(sourcePatientInfo.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntryConfidentialityCodes")) {
            requiredFields.add(confidentialityCodesGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(confidentialityCodesGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntryEventCodes")) {
            requiredFields.add(eventCodesGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(eventCodesGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntryServiceStartTime")) {
            requiredFields.add(serviceStartTime.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(serviceStartTime.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }
        if (isRequired("docEntryServiceStopTime")) {
            requiredFields.add(serviceStopTime.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }else{
            optionalFields.add(serviceStopTime.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, FIELD_BOTTOM_MARGIN, 0)));
        }

        SimpleContainer bottomToolbarContainer = new SimpleContainer();
        bottomToolbarContainer.add(editorBottomToolbar);
        optionalFields.add(bottomToolbarContainer);

        // Adding required and optional fields panels to the main container of editor view
        container.add(editorTopToolbar, new VerticalLayoutData(-1, -1,new Margins(FIELD_BOTTOM_MARGIN)));
        container.add(new HtmlLayoutContainer("<h2>Document Entry Editor</h2>"),new VerticalLayoutData(-1,-1,new Margins(0,0,0,FIELD_BOTTOM_MARGIN)));
        container.add(new HtmlLayoutContainer("<h3>Required fields</h3>"),new VerticalLayoutData(-1,-1,new Margins(0,0,0,FIELD_BOTTOM_MARGIN)));
        container.add(fp1, new VerticalLayoutData(1, -1, new Margins(5,20,20,10)));
        container.add(new HtmlLayoutContainer("<h3>Optional fields</h3>"), new VerticalLayoutData(1, -1, new Margins(20,0,0,FIELD_BOTTOM_MARGIN)));
        container.add(fp2, new VerticalLayoutData(1, -1, new Margins(5,20,0,10)));

        collapseAll();

        setWidgetsInfo();

        form.setScrollMode(ScrollMode.AUTO);
        form.add(container);

        form.forceLayout();

        presenter.initDriver(presenter.getModel());
    }

    private boolean isRequired(String attributeId) {
        return "R".equals(selector.getStdPropertiesMap().get(attributeId));
    }
}
