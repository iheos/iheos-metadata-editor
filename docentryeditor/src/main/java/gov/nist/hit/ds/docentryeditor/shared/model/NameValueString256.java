package gov.nist.hit.ds.docentryeditor.shared.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>
 * <b> This class represents a NameValueString256.</b><br>
 * A NameValueString256 depends on a type T which is implemented during the
 * instantiation
 * </p>
 * <p>
 * An NameValueString256 has the following parameters:
 * <ul>
 * <li>{@link #name}: The name of the NameValueString256 ({@link String256}) ;</li>
 * <li>{@link #values}: A list of values (ArrayList of T) ;</li>
 * </ul>
 * </p>
 * <p>
 * It contains a toXML method to return the NameValueString256 in XML format.<br>
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
 * @see ModelElement class ModelElement
 */
public class NameValueString256 implements ModelElement, Serializable {
    private static final long serialVersionUID = -6036404246643751647L;

    /**
     * <b>String256 name</b> - The name of the NameValueString256 [Mandatory].<br>
     * Type: {@link String256}</br>
     *
     * @see String256 class String256
     * @see NameValueString256
     */
    @NotNull
    private String256 name;

    /**
     * <b>ArrayList(String256)</b> - A list of values [Mandatory].<br>
     * Type: ArrayList of T</br> T could be {@link Integer} or {@link String256}
     * or {@link DTM}
     *
     * @see NameValueString256
     */
    @NotNull
    @NotEmpty
    private ArrayList<String256> values = new ArrayList<String256>();

    public NameValueString256() {
        name = new String256();
        values = new ArrayList<String256>();
    }

    public ArrayList<String256> getValues() {
        return values;
    }

    public void setValues(ArrayList<String256> values) {
        this.values = values;
    }

    public String256 getName() {
        return name;
    }

    public void setName(String256 name) {
        this.name = name;
    }

    /**
     * <p>
     * <b>Method toXML</b> <br>
     * This method will be called to build a XML file by the
     * {@link XdsDocumentEntry} with the information taken from the local
     * {@link NameValueString256}.<br>
     * </p>
     *
     * @return String which contains the NameValueString256 in XML format
     * @see NameValueString256 class NameValueString256
     */
    public String toXML() {
        String answer = "\t\t<namevalue>\n\t\t\t<name>" + name.toString() + "</name>\n\t\t\t<values>\n";
        for (String256 str : values) {
            answer = answer + "\t\t\t\t<value>" + str.toString().replace("&","&amp;") + "</value>\n";
        }
        answer = answer + "\t\t\t</values>\n\t\t</namevalue>\n";

        return answer;
    }

    // abstract public AssertionGroup validateValueType(String256 value);

    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check whether the syntax's
     * {@link NameValueString256} is correct </br>
     * </p>
     *
     * @return boolean true if the syntax is correct, else return false
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see NameValueString256 class NameValueString256
     */
    @Override
    public boolean verify() throws String256Exception {
        // FIXME I don't understand the purpose of this code
        boolean answer = true;
        answer = name.verify();

        for (String256 t : values) {
            answer = t.verify();
        }
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameValueString256)) return false;

        NameValueString256 that = (NameValueString256) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (values != null && values.size() == that.getValues().size()) {
            for (int i = 0; i < values.size(); i++) {
                if (!values.get(i).equals(that.getValues().get(i))) {
                    return false;
                }
            }
        } else return false;

        return true;
    }

    @Override
    public String toString() {
        return "NameValueString256{" +
                "name=" + name +
                ", values=" + values +
                '}';
    }

    public NameValueString256 copy() {
        NameValueString256 cp = new NameValueString256();
        cp.setName(this.getName().copy());
        for (String256 s : this.values){
            cp.getValues().add(s.copy());
        }
        return cp;
    }
}
