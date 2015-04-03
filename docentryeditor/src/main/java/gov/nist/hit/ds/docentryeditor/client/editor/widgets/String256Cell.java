package gov.nist.hit.ds.docentryeditor.client.editor.widgets;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import gov.nist.hit.ds.docentryeditor.shared.model.String256;

@Deprecated
public class String256Cell extends AbstractCell<String256> {

	@Override
	public void render(Context context, String256 value, SafeHtmlBuilder sb) {
		if (value != null) {
			sb.appendEscaped(value.getString());
		}

	}

}
