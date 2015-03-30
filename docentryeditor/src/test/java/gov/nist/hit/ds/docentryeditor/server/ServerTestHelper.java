package gov.nist.hit.ds.docentryeditor.server;

import gov.nist.hit.ds.docentryeditor.shared.model.*;

import java.text.SimpleDateFormat;

/**
 * Created by onh2 on 10/2/2014.
 */
public class ServerTestHelper {
    public final static ServerTestHelper instance = new ServerTestHelper();
    private XdsDocumentEntry docentry;
    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

    private ServerTestHelper() {
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
        docentry.getConfidentialityCodes().add(new CodedTerm("1.3.6.1.4.1.21367.2006.7.102", "Clinical-Staff", "urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f"));
        docentry.getConfidentialityCodes().add(new CodedTerm("1.3.6.1.4.1.21367.2006.7.103", "Clinical-Staff-Allowed", "urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840a"));
        docentry.getConfidentialityCodes().add(new CodedTerm("1.3.6.1.4.1.21367.2006.7.104", "Clinical-Staff-Priority", "urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840b"));
        // ---- CREATION TIME
        NameValueDTM creation = new NameValueDTM();
        creation.setName(new String256("creationTime"));
        creation.getValues().clear();
        try {
            creation.getValues().add(new DTM((format.parse("20131126214203"))));
        } catch (Exception e) {
            System.err.println("Error while parsing date to format yyyyMMddHHmmss");
            System.err.println(e.getStackTrace());
        }
        docentry.setCreationTime(creation);
        // ---- EVENT CODES
        docentry.getEventCode().add(new CodedTerm("Op-1", "Operations", "urn:uuid:f4f85eac-e6cb-4883-b524-f2705395555d"));
        docentry.getEventCode().add(new CodedTerm("HEv-1.3.5", "Health Events", "urn:uuid:f4f85eac-e6cb-4883-b524-f2705395555e"));
        // ---- FORMAT CODE
        docentry.setFormatCode(new CodedTerm("CDAR2/IHE 1.0", "CDAR2/IHE 1.0", "urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d"));
        // ---- HEALTHCARE FACILITY TYPE
        docentry.setHealthcareFacilityType(new CodedTerm("Outpatient", "Outpatient", "urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1"));
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
        docentry.setPracticeSettingCode(new CodedTerm("General Medicine", "General Medicine", "urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead"));
        // ---- REPOSITORY UNIQUE ID
        docentry.setRepoUId(new OID(new String256("1.3.6.1.4.1.28867.2006.3.1")));
        // ---- SERVICE START TIME
        NameValueDTM start = new NameValueDTM();
        start.setName(new String256("startTime"));
        start.getValues().clear();
        try {
            start.getValues().add(new DTM((format.parse("20131202201000"))));
        } catch (Exception e) {
            System.err.println("Error while parsing date to format yyyyMMddHHmmss");
            System.err.println(e.getStackTrace());
        }
        docentry.setServiceStartTime(start);
        // ---- SERVICE STOP TIME
        NameValueDTM stop = new NameValueDTM();
        stop.setName(new String256("stopTime"));
        stop.getValues().clear();
        try {
            start.getValues().add(new DTM((format.parse("20140213101500"))));
        } catch (Exception e) {
            System.err.println("Error while parsing date to format yyyyMMddHHmmss");
            System.err.println(e.getStackTrace());
        }
        docentry.setServiceStopTime(stop);
        // ---- SIZE
        docentry.getSize().setName(new String256("size"));
        docentry.getSize().getValues().clear();
        docentry.getSize().getValues().add(65);
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
        docentry.setTypeCode(new CodedTerm("History and Physical", "History and Physical", "urn:uuid:aa543740-bdda-424e-8c96-df4873be8500"));
        // ---- UNIQUE ID
        docentry.setUniqueId(new IdentifierOID(new OID(new String256("1.3.6.1.4.1.21367.2005.3.7^11379")), new String256("urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab")));
        // ---- URI
        docentry.setUri(new String256("http://www.ihe.net"));
        // ---- DocEntryUUID
        docentry.setId(new String256("123456789"));
        // ---- HASH
        docentry.setHash(new String256("da39a3ee5e6b4b0d3255bfef95601890afd80709"));
    }

