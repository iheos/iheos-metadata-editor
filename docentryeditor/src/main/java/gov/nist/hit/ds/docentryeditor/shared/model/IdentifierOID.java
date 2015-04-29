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
 *
 * @see ModelElement class ModelElement <
 */
public class IdentifierOID implements ModelElement, Serializable {

    private static final long serialVersionUID = 6909154820202656532L;

    /**
     * The value of the identifier [Mandatory].<br>
     */
    @NotNull
    private OID value;

    /**
     * The type of the identifier [Mandatory].<br>
     */
    @NotNull
    private String256 idType;

    /**
     * Parametrized constructor.
     * @param string256 value of identifier.
     * @param idOID id type of the identifier.
     */
    public IdentifierOID(OID string256, String256 idOID) {
        value = string256;
        idType = idOID;
    }

    /**
     * Default constructor.
     */
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
     * Generates an XML representation of the object.
     * </p>
     *
     * @return String which contains the Identifier in XML format
     */
    public String toXML() {
        return "\t\t<identifier>\n\t\t\t<value>" + value.toString() + "</value>\n\t\t\t<idtype>" + idType.toString()
                + "</idtype>\n\t\t</identifier>\n";
    }

    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check whether the syntax is correct </br>
     * </p>
     *
     * @return boolean true if the syntax is correct, else return false
     * @throws String256Exception if there is a String256 with more than 256 characters
     */
    @Override
    public boolean verify() throws String256Exception {
        // TODO
        return true;
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
        if (this == o){
            return true;
        }
        if (!(o instanceof IdentifierOID)){
            return false;
        }
        IdentifierOID that = (IdentifierOID) o;
        if (idType != null ? !idType.equals(that.idType) : that.idType != null){
            return false;
        }
        if (value != null ? !value.equals(that.value) : that.value != null){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = HASHING_KEY * result + (idType != null ? idType.hashCode() : 0);
        return result;
    }

    public IdentifierOID copy() {
        return new IdentifierOID(this.value.copy(),this.idType.copy());
    }
}
