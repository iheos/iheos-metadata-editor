package gov.nist.hit.ds.docentryeditor.shared.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>
 * <b> This class represents a NameValue.</b><br>
 * A NameValue depends on a type T which is implemented during the instantiation
 * </p>
 * <p>
 * An NameValue has the following parameters:
 * <ul>
 * <li>{@link #name}: The name of the NameValue ({@link String256}) ;</li>
 * <li>{@link #values}: A list of values (ArrayList of Integer) ;</li>
 * </ul>
 * </p>
 * <p>
 * It contains a toXML method to return the NameValue in XML format.<br>
 * This class also contains getters/setters.</br> In addition, it has verify
 * method to check its syntax.
 * </p>
 * <p/>
 * <p>
 * <b>See below each method mentioned above.</b> <br>
 * {@link #verify() method verify}</br> {@link #toXML() method toXML} <br>
 * </p>
 *
 * @see XdsDocumentEntry
 * @see ModelElement
 */
public class NameValueInteger implements ModelElement, Serializable {
    private static final long serialVersionUID = -8454362856443516728L;

    /**
     * <b>String256 name</b> - The name of the NameValue [Mandatory].<br>
     * Type: {@link String256}</br>
     *
     * @see String256
     * @see NameValueInteger
     */
    @NotNull
    private String256 name;

    /**
     * <b>ArrayList(Integer) values</b> - A list of values [Mandatory].<br>
     * Type: ArrayList of {@link Integer}
     *
     * @see NameValueInteger
     */
    @NotNull
    @NotEmpty
    private ArrayList<Integer> values;

    public NameValueInteger() {
        name = new String256();
        values = new ArrayList<Integer>();
        values.add(0);
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
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
     * {@link NameValueInteger}.<br>
     * </p>
     *
     * @return String which contains the NameValue in XML format
     * @see NameValueInteger
     */
    public String toXML() {
        String answer = "\t\t<namevalue>\n\t\t\t<name>" + name.toString() + "</name>\n\t\t\t<values>\n";
        for (Integer str : values) {
            answer = answer + "\t\t\t\t<value>" + str.toString() + "</value>\n";
        }
        answer = answer + "\t\t\t</values>\n\t\t</namevalue>\n";

        return answer;
    }

    // abstract public AssertionGroup validateValueType(String256 value);

    /**
     * <p>
     * <b>Method verify</b> <br>
     * This method will be called to check whether the syntax's
     * {@link NameValueInteger} is correct </br>
     * </p>
     *
     * @return boolean true if the syntax is correct, else return false
     * @throws String256Exception if there is a String256 with more than 256 characters
     * @see NameValueInteger
     */
    @Override
    public boolean verify() throws String256Exception {
        // FIXME I don't understand the purpose of this code, it's far incomplete
        boolean answer = true;
        answer = name.verify();

        return answer;
    }

    @Override
    public String toString() {
        return "NameValueInteger{" +
                "name=" + name +
                ", values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameValueInteger)) return false;

        NameValueInteger that = (NameValueInteger) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (values != null ? !values.equals(that.values) : that.values != null) return false;

        return true;
    }

    public NameValueInteger copy() {
        NameValueInteger cp = new NameValueInteger();
        cp.setName(this.name.copy());
        for (Integer i:this.values){
            cp.getValues().set(0,new Integer(i));
        }
        return cp;
    }
}
