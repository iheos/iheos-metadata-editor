package gov.nist.hit.ds.docentryeditor.shared.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The SubmissionSet represents a collection of Folders, Documents and Associations
 * submitted together.
 *
 * Created by onh2 on 2/24/2015.
 */
public class XdsSubmissionSet extends XdsModelElement implements Serializable {
    /**
     * The humans and/or machines that created the submission set.
     * This attribute contains the subattributes:
     *      authorInstitution, authorPerson,
     *      authorRole, authorSpecialty,
     *      authorTelecommunication.
     * [Required if known R2] regardless the standard (XDS DS, XDS DR, XDM MC, XDR DS, XDR MS).
     */
    private List<Author> authors;

    /**
     * The lifecycle status of the SubmissionSet.
     * [Optional] regardless the standard (XDS DS, XDS DR, XDM MC, XDR DS, XDR MS).
     */
    // TODO replace by a enum
    private String256 availabilityStatus;

    /**
     * Comments associated with the SubmissionSet.
     * [Optional] regardless the standard (XDS DS, XDS DR, XDM MC, XDR DS, XDR MS).
     */
    private List<InternationalString> comments;

    /**
     * The code specifying the type of clinical activity
     * that resulted in placing the documents in this SubmissionSet.
     * [Required R] in XDS (DS and DR) and in XDS DS.
     * [Required if known R2] in XDM MC and XDR MS.
     */
    private CodedTerm contentTypeCode;

    /**
     * A globally unique identifier used to identify the SubmissionSet.
     * [Required R] regardless the standard (XDS DS, XDS DR, XDM MC, XDR DS, XDR MS).
     */
    @NotNull
    private String256 entryUUID;

    /**
     * A globally unique identifier for a community.
     * [Optional] regardless the standard (XDS DS, XDS DR, XDM MC, XDR DS, XDR MS).
     */
    // FIXME Possibly an OID, IdentifierOID...?
    private String256 homeCommunityId;

    /**
     * The organization(s) or person(s) for whom the SubmissionSet is intended.
     * [Optional] in XDS (DS and DR).
     * [Required if known R2] in XDM MC, XDR DS and XDR MS.
     */
    private NameValueString256 intendedRecipient;

    /**
     * The patientId represents the subject of care
     * whose longitudinal record is being reflected in this SubmissionSet.
     * [Required R] in XDS (DS and DR) and in XDS DS.
     * [Required if known R2] in XDM MC and XDR MS.
     */
    private IdentifierString256 patientId;

    /**
     * OID identifying the instance of the creating entity
     * that contributed the SubmissionSet.
     * [Required R] regardless the standard (XDS DS, XDS DR, XDM MC, XDR DS, XDR MS).
     */
    @NotNull
    private IdentifierOID sourceId;

    /**
     * Point in time at the creating entity when the SubmissionSet was created.
     * [Required R] regardless the standard (XDS DS, XDS DR, XDM MC, XDR DS, XDR MS).
     */
    @NotNull
    private NameValueDTM submissionTime;

    /**
     * The title of the SubmissionSet.
     * [Optional] regardless the standard (XDS DS, XDS DR, XDM MC, XDR DS, XDR MS).
     */
    // FIXME List or not?
    private List<InternationalString> title;

    /**
     * A globally unique identifier for the SubmissionSet
     * assigned by the creating entity.
     * [Required R] regardless the standard (XDS DS, XDS DR, XDM MC, XDR DS, XDR MS).
     */
    @NotNull
    private IdentifierOID uniqueId;

    /**
     * Default constructor.
     */
    public XdsSubmissionSet(){
        authors=new ArrayList<Author>();
        availabilityStatus=new String256();
        comments=new ArrayList<InternationalString>();
        contentTypeCode=new CodedTerm();
        entryUUID=new String256();
        homeCommunityId=new String256();
        intendedRecipient=new NameValueString256();
        intendedRecipient.setName(new String256("intendedRecipient"));
        patientId=new IdentifierString256();
        sourceId=new IdentifierOID();
        submissionTime=new NameValueDTM();
        title=new ArrayList<InternationalString>();
        uniqueId=new IdentifierOID();
    }

    //////////////////////////////////////////////////////////////////////////////
    //-------------------------\/ GETTERS & SETTERS \/--------------------------//
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String256 getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String256 availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public List<InternationalString> getComments() {
        return comments;
    }

    public void setComments(List<InternationalString> comments) {
        this.comments = comments;
    }

    public CodedTerm getContentTypeCode() {
        return contentTypeCode;
    }

    public void setContentTypeCode(CodedTerm contentTypeCode) {
        this.contentTypeCode = contentTypeCode;
    }

    public String256 getEntryUUID() {
        return entryUUID;
    }

    public void setEntryUUID(String256 entryUUID) {
        this.entryUUID = entryUUID;
    }

    public String256 getHomeCommunityId() {
        return homeCommunityId;
    }

