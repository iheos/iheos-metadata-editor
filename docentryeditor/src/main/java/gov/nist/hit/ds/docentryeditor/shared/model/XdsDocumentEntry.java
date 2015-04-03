package gov.nist.hit.ds.docentryeditor.shared.model;

import gov.nist.hit.ds.ebMetadata.Metadata;
import gov.nist.hit.ds.ebMetadata.MetadataSupport;
import org.apache.axiom.om.OMElement;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <b>This class represents the model which have to be respected by the xml
 * document</b>
 * <p/>
 * <p>
 * An XML document should have this different parameters
 * <ul>
 * <li>{@link #titles}: A title (ArrayList({@link InternationalString})) ;</li>
 * <li>{@link #comments}: Comments (ArrayList({@link InternationalString})) ;</li>
 * <li>{@link #authors}: One or several author(s) (ArrayList({@link Author})) ;</li>
 * <li>{@link #classCode}: A class code ({@link CodedTerm}) ;</li>
 * <li>{@link #confidentialityCodes}: A confidentiality code (ArrayList(
 * {@link CodedTerm})) ;</li>
 * <li>{@link #creationTime}: A creation time ({@link NameValueDTM}) ;</li>
 * <li>{@link #id}: An id ({@link String256}) ;</li>
 * <li>{@link #eventCode}: An event code (ArrayList({@link CodedTerm})) ;</li>
 * <li>{@link #formatCode}: A format code ({@link CodedTerm}) ;</li>
 * <li>{@link #hash}: An hash ({@link String256}) ;</li>
 * <li>{@link #healthcareFacilityType}: An health care facility type(
 * {@link CodedTerm}) ;</li>
 * <li>{@link #languageCode}: A language code {@link LanguageCode}) ;</li>
 * <li>{@link #legalAuthenticator}: A legal authenticator (
 * {@link NameValueString256}) ;</li>
 * <li>{@link #mimeType}: A mime type ({@link String256}) ;</li>
 * <li>{@link #patientID}: A patient id ({@link IdentifierString256}) ;</li>
 * <li>{@link #practiceSettingCode}: A practice setting code ({@link CodedTerm}
 * ) ;</li>
 * <li>{@link #repoUId}: A repository unique id ({@link String256}) ;</li>
 * <li>{@link #serviceStartTime}: A service start time ({@link NameValueDTM}) ;</li>
 * <li>{@link #serviceStopTime}: A service stop time ({@link NameValueDTM}) ;</li>
 * <li>{@link #size}: A size ({@link NameValueInteger}) ;</li>
 * <li>{@link #sourcePatientId}: A source patient id ({@link NameValueString256}
 * ) ;</li>
 * <li>{@link #typeCode}: A type code ({@link CodedTerm}) ;</li>
 * <li>{@link #uniqueId}: A unique id ({@link IdentifierOID}) ;</li>
 * <li>{@link #uri}: An uri ({@link String256}).</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * This class also contains getters/setters, toXml() method to return
 * information in XML format and verify method to check syntax's document.
 * </p>
 * <p/>
 * <p/>
 * <p>
 * <b> See below each type of element you can find in this model</b><br>
 * {@link Author}</br> {@link CodedTerm} <br>
 * {@link IdentifierOID}</br> {@link IdentifierString256}<br>
 * {@link InternationalString} </br> {@link NameValueString256}<br>
 * {@link NameValueDTM}</br>{@link NameValueInteger}<br>
 * {@link String256} </br> {@link OID}<br>
 * {@link DTM}
 * </p>
 * <p/>
 * <p>
 * <b>See below each method mentioned above.</b> <br>
 * {@link #verify() method verify}</br> {@link #toXML() method toXML} <br>
 * </p>
 *
 * @see ModelElement class ModelElement
 */
public class XdsDocumentEntry extends XdsModelElement implements ModelElement,Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * <b>ArrayList(ArrayList(String)) validationErrors</b> - The error(s) which
     * occure(s) when verify() method is called.<br>
     * Type: ArrayList<ArrayList<String>></br> </p>
     * <p/>
     * <p>
     * This array represents (("document_variable_name", errorMessage)
     * </p>
     *
     * @see XdsDocumentEntry class DocumentModel
     */
    private ArrayList<ArrayList<String>> validationErrors = new ArrayList<ArrayList<String>>();
    /**
     * <b>ArrayList(Author) authors</b> - The author(s) of the document
     * [Optional].<br>
     * Type: ArrayList of {@link Author}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..n </p>
     *
     * @see Author
     * @see XdsDocumentEntry
     */
    @Nullable
    private List<Author> authors;
    /**
     * <b>CodedTerm classCode</b> - The class code of the document [Mandatory].<br>
     * Type: {@link CodedTerm}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..1 </p>
     *
     * @see CodedTerm
     * @see XdsDocumentEntry
     */
    @NotNull
    private CodedTerm classCode;
    /**
     * <b>ArrayList(InternationalString) comments</b> - The comments of the
     * document [Optional].<br>
     * Type: ArrayList of {@link InternationalString}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..1 </p>
     *
     * @see InternationalString
     * @see XdsDocumentEntry
     */
    @Nullable
    private List<InternationalString> comments;
    /**
     * <b>CodedTerm confidentialityCode</b> - The confidentiality code of the
     * document [Mandatory].<br>
     * Type: ArrayList of {@link CodedTerm}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..n </p>
     *
     * @see CodedTerm
     * @see XdsDocumentEntry
     */
    @NotNull
    @NotEmpty
    // FIXME should not be a list
    private List<CodedTerm> confidentialityCodes;
    /**
     * <b>NameValueDTM creationTime</b> - The creation time of the document
     * [Mandatory].<br>
     * Type: {@link NameValueDTM}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..1 </p>
     *
     * @see NameValueDTM
     * @see XdsDocumentEntry
     */
    @NotNull
    private NameValueDTM creationTime;
    /**
     * <b>ArrayList(CodedTerm) eventCode</b> - The event code of the document
     * [Optional].<br>
     * Type: {@link CodedTerm}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..n </p>
     *
     * @see CodedTerm
     * @see XdsDocumentEntry
     */
    @Nullable
    private List<CodedTerm> eventCode;
    /**
     * <b>String256 fileName</b> - The name of the metadata file which will be generated.
     * [Mandatory]<br/>
     * <p/>
     * <b>Cardinality:</b><br/>
     * 1..1
     */
    @NotNull
    @NotEmpty
    private String256 fileName;
    /**
     * <b>CodedTerm formatCode</b> - The format code of the document
     * [Mandatory].<br>
     * Type: {@link CodedTerm}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..1 </p>
     *
     * @see CodedTerm
     * @see XdsDocumentEntry
     */
    @NotNull
    private CodedTerm formatCode;
    /**
     * <b>String256 hash</b> - The hash of the document [Optional].<br>
     * Type: {@link String256}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..1 </p>
     *
     * @see String256
     * @see XdsDocumentEntry
     */
    @Nullable
    private String256 hash;
    /**
     * <b>CodedTerm healthcareFacilityType</b> - The health care facility type
     * of the document [Mandatory].<br>
     * Type: {@link CodedTerm}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..n </p>
     *
     * @see CodedTerm
     * @see XdsDocumentEntry
     */
    @NotNull
    @NotEmpty
    private CodedTerm healthcareFacilityType;
    /**
     * <b>String256 id</b> - The id of the document [Mandatory].<br>
     * Type: {@link String256}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..1 </p>
     *
     * @see String256
     * @see XdsDocumentEntry
     */
    @NotNull
    private String256 id;
    /**
     * <b>LanguageCode languageCode</b> - The language code of the document
     * [Mandatory].<br>
     * Type: {@link LanguageCode}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..1 </p>
     *
     * @see LanguageCode
     * @see XdsDocumentEntry
     */
    @NotNull
    private LanguageCode languageCode;
    /**
     * <b>NameValueString256 legalAuthenticator</b> - The legal authenticator of
     * the document [Optional].<br>
     * Type: {@link NameValueString256}</br> </p>
     * <p/>
     * Represents a participant who has legally authenticated or attested the
     * document within the authorInstitution. Legal authentication implies that
     * a document has been signed manually or electronically by the
     * legalAuthenticator. This attribute may be absent if not applicable. If
     * present, shall have a single value
     * <p/>
     * <pre>
     *     {@code
     *     <rim:Slot name="legalAuthenticator">
     *            <rim:ValueList>
     *               <rim:Value>^Welby^Marcus^^^Dr^MD</rim:Value>
     *            </rim:ValueList>
     *     </rim:Slot>
     *     }
     * </pre>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..1 </p>
     *
     * @see NameValueString256
     * @see XdsDocumentEntry
     */
    @Nullable
    private NameValueString256 legalAuthenticator;
    /**
     * <b>String256 mimeType</b> - The MIME Type of the document in the repository [Mandatory].<br>
     * Type: {@link String256}</br> </p>
     * <p/>
     * <pre>
     *     {@code
     *        <rim:ExtrinsicObject mimeType="application/pdf"
     *              id="theDocument"
     *              objectType="urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1">
     *     }
     * </pre>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..1 </p>
     *
     * @see String256
     * @see XdsDocumentEntry
     */
    @NotNull
    private String256 mimeType;
    /**
     * <b>IdentifierString256 patientID</b> - The patient id of the document
     * [Mandatory].<br>
     * Type: {@link IdentifierString256}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..1
     *
     * @see IdentifierString256
     * @see XdsDocumentEntry
     */
    @NotNull
    private IdentifierString256 patientID;
    /**
     * <b>CodedTerm practiceSettingCode</b> - The practice setting code of the
     * document [Mandatory].<br>
     * Type: {@link CodedTerm}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..1
     *
     * @see CodedTerm
     * @see XdsDocumentEntry
     */
    @NotNull
    private CodedTerm practiceSettingCode;
    /**
     * <b>OID repositoryUId</b> - The repository unique id of the document
     * [Optional].<br>
     * Type: {@link String256}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..1 </p>
     *
     * @see OID
     * @see XdsDocumentEntry
     */
    @Nullable
    private OID repoUId;
    /**
     * <b>NameValueDTM serviceStartTime</b> - The service start time of the
     * document [Optional].<br>
     * Type: {@link NameValueDTM}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..1
     *
     * @see NameValueDTM
     * @see XdsDocumentEntry
     */
    @Nullable
    private NameValueDTM serviceStartTime;
    /**
     * <b>NameValueDTM serviceStopTime</b> - The service stop time of the
     * document [Optional].<br>
     * Type: {@link NameValueDTM}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..1 </p>
     *
     * @see NameValueDTM
     * @see XdsDocumentEntry
     */
    @Nullable
    private NameValueDTM serviceStopTime;
    /**
     * <b>NameValueInteger size</b> - The size of the document [Optional].<br>
     * Type: {@link NameValueInteger}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..1 </p>
     *
     * @see NameValueInteger
     * @see XdsDocumentEntry
     */
    @Nullable
    private NameValueInteger size;
    /**
     * <b>NameValueString256 sourcePatientId</b> - The source patient id of the
     * document [Optional].<br>
     * <p/>
     * Type: {@link NameValueString256}</br></p>
     * <p/>
     * <b>Cardinality:</b> 0..1</p>
     *
     * @see NameValueString256
     * @see XdsDocumentEntry
     */
    @Nullable
    private NameValueString256 sourcePatientId;
    /**
     * <b>NameValueString256 sourcePatientInfo</b> - The source patient info of the
     * document [Optional].<br>
     * <br/>
     * This attribute should contain demographics information of the patient to
     * whose medical record this document belongs, as the Document Source knew it at the time of submission.
     * <br/>
     * This information typically includes: the patient first and last name, sex,
     * and birth date. The Clinical XDS Affinity Domain policies may require
     * more or less specific information and format.
     * This patient information is not intended to be updated once the
     * Document is registered (just as the Document content and metadata itself
     * will not be updated without replacing the previous document). As
     * sourcePatientInfo may have been updated by the source actor, it may no
     * longer be in use within the Document Source (EHR-CR). It is only
     * intended as an audit/checking mechanism and has occasional use for
     * Document Consumer actors. Shall have a single value (only a single
     * sourcePatientInfo slot may be present).
     * <br/>
     * <p/>
     * XML Representation:
     * <pre>
     *     {@code
     *     <rim:Slot name="sourcePatientInfo">
     *               <rim:ValueList>
     *                   <rim:Value>PID-3|DTP-1^^^&amp;1.3.6.1.4.1.21367.2005.3.7&amp;ISO</rim:Value>
     *                   <rim:Value>PID-5|DICTAPHONE^ONE^^^</rim:Value>
     *                   <rim:Value>PID-7|19650120</rim:Value>
     *                   <rim:Value>PID-8|M</rim:Value>
     *                   <rim:Value>PID-11|100 MainSt^^BURLINGTON^MA^01803^USA</rim:Value>
     *               </rim:ValueList>
     *     </rim:Slot>
     *     }
     * </pre>
     * <p/>
     * PID-3 should include the source patient identifier.
     * PID-5 should include the patient name.
     * PID-8 should code the patient gender as
     * M – Male F – Female
     * O – OtherU – Unknown
     * PID-7 should include the patient date of birth.
     * PID-11 should include the patient address.
     * PID-2, PID-4, PID-12 and PID-19 should not be used.
     * <p/>
     * <p/>
     * Type: {@link NameValueString256}</br></p>
     * <p/>
     * <b>Cardinality:</b> 0..1</p>
     *
     * @see NameValueString256
     * @see XdsDocumentEntry
     */
    @Nullable
    private NameValueString256 sourcePatientInfo;
    /**
     * <b>ArrayList(InternationalString title</b> - The title of the document
     * [Optional].<br>
     * Type: ArrayList of {@link InternationalString}</br> </p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..1 </p>
     *
     * @see InternationalString
     * @see XdsDocumentEntry
     */
    @Nullable
    // FIXME Are you sure this is meant to be a list?
    private List<InternationalString> titles;
    /**
     * <b>CodedTerm typeCode</b> - The type code of the document [Mandatory].<br>
     * Type: {@link CodedTerm}</br></p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..1</p>
     *
     * @see CodedTerm
     * @see XdsDocumentEntry
     */
    @NotNull
    private CodedTerm typeCode;
    /**
     * <b> IdentifierOID uniqueId</b> - The unique id of the document
     * [Mandatory].<br>
     * Type: {@link IdentifierOID}</br></p>
     * <p/>
     * <b>Cardinality:</b><br>
     * 1..1</p>
     *
     * @see IdentifierOID
     * @see XdsDocumentEntry
     */
    @NotNull
    private IdentifierOID uniqueId;
    /**
     * <b>String256 uri</b> - The uri of the document [Optional].<br>
     * Type: {@link String256}</br></p>
     * <p/>
     * For XDM the URI element shall point to the file containing the
     * document.
     * If present, shall have a single value.
     * There are two formats for coding this attribute. If the string representing
     * the URI is 128 characters or shorter, the short format may be used:
     * <pre>
     *     {@code
     *      <rim:Slot name="URI">
     *          <rim:ValueList>
     *              <rim:Value>http://www.ihe.net</rim:Value>
     *          </rim:ValueList>
     *      </rim:Slot>
     *      }
     * </pre>
     * <p/>
     * If the string is more than 128 characters long, the long format shall be
     * used:
     * <pre>
     *     {@code
     *     <rim:Slot name="URI">
     *          <rim:ValueList>
     *              <rim:Value>1|http://www.ihe.net/IHERetrieveDocument?</rim:Value>
     *              <rim:Value>2|requestType=DOCUMENT&documentUID=1.2.3</rim:Value>
     *              <rim:Value>3|&preferredContentType=application%2fpdf</rim:Value>
     *          </rim:ValueList>
     *     </rim:Slot>
     *     }
     * </pre>
     * <p/>
     * Each Value is composed of an ordering prefix followed by a portion of
     * the actual URI string. The ordering prefix shall be sequential starting at
     * the value 1. When the long format is used, all Values shall have an
     * ordering prefix.
     * Each value is ordered by its ordering prefix:
     * ordering-prefix :== digit vertical-bar
     * digit :== ‘1’ | ‘2’ | ‘3’ | ‘4’ | ‘5’ | ‘6’ | ‘7’ | ‘8’ | ‘9’
     * vertical-bar :== ‘|’
     * The long version may be used for URIs of less than 129 characters. This
     * profile does not specify how a URI is to be broken up into pieces. The
     * following example is valid.
     * <pre>
     *     {@code
     *      <rim:Slot name="URI">
     *          <rim:ValueList>
     *              <rim:Value>1|http://www.ihe.net</rim:Value>
     *          </rim:ValueList>
     *      </rim:Slot>
     *     }
     * </pre>
     * <p/>
     * <b>Cardinality:</b><br>
     * 0..1</p>
     *
     * @see String256
     * @see XdsDocumentEntry
     */
    @Nullable
    private String256 uri;

    // TODO Use a map for validation purpose (Map<String,(String|XdsError)>
    private Map<String, String> errorsMap = new HashMap<String, String>();
    private String256 logicalId;
    private String256 version;
    private String256 availabilityStatus;
    private String256 homeCommunityId;

    public XdsDocumentEntry() {
        languageCode=LanguageCode.ENGLISH_UNITED_STATES;
        classCode = new CodedTerm();
        creationTime = new NameValueDTM();
        creationTime.setName(new String256().setString("creationTime"));
        id = new String256();
        fileName = new String256();
        formatCode = new CodedTerm();
        hash = new String256();
        healthcareFacilityType = new CodedTerm();
        // languageCode = new String256();
        legalAuthenticator = new NameValueString256();
        legalAuthenticator.setName(new String256()
                .setString("legalAuthenticator"));
        mimeType = new String256();
        patientID = new IdentifierString256();
        patientID.setIdType(new String256().setString("urn:uuid:6b5aeala-874d-4603-a4bc-96a0a7b38446"));
        practiceSettingCode = new CodedTerm();
        repoUId = new OID();
        serviceStartTime = new NameValueDTM();
        serviceStartTime.setName(new String256().setString("serviceStartTime"));
        serviceStopTime = new NameValueDTM();
        serviceStopTime.setName(new String256().setString("serviceStopTime"));
        size = new NameValueInteger();
        size.setName(new String256().setString("size"));
        sourcePatientId = new NameValueString256();
        sourcePatientId.setName(new String256().setString("sourcePatientId"));
        sourcePatientInfo = new NameValueString256();
        sourcePatientInfo.setName(new String256().setString("sourcePatientInfo"));
        typeCode = new CodedTerm();
        uniqueId = new IdentifierOID();
        uniqueId.setIdType(new String256().setString("urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab"));
        uri = new String256();

        titles = new ArrayList<InternationalString>();
        comments = new ArrayList<InternationalString>();
        authors = new ArrayList<Author>();
        confidentialityCodes = new ArrayList<CodedTerm>();
        eventCode = new ArrayList<CodedTerm>();
    }

    public String256 getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String256 availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public List<InternationalString> getTitles() {
        return titles;
    }

    public void setTitles(List<InternationalString> titles) {
        this.titles = titles;
    }

    public List<InternationalString> getComments() {
        return comments;
    }

    public void setComments(List<InternationalString> comments) {
        this.comments = comments;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public CodedTerm getClassCode() {
        return classCode;
    }

    public void setClassCode(CodedTerm classCode) {
        this.classCode = classCode;
    }

    public List<CodedTerm> getConfidentialityCodes() {
        return confidentialityCodes;
    }

    public void setConfidentialityCodes(List<CodedTerm> confidentialityCodes) {
        this.confidentialityCodes = confidentialityCodes;
    }

    public NameValueDTM getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(NameValueDTM creationTime) {
        this.creationTime = creationTime;
    }

    public String256 getId() {
        return id;
    }

    public void setId(String256 id) {
        this.id = id;
    }

    public List<CodedTerm> getEventCode() {
        return eventCode;
    }

    public void setEventCode(List<CodedTerm> eventCode) {
        this.eventCode = eventCode;
    }

    public CodedTerm getFormatCode() {
        return formatCode;
    }

    public void setFormatCode(CodedTerm formatCode) {
        this.formatCode = formatCode;
    }

    public String256 getHash() {
        return hash;
    }

    public void setHash(String256 hash) {
        this.hash = hash;
    }

    public CodedTerm getHealthcareFacilityType() {
        return healthcareFacilityType;
    }

    public void setHealthcareFacilityType(CodedTerm healthcareFacilityType) {
        this.healthcareFacilityType = healthcareFacilityType;
    }

    public LanguageCode getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(LanguageCode languageCode) {
        this.languageCode = languageCode;
    }

    public NameValueString256 getLegalAuthenticator() {
        return legalAuthenticator;
    }

    public void setLegalAuthenticator(NameValueString256 legalAuthenticator) {
        this.legalAuthenticator = legalAuthenticator;
    }

    public String256 getFileName() {
        return fileName;
    }

    public void setFileName(String256 fileName) {
        this.fileName = fileName;
    }

    public String256 getHomeCommunityId(){
        return homeCommunityId;
    }

    public void setHomeCommunityId(String256 homeCommunityId) {
        this.homeCommunityId = homeCommunityId;
    }

    public String256 getLogicalId(){
        return logicalId;
    }

    public void setLogicalId(String256 logicalId) {
        this.logicalId = logicalId;
    }

    public String256 getMimeType() {
        return mimeType;
    }

    public void setMimeType(String256 mimeType) {
        this.mimeType = mimeType;
    }

    public IdentifierString256 getPatientID() {
        return patientID;
    }

    public void setPatientID(IdentifierString256 patientID) {
        this.patientID = patientID;
    }

    public CodedTerm getPracticeSettingCode() {
        return practiceSettingCode;
    }

    public void setPracticeSettingCode(CodedTerm practiceSettingCode) {
        this.practiceSettingCode = practiceSettingCode;
    }

    public OID getRepoUId() {
        return repoUId;
    }

    public void setRepoUId(OID repoUId) {
        this.repoUId = repoUId;
    }

    public NameValueDTM getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(NameValueDTM serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public NameValueDTM getServiceStopTime() {
        return serviceStopTime;
    }

    public void setServiceStopTime(NameValueDTM serviceStopTime) {
        this.serviceStopTime = serviceStopTime;
    }

    public NameValueInteger getSize() {
        return size;
    }

    public void setSize(NameValueInteger size) {
        this.size = size;
    }

    public NameValueString256 getSourcePatientId() {
        return sourcePatientId;
    }

    public void setSourcePatientId(NameValueString256 sourcePatientId) {
        this.sourcePatientId = sourcePatientId;
    }

    public NameValueString256 getSourcePatientInfo() {
        return sourcePatientInfo;
    }

    public void setSourcePatientInfo(NameValueString256 sourcePatientInfo) {
        this.sourcePatientInfo = sourcePatientInfo;
    }

    public CodedTerm getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(CodedTerm typeCode) {
        this.typeCode = typeCode;
    }

    public IdentifierOID getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(IdentifierOID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String256 getUri() {
        return uri;
    }

    public void setUri(String256 uri) {
        this.uri = uri;
    }

    public String256 getVersion(){
        return version;
    }

    public void setVersion(String256 version) {
        this.version = version;
    }

    public XdsDocumentEntry copy(){
        XdsDocumentEntry doc=new XdsDocumentEntry();
        doc.setId(this.id!=null ? this.id.copy() : null);
        doc.setHomeCommunityId(this.homeCommunityId!=null ? this.homeCommunityId.copy():null);
        doc.setUri(this.uri.copy());
        for (Author cp : this.authors) {
            doc.getAuthors().add(cp.copy());
        }
        doc.setAvailabilityStatus(this.availabilityStatus!=null ? this.availabilityStatus.copy():null);
        doc.setClassCode(this.classCode.copy());
        for (InternationalString cp : this.comments) {
            doc.getComments().add(cp.copy());
        }
        for (CodedTerm cp : this.confidentialityCodes) {
            doc.getConfidentialityCodes().add(cp.copy());
        }
        doc.setCreationTime(this.creationTime.copy());
        for (CodedTerm cp : this.eventCode){
            doc.getEventCode().add(cp.copy());
        }
        doc.setFileName(new String256(this.fileName.getString()));
        doc.setFormatCode(this.formatCode.copy());
        doc.setHash(this.hash.copy());
        doc.setHealthcareFacilityType(this.healthcareFacilityType.copy());
        doc.setLanguageCode(this.languageCode);
        doc.setLegalAuthenticator(this.legalAuthenticator.copy());
        doc.setLogicalId(this.logicalId!=null?this.logicalId.copy():null);
        doc.setMimeType(this.mimeType.copy());
        doc.setPatientID(this.patientID.copy());
        doc.setPracticeSettingCode(this.practiceSettingCode.copy());
        doc.setRepoUId(this.repoUId.copy());
        doc.setServiceStartTime(this.serviceStartTime.copy());
        doc.setServiceStopTime(this.serviceStopTime.copy());
        doc.setSize(this.size.copy());
        doc.setSourcePatientId(this.sourcePatientId.copy());
        doc.setSourcePatientInfo(this.sourcePatientInfo.copy());
        doc.setTypeCode(this.typeCode.copy());
        for (InternationalString cp:this.titles) {
            doc.getTitles().add(cp.copy());
        }
        doc.setUniqueId(this.uniqueId.copy());
        doc.setVersion(this.version!=null?this.version.copy():null);
        return doc;
    }

    /**
     * <p>
     * <b>Method toXML</b> <br>
     * This method will be called to build a XML file with the information taken
     * from the local DocumentModel.<br>
     * It uses methods toXML() from each model class.
     * </p>
     *
     * @return String which contains the document in XML format
     * @see XdsDocumentEntry class DocumentModel
     */
    // TODO change this method or create a translator to get real xds xml document format
    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Document>\n");
        System.out.println(getTitles());
        if (getTitles() != null && titles.size() > 0) {
            xml.append("\t<titles>\n");
            for (InternationalString str : titles) {
                xml.append(str.toXML());
            }
            xml.append("\t</titles>\n");
        }
        if (getComments() != null && comments.size() > 0) {
            xml.append("\t<comments>\n");
            for (InternationalString str : comments) {
                xml.append(str.toXML());
            }
            xml.append("\t</comments>\n");
        }

        if (getAuthors() != null && authors.size() > 0) {
            xml.append("\t<authors>\n");
            for (Author auth : authors) {
                xml.append(auth.toXML());
            }
            xml.append("\t</authors>\n");
        }

        if (classCode != null && !classCode.getCode().equals("")) {
            xml.append("\t<classcode>\n");
            xml.append(classCode.toXML());
            xml.append("\t</classcode>\n");
        }
        if (confidentialityCodes.size() > 0) {
            xml.append("\t<confidentialitycode>\n");
            for (CodedTerm ct : confidentialityCodes) {
                xml.append(ct.toXML());
            }
            xml.append("\t</confidentialitycode>\n");
        }
        if (creationTime != null) {
            xml.append("\t<creationtime>\n");
            xml.append(creationTime.toXML());
            xml.append("\t</creationtime>\n");
        }
        if (id != null && !id.equals("")) {
            xml.append("\t<id>");
            xml.append(id.toString());
            xml.append("</id>\n");
        }
        if (getEventCode() != null && eventCode.size() > 0) {
            xml.append("\t<eventcode>\n");
            for (CodedTerm ct : eventCode) {
                xml.append(ct.toXML());
            }
            xml.append("\t</eventcode>\n");
        }

        if (formatCode != null && !formatCode.equals("")) {
            xml.append("\t<formatcode>\n");
            xml.append(formatCode.toXML());
            xml.append("\t</formatcode>\n");
        }
        if (getHash() != null && !hash.equals("")) {
            xml.append("\t<hash>");
            xml.append(hash.toString());
            xml.append("</hash>\n");
        }

        if (!healthcareFacilityType.getCode().getString().equals("")) {
            xml.append("\t<healthcarefacilitytype>\n");
            xml.append(healthcareFacilityType.toXML());
            xml.append("\t</healthcarefacilitytype>\n");
        }

        if (languageCode != null) {
            xml.append("\t<languagecode>");
            xml.append(languageCode.toString());
            xml.append("</languagecode>\n");
        }
        if (getLegalAuthenticator() != null && legalAuthenticator.getValues().size() > 0) {
            xml.append("\t<legalauthenticator>\n");
            xml.append(legalAuthenticator.toXML());
            xml.append("\t</legalauthenticator>\n");
        }
        if (mimeType != null && !mimeType.equals("")) {
            xml.append("\t<mimetype>");
            xml.append(mimeType.toString());
            xml.append("</mimetype>\n");
        }
        if (patientID != null && patientID.getValue() != null && !patientID.getValue().getString().equals("")) {
            xml.append("\t<patientid>\n");
            xml.append(patientID.toXML());
            xml.append("\t</patientid>\n");
        }
        if (practiceSettingCode != null && !practiceSettingCode.getCode().getString().equals("")) {
            xml.append("\t<practicesettingcode>\n");
            xml.append(practiceSettingCode.toXML());
            xml.append("\t</practicesettingcode>\n");
        }
        if (getRepoUId() != null && !repoUId.getOid().getString().equals("")) {
            xml.append("\t<repositoryuniqueid>");
            xml.append(repoUId.toString());
            xml.append("</repositoryuniqueid>\n");
        }

        if (getServiceStartTime() != null) {
            xml.append("\t<servicestarttime>\n");
            xml.append(serviceStartTime.toXML());
            xml.append("\t</servicestarttime>\n");
        }

        if (serviceStopTime != null) {
            xml.append("\t<servicestoptime>\n");
            xml.append(serviceStopTime.toXML());
            xml.append("\t</servicestoptime>\n");
        }

        if (size != null) {
            xml.append("\t<size>\n");
            xml.append(size.toXML());
            xml.append("\t</size>\n");
        }

        if (getSourcePatientId() != null && sourcePatientId.getValues().size() > 0) {
            xml.append("\t<sourcepatientid>\n");
            xml.append(sourcePatientId.toXML());
            xml.append("\t</sourcepatientid>\n");
        }

        if (getSourcePatientInfo() != null && sourcePatientInfo.getValues().size() > 0) {
            xml.append("\t<sourcepatientinfo>\n");
            xml.append(sourcePatientInfo.toXML());
            xml.append("\t</sourcepatientinfo>\n");
        }

        if (typeCode != null && typeCode.getCode() != null && !typeCode.getCode().getString().equals("")) {
            xml.append("\t<typecode>\n");
            xml.append(typeCode.toXML());
            xml.append("\t</typecode>\n");
        }
        if (uniqueId != null && !uniqueId.getValue().getOid().getString().equals("")) {
            xml.append("\t<uniqueid>\n");
            xml.append(uniqueId.toXML());
            xml.append("\t</uniqueid>\n");
        }
        if (getUri() != null && !uri.getString().equals("")) {
            xml.append("\t<uri>");
            xml.append(uri.toString());
            xml.append("</uri>\n");
        }

        xml.append("</Document>");

        String newXmlFile = xml.toString().replaceAll("&", "&amp;");
        return newXmlFile;
    }

    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check syntax's document<br>
     * It uses methods verify() from each model class</br> TODO (and save all
     * error message in validationError.)
     * </p>
     *
     * @return boolean true if all the document is available, else return false
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsDocumentEntry
     * @see #validationErrors
     */
    public boolean verify() throws String256Exception {
        // FIXME verification issue answer isn't well assigned along the process, this algorithm is horrible and wrong
        boolean answer = true;
        int total = 0;
        for (InternationalString is : titles) {
            if (!is.verify()) {
                ArrayList<String> titleError = new ArrayList<String>();
                titleError.set(0, "title");
                // titleError
                // .set(1,
                // "message d'erreur, ou appel de fonction qui analyse la faute dans la classe InternationalString et retourne le msg d'erreur, ou encore intervention d' exception");

                // titleError.set(1, is.getError());

                validationErrors.set(total, titleError);
                total = +1;
            }
        }

        for (InternationalString is : comments) {
            answer = is.verify();
        }

        for (Author auth : authors) {
            answer = auth.verify();
        }

        answer = classCode.verify();
        for (CodedTerm ct : confidentialityCodes) {
            answer = ct.verify();
        }

        answer = creationTime.verify();
        answer = id.verify();

        for (CodedTerm ct : eventCode) {
            answer = ct.verify();
        }

        // FIXME possible NullPointerExceptions
        answer = formatCode.verify();
        answer = hash.verify();
        answer = healthcareFacilityType.verify();
        // answer = languageCode.verify();
        answer = legalAuthenticator.verify();
        answer = mimeType.verify();
        answer = patientID.verify();
        answer = practiceSettingCode.verify();
        answer = repoUId.verify();
        answer = serviceStartTime.verify();
        answer = serviceStopTime.verify();
        answer = size.verify();
        answer = sourcePatientId.verify();
        answer = typeCode.verify();
        answer = uniqueId.verify();
        answer = uri.verify();

        return answer;
    }

    @Override
    public String toString() {
        return "XdsDocumentEntry[" +
                "authors={" + authors +
                "}, classCode=" + classCode +
                ", comments={" + comments +
                "}, confidentialityCodes={" + confidentialityCodes +
                "}, creationTime=" + creationTime +
                ", eventCode={" + eventCode +
                "}, fileName=" + fileName +
                ", formatCode=" + formatCode +
                ", hash=" + hash +
                ", healthcareFacilityType=" + healthcareFacilityType +
                ", id=" + id +
                ", languageCode=" + languageCode +
                ", legalAuthenticator=" + legalAuthenticator +
                ", mimeType=" + mimeType +
                ", patientID=" + patientID +
                ", practiceSettingCode=" + practiceSettingCode +
                ", repoUId=" + repoUId +
                ", serviceStartTime=" + serviceStartTime +
                ", serviceStopTime=" + serviceStopTime +
                ", size=" + size +
                ", sourcePatientId=" + sourcePatientId +
                ", sourcePatientInfo={" + sourcePatientInfo +
                "}, titles={" + titles +
                "}, typeCode=" + typeCode +
                ", uniqueId=" + uniqueId +
                ", uri=" + uri +
                ", logicalId=" + logicalId +
                ", version=" + version +
                ", availabilityStatus=" + availabilityStatus +
                ", homeCommunityId=" + homeCommunityId +
                ']';
    }

}
