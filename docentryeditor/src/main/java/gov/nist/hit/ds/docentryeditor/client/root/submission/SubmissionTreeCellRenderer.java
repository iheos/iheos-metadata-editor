package gov.nist.hit.ds.docentryeditor.client.root.submission;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * Cell renderer for the Submission tree.
 */
public class SubmissionTreeCellRenderer extends AbstractCell<SubmissionMenuData>{

    @Override
    public void render(Context context, SubmissionMenuData value, SafeHtmlBuilder safeHtmlBuilder) {
        if (value.isActiveLink()){
            safeHtmlBuilder.appendHtmlConstant("<span style='color:blue;'>"+value.getValue()+"</span>");
        }else{
            safeHtmlBuilder.appendEscaped(value.getValue());
        }
    }
}
