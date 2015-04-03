package gov.nist.hit.ds.docentryeditor.shared.model;

import javax.annotation.MatchesPattern;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * <b>This class represents a CodingScheme.</b>
 * </p>
 * <p>
 * A CodedTerm has the following parameter:
 * <ul>
 * <li>{@link #codingScheme codingScheme}: The CodingScheme itself (
 * {@link String256}) ;</li>
 * </li>
 * </ul>
 * </p>
 * <p>
 * It contains a toString method to return the author in String format and
 * verify method to check if syntax's CodingScheme is correct.
 * </p>
 *
 * <p>
 * This class also contains getters/setters.
 * </p>
 *
 * <p>
 * <b>See below the method mentioned above.</b> <br>
 * {@link #verify() method verify}</br> {@link #toString() method toString} <br>
 * </p>
 *
 *
 * @see CodedTerm class CodedTerm
 * @see XdsDocumentEntry class DocumentModel
 * @see ModelElement class ModelElement </p>
 *
 *
 *
 *
 */
public class CodingScheme implements ModelElement, Serializable {

	private static final long serialVersionUID = 8715185246676630319L;
	/**
	 * <b>String256 codingScheme</b> - The codingScheme [Mandatory].<br>
	 * Type: {@link String256}</br> </p>
	 *
	 *
	 * @see String256
	 * @see CodingScheme
	 * @see CodedTerm
	 */
	@NotNull
    // TODO Check that RegExp
    @MatchesPattern(value = "urn:uuid:[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}")
	private String256 codingScheme;

	public CodingScheme() {
		codingScheme = new String256();
	}


    public CodingScheme(String256 codingScheme) {
        if (codingScheme.toString().length() < 257) {
            this.codingScheme = codingScheme;
        } else {
            this.codingScheme = new String256();
        }
    }

    public String256 getCodingScheme() {
        return codingScheme;
    }

	public CodingScheme setCodingScheme(String256 codingScheme) {
		this.codingScheme = codingScheme;return this;
	}

	/**
	 *
	 * <p>
	 * <b>Method toString</b> <br>
	 * This method will be called to build a XML file by the {@link CodedTerm}
     * (called itself by the {@link XdsDocumentEntry}) with the information taken
     * from the local {@link CodingScheme}.<br>
     * </p>
	 *
	 * @return String which contains the CodingScheme
	 *
	 * @see CodingScheme
	 */
	@Override
	public String toString() {
		return codingScheme.toString();
	}

	/**
	 * <p>
	 * <b>Method verify</b> <br>
	 * This method will be called to check whether the syntax's
	 * {@link CodingScheme} is correct </br>
	 * </p>
	 *
	 * @return boolean true if the syntax is correct, else return false
	 * @throws String256Exception
	 *             if there is a String256 with more than 256 characters
	 *
	 * @see CodingScheme
	 *
	 */
	@Override
	public boolean verify() throws String256Exception {
        return codingScheme.verify();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodingScheme)) return false;

        CodingScheme that = (CodingScheme) o;

        if (!codingScheme.equals(that.codingScheme)) return false;

        return true;
    }

}
