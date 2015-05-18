package gov.nist.hit.ds.docentryeditor.client.resources;

import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

/**
 * Created by onh2 on 4/7/2015.
 */
public enum ToolTipResources {
    INSTANCE;
    public String getEditSubSetTooltip(){
        return "Start editing the submission set.";
    }
    public String getNewDocEntryTooltip(){
        return "Create an empty document entry and add it to the existing submission set.";
    }
    public String getNewPrefilledDocEntryTooltip(){
        return "Create a document entry pre-filled with test data and add it to the existing submission set.";
    }

    public String getUploadFileTooltip() {
        return "Upload an existing XDS metadata file in ebRim XML format. It shall contain at least a submission set, a document entry and an association to bind them.";
    }

    public String getSaveButtonToolTip() {
        return "Save data currently being edited and download an XDS Metadata file in ebRim XML format.";
    }

    public String getCancelButtonToolTip() {
        return "Rollback all the changes made since the last save or the last page change (auto save).";
    }

    public String getGridAddToolTip(){
        return "Add a new entry.";
    }

    public String getGridDeleteToolTip() {
        return "Delete selected element(s).";
    }

    public String getGridClearTooltip(){
        return "Clear widget (remove all entries).";
    }

    public String getHelpButtonToolTip(){
        return "Help";
    }

    public String getClearSubmissionSetToolTip() {
        return "Clear submission set from all elements.";
    }

    public String getClearAssociationsListToolTip() {
        return "Remove all associations from the list.";
    }

    public String getString256ToolTip() {
        return "It should contain less than 256 characters";
    }

    public ToolTipConfig getUniqueIdTooltipConfig() {
        return new ToolTipConfig("Unique ID is an OID", "As defined in the HL7 implementation for OID (http://www.hl7.org/implement/standards/product_brief.cfm?product_id=210)<br/>Unique ID format is \"[1-9](\\.[0-9]+)*]\"");
    }

    public ToolTipConfig getDocEntryTitleTooltipConfig() {
        return new ToolTipConfig("Help on Titles", "Represents the title of the document. Clinical documents often do not " +
                "have a title, and are collectively referred to by the display name of the " +
                "classCode (e.g., a \"consultation\" or \"progress note\"). Where these display " +
                "names are rendered to the clinician, or where the document has a unique " +
                "title, the title component shall be used. Max length, 128 bytes, UTF-8. " +
                "Each title must be the same in a different language.");
    }

    public ToolTipConfig getSourcePatientInfoToolTipConfig() {
        return new ToolTipConfig("Help on Source Patient Info", "These elements should contain demographics information of the patient to " +
                "whose medical record this document belongs. It is made several values and should be formatted as follow:<br/>" +
                "<b>PID-3</b> should include the source patient identifier.<br/>" +
                "<b>PID-5</b> should include the patient name.<br/>" +
                "<b>PID-7</b> should include the patient date of birth.<br/>" +
                "<b>PID-8</b> should code the patient gender as <br/>" +
                "<center><i>M Male - F Female - O Other - U Unknown</i></center>" +
                "<b>PID-11</b> should include the patient address.<br/>" +
                "PID-2, PID-4, PID-12 and PID-19 should not be used.<br/>");
    }

    public ToolTipConfig getSourcePatientIdTooltipConfig() {
        return new ToolTipConfig("Help on Source Patient ID", "The sourcePatientId represents the subject of care medical record " +
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
                "sourcePatientId.");
    }

    public ToolTipConfig getServiceStopTimeTooltipConfig() {
        return new ToolTipConfig("Help on Service stop time",
                "Represents the stop time the service being documented took place." +
                        "This may be the same as the encounter time in " +
                        "case the service was delivered during an encounter. This time is expressed as (date/time/UTC). " +
                        "If present, shall have a single value.");
    }

    public ToolTipConfig getServiceStartTimeTooltipConfig() {
        return new ToolTipConfig("Help on Service start time", "Represents the start time the service being documented took place " +
                "(clinically significant, but not necessarily when the document was " +
                "produced or approved). This may be the same as the encounter time in " +
                "case the service was delivered during an encounter. Encounter time is not " +
                "coded in XDS metadata but may be coded in documents managed by " +
                "XDS. This time is expressed as (date/time/UTC). If present, shall have a " +
                "single value. " +
                "<br/><i>Note: Other times, such as document creation or approval are to be " +
                "recorded, if needed, within the document.</i>");
    }

    public ToolTipConfig getRepoUniqueIdToolTipConfig() {
        return new ToolTipConfig("Repository Unique ID is an OID",
                "As defined in the HL7 implementation for OID (http://www.hl7.org/implement/standards/product_brief.cfm?product_id=210) "
                        + "OID format is \"[1-9](\\.[0-9]+)*]\"");
    }

    public ToolTipConfig getPatientIdTooltipConfig() {
        return new ToolTipConfig("Patient ID is a String256 in HL7 CX format", "The required format is:" +
                "IDNumber^^^&OIDofAssigningAuthority&ISO");
    }

