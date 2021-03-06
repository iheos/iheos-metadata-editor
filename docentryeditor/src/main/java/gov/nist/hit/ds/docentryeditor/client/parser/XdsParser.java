package gov.nist.hit.ds.docentryeditor.client.parser;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import gov.nist.hit.ds.docentryeditor.client.resources.AppResources;
import gov.nist.hit.ds.docentryeditor.shared.model.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * <b>This class parses a XML file to build the model</b>
 * <p/>
 * <p>
 * To do it, here are the following variables required
 * </p>
 * <ul>
 * <li>{@link #INSTANCE}: The parser object, this class is a singleton (Parse) ;</li>
 * <li>{@link #documentXml}: The String which contains the XML data (String) ;</li>
 * <li>{@link #xdsDocumentEntry}: The document model which will be completed.</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * This class also contains getters/setters.<br>
 * In addition, it contains several methods such as buildMyModel, findElements,
 * getDocumentParsed, generalMethod and finally one method per element's
 * document.</br>
 * </p>
 * <p/>
 * <p>
 * <b>How it works ?</b><br>
 * The method {@link #parse(String)} return the completed model, indeed it calls
 * {@link #findElements()} whose aim is to call the appropriate method parseType
 * where Type correspond to a specific type describe in the
 * {@link gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry} (InternationalString, Author ...).</br>All this method
 * throws String256Exception if there is a String256 which is larger than 256
 * characters.<br>
 * The method entitled getDocumentParsed instantiates a Preparse object from the
 * singleton Preparse class to delete unexpected escape and to make it UTF-8
 * compatible.</br>
 * </p>
 * <p/>
 * <p/>
 * <p>
 * <b>See below each method mentioned above.</b> <br>
 * {@link #parse(String)}</br> {@link #findElements()} <br>
 * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
 * </br> {@link #getDocumentParsed()}
 * </p>
 * <p/>
 * <p>
 * <b>See also the parseType methods:</b><br>
 * {@link #parseArrayInternationalString(String)}<br>
 * {@link #methodParseAuthors()}<br>
 * {@link #parseCodedTerm(String)}<br>
 * {@link #parseArrayCodedTerm(String)}<br>
 * {@link #parseIdentifierString256(String)}<br>
 * {@link #parseIdentifierOID(String)}<br>
 * {@link #parseNameValueString256(String)}<br>
 * {@link #parseNameValueInteger(String)}<br>
 * {@link #parseNameValueDTM(String)}<br>
 * {@link #parseString256(String)}
 * </p>
 *
 * @see gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry
 * @see PreParse
 * @see String256
 */
public class XdsParser {
    /**
     * <b>Parse myParse</b> - The instance of Parse class (it's a singleton
     * class).<br>
     * Type: {@link XdsParser}</br>
     *
     * @see XdsParser
     */
    private static final XdsParser INSTANCE = new XdsParser();
    private static final Logger LOGGER = Logger.getLogger(XdsParser.class.getName());
    /**
     * <b>DocumentModel xdsDocumentEntry</b> - The model which will be completed
     * by buildMyModel using the data's XML file.<br>
     * Type: {@link gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry}</br> </p>
     *
     * @see gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry
     * @see XdsParser
     */
    private final XdsDocumentEntry xdsDocumentEntry = new XdsDocumentEntry();
    private static XdsDocumentEntry prefilledXdsDocumentEntry;
    /**
     * <b>String documentXml</b> - The data taken from the XML document and send
     * by the server, this is the String to parser.<br>
     * Type: String</br>
     *
     * @see XdsParser
     */
    private String documentXml;
    /**
     * <b>Document document</b> - The XML file which is contained in a Document
     * so as to be parsed.<br>
     * Type: {@link Document}</br>
     *
     * @see XdsParser
     */
    private Document document;

    @Inject
    private PreParse preParse;

    public static XdsParser getInstance() {
        return INSTANCE;
    }

    /**
     * <b>Method parser</b> <br>
     * Firstly, it calls {@link #getDocumentParsed()} method to parser the String
     * {@link #documentXml} and to complete the {@link gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry}
     * {@link #xdsDocumentEntry} thanks to the {@link #findElements()} method.
     * </br>
     *
     * @param newDocumentXml - The String which contains the XML content
     * @return parsed XDS Document Entry - The {@link gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry}
     * @see XdsParser
     */
    public XdsDocumentEntry parse(String newDocumentXml) {
        LOGGER.info("Parsing document entry...");
        documentXml = preParse.doPreParse(newDocumentXml);
        getDocumentParsed();
        try {
            findElements();
        } catch (String256Exception e) {
            LOGGER.warning(e.getMessage());
        }
        LOGGER.info("... document entry parsed.");
        return xdsDocumentEntry;
    }

    /**
     * <b>Method getDocumentParsed</b> <br>
     * Get the instance of PreParse object to prepare the String parsing. </br>
     *
     * @see PreParse
     * @see XdsParser
     */
    private void getDocumentParsed() {
        documentXml = documentXml.replaceAll("&lt;", "<");
        documentXml = documentXml.replaceAll("&gt;", ">");
        documentXml = documentXml.replaceAll("&amp;", "&");
        documentXml = preParse.doPreParseUTF8(documentXml);

        // parser the XML document into a DOM
        document = XMLParser.parse(documentXml);
    }

    /**
     * <b>Method findElements</b> <br>
     * It calls all parseType methods on each element from {@link gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry}
     * through
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * . </br>
     *
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private void findElements() throws String256Exception {
        // generalMethod("titles"); ...
        for (RootNodesEnum node : RootNodesEnum.values()) {
            generalMethod(node);
        }
    }

    /**
     * <b>Method generalMethod</b> <br>
     * It calls all parseMethod on each element from {@link gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry}.
     *
     * @param node (String): The first node to match.
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private void generalMethod(RootNodesEnum node) throws String256Exception {
        String nodeName = node.toString();
        switch (node) {
            case authors:
                // FIXME Why does this case not have the same code pattern as all others?
                methodParseAuthors();
                break;
            case classcode:
                xdsDocumentEntry.setClassCode(parseCodedTerm(nodeName));
                break;
            case comments:
                xdsDocumentEntry.setComments(parseArrayInternationalString(nodeName));
                break;
            case confidentialitycode:
                xdsDocumentEntry.setConfidentialityCodes(parseArrayCodedTerm(nodeName));
                break;
            case creationtime:
                xdsDocumentEntry.setCreationTime(parseNameValueDTM(nodeName));
                break;
            case eventcode:
                xdsDocumentEntry.setEventCode(parseArrayCodedTerm(nodeName));
                break;
            case formatcode:
                xdsDocumentEntry.setFormatCode(parseCodedTerm(nodeName));
                break;
            case hash:
                xdsDocumentEntry.setHash(parseString256(nodeName));
                break;
            case healthcarefacilitytype:
                xdsDocumentEntry.setHealthcareFacilityType(parseCodedTerm(nodeName));
                break;
            case id:
                xdsDocumentEntry.setId(parseString256(nodeName));
                break;
            case mimetype:
                xdsDocumentEntry.setMimeType(parseString256(nodeName));
                break;
            case languagecode:
                xdsDocumentEntry.setLanguageCode(LanguageCode.getValueOf(parseString256(nodeName).getString()));
                break;
            case legalauthenticator:
                xdsDocumentEntry.setLegalAuthenticator(parseNameValueString256(nodeName));
                break;
            case patientid:
                xdsDocumentEntry.setPatientID(parseIdentifierString256(nodeName));
                break;
            case practicesettingcode:
                xdsDocumentEntry.setPracticeSettingCode(parseCodedTerm(nodeName));
                break;
            case repositoryuniqueid:
                xdsDocumentEntry.setRepoUId(parseOID(nodeName));
                break;
            case servicestarttime:
                xdsDocumentEntry.setServiceStartTime(parseNameValueDTM(nodeName));
                break;
            case servicestoptime:
                xdsDocumentEntry.setServiceStopTime(parseNameValueDTM(nodeName));
                break;
            case size:
                xdsDocumentEntry.setSize(new String256(parseNameValueInteger(nodeName).getValues().get(0).toString()));
                break;
            case sourcepatientid:
                xdsDocumentEntry.setSourcePatientId(parseNameValueString256(nodeName));
                break;
            case sourcepatientinfo:
                xdsDocumentEntry.setSourcePatientInfo(parseNameValueString256(nodeName));
                break;
            case titles:
                xdsDocumentEntry.setTitles(parseArrayInternationalString(nodeName));
                break;
            case typecode:
                xdsDocumentEntry.setTypeCode(parseCodedTerm(nodeName));
                break;
            case uniqueid:
                xdsDocumentEntry.setUniqueId(parseIdentifierOID(nodeName));
                break;
            case uri:
                xdsDocumentEntry.setUri(parseString256(nodeName));
                break;
            default:
                break;
        }

    }

    /**
     * <b>Method methodParseAuthors</b> <br>
     * To obtain the author(s) of the XML file (ArrayList of {@link Author}
     * ).</br>Called by
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * .
     *
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    // TODO This methods should handle exceptions and/or fire events which could be handled in GUI.
    private void methodParseAuthors() throws String256Exception {
        NodeList authorsNode = document.getElementsByTagName(RootNodesEnum.authors.toString());
        List<Author> authors = new ArrayList<Author>();

        if (authorsNode != null && !authorsNode.toString().isEmpty()) {
            for (int a = 0; a < authorsNode.getLength(); a++) {
                NodeList authorNodes = ((Element) authorsNode.item(a)).getElementsByTagName("author");
                for (int i = 0; i < authorNodes.getLength(); i++) {
                    Author internTemp = new Author();

                    List<String256> authorInstitutionsTemp = new ArrayList<String256>();
                    List<String256> authorRolesTemp = new ArrayList<String256>();
                    List<String256> authorSpecialitiesTemp = new ArrayList<String256>();
                    List<String256> authorTelecommunicationsTemp = new ArrayList<String256>();

                    // Set authorPerson
                    String256 authorPerson256 = new String256();
                    NodeList authorPersonNodes = ((Element) authorNodes.item(i))
                            .getElementsByTagName(RootNodesEnum.SubNodesEnum.authorperson.toString());
                    if (authorPersonNodes != null) {
                        for (int p = 0; p < authorPersonNodes.getLength(); p++) {
                            authorPerson256.setString(authorPersonNodes.item(p).getFirstChild().getNodeValue());
                            internTemp.setAuthorPerson(authorPerson256);
                        }
                    } else {
                        LOGGER.warning("This author lacks an author person node. \nCheck your XML Document!");
                    }
                    // Set authorInstitutions
                    NodeList institutionNodes = ((Element) authorNodes.item(i))
                            .getElementsByTagName(RootNodesEnum.SubNodesEnum.authorinstitution.toString());
                    if (institutionNodes != null) {
                        for (int j = 0; j < institutionNodes.getLength(); j++) {
                            String256 str = new String256();
                            str.setString(institutionNodes.item(j).getFirstChild().getNodeValue());
                            if (str.verify()) {
                                authorInstitutionsTemp.add(str);
                            }
                        }
                        internTemp.setAuthorInstitutions(authorInstitutionsTemp);
                    } else {
                        LOGGER.warning("AuthorInstitutions node is empty for author named " + authorPerson256
                                + ".\nCheck your XML Document!");
                    }
                    // Set authorRoles
                    NodeList roleNodes = ((Element) authorNodes.item(i))
                            .getElementsByTagName(RootNodesEnum.SubNodesEnum.authorrole.toString());
                    if (roleNodes != null) {
                        for (int j = 0; j < roleNodes.getLength(); j++) {
                            String256 str = new String256();
                            str.setString(roleNodes.item(j).getFirstChild().getNodeValue());
                            if (str.verify()) {
                                authorRolesTemp.add(str);
                            }
                        }
                        internTemp.setAuthorRoles(authorRolesTemp);
                    } else {
                        LOGGER.warning("AuthorRoles node is empty for author named " + authorPerson256
                                + ".\nCheck your XML Document!");
                        // TODO Fire an eventbus for this error that could be handled in the view
                    }
                    // Set authorSpecialities
                    NodeList specialtyNodes = ((Element) authorNodes.item(i))
                            .getElementsByTagName(RootNodesEnum.SubNodesEnum.authorspecialty.toString());
                    if (specialtyNodes != null) {
                        for (int j = 0; j < specialtyNodes.getLength(); j++) {
                            String256 str = new String256();
                            str.setString(specialtyNodes.item(j).getFirstChild().getNodeValue());
                            if (str.verify()) {
                                authorSpecialitiesTemp.add(str);
                            }
                        }
                        internTemp.setAuthorSpecialties(authorSpecialitiesTemp);
                    } else {
                        LOGGER.warning("AuthorSpecialities node is empty for author named " + authorPerson256
                                + "!\nCheck your XML Document");
                        // TODO fire an eventbus to handle the issue in the view
                    }
                    // Set authorTelecommunications
                    NodeList telecommunicationNodes = ((Element) authorNodes.item(i))
                            .getElementsByTagName(RootNodesEnum.SubNodesEnum.authortelecommunication.toString());
                    if (telecommunicationNodes != null) {
                        for (int j = 0; j < telecommunicationNodes.getLength(); j++) {
                            String256 str = new String256();
                            str.setString(telecommunicationNodes.item(j).getFirstChild().getNodeValue());
                            if (str.verify()) {
                                authorTelecommunicationsTemp.add(str);
                            }
                        }
                        internTemp.setAuthorTelecommunications(authorTelecommunicationsTemp);
                    } else {
                        LOGGER.warning("AuthorTelecommunications node is empty for author named " + authorPerson256
                                + ".\nCheck your XML Document!");
                        // TODO fire an eventbus to handle the issue in the view
                    }
                    // Add this Element to the model
                    authors.add(internTemp);
                }
                // Set title to xdsDocumentEntry
                xdsDocumentEntry.setAuthors(authors);
            }
        } else {
            xdsDocumentEntry.setAuthors(null);
            LOGGER.warning("Authors node is empty.\nCheck your XML Document!");
            // TODO Fire an eventbus for this error that could be handled in the view
        }
    }

    /**
     * <b>Method parseString256</b> <br>
     * To obtain the element of type {@link String256}.</br>Called by
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * on each {@link String256} element.
     *
     * @param node (String): The name of the root node.
     * @return {@link String256}
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private String256 parseString256(String node) throws String256Exception {
        NodeList nodeList = document.getElementsByTagName(node);
        if (!nodeList.toString().isEmpty()) {
            String256 string256 = new String256();
            string256.setString(nodeList.item(0).getFirstChild().getNodeValue());
            if (string256.verify()) {
                return string256;
            } else {
                // FIXME Not sure it should be done here but maybe within the verify method which may return something
                LOGGER.warning(node + " node is larger than 256 characters.");
                return null;
            }
        } else {
            LOGGER.warning(node + " node is empty.\nCheck your XML Document!");
            return null;
        }
    }

    /**
     * <b>Method parseOID</b> <br>
     * To obtain the element of type {@link OID}.</br>Called by
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * on each OID element.
     *
     * @param node (String): The name of the root node.
     * @return {@link OID}
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private OID parseOID(String node) throws String256Exception {
        NodeList nodeList = document.getElementsByTagName(node);
        if (!nodeList.toString().isEmpty()) {
            OID oid = new OID();
            oid.setOid(new String256().setString(nodeList.item(0).getFirstChild().getNodeValue()));
            if (oid.verify()) {
                return oid;
            } else {
                // FIXME Not sure it should be done that way (abstraction issue, string256 validation check)
                LOGGER.warning(node + " node is larger than 256 characters.");
                return null;
            }
        } else {
            // TODO Fire an eventbus for this error that could be handled in the view
            LOGGER.warning(node + " node is empty.\nCheck your XML Document!");
            return null;
        }
    }

    /**
     * <p>
     * <b>Method parseIdentifierString256</b> <br>
     * To obtain the element of type {@link IdentifierString256}.</br>Called by
     * {@link #generalMethod generalMethod} on each {@link IdentifierString256}
     * element.
     * </p>
     *
     * @param node (String) : The name of the root node.
     * @return {@link IdentifierString256}
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser class Parse
     */
    private IdentifierString256 parseIdentifierString256(String node) throws String256Exception {
        NodeList nodeList = document.getElementsByTagName(node);

        if (!nodeList.toString().isEmpty()) {
            IdentifierString256 identifier = new IdentifierString256();

            // Set value
            String256 identifier256Value = new String256();
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList valueNodes = ((Element) nodeList.item(i)).getElementsByTagName(RootNodesEnum.SubNodesEnum.value
                        .toString());
                for (int j = 0; j < valueNodes.getLength(); j++) {
                    identifier256Value.setString(valueNodes.item(j).getFirstChild().getNodeValue());
                }
            }
            if (identifier256Value.verify()) {
                identifier.setValue(identifier256Value);
            } else {
                // FIXME should do something to raise an exception or fire eventbus
                return null;
            }

            // Set type
            String256 identifier256Type = new String256();
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList valueNodes = ((Element) nodeList.item(i)).getElementsByTagName(RootNodesEnum.SubNodesEnum.idtype
                        .toString());
                for (int j = 0; j < valueNodes.getLength(); j++) {
                    identifier256Type.setString(valueNodes.item(j).getFirstChild().getNodeValue());
                }
            }
            if (identifier256Type.verify()) {
                identifier.setIdType(identifier256Type);
            } else {
                // FIXME should do something to raise an exception or fire eventbus
                return null;
            }

            // Set IDPatient element to xdsDocumentEntry
            return identifier;
        } else {
            LOGGER.warning(node + " node is empty.\nCheck your XML Document!");
            return null;
        }
    }

    /**
     * <b>Method parseIdentifierOID</b> <br>
     * To obtain the element of type {@link IdentifierOID}.</br>Called by
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * on each {@link IdentifierOID} element. </p>
     *
     * @param node (String) : The name of the root node.
     * @return {@link IdentifierOID}
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private IdentifierOID parseIdentifierOID(String node) throws String256Exception {
        NodeList nodeList = document.getElementsByTagName(node);

        if (!nodeList.toString().isEmpty()) {
            IdentifierOID identifier = new IdentifierOID();

            // Set value
            OID identifier256Value = new OID();
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList valueNodes = ((Element) nodeList.item(i)).getElementsByTagName(RootNodesEnum.SubNodesEnum.value
                        .toString());
                for (int j = 0; j < valueNodes.getLength(); j++) {
                    identifier256Value.setOid(new String256().setString(valueNodes.item(j).getFirstChild().getNodeValue()));
                }
            }
            if (identifier256Value.verify()) {
                identifier.setValue(identifier256Value);
            } else {
                // FIXME should do something to raise an exception or fire eventbus
                return null;
            }

            // Set type
            String256 identifier256Type = new String256();
            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList valueNodes = ((Element) nodeList.item(i)).getElementsByTagName(RootNodesEnum.SubNodesEnum.idtype
                        .toString());
                for (int j = 0; j < valueNodes.getLength(); j++) {
                    identifier256Type.setString(valueNodes.item(j).getFirstChild().getNodeValue());
                }
            }
            if (identifier256Type.verify()) {
                identifier.setIdType(identifier256Type);
            } else {
                // FIXME should do something to raise an exception or fire eventbus
                return null;
            }

            // Set IDPatient element to xdsDocumentEntry
            return identifier;
        } else {
            LOGGER.warning(node + " node is empty.\nCheck your XML Document!");
            return null;
        }
    }

    /**
     * <b>Method parseArrayInternationalString</b> <br>
     * To obtain the element of type ArrayList(InternationalString) .</br>Called
     * by
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * on each ArrayList(InternationalString) element.
     *
     * @param node (String) : The name of the root node.
     * @return ArrayList(InternationalString)
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private List<InternationalString> parseArrayInternationalString(String node) throws String256Exception {
        NodeList nodeList = document.getElementsByTagName(node);
        List<InternationalString> array = new ArrayList<InternationalString>();

        if (!nodeList.toString().isEmpty()) {
            int nodeLength = nodeList.item(0).getChildNodes().getLength();
            for (int i = 0; i < nodeLength; i++) {
                InternationalString internationalString = new InternationalString();

                // Set languageCode
                LanguageCode international256LangCode = LanguageCode.getValueOf(((Element) nodeList.item(0).getChildNodes()
                        .item(i)).getElementsByTagName(RootNodesEnum.SubNodesEnum.language.toString()).item(0)
                        .getFirstChild().getNodeValue());
                internationalString.setLangCode(international256LangCode);

                // Set information
                String256 international256Value = new String256();
                international256Value.setString(((Element) nodeList.item(0).getChildNodes().item(i))
                        .getElementsByTagName(RootNodesEnum.SubNodesEnum.information.toString()).item(0).getFirstChild()
                        .getNodeValue());
                if (international256Value.verify()) {
                    internationalString.setValue(international256Value);
                }
                // TODO else w/ exception and/or fire eventbus

                // Add this Element to the model
                array.add(internationalString);
            }
            // Set title to xdsDocumentEntry
            return array;
        } else {
            LOGGER.warning(node + " node is empty!\nCheck your XML Document");
            return null;
        }
    }

    /**
     * <b>Method parseCodedTerm</b> <br>
     * To obtain the element of type CodedTerm.</br>Called by
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * on each {@link CodedTerm} element. </p>
     *
     * @param node (String) : The name of the root node.
     * @return {@link CodedTerm}
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private CodedTerm parseCodedTerm(String node) throws String256Exception {
        return parseArrayCodedTerm(node).get(0);
    }

    /**
     * <p>
     * <b>Method parseArrayCodedTerm</b> <br>
     * To obtain the element of type ArrayList(CodedTerm).</br>Called by
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * on each ArrayList(CodedTerm) element.
     * </p>
     *
     * @param node (String) : The name of the root node.
     * @return ArrayList(CodedTerm)
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private List<CodedTerm> parseArrayCodedTerm(String node) throws String256Exception {
        NodeList nodeList = document.getElementsByTagName(node);
        if (!nodeList.toString().isEmpty()) {
            List<CodedTerm> arrayCodedTerm = new ArrayList<CodedTerm>();
            NodeList codedTermNodes = ((Element) nodeList.item(0)).getElementsByTagName(RootNodesEnum.SubNodesEnum.codedterm
                    .toString());
            for (int i = 0; i < codedTermNodes.getLength(); i++) {
                CodedTerm codedTerm = new CodedTerm();
                CodingScheme codingScheme = new CodingScheme();
                String256 codedTerm256Name = new String256();
                String256 codedTerm256Code = new String256();

                // Set displayName
                codedTerm256Name.setString(((Element) codedTermNodes.item(i))
                        .getElementsByTagName(RootNodesEnum.SubNodesEnum.displayname.toString()).item(0).getFirstChild()
                        .getNodeValue());

                if (codedTerm256Name.verify()) {
                    codedTerm.setDisplayName(codedTerm256Name);
                } else {
                    // TODO return problem to client view
                }
                // Set code
                codedTerm256Code.setString(((Element) codedTermNodes.item(i))
                        .getElementsByTagName(RootNodesEnum.SubNodesEnum.code.toString()).item(0).getFirstChild()
                        .getNodeValue());
                if (codedTerm256Code.verify()) {
                    codedTerm.setCode(codedTerm256Code);
                } else {
                    // TODO return problem to client view
                }
                // Set codingScheme
                String test = ((Element) codedTermNodes.item(i))
                        .getElementsByTagName(RootNodesEnum.SubNodesEnum.codingscheme.toString()).item(0).getFirstChild()
                        .getNodeValue();
                codingScheme.setCodingScheme(new String256().setString(test));
                if (codingScheme.verify()) {
                    codedTerm.setCodingScheme(codingScheme);
                } else {
                    // TODO fire there is a problem
                }
                // Add this CodedTerm
                if (codedTerm.verify()) {
                    arrayCodedTerm.add(codedTerm);
                } else {
                    // TODO return problem to client view
                }
            }
            return arrayCodedTerm;
        } else {
            LOGGER.warning(node + " node is empty!\nCheck your XML Document");
            return null;
        }
    }

    /**
     * <b>Method parseNameValueString256</b> <br>
     * To obtain the element of type {@link NameValueString256}.</br>Called by
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * on each {@link NameValueString256} element.
     *
     * @param node (String) : The name of the root node.
     * @return {@link NameValueString256}
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private NameValueString256 parseNameValueString256(String node) throws String256Exception {
        NodeList nodeList = document.getElementsByTagName(node);

        if (nodeList.toString().isEmpty()) {
            LOGGER.warning(node + " node is empty!\nCheck your XML Document");
            return null;
        } else {
            // NameValue<String256>
            NameValueString256 nameValue = new NameValueString256();
            List<String256> values = new ArrayList<String256>();
            // Set name
            String256 name256 = new String256();
            name256.setString(((Element) nodeList.item(0)).getElementsByTagName(RootNodesEnum.SubNodesEnum.name.toString())
                    .item(0).getFirstChild().getNodeValue());
            if (name256.verify()) {
                nameValue.setName(name256);
            }
            // Set values
            NodeList valueNodes = ((Element) nodeList.item(0)).getElementsByTagName(RootNodesEnum.SubNodesEnum.value
                    .toString());
            for (int i = 0; i < valueNodes.getLength(); i++) {
                String256 value256 = new String256();
                value256.setString(valueNodes.item(i).getFirstChild().getNodeValue());
                values.add(value256);
            }
            nameValue.setValues(values);
            return nameValue;
        }
    }

    /**
     * <b>Method parseNameValueInteger</b> <br>
     * To obtain the element of type {@link NameValueInteger}.</br>Called by
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * on each {@link NameValueInteger} element.
     *
     * @param node (String) : The name of the root node.
     * @return {@link NameValueInteger}
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private NameValueInteger parseNameValueInteger(String node) throws String256Exception {
        NodeList nodeList = document.getElementsByTagName(node);
        if (nodeList.toString().isEmpty()) {
            LOGGER.warning(node + " node is empty.\nCheck your XML Document!");
            return null;
        } else {
            // NameValue<String256>
            NameValueInteger nameValue = new NameValueInteger();
            List<Integer> values = new ArrayList<Integer>();
            // Set name
            String256 name256 = new String256();
            name256.setString(((Element) nodeList.item(0)).getElementsByTagName(RootNodesEnum.SubNodesEnum.name.toString())
                    .item(0).getFirstChild().getNodeValue());
            if (name256.verify()) {
                nameValue.setName(name256);
            }
            // Set values
            NodeList valueNodes = ((Element) nodeList.item(0)).getElementsByTagName(RootNodesEnum.SubNodesEnum.value
                    .toString());
            for (int i = 0; i < valueNodes.getLength(); i++) {
                String value = valueNodes.item(i).getFirstChild().getNodeValue();
                values.add(Integer.parseInt(value));
            }
            nameValue.setValues(values);
            return nameValue;
        }
    }

    /**
     * <b>Method parseNameValueDTM</b> <br>
     * To obtain the element of type {@link NameValueDTM}.</br>Called by
     * {@link #generalMethod(gov.nist.hit.ds.docentryeditor.client.parser.RootNodesEnum)}
     * on each {@link NameValueDTM} element.
     *
     * @param node (String) : The name of the root node.
     * @return {@link NameValueDTM}
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see XdsParser
     */
    private NameValueDTM parseNameValueDTM(String node) throws String256Exception {
        NodeList nodeList = document.getElementsByTagName(node);
        if (nodeList.toString().isEmpty()) {
            LOGGER.warning(node + " node is empty.\nCheck your XML Document!");
            return null;
        } else {
            NameValueDTM nameValue = new NameValueDTM();
            List<DTM> values = new ArrayList<DTM>();
            // Set name
            String256 name256 = new String256();
            name256.setString(((Element) nodeList.item(0)).getElementsByTagName(RootNodesEnum.SubNodesEnum.name.toString())
                    .item(0).getFirstChild().getNodeValue());
            if (name256.verify()) {
                nameValue.setName(name256);
            } else {
                // TODO fire problem to view
            }
            // Set values
            NodeList valueNodes = ((Element) nodeList.item(0)).getElementsByTagName(RootNodesEnum.SubNodesEnum.value
                    .toString());
            // FIXME this should be done elsewhere
            for (int i = 0; i < valueNodes.getLength(); i++) {
                String256 value256 = new String256();
                DTM dtm = new DTM();
                value256.setString(valueNodes.item(i).getFirstChild().getNodeValue());
                String df = null;
                int dateLength = value256.getString().length();
                switch (dateLength) {
                    case 4:
                        df = "yyyy";
                        break;
                    case 6:
                        df = "yyyyMM";
                        break;
                    case 8:
                        df = "yyyyMMdd";
                        break;
                    case 10:
                        df = "yyyyMMddhh";
                        break;
                    case 12:
                        df = "yyyyMMddhhmm";
                        break;
                    case 14:
                        df = "yyyyMMddhhmmss";
                        break;
                    default:
                        MessageBox mb = new MessageBox("Date parsing error", "Your " + nameValue.getName()
                                + " date is not well formatted, this value can not be treated.");
                        mb.show();
                        break;
                }
                if (df != null) {
                    dtm.setDtm(DateTimeFormat.getFormat(df).parse(value256.getString()));
                } else {
                    return null;
                }
                values.add(dtm);
            }
            nameValue.setValues(values);
            return nameValue;
        }
    }

    public XdsDocumentEntry getPrefilledDocumentEntry() {
        if(prefilledXdsDocumentEntry==null) {
            prefilledXdsDocumentEntry= parse(PreParse.getInstance().doPreParse(AppResources.INSTANCE.xdsPrefill().getText()));
        }
        return prefilledXdsDocumentEntry;
    }
}
