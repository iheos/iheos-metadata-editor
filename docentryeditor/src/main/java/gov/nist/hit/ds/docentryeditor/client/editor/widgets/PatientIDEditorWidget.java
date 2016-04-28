package gov.nist.hit.ds.docentryeditor.client.editor.widgets;

import gov.nist.hit.ds.docentryeditor.client.editor.widgets.identifier.IdentifierString256EditorWidget;
import gov.nist.hit.ds.docentryeditor.client.resources.ClientFormatValidationResource;
import gov.nist.hit.ds.docentryeditor.client.resources.ToolTipResources;

/**
 * Created by onh2 on 4/28/16.
 */
public class PatientIDEditorWidget extends IdentifierString256EditorWidget {
    public PatientIDEditorWidget(){
        super();
        configure();
    }

    private void configure() {
        this.setEmptyTexts("ex: 76cc^^^&1.3.6367.2005.3.7&ISO", "ex: urn:uuid:6b5aea1a-625s-5631-v4se-96a0a7b38446");
        this.setToolTipConfigs(ToolTipResources.INSTANCE.getPatientIdTooltipConfig());
        this.addValueFieldValidator(ClientFormatValidationResource.INSTANCE.getPatientIDRegExpValidator());
    }
}