    public ToolTipConfig getLegalAuthenticatorToolTipConfig() {
        return new ToolTipConfig("Help about legal authenticator", "Represents a participant who has legally authenticated or attested the" +
                "document within the authorInstitution. Legal authentication implies that " +
                "a document has been signed manually or electronically by the " +
                "legalAuthenticator. This attribute may be absent if not applicable. If " +
                "present, shall have a single value. <br/>It should be formatted as following:<br/>" +
                "<ul><li>Identifier</li><li>Last Name</li><li>First name</li><li>Second and further names</li><li>Suffix</li><li>Prefix</li><li>Assigning authority</li></ul><br/>" +
                "An example of person name with ID number using this data type is as follows:<br/>" +
                "<b>11375^Welby^Marcus^J^Jr. MD^Dr^^^&1.2.840.113619.6.197&ISO</b>");
    }

    public ToolTipConfig getEventCodesTooltipConfig() {
        return new ToolTipConfig("Help on Event codes", "This list of codes represents the main clinical acts, such as a colonoscopy " +
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
                "values by creating multiple classification objects.");
    }

    public ToolTipConfig getConfidentialityCodesToolTipConfig() {
        return new ToolTipConfig("Help on Confidentiality codes", "The code specifying the level of confidentiality of the XDS Document." +
                "These codes are specific to an XDS Affinity Domain. Enforcement and " +
                "issues related to highly sensitive documents are beyond the scope of " +
                "XDS (see security section). These issues are expected to be addressed in " +
                "later years. confidentialityCode is part of a codification scheme and " +
                "value set enforced by the Document Registry. Shall have one or more " +
                "values. Code multiple values by creating multiple classification objects");
    }

    public ToolTipConfig getDocEntryCommentsTooltipConfig() {
        return new ToolTipConfig("Help on comments", "Comments associated with the Document. Free form text with an XDS " +
                "Affinity Domain specified usage. Each comment should have the same meaning in a different language.");
    }
    public ToolTipConfig getSubSetCommentsTooltipConfig() {
        return new ToolTipConfig("Help on comments",
                        "It contains comments associated with the SubmissionSet (Max length is unbounded). " +
                                "Each comment should have the same meaning in a different language.");
    }

    public ToolTipConfig getSubmissionTimeToolTipConfig() {
        return new ToolTipConfig("Help on Submission time", "Represents the point in time at the creating entity when the Submission Set was submitted.\n" +
                "This shall be provided by the submitting system.");
    }

    public ToolTipConfig getSubSetTitleTooltipConfig() {
        return new ToolTipConfig("Help on Titles", "Represents the title of the Submission Set. The format of a title shall be any string of length less than 256 characters.\n");
    }

    public ToolTipConfig getSourceIdTooltipConfig() {
        return new ToolTipConfig("Source ID is an OID", "Source ID is the globally unique, immutable, identifier of the entity that contributed the SubmissionSet. It is an OID, as defined in the HL7 implementation for OID (http://www.hl7.org/implement/standards/product_brief.cfm?product_id=210)<br/>Unique ID format is \"[1-9](\\.[0-9]+)*]\"");
    }


    public String getIntendedRecipientEditorTooltip() {
        return "The value shall be a <b>XON|XCN|XTN</b>\n" +
                "                where: <ul><li><b>XON</b> identifies the organization,</li> <li><b>XCN</b> identifies a person</li> <li><b>XTN</b> identifies the" +
                "                telecommunications.</li></ul>" +
                "                There is a \"<b>|</b>\" character separator between the organization and the person, and between the person and" +
                "                the telecommunications address, which is required when the person or the telecommunications" +
                "                address information is present.";
    }

    public ToolTipConfig getIntendedRecipientHelpTooltipConfig() {
        return new ToolTipConfig("Help of intended recipients", "It represents the organization(s) or person(s) for whom the SubmissionSet is intended at time of\n" +
                "submission. Each slot value shall include at least one of the organization, person, or\n" +
                "telecommunications address fields described below. It is highly recommended to define the\n" +
                "organization for all the persons, avoiding errors in the transmission of the documents. " +
                "Values shall be formatted as <b>XON|XCN|XTN</b>.");
    }

    public ToolTipConfig getHomeCommunityIdTooltipConfig() {
        return new ToolTipConfig("It is an OID URN.","It is a globally unique identifier for a community. It should be  an OID URN (ex: \"urn:oid:1.2.3\").");
    }

    public ToolTipConfig getAvailabilityStatusToolTipConfig() {
        return new ToolTipConfig("It represents the status of the SubmissionSet. Since the deprecation of SubmissionSets is not\n" +
                "allowed, this value shall always be Approved.\n\nThe availabilityStatus value shall be \"urn:oasis:names:tc:ebxml-regrep:StatusType:Approved\"");
    }
}
