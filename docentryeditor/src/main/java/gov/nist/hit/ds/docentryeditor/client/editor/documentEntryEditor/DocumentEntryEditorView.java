package gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor;

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
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldSet;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.AuthorWidgets.AuthorsListEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.CodedTermWidgets.CodedTermsEditableGridWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.CodedTermWidgets.PredefinedCodedTermComboBox;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.*;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.IdentifierWidgets.IdentifierOIDEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.IdentifierWidgets.IdentifierString256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.InternationalStringWidgets.InternationalStringEditableGrid;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.NameValueWidgets.NameValueDTMEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.NameValueWidgets.NameValueIntegerEditorWidget;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.NameValueWidgets.NameValueString256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;
import gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodes;
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
 * It work with a presenter to handle the XdsDocumentEntry model.
 *
 * @see gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry
 * @see gov.nist.hit.ds.docentryeditor.client.editor.documentEntryEditor.DocumentEntryEditorPresenter
 */
public class DocumentEntryEditorView extends AbstractView<DocumentEntryEditorPresenter> implements Editor<XdsDocumentEntry> {
    private final VerticalLayoutContainer form = new VerticalLayoutContainer();

    VerticalLayoutContainer requiredFields = new VerticalLayoutContainer();
    VerticalLayoutContainer optionalFields = new VerticalLayoutContainer();

    /* simple fields declaration */
    @Inject
    String256EditorWidget id;
    @Inject
    String256EditorWidget hash;
    @Inject
    String256EditorWidget fileName;
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

