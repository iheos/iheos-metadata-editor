package gov.nist.hit.ds.docentryeditor.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class made to gather all information of a Metadata document
 * (Submission set, Document entries, Associations and Folders).
 *
 * @See XdsSubmissionSet
 * @See XdsDocumentEntry
 * Created by onh2 on 2/19/2015.
 */
public class XdsMetadata implements Serializable{
    private static final long serialVersionUID = 1L;

    private XdsSubmissionSet submissionSet;
    private List<XdsDocumentEntry> documentEntries;

    public XdsMetadata(){
        documentEntries=new ArrayList<XdsDocumentEntry>();
    }

    public XdsSubmissionSet getSubmissionSet() {
        return submissionSet;
    }

    public void setSubmissionSet(XdsSubmissionSet submissionSet) {
        this.submissionSet = submissionSet;
    }

    public List<XdsDocumentEntry> getDocumentEntries(){return documentEntries;}

    public void setDocumentEntries(List<XdsDocumentEntry> documentEntries){this.documentEntries=documentEntries;}
}