    public String docEntryToXML(XdsDocumentEntry docentry) {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<Document>\n");
        System.out.println(docentry.getTitles());
        if (docentry.getTitles() != null && docentry.getTitles().size() > 0) {
            xml.append("\t<titles>\n");
            for (InternationalString str : docentry.getTitles()) {
                xml.append(str.toXML());
            }
            xml.append("\t</titles>\n");
        }
        if (docentry.getComments() != null && docentry.getComments().size() > 0) {
            xml.append("\t<comments>\n");
            for (InternationalString str : docentry.getComments()) {
                xml.append(str.toXML());
            }
            xml.append("\t</comments>\n");
        }

        if (docentry.getAuthors() != null && docentry.getAuthors().size() > 0) {
            xml.append("\t<authors>\n");
            for (Author auth : docentry.getAuthors()) {
                xml.append(auth.toXML());
            }
            xml.append("\t</authors>\n");
        }

        if (docentry.getClassCode() != null && !docentry.getClassCode().getCode().equals("")) {
            xml.append("\t<classcode>\n");
            xml.append(docentry.getClassCode().toXML());
            xml.append("\t</classcode>\n");
        }
        if (docentry.getConfidentialityCodes().size() > 0) {
            xml.append("\t<confidentialitycode>\n");
            for (CodedTerm ct : docentry.getConfidentialityCodes()) {
                xml.append(ct.toXML());
            }
            xml.append("\t</confidentialitycode>\n");
        }
        if (docentry.getCreationTime() != null) {
            xml.append("\t<creationtime>\n");
            xml.append(new SimpleDateFormat("yyyyMMddHHmmss").format(docentry.getCreationTime().getValues().get(0).getDtm()));
            xml.append("\t</creationtime>\n");
        }
        if (docentry.getId() != null && !docentry.getId().equals("")) {
            xml.append("\t<id>");
            xml.append(docentry.getId().toString());
            xml.append("</id>\n");
        }
        if (docentry.getEventCode() != null && docentry.getEventCode().size() > 0) {
            xml.append("\t<eventcode>\n");
            for (CodedTerm ct : docentry.getEventCode()) {
                xml.append(ct.toXML());
            }
            xml.append("\t</eventcode>\n");
        }

        if (docentry.getFormatCode() != null && !docentry.getFormatCode().equals("")) {
            xml.append("\t<formatcode>\n");
            xml.append(docentry.getFormatCode().toXML());
            xml.append("\t</formatcode>\n");
        }
        if (docentry.getHash() != null && !docentry.getHash().equals("")) {
            xml.append("\t<hash>");
            xml.append(docentry.getHash().toString());
            xml.append("</hash>\n");
        }

        if (!docentry.getHealthcareFacilityType().getCode().getString().equals("")) {
            xml.append("\t<healthcarefacilitytype>\n");
            xml.append(docentry.getHealthcareFacilityType().toXML());
            xml.append("\t</healthcarefacilitytype>\n");
        }

        if (docentry.getLanguageCode() != null) {
            xml.append("\t<languagecode>");
            xml.append(docentry.getLanguageCode().toString());
            xml.append("</languagecode>\n");
        }
        if (docentry.getLegalAuthenticator() != null && docentry.getLegalAuthenticator().getValues().size() > 0) {
            xml.append("\t<legalauthenticator>\n");
            xml.append(docentry.getLegalAuthenticator().toXML());
            xml.append("\t</legalauthenticator>\n");
        }
        if (docentry.getMimeType() != null && !docentry.getMimeType().equals("")) {
            xml.append("\t<mimetype>");
            xml.append(docentry.getMimeType().toString());
            xml.append("</mimetype>\n");
        }
        if (docentry.getPatientID() != null && docentry.getPatientID().getValue() != null && !docentry.getPatientID().getValue().getString().equals("")) {
            xml.append("\t<patientid>\n");
            xml.append(docentry.getPatientID().toXML());
            xml.append("\t</patientid>\n");
        }
        if (docentry.getPracticeSettingCode() != null && !docentry.getPracticeSettingCode().getCode().getString().equals("")) {
            xml.append("\t<practicesettingcode>\n");
            xml.append(docentry.getPracticeSettingCode().toXML());
            xml.append("\t</practicesettingcode>\n");
        }
        if (docentry.getRepoUId() != null && !docentry.getRepoUId().getOid().getString().equals("")) {
            xml.append("\t<repositoryuniqueid>");
            xml.append(docentry.getRepoUId().toString());
            xml.append("</repositoryuniqueid>\n");
        }

        if (docentry.getServiceStartTime() != null) {
            xml.append("\t<servicestarttime>\n");
            xml.append(new SimpleDateFormat("yyyyMMddHHmmss").format(docentry.getServiceStartTime().getValues().get(0).getDtm()));
            xml.append("\t</servicestarttime>\n");
        }

        if (docentry.getServiceStopTime() != null && !docentry.getServiceStopTime().getValues().isEmpty()) {
            xml.append("\t<servicestoptime>\n");
            xml.append(new SimpleDateFormat("yyyyMMddHHmmss").format(docentry.getServiceStopTime().getValues().get(0).getDtm()));
            xml.append("\t</servicestoptime>\n");
        }

        if (docentry.getSize() != null) {
            xml.append("\t<size>\n");
            xml.append(docentry.getSize().toXML());
            xml.append("\t</size>\n");
        }

        if (docentry.getSourcePatientId() != null && docentry.getSourcePatientId().getValues().size() > 0) {
            xml.append("\t<sourcepatientid>\n");
            xml.append(docentry.getSourcePatientId().toXML());
            xml.append("\t</sourcepatientid>\n");
        }

        if (docentry.getSourcePatientInfo() != null && docentry.getSourcePatientInfo().getValues().size() > 0) {
            xml.append("\t<sourcepatientinfo>\n");
            xml.append(docentry.getSourcePatientInfo().toXML());
            xml.append("\t</sourcepatientinfo>\n");
        }

        if (docentry.getTypeCode() != null && docentry.getTypeCode().getCode() != null && !docentry.getTypeCode().getCode().getString().equals("")) {
            xml.append("\t<typecode>\n");
            xml.append(docentry.getTypeCode().toXML());
            xml.append("\t</typecode>\n");
        }
        if (docentry.getUniqueId() != null && !docentry.getUniqueId().getValue().getOid().getString().equals("")) {
            xml.append("\t<uniqueid>\n");
            xml.append(docentry.getUniqueId().toXML());
            xml.append("\t</uniqueid>\n");
        }
        if (docentry.getUri() != null && !docentry.getUri().getString().equals("")) {
            xml.append("\t<uri>");
            xml.append(docentry.getUri().toString());
            xml.append("</uri>\n");
        }

        xml.append("</Document>");

        String newXmlFile = xml.toString().replaceAll("&", "&amp;");
        return newXmlFile;
    }

    public XdsDocumentEntry getDocEntry() {
        return docentry;
    }
}
