package gov.nist.hit.ds.docentryeditor.shared.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>
 * <b>This class represents a String256</b> <br>
 * A string256 is a String which contains less than 256 characters.</br>
 * </p>
 * <p>
 * An string256 has the following parameter:
 * <ul>
 * <li>{@link #string}: A String256;</li>
 * </ul>
 * </p>
 * <p>
 * It contains toString method to return the String256.<br>
 * This class also contains getters/setters.</br> In addition, it has verify
 * method to check its syntax.
 * </p>
 * <p/>
 * <p>
 * <b>See below each method mentioned above.</b> <br>
 * {@link #verify() method verify}</br> {@link #toString() method toString} <br>
 * </p>
 *
 * @see String256Exception class String256Exception
 * @see XdsDocumentEntry class DocumentModel
 * @see ModelElement class ModelElement
 */
public class String256 implements Serializable {

    private static final long serialVersionUID = 4537891086390463367L;

    // @Max(value = 2)
    /**
     * <b>String256 string<b/> - The string [Mandatory].<br>
     * Type: String256</br>
     *
     * @see String256 class String256
     */
    @NotNull
    @Size(max = 256)
    private String string = new String();

    public String256() {
    }

    public String256(String string) {
        if (string.length() < 257) {
            this.string = string;
        }
    }

    @Deprecated
    public boolean verify(String str) throws String256Exception {
        if (str.length() > 256) {
            throw new String256Exception();
        } else {
            return true;
        }
    }

    /**
     * <p>
     * <b>Method verify</b></br> This method verify if the String contains less
     * than 256 characters.
     * </p>
     *
     * @return boolean: true if it's a String256 else false
     * @throws String256Exception if the String is larger than 256 characters
     * @see String256Exception class String256Exception
     * @see String256 class String256
     */
    public boolean verify() throws String256Exception {
        if (string.length() > 256) {
            throw new String256Exception();
        } else {
            return true;
        }
    }

    public String getString() {
        return string;
    }

    public String256 setString(String string) {
        this.string = string;
        return this;
    }

    /**
     * <p>
     * <b>Method toString</b> <br>
     * This method will be called to build a XML file by the DocumentModel with
     * the information taken from the local String256.<br>
     * </p>
     *
     * @return String which contains the String256
     * @see String256 class String256
     */
    @Override
    public String toString() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof String256)) return false;

        String256 string256 = (String256) o;

        if (!string.equals(string256.string)) return false;

        return true;
    }

    public String256 copy() {
       return new String256(new String(this.string));
    }
}
