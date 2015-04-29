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
 * <li>{@link #idType}: The id type ({@link String256});</li>
 * </li>
 * </ul>
 * </p>
 * @see ModelElement class ModelElement
 */
public class IdentifierString256 implements ModelElement, Serializable {

    private static final long serialVersionUID = -1717453248217705389L;

    /**
     * <p>
     * <p/>
     * The value of the identifier [Mandatory].<br>
     * Type: {@link String256}</br>
     * </p>
     *
     * @see String256 class String256
     */
    @NotNull
    private String256 value;

    /**
     * The type of the identifier [Mandatory].<br>
     * Type: {@link String256}</br>
     */
    @NotNull
    private String256 idType;

    /**
     * Parametrized constructor.
     * @param valueId Value of the identifier object
     * @param idTypeId type of identifier object
     */
    public IdentifierString256(String256 valueId, String256 idTypeId) {
        value = valueId;
        idType = idTypeId;
    }

    /**
     * Default constructor.
     */
    public IdentifierString256() {
        value = new String256();
        idType = new String256();
    }

    public String256 getValue() {
        return value;
    }

    public void setValue(String256 value) {
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
     * <b>Method toXML</b> <br/>
     * It generates the xml for an identifier of type String256 (custom XML format).
     *
     * @return String which contains the Identifier in XML format
     */
    public String toXML() {
      return "\t\t<identifier>\n\t\t\t<value>" + value.getString().replace("&","&amp;") + "</value>\n\t\t\t<idtype>" + idType.toString()
                + "</idtype>\n\t\t</identifier>\n";
    }

    /**
     * <p>
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
        return "IdentifierString256{" +
                "value=" + value +
                ", idType=" + idType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof IdentifierString256)){
            return false;
        }
        IdentifierString256 that = (IdentifierString256) o;
        if (idType != null ? !idType.equals(that.idType) : that.idType != null) {
            return false;
        }
        if (value != null ? !value.equals(that.value) : that.value != null) {
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

    public IdentifierString256 copy() {
        return new IdentifierString256(this.value.copy(),this.getIdType().copy());
    }
}