    public void setHomeCommunityId(String256 homeCommunityId) {
        this.homeCommunityId = homeCommunityId;
    }

    public NameValueString256 getIntendedRecipient() {
        return intendedRecipient;
    }

    public void setIntendedRecipient(NameValueString256 intendedRecipient) {
        this.intendedRecipient = intendedRecipient;
    }

    public IdentifierString256 getPatientId() {
        return patientId;
    }

    public void setPatientId(IdentifierString256 patientId) {
        this.patientId = patientId;
    }

    public IdentifierOID getSourceId() {
        return sourceId;
    }

    public void setSourceId(IdentifierOID sourceId) {
        this.sourceId = sourceId;
    }

    public NameValueDTM getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(NameValueDTM submissionTime) {
        this.submissionTime = submissionTime;
    }

    public List<InternationalString> getTitle() {
        return title;
    }

    public void setTitle(List<InternationalString> title) {
        this.title = title;
    }

    /**
     * This methods returns a globally unique identifier for the
     * SubmissionSet assigned by the creating entity.
     * @return uniqueId
     */
    public IdentifierOID getUniqueId() {
        return uniqueId;
    }

    /**
     * This methods sets a globally unique identifier for the
     * SubmissionSet assigned by the creating entity.
     * @param  uniqueId
     */
    public void setUniqueId(IdentifierOID uniqueId) {
        this.uniqueId = uniqueId;
    }
    //-------------------------/\ GETTERS & SETTERS /\--------------------------//
    //////////////////////////////////////////////////////////////////////////////

    public void populate(){
        entryUUID.setString("SubmissionSet01");
        uniqueId.setValue(new OID(new String256("2009.9.1.2456")));
        sourceId.setValue(new OID(new String256("1.3.6.1.4.1.21367.2009.1.2.1")));
        patientId.setValue(new String256("6578946^^^&1.3.6.1.4.1.21367.2005.3.7&ISO"));
        contentTypeCode=new CodedTerm("11502-2","LABORATORY REPORT.TOTAL","2.16.840.1.113883.6.1");
        availabilityStatus.setString("urn:oasis:names:tc:ebxml-regrep:StatusType:Approved");
        homeCommunityId.setString("urn:oid:1.2.3");
        Author author1=new Author();
        author1.setAuthorPerson(new String256("Jack Scelere"));
        author1.getAuthorInstitutions().add(new String256("Cleveland Clinic"));
        author1.getAuthorInstitutions().add(new String256("Parma Community"));
        author1.getAuthorRoles().add(new String256("attending"));
        author1.getAuthorSpecialties().add(new String256("Orthopedic"));
        Author author2=new Author();
        author2.setAuthorPerson(new String256("D12398^Doe^John^^^^^^&1.2.3.4.5.6.7.8.9.1789.45.1&ISO"));
        author2.getAuthorInstitutions().add(new String256("Cleveland Clinic^^^^^^^^^1.2.3.4.5.6.7.8.9.1789.45"));
        author2.getAuthorInstitutions().add(new String256("Berea Community"));
        author2.getAuthorRoles().add(new String256("Primary Surgeon"));
        author2.getAuthorSpecialties().add(new String256("Orthopedic"));
        authors.add(author1);
        authors.add(author2);
        intendedRecipient.getValues().add(new String256("SomeHospital^^^^^^^^^1.2.3.9.1789.45|^Wel^Marcus^^^Dr^MD|^^Internet^mwel@healthcare.example.org"));
        intendedRecipient.getValues().add(new String256("Some Hospital^^^^^^^^^1.2.3.9.1789.45|^Peirre^LaPointe^^^Dr^MD"));
        intendedRecipient.getValues().add(new String256("|12345^LaShawn^James^^Dr^MD"));
        intendedRecipient.getValues().add(new String256("MainHospital^^^^^^^^^1.2.3.4.5.6.7.8.9.1789.2364"));
        intendedRecipient.getValues().add(new String256("||^^Internet^dr.oz@healthcare.example.org"));
        title.add(new InternationalString(LanguageCode.ENGLISH_UNITED_STATES, new String256("Example Submission Set Title")));
        comments.add(new InternationalString(LanguageCode.ENGLISH_UNITED_STATES, new String256("comment associated with SubmissionSet")));
    }

    @Override
    public String toString() {
        return "SubmissionSet{" +
                "authors=" + authors +
                ", availabilityStatus=" + availabilityStatus +
                ", comments=" + comments +
                ", contentTypeCode=" + contentTypeCode +
                ", entryUUID=" + entryUUID +
                ", homeCommunityId=" + homeCommunityId +
                ", intendedRecipient=" + intendedRecipient +
                ", patientId=" + patientId +
                ", sourceId=" + sourceId +
//                ", submissionTime=" + submissionTime +
                ", title=" + title +
                ", uniqueId=" + uniqueId +
                '}';
    }
}