    /**
     * This is the abstract method implementation that builds a collection of objects
     * mapping a String key to a Widget for the Document Entry editor view.
     * @return Map of widgets for the Submission Set editor view.
     */
    @Override
    protected Map<String, Widget> getPathToWidgetsMap() {
        Map<String, Widget> map = new HashMap<String, Widget>();
        map.put("id", id);
        map.put("fileName", fileName);
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
     * @return Submission Set editor view as a Widget.
     */
    @Override
    protected Widget buildUI() {
        final VerticalLayoutContainer container = new VerticalLayoutContainer();
        container.getElement().setMargins(10);
        container.setBorders(false);

        SimpleContainer fp1 = new SimpleContainer();
        SimpleContainer fp2 = new SimpleContainer();

        fp1.add(requiredFields);
        fp2.add(optionalFields);

        fp1.setTitle("Required fields");
        fp2.setTitle("Optional fields");

        // Adding required and optional fields panels to the main container of editor view
        container.add(editorTopToolbar, new VerticalLayoutData(-1,30));
        container.add(new HtmlLayoutContainer("<h2>Document Entry Editor</h2>"));
        container.add(new HtmlLayoutContainer("<h3>Required fields</h3>"));
        container.add(fp1, new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        container.add(new HtmlLayoutContainer("<h3>Optional fields</h3>"));
        container.add(fp2, new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));

        // //////////////////////////////////////
        // Simple fields label and options (init)
        // /////////////////////////////////////
        // ID Field (required)
        EditorFieldLabel idLabel = new EditorFieldLabel(id, "Entry UUID");
        // Filename field
        EditorFieldLabel filenameLabel = new EditorFieldLabel(fileName, "File name");
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
        simpleRequiredFieldsContainer.add(filenameLabel, new VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(idLabel, new VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(uniqueIdLabel, new VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(patientIdLabel, new VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(languageCodeLabel, new VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(mimeTypeLabel, new VerticalLayoutData(1, -1));
        simpleRequiredFieldsContainer.add(classCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        simpleRequiredFieldsContainer.add(typeCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        simpleRequiredFieldsContainer.add(formatCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        simpleRequiredFieldsContainer.add(healthcareFacilityTypeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));
        simpleRequiredFieldsContainer.add(practiceSettingCodeLabel, new VerticalLayoutData(1, -1/*, new Margins(0, 0, 5, 0)*/));

		/* REQUIRED container added to a fieldset */
        FieldSet fieldSet_general_fields_required = new FieldSet();
        fieldSet_general_fields_required.setHeadingText("General");
        fieldSet_general_fields_required.setCollapsible(true);
        fieldSet_general_fields_required.add(simpleRequiredFieldsContainer);

        // //////////////////////////////////////////////////////////
        // --- Adding OPTIONAL simple fields labels to containers
        // //////////////////////////////////////////////////////////
        VerticalLayoutContainer filePropertiesFieldsContainer = new VerticalLayoutContainer();
        filePropertiesFieldsContainer.add(hashLabel, new VerticalLayoutData(1, -1));
        filePropertiesFieldsContainer.add(size.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));

        VerticalLayoutContainer repositoryAttributesFieldsContainer = new VerticalLayoutContainer();
        repositoryAttributesFieldsContainer.add(uriLabel, new VerticalLayoutData(1, -1));
        repositoryAttributesFieldsContainer.add(repositoryLabel, new VerticalLayoutData(1, -1));

		/* OPTIONAL container added to a fieldset */
        FieldSet fieldSet_fileProperties = new FieldSet();
        fieldSet_fileProperties.setHeadingText("Files properties");
        fieldSet_fileProperties.setCollapsible(true);
        fieldSet_fileProperties.add(filePropertiesFieldsContainer);

        FieldSet fieldSet_repoAttributes = new FieldSet();
        fieldSet_repoAttributes.setHeadingText("Repository attributes");
        fieldSet_repoAttributes.setCollapsible(true);
        fieldSet_repoAttributes.add(repositoryAttributesFieldsContainer, new VerticalLayoutData(1, -1));

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
//        serviceStartTime.disableEditing();

        // Service Stop Time (optional)
        serviceStopTime.setListMaxSize(1);

        // Size (optional)
//        size.disableEditing();
        size.disableToolbar();
        size.setListMaxSize(1);

        // Creation Time (required)
        creationTime.disableToolbar();
        creationTime.setListMaxSize(1);

        // AUTHORS (Optional)
        FieldSet fieldSet_authors = new FieldSet();
        fieldSet_authors.setHeadingText("Authors");
        fieldSet_authors.setCollapsible(true);
        fieldSet_authors.add(authors.asWidget());

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
        requiredFields.add(fieldSet_general_fields_required, new VerticalLayoutData(1,-1,new Margins(0,0,10,0)));
        requiredFields.add(creationTime.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));

        // /////////////////////////////////////////////////////////
        // Adding and ordering fieldsets in OPTIONAL fields panel
        // /////////////////////////////////////////////////////////
        /* simple optional fields added to FramedPanel container */
        optionalFields.add(fieldSet_fileProperties, new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(fieldSet_repoAttributes, new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(titlesGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(commentsGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(fieldSet_authors, new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(legalAuthenticator.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(sourcePatientId.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(sourcePatientInfo.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(confidentialityCodesGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(eventCodesGrid.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(serviceStartTime.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));
        optionalFields.add(serviceStopTime.getDisplay(), new VerticalLayoutData(1, -1, new Margins(0, 0, 10, 0)));

        SimpleContainer bottomToolbarContainer = new SimpleContainer();
        bottomToolbarContainer.setHeight(35);
        bottomToolbarContainer.add(editorBottomToolbar);
        optionalFields.add(bottomToolbarContainer);

        setWidgetsInfo();

        form.setScrollMode(ScrollMode.AUTO);
        form.add(container);

        form.forceLayout();

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
    }

    public void refreshGridButtonsDisplay() {
        if (serviceStartTime.getStoreMaxSize() != 0 && serviceStartTime.getStore().size() >= serviceStartTime.getStoreMaxSize()) {
            serviceStartTime.disableNewButton();
        } else {
            serviceStartTime.enableNewButton();
        }
        if (serviceStopTime.getStoreMaxSize() != 0 && serviceStopTime.getStore().size() >= serviceStopTime.getStoreMaxSize()) {
            serviceStopTime.disableNewButton();
        } else {
            serviceStopTime.enableNewButton();
        }
        if (commentsGrid != null)
            if (commentsGrid.getStoreMaxSize() != 0 && commentsGrid.getStore().size() >= commentsGrid.getStoreMaxSize()) {
                commentsGrid.disableNewButton();
            } else {
                commentsGrid.enableNewButton();
            }
        if (confidentialityCodesGrid != null)
            if (confidentialityCodesGrid.getStoreMaxSize() != 0 && confidentialityCodesGrid.getStore().size() >= confidentialityCodesGrid.getStoreMaxSize()) {
                confidentialityCodesGrid.disableNewButton();
            } else {
                confidentialityCodesGrid.enableNewButton();
            }
        if (eventCodesGrid != null)
            if (eventCodesGrid.getStoreMaxSize() != 0 && eventCodesGrid.getStore().size() >= eventCodesGrid.getStoreMaxSize()) {
                eventCodesGrid.disableNewButton();
            } else {
                eventCodesGrid.enableNewButton();
            }
        if (legalAuthenticator != null)
            if (legalAuthenticator.getStoreMaxSize() != 0 && legalAuthenticator.getStore().size() >= legalAuthenticator.getStoreMaxSize()) {
                legalAuthenticator.disableNewButton();
            } else {
                legalAuthenticator.enableNewButton();
            }
        if (sourcePatientId != null)
            if (sourcePatientId.getStoreMaxSize() != 0 && sourcePatientId.getStore().size() >= sourcePatientId.getStoreMaxSize()) {
                sourcePatientId.disableNewButton();
            } else {
                sourcePatientId.enableNewButton();
            }
        if (sourcePatientInfo != null)
            if (sourcePatientInfo.getStoreMaxSize() != 0 && sourcePatientInfo.getStore().size() >= sourcePatientInfo.getStoreMaxSize()) {
                sourcePatientInfo.disableNewButton();
            } else {
                sourcePatientInfo.enableNewButton();
            }
        if (titlesGrid != null)
            if (titlesGrid.getStoreMaxSize() != 0 && titlesGrid.getStore().size() >= titlesGrid.getStoreMaxSize()) {
                titlesGrid.disableNewButton();
            } else {
                titlesGrid.enableNewButton();
            }
    }

    /**
     * Sets information about each widgets to guide the user. Tooltips, empty texts and validators
     */
    public void setWidgetsInfo() {
        // class code
        classCode.setEmptyText("Select a class...");
        classCode.clear();
        classCode.setAllowBlank(false);
        // comments
        commentsGrid.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on Comments", "Comments associated with the Document. Free form text with an XDS " +
                "Affinity Domain specified usage. Each comment should have the same meaning in a different language."));
        // confidentiality codes
        confidentialityCodesGrid.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on Confidentiality codes", "The code specifying the level of confidentiality of the XDS Document." +
                "These codes are specific to an XDS Affinity Domain. Enforcement and " +
                "issues related to highly sensitive documents are beyond the scope of " +
                "XDS (see security section). These issues are expected to be addressed in " +
                "later years. confidentialityCode is part of a codification scheme and " +
                "value set enforced by the Document Registry. Shall have one or more " +
                "values. Code multiple values by creating multiple classification objects"));
        // event codes
        eventCodesGrid.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on Event codes", "This list of codes represents the main clinical acts, such as a colonoscopy " +
                "or an appendectomy, being documented. In some cases, the event is " +
                "inherent in the typeCode, such as a \"History and Physical Report\" in " +
                "which the procedure being documented is necessarily a \"History and " +
                "Physical\" act. " +
                "An event can further specialize the act inherent in the typeCode, such as " +
                "where it is simply \"Procedure Report\" and the procedure was a " +
                "\"colonoscopy\". If one or more eventCodes are included, they shall not " +
                "conflict with the values inherent in the classCode, practiceSettingCode or " +
                "typeCode, as such a conflict would create an ambiguous situation. " +
                "This short list of codes is provided to be used as “key words” for certain " +
                "types of queries. If present, shall have one or more values. Code multiple " +
                "values by creating multiple classification objects."));
        // filename
        fileName.setEmptyText("Metadata filename (with file extension)");
        fileName.setToolTipConfig(new ToolTipConfig("Metadata file name (with extension)", "This is the name of the metadata file which will be generated. <br/>For example: xds-metadata.xml"));
        fileName.setAllowBlank(false);
        // format code
        formatCode.setEmptyText("Select a format...");
        formatCode.clear();
        formatCode.setAllowBlank(false);
        // hash code
        hash.setEmptyText("ex: Hex456");
        hash.setToolTipConfig(new ToolTipConfig("Hash is a string", "It should contain less than 256 characters"));
        hash.setAllowBlank(true);
        hash.addValidator(new RegExValidator("^[0-9a-fA-F]+$", "Value is not correct. It is supposed to be a hexadecimal value."));
        // healthcare facility
        healthcareFacilityType.setEmptyText("Select an healthcare facility...");
        healthcareFacilityType.clear();
        healthcareFacilityType.setAllowBlank(false);
        // entry uuid
        id.setEmptyText("ex: 123456789");
        id.setToolTipConfig(new ToolTipConfig("ID is a string", "It should contain less than 256 characters"));
        id.setAllowBlank(false);
        // id.addOIDValidator(new RegExValidator("[1-9][0-9]+", "Value is not correct. It is supposed to be a number."));
        id.addValidator(new UuidFormatClientValidator());
        // language code
        languageCode.setAllowBlank(false);
        languageCode.setEmptyText("Select a language...");
        languageCode.setToolTipConfig(new ToolTipConfig("LanguageCode from RFC3066", "Language code format is \"[a-z](2)-[A-Z](2)\""));
        // legal authenticator
        legalAuthenticator.setEditingFieldToolTip("A legal authenticator is a string256 in XCN format. It should be formatted as follow: \n<b>Identifier^LastName^FirstName[^SecondName[^FurtherGivenNames]][^Suffix][^Prefix]^AssigningAuthority</b>.");
        // legalAuthenticator.setEmptyTexts("ex: 11375^Welby^Marcus^J^Jr. MD^Dr^^^&1.2.840.113619.6.197&ISO");
        legalAuthenticator.addFieldValidator(new RegExValidator("^[0-9]+\\^(([A-Za-z]+\\.?\\s?)+\\^){3,7}\\^{2}&[0-9]+(\\.[0-9]+)*(&ISO)$"));
        legalAuthenticator.setToolbarHelpButtonTooltip(new ToolTipConfig("Help about legal authenticator", "Represents a participant who has legally authenticated or attested the" +
                "document within the authorInstitution. Legal authentication implies that " +
                "a document has been signed manually or electronically by the " +
                "legalAuthenticator. This attribute may be absent if not applicable. If " +
                "present, shall have a single value. <br/>It should be formatted as following:<br/>" +
                "<ul><li>Identifier</li><li>Last Name</li><li>First name</li><li>Second and further names</li><li>Suffix</li><li>Prefix</li><li>Assigning authority</li></ul><br/>" +
                "An example of person name with ID number using this data type is as follows:<br/>" +
                "<b>11375^Welby^Marcus^J^Jr. MD^Dr^^^&1.2.840.113619.6.197&ISO</b>"));
        // mime type
        mimeType.setAllowBlank(false);
        mimeType.setEmptyText("Select a mime type...");
        mimeType.setToolTipConfig(new ToolTipConfig("Mime Type is a string", "It should contain less than 256 characters"));
        // patient id
        patientID.setEmptyTexts("ex: 76cc^^1.3.6367.2005.3.7&ISO", "ex: urn:uuid:6b5aea1a-625s-5631-v4se-96a0a7b38446");
        patientID.setToolTipConfigs(new ToolTipConfig("Patient ID is a String256 in HL7 CX format", "The required format is:" +
                "IDNumber^^^&OIDofAssigningAuthority&ISO"), new ToolTipConfig(
                "idType is a string256 in HL7 CX format", "The required format is: " +
                "IDNumber^^^&OIDofAssigningAuthority&ISO"));
        patientID.setAllowBlanks(false, false);
        patientID.addValueFieldValidator(new RegExValidator("^(([A-Za-z])|([1-9]))*[0-9A-z]+\\^{3}&[1-9][0-9]*(\\.[1-9][0-9]*)+(&ISO)$", "Value's format is not a correct. \nIt should be like this: 6578946^^^&1.3.6.1.4.1.21367.2005.3.7&ISO."));
        // pratice setting code
        practiceSettingCode.setEmptyText("Select a practice setting...");
        practiceSettingCode.clear();
        practiceSettingCode.setAllowBlank(false);
        // repository unique id
        repoUId.setEmptyText("ex: 1.2.7.0.3.2.37768.2007.2.2");
        repoUId.setToolTipConfig(new ToolTipConfig("Repository Unique ID is an OID",
                "As defined in the HL7 implementation for OID (http://www.hl7.org/implement/standards/product_brief.cfm?product_id=210) "
                        + "OID format is \"[1-9](\\.[0-9]+)*]\""));
        repoUId.setAllowBlank(true);
        repoUId.addOIDValidator(new RegExValidator("^[1-9][0-9]*(\\.[1-9][0-9]*)+$", "Value is not correct. A repository unique ID is supposed to be a suite of numbers separated by periods."));
        // service start time
        serviceStartTime.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on Service start time", "Represents the start time the service being documented took place " +
                "(clinically significant, but not necessarily when the document was " +
                "produced or approved). This may be the same as the encounter time in " +
                "case the service was delivered during an encounter. Encounter time is not " +
                "coded in XDS metadata but may be coded in documents managed by " +
                "XDS. This time is expressed as (date/time/UTC). If present, shall have a " +
                "single value. " +
                "<br/><i>Note: Other times, such as document creation or approval are to be " +
                "recorded, if needed, within the document.</i>"));
        serviceStopTime.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on Service stop time",
                "Represents the stop time the service being documented took place." +
                        "This may be the same as the encounter time in " +
                        "case the service was delivered during an encounter. This time is expressed as (date/time/UTC). " +
                        "If present, shall have a single value."));
        // source patient id
        sourcePatientId.addFieldValidator(new RegExValidator("^(([A-Za-z])|([1-9]))*[0-9A-z]+\\^{3}&[1-9][0-9]*(\\.[1-9][0-9]*)+(&ISO)$", "This value is not a correct source patient id."));
        // sourcePatientId.addFieldValidator(new RegExValidator("^[A-Za-z]*[0-9]+\\^{3}&[1-9][0-9]*(\\.[1-9][0-9]*)+(&ISO)$", "This value is not a correct source patient id."));
        // sourcePatientId.setEmptyTexts("j98789^^^&1.2.3.4.343.1&ISO");
        sourcePatientId.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on Source Patient ID", "The sourcePatientId represents the subject of care medical record " +
                "Identifier (e.g., Patient Id) in the local patient Identifier Domain of the " +
                "Document Source. It shall contain two parts:" +
                "<ul><li>Authority Domain Id</li>" +
                "<li>An Id in the above domain (e.g., Patient Id).</li></ul>" +
                "This sourcePatientId is not intended to be updated once the Document is " +
                "registered (just as the Document content and metadata itself will not be " +
                "updated without replacing the previous document). As this " +
                "sourcePatientId may have been merged by the source actor, it may no " +
                "longer be in use within the Document Source (EHR-CR). It is only " +
                "intended as an audit/checking mechanism and has occasional use for " +
                "Document Consumer Actors. There can be only one Slot named " +
                "sourcePatientId."));
        // source patient info
        sourcePatientInfo.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on Source Patient Info", "These elements should contain demographics information of the patient to " +
                "whose medical record this document belongs. It is made several values and should be formatted as follow:<br/>" +
                "<b>PID-3</b> should include the source patient identifier.<br/>" +
                "<b>PID-5</b> should include the patient name.<br/>" +
                "<b>PID-7</b> should include the patient date of birth.<br/>" +
                "<b>PID-8</b> should code the patient gender as <br/>" +
                "<center><i>M Male - F Female - O Other - U Unknown</i></center>" +
                "<b>PID-11</b> should include the patient address.<br/>" +
                "PID-2, PID-4, PID-12 and PID-19 should not be used.<br/>"));
        sourcePatientInfo.addFieldValidator(new RegExValidator("^PID-(((3|5|11|2|4|12|19)\\|)|(8\\|(M|F|O|U)$)|(7\\|(((19|20)\\d\\d)(0?[1-9]|1[012])(0?[1-9]|[12][0-9]|3[01])))$)", "This value is not a correct source patient info."));
        // title
        titlesGrid.setToolbarHelpButtonTooltip(new ToolTipConfig("Help on Titles", "Represents the title of the document. Clinical documents often do not " +
                "have a title, and are collectively referred to by the display name of the " +
                "classCode (e.g., a \"consultation\" or \"progress note\"). Where these display " +
                "names are rendered to the clinician, or where the document has a unique " +
                "title, the title component shall be used. Max length, 128 bytes, UTF-8. " +
                "Each title must be the same in a different language."));
        // type code
        typeCode.setEmptyText("Select a type...");
        typeCode.clear();
        typeCode.setAllowBlank(false);
        // unique id
        uniqueId.setAllowBlanks(false, false);
        uniqueId.addValueFieldValidator(new RegExValidator("^[1-9][0-9]*(\\.[1-9][0-9]*)+(\\^[1-9][0-9]+)?$", "Value's format is not a correct. It is supposed to be a suite of figures separated by periods."));

        uniqueId.setEmptyTexts("ex: 2008.8.1.35447^5846", "ex: 2008.8.1.35447");
        uniqueId.setToolTipConfigs(
                new ToolTipConfig("Unique ID is an OID", "As defined in the HL7 implementation for OID (http://www.hl7.org/implement/standards/product_brief.cfm?product_id=210)<br/>Unique ID format is \"[1-9](\\.[0-9]+)*]\""),
                new ToolTipConfig(
                        "idType is an OID",
                        "As defined in the HL7 implementation for OID (http://www.hl7.org/implement/standards/product_brief.cfm?product_id=210)<br/>Unique ID format is \"[1-9](\\.[0-9]+)*]\""));
        // uri
        uri.setAllowBlank(true);
        uri.setEmptyText("ex: uriO");
        uri.setToolTipConfig(new ToolTipConfig("URI is a string", "It should contain less than 256 characters"));
    }

}
