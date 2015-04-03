package gov.nist.hit.ds.docentryeditor.shared.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * <b> This class represents an Identifier.</b>
 * </p>
 * <p>
 * An Identifier has the following parameters:
 * <ul>
 * <li>{@link #value}: The value of the identifier ({@link String256}) ;</li>
 * <li>{@link #idType}: The id type ({@link OID});</li>
 * </li>
 * </ul>
 * </p>
 * <p>
 * </p>
 * <p>
 * It contains a toXML method to return the Identifier in XML format.<br>
 * This class also contains getters/setters.</br> In addition, it has verify
 * method to check its syntax.
 * </p>
 * <p/>
 * <p>
 * <b>See below each method mentioned above.</b> <br>
 * {@link #verify() method verify}</br> {@link #toXML() method toXML} <br>
 * </p>
 *
 * @see XdsDocumentEntry class DocumentModel
 * @see ModelElement class ModelElement <
 */
public class IdentifierOID implements ModelElement, Serializable {

    private static final long serialVersionUID = 6909154820202656532L;

    /**
     * <b>String256 value</b> - The value of the identifier [Mandatory].<br>
     * Type: {@link String256}</br>
     *
     * @see OID
     * @see IdentifierOID
     */
    @NotNull
    private OID value;

    /**
     * <b>OID idType</b> - The type of the identifier [Mandatory].<br>
     * Type: {@link OID}</br>
     *
     * @see IdentifierOID
     */
    @NotNull
    private String256 idType;

    public IdentifierOID(OID string256, String256 idOID) {
        value = string256;
        idType = idOID;
    }

    public IdentifierOID() {
        value = new OID();
        idType = new String256();
    }

    public OID getValue() {
        return value;
    }

    public void setValue(OID value) {
        this.value = value;
    }

    public String256 getIdType() {
        return idType;
    }

    public void setIdType(String256 element) {
        idType = element;
    }

    /**
     * <p>
     * <b>Method toXML</b> <br>
     * This method will be called to build a XML file by the
     * {@link XdsDocumentEntry} with the information taken from the local
     * Identifier.<br/>
     * </p>
     *
     * @return String which contains the Identifier in XML format
     * @see IdentifierOID
     */
    public String toXML() {
        String answer = null;
        answer = "\t\t<identifier>\n\t\t\t<value>" + value.toString() + "</value>\n\t\t\t<idtype>" + idType.toString()
                + "</idtype>\n\t\t</identifier>\n";

        return answer;
    }

    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check whether the syntax's
     * {@link IdentifierOID} is correct </br>
     * </p>
     *
     * @return boolean true if the syntax is correct, else return false
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see IdentifierOID
     */
    @Override
    public boolean verify() throws String256Exception {
        // FIXME I don't understand the purpose of this code
        boolean answer = true;
        answer = value.verify();
        answer = idType.verify();
        return answer;
    }

    @Override
    public String toString() {
        return "IdentifierOID{" +
                "value=" + value +
                ", idType=" + idType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentifierOID)) return false;

        IdentifierOID that = (IdentifierOID) o;

        if (idType != null ? !idType.equals(that.idType) : that.idType != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    public IdentifierOID copy() {
        return new IdentifierOID(this.value.copy(),this.idType.copy());
    }
}
