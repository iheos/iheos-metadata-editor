package gov.nist.hit.ds.docentryeditor.client.resources;

/**
 * Created by onh2 on 4/7/2015.
 */
public enum ToolTipResources {
    INSTANCE;
    public String getEditSubSetTooltip(){
        return "Start editing the submission set.";
    }
    public String getNewDocEntryTooltip(){
        return "Create an empty document entry and add it to the existing submission set.";
    }
    public String getNewPrefilledDocEntryTooltip(){
        return "Create a document entry pre-filled with test data and add it to the existing submission set.";
    }

    public String getUploadFileTooltip() {
        return "Upload a existing XDS metadata file in ebRim XML format. It musts at least contain a Submission set, a document entry and an association to bind them.";
    }

    public String getSaveButtonToolTip() {
        return "Save data currently being edited and download an XDS Metadata file in ebRim XML format.";
    }

    public String getCancelButtonToolTip() {
        return "Rollback all the changes made since the last save or the last page change (auto save).";
    }

    public String getGridAddToolTip(){
        return "Add a new entry.";
    }

    public String getGridDeleteToolTip() {
        return "Delete selected element(s).";
    }

    public String getGridClearTooltip(){
        return "Clear widget (remove all entries).";
    }

    public String getHelpButtonToolTip(){
        return "Help";
    }

    public String getClearSubmissionSetToolTip() {
        return "Clear submission set from all elements.";
    }
}
