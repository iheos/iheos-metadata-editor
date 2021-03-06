package gov.nist.hit.ds.docentryeditor.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import gov.nist.hit.ds.docentryeditor.shared.model.*;

/**
 * Created by onh2 on 10/2/2014.
 */
public class ClientTestHelper {
    public static final ClientTestHelper INSTANCE = new ClientTestHelper();
    public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    private XdsDocumentEntry docentry;

    private ClientTestHelper() {
        initDocEntry();
    }

    private void initDocEntry() {
        docentry = new XdsDocumentEntry();
        // ---- TITLES
        docentry.getTitles().add(new InternationalString(LanguageCode.getValueOf("en-GB"), new String256("Health record")));
        docentry.getTitles().add(new InternationalString(LanguageCode.getValueOf("fr-FR"), new String256("Carnet de sante")));
        // ---- COMMENTS
        docentry.getComments().add(new InternationalString(LanguageCode.getValueOf("en-GB"), new String256("Health record metadata")));
        docentry.getComments().add(new InternationalString(LanguageCode.getValueOf("fr-FR"), new String256("Metadonnees du carnet de sante")));
        // ---- AUTHORS
        Author author1 = new Author();
        author1.setAuthorPerson(new String256("Jack Scelere"));
        author1.getAuthorInstitutions().add(new String256("Cleveland Clinic"));
        author1.getAuthorInstitutions().add(new String256("Parma Community"));
        author1.getAuthorRoles().add(new String256("attending"));
        author1.getAuthorSpecialties().add(new String256("Orthopedic"));
        author1.getAuthorTelecommunications().add(new String256("^^Internet^Jack.Scelere@healthcare.example.org"));
        Author author2 = new Author();
        author2.setAuthorPerson(new String256("D12398^Doe^John^^^^^^&1.2.3.4.5.6.7.8.9.1789.45.1&ISO"));
        author2.getAuthorInstitutions().add(new String256("Cleveland Clinic^^^^^^^^^1.2.3.4.5.6.7.8.9.1789.45"));
        author2.getAuthorInstitutions().add(new String256("Berea Community"));
        author2.getAuthorRoles().add(new String256("Primary Surgon"));
        author2.getAuthorSpecialties().add(new String256("Orthopedic"));
        author2.getAuthorTelecommunications().add(new String256("^^Internet^john.doe@healthcare.example.org"));
        docentry.getAuthors().add(author1);
        docentry.getAuthors().add(author2);
        // ---- CLASS CODE
        docentry.setClassCode(new CodedTerm("H25l", "test", "Connect-a-thon contentTypeCodes"));
        // ---- CONFIDENTIALITY CODES
        docentry.getConfidentialityCodes().add(new CodedTerm("1.3.6.1.4.1.21367.2006.7.102", "Clinical-Staff", "Connect-a-thon confidentialityCodes 1"));
        docentry.getConfidentialityCodes().add(new CodedTerm("1.3.6.1.4.1.21367.2006.7.103", "Clinical-Staff-Allowed", "Connect-a-thon confidentialityCodes 2"));
        docentry.getConfidentialityCodes().add(new CodedTerm("1.3.6.1.4.1.21367.2006.7.104", "Clinical-Staff-Priority", "Connect-a-thon confidentialityCodes 3"));
        // ---- CREATION TIME
        NameValueDTM creation = new NameValueDTM();
        creation.setName(new String256("creationTime"));
        creation.getValues().clear();
        creation.getValues().add(new DTM(DateTimeFormat.getFormat(DATE_TIME_FORMAT).parse("20131126214203")));
        docentry.setCreationTime(creation);
        // ---- EVENT CODES
        docentry.getEventCode().add(new CodedTerm("Op-1", "Operations", "Connect-a-thon eventCodes 1"));
        docentry.getEventCode().add(new CodedTerm("HEv-1.3.5", "Health Events", "Connect-a-thon eventCodes 2"));
        // ---- FORMAT CODE
        docentry.setFormatCode(new CodedTerm("CDAR2/IHE 1.0", "CDAR2/IHE 1.0", "Connect-a-thon formatCodes"));
        // ---- HEALTHCARE FACILITY TYPE
        docentry.setHealthcareFacilityType(new CodedTerm("Outpatient", "Outpatient", "Connect-a-thon healthcareFacilityCodes"));
        // ---- LANGUAGE CODE
        docentry.setLanguageCode(LanguageCode.getValueOf("en-GB"));
        // ---- LEGAL AUTHENTICATOR
        docentry.getLegalAuthenticator().getValues().clear();
        docentry.getLegalAuthenticator().getValues().add(new String256("11375^Welby^Marcus^J^Jr. MD^Dr^^^&1.2.840.113619.6.197&ISO"));
        // ---- MIME TYPE
        docentry.setMimeType(new String256("application/pdf"));
        // ---- PATIENT ID
        docentry.setPatientID(new IdentifierString256(new String256("6578946^^^&1.3.6.1.4.1.21367.2005.3.7&ISO"), new String256("urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446")));
        // ---- PRACTICE SETTING CODE
        docentry.setPracticeSettingCode(new CodedTerm("General Medicine", "General Medicine", "Connect-a-thon practiceSettingCodes"));
        // ---- REPOSITORY UNIQUE ID
        docentry.setRepoUId(new OID(new String256("1.3.6.1.4.1.28867.2006.3.1")));
        // ---- SERVICE START TIME
        NameValueDTM start = new NameValueDTM();
        start.setName(new String256("startTime"));
        start.getValues().clear();
        start.getValues().add(new DTM(DateTimeFormat.getFormat("yyyyMMddHHmmss").parse("20131202201000")));
        docentry.setServiceStartTime(start);
        // ---- SERVICE STOP TIME
        NameValueDTM stop = new NameValueDTM();
        stop.setName(new String256("stopTime"));
        stop.getValues().clear();
        stop.getValues().add(new DTM(DateTimeFormat.getFormat("yyyyMMddHHmmss").parse("20140213101500")));
        docentry.setServiceStopTime(stop);
        // ---- SIZE
        docentry.setSize(new String256("65"));
        // ---- SOURCE PATIENT ID
        docentry.getSourcePatientId().setName(new String256("SourcePatientID"));
        docentry.getSourcePatientId().getValues().clear();
        docentry.getSourcePatientId().getValues().add(new String256("j98789^^^&1.2.3.4.343.1&ISO"));
        // ---- SOURCE PATIENT INFO
        docentry.getSourcePatientInfo().setName(new String256("SourcePatientInfo"));
        docentry.getSourcePatientInfo().getValues().clear();
        docentry.getSourcePatientInfo().getValues().add(new String256("PID-3|DTP-1^^^&j98789^^^&1.2.3.4.343.1&ISO"));
        docentry.getSourcePatientInfo().getValues().add(new String256("PID-5|DICTAPHONE^ONE^^^"));
        docentry.getSourcePatientInfo().getValues().add(new String256("PID-7|19650120"));
        docentry.getSourcePatientInfo().getValues().add(new String256("PID-8|M"));
        docentry.getSourcePatientInfo().getValues().add(new String256("PID-11|100 MainSt^^BURLINGTON^MA^01803^USA"));
        // ---- TYPE CODE
        docentry.setTypeCode(new CodedTerm("History and Physical", "History and Physical", "Connect-a-thon typeCodes"));
        // ---- UNIQUE ID
        docentry.setUniqueId(new IdentifierOID(new OID(new String256("1.3.6.1.4.1.21367.2005.3.7^11379")), new String256("urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab")));
        // ---- URI
        docentry.setUri(new String256("http://www.ihe.net"));
        // ---- DocEntryUUID
        docentry.setId(new String256("123456789"));
        // ---- HASH
        docentry.setHash(new String256("da39a3ee5e6b4b0d3255bfef95601890afd80709"));


    }

    public XdsDocumentEntry getDocEntry() {
        return docentry;
    }
}
