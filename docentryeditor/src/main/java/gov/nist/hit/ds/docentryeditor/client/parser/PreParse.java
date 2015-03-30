package gov.nist.hit.ds.docentryeditor.client.parser;

import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.DOMException;

/**
 *
 * <b>This class prepares a XML file to be parsed</b>
 *
 * <p>
 * To do it, here are the following variables required
 * </p>
 * <ul>
 * <li>{@link #myPreParse} : The PreParse object, this class is a singleton
 * (PreParse) ;</li>
 * <li>{@link #documentXml} : The String which contains the XML data (String).</li>
 * </ul>
 * </p>
 *
 * <p>
 * This class also contains getters/setters.<br>
 * In addition, it contains several methods such as cleanEscape to delete
 * unexpected space or allowUTF8 to enable UTF8 character in
 * {@link #documentXml}.
 * </p>
 *
 * <p>
 * <b>How it works ?</b><br>
 * This class is a singleton which the only instance is called by a
 * {@link gov.nist.hit.ds.docentryeditor.client.parser.XdsParser} object, then it enables to prepare the String thanks to
 * cleanEscape and allowUTF called in the doPreParse method as follows:
 * {@link #doPreParseUTF8(String)}.</br>
 * </p>
 *
 *
 * <p>
 * <b>See below each method mentioned above or come back to an other class.</b>
 * <br>
 * {@link #cleanEscape()}</br> {@link #allowUTF8()} <br>
 * {@link #doPreParseUTF8(String)} </br>
 * </p>
 *
 *
 *
 * @see gov.nist.hit.ds.docentryeditor.client.parser.XdsParser
 *
 */
public class PreParse {

	/**
	 * <p>
	 *
	 * documentXml - The data taken from the XML document and send by the
	 * server, this is the String to parser.<br>
	 * Type : String</br>
	 * </p>
	 *
	 * @see gov.nist.hit.ds.docentryeditor.client.parser.PreParse
	 * @see gov.nist.hit.ds.docentryeditor.client.parser.XdsParser
	 */
	private static String documentXml;

	/**
	 * <p>
	 *
	 * myPreParse - The instance of PreParse class (it's a singleton class).<br>
	 * Type : PreParse</br>
	 * </p>
	 *
	 *
	 * @see gov.nist.hit.ds.docentryeditor.client.parser.PreParse
	 */
	private final static PreParse myPreParse = new PreParse();

	public static PreParse getInstance() {
		return myPreParse;
	}

	public String doPreParseUTF8(String msg) {
		documentXml = msg;
		cleanEscape();
		allowUTF8();
		return documentXml;
	}

	public String doPreParse(String msg) {
		documentXml = msg;
		cleanEscape();
		return documentXml;
	}

	/**
	 * <p>
	 * <b>Method cleanEscape</b> <br>
	 * It uses a {@link StringBuilder} to copy the data's XML without unexpected
	 * space.</br>It analysis char by char whether there are too much space.<br>
	 * This method is called by {@link #doPreParseUTF8(String)}.
	 * </p>
	 *
	 *
	 * @see gov.nist.hit.ds.docentryeditor.client.parser.PreParse
	 *
	 */
	private void cleanEscape() {
		StringBuilder xmlWithoutEscape = new StringBuilder();
		try {
			// Delete escape between > <

			for (int i = 0; i < documentXml.length(); i++) {
				char c = documentXml.charAt(i);
				// c == 32 <=> c = ' '
				if (c == 32) {
					// Delete escape after an escape or after > (==62) or before
					// < (==60)
					if (!(documentXml.charAt(i - 1) == 62
							|| documentXml.charAt(i - 1) == 32
							|| documentXml.charAt(i + 1) == 60)) {
						xmlWithoutEscape.append(c);
					}
				} else {
					xmlWithoutEscape.append(c);
				}
			}
		} catch (DOMException e) {
			Window.alert("Could not parse XML document.");
		}
		documentXml = xmlWithoutEscape.toString();
	}

	/**
	 * <p>
	 * <b>Method allowUTF8</b> <br>
	 * It uses a {@link StringBuilder} to copy the data's XML with available
	 * UTF8 characters.</br>It analysis char by char whether there are UTF8
	 * characters.<br>
	 * This method is called by {@link #doPreParseUTF8(String)}.
	 * </p>
	 *
	 *
	 * @see gov.nist.hit.ds.docentryeditor.client.parser.PreParse
	 *
	 */
	private void allowUTF8() {
		// For the UTF-8 caracters : replace by their ASCII number
		StringBuilder xmlEscaped = new StringBuilder();
		for (int i = 0; i < documentXml.toString().length(); i++) {
			char c = documentXml.toString().charAt(i);
			if (c < 127) {
				if (c == 38) {
					// caracter &
					xmlEscaped.append("&#" + ((int) c) + ";");
				} else {
					// to avoid \n \t \s, warning : pb if \\n \\t \\s
					if (c == 92
							&& (documentXml.charAt(i + 1) == 't'
									|| documentXml.charAt(i + 1) == 'n' || documentXml
									.charAt(i + 1) == 's')) {
						i = i + 1;
					} else {
						// character TAB LF VT
						if (!(c == 9 || c == 10 || c == 11)){
							xmlEscaped.append(c);
						}
					}
				}
			} else {
				xmlEscaped.append("&#" + ((int) c) + ";");
			}
		}
		// replace &lt and &gt
		String xmlEscapedString = xmlEscaped.toString().replaceAll("&lt;", "<");
		xmlEscapedString = xmlEscapedString.replaceAll("&gt;", ">");
		documentXml = xmlEscapedString;

	}
}
