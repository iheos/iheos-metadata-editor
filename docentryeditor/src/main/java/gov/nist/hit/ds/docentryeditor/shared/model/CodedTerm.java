package gov.nist.hit.ds.docentryeditor.shared.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * <b>This class represents a CodedTerm.</b>
 * </p>
 * <p>
 * A CodedTerm has the following parameters:
 * <ul>
 * <li>{@link #displayName}: The name of the CodedTerm ( {@link String256}) ;</li>
 * <li>{@link #code}: A suitable code ({@link String256}) ;</li>
 * <li>{@link #codingScheme}: A codingScheme which have to be respected (
 * {@link CodingScheme}) ;</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * It contains a toXML method to return the author in XML format and verify
 * method to check if syntax's CodedTerm is correct.
 * </p>
 * <p/>
 * <p>
 * This class also contains getters/setters.
 * </p>
 * <p/>
 * <p>
 * <b>See below the method mentioned above.</b> <br>
 * {@link #verify() method verify}</br> {@link #toXML() method toXML} <br>
 * </p>
 *
 * @see XdsDocumentEntry class DocumentModel
 * @see ModelElement class ModelElement
 */
public class CodedTerm implements ModelElement, Serializable {

    private static final long serialVersionUID = 3881628246755015516L;

    /**
     * <b>String256 displayName</b> - The name of the CodedTerm [Mandatory].<br>
     * Type: {@link String256}</br> </p>
     *
     * @see String256
     * @see CodedTerm
     */
    @NotNull
    private String256 displayName;

    /**
     * <b>String256 code</b> - The code of the CodedTerm [Mandatory].<br>
     * Type: {@link String256}</br> </p>
     *
     * @see String256
     * @see CodedTerm
     */
    @NotNull
    private String256 code;

    /**
     * <b>CodingScheme codingScheme</b> - The name of the CodedTerm [Mandatory].<br>
     * Type: {@link CodingScheme}</br> </p>
     *
     * @see CodingScheme
     * @see CodedTerm
     */
    @NotNull
    private CodingScheme codingScheme;

    public CodedTerm() {
        displayName = new String256();
        code = new String256();
        codingScheme = new CodingScheme();
    }

    public CodedTerm(String code, String displayName, String codingScheme) {
        this.displayName = new String256().setString(displayName);
        this.code = new String256().setString(code);
        this.codingScheme = new CodingScheme().setCodingScheme(new String256().setString(codingScheme));
    }

    public String256 getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String256 displayName) {
        this.displayName = displayName;
    }

    public String256 getCode() {
        return code;
    }

    public void setCode(String256 code) {
        this.code = code;
    }

    public CodingScheme getCodingScheme() {
        return codingScheme;
    }

    public void setCodingScheme(CodingScheme codingScheme) {
        this.codingScheme = codingScheme;
    }

    /**
     * <p>
     * <b>Method toXML</b> <br>
     * This method will be called to build a XML file by the
     * {@link XdsDocumentEntry} with the information taken from the local
     * CodedTerm. </br>
     * </p>
     *
     * @return String which contains the CodedTerm in XML format
     * @see CodedTerm class CodedTerm
     */
    public String toXML() {
        String answer;
        answer = "\t\t<codedterm>\n\t\t\t<displayname>" + displayName + "</displayname>\n\t\t\t<code>" + code + "</code>\n\t\t\t<codingscheme>"
                + codingScheme.toString() + "</codingscheme>\n\t\t</codedterm>\n";
        return answer;
    }

    @Override
    public String toString() {
        return "CodedTerm{" +
                "displayName=" + displayName +
                ", code=" + code +
                ", codingScheme=" + codingScheme +
                '}';
    }

    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check whether the syntax's CodedTerm is
     * correct </br>
     * </p>
     *
     * @return boolean true if the syntax is correct, else return false
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see CodedTerm class CodedTerm
     */
    @Override
    public boolean verify() throws String256Exception {
        // FIXME I don't understand the purpose of this code
        boolean answer = true;
        answer = displayName.verify();
        answer = code.verify();
        answer = codingScheme.verify();
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodedTerm codedTerm = (CodedTerm) o;

        if (!code.equals(codedTerm.code)) return false;
        if (!codingScheme.equals(codedTerm.codingScheme)) return false;
        if (!displayName.equals(codedTerm.displayName)) return false;

        return true;
    }

    public CodedTerm copy() {
        return new CodedTerm(this.code.toString(),this.displayName.toString(),this.codingScheme.toString());
    }

// Ajouter: abstract public AssertionGroup validateCode(String256 code);

}
