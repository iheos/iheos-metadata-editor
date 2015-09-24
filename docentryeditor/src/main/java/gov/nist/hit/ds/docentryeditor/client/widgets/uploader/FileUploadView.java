package gov.nist.hit.ds.docentryeditor.client.widgets.uploader;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.info.Info;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.EditorFieldLabel;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;
import gov.nist.hit.ds.docentryeditor.client.resources.ToolTipResources;
import gov.nist.hit.ds.docentryeditor.client.widgets.StandardSelector;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class FileUploadView extends AbstractView<FileUploadPresenter> {
    public static final int INSTRUCTIONS_BOTTOM_MARGIN = 10;
    private FormPanel form;
    private FramedPanel panel;
    private TextButton btnSubmit;
    private TextButton btnCancel;

    private StandardSelector selector=new StandardSelector();
    private FileUploadField file;

    private static final int FORM_MARGIN = 5;
    private static final int UPLOADER_WIDTH=500;
    private static final int UPLOADER_HEIGHT=150;

    @Override
    public Widget asWidget() {
        return panel.asWidget();
    }

    public TextButton getBtnSubmit() {
        return btnSubmit;
    }

    public TextButton getBtnCancel() {
        return btnCancel;
    }

    @Override
    protected Map<String, Widget> getPathToWidgetsMap() {
        Map<String, Widget> map = new HashMap<String, Widget>();
        map.put("file", file);
        return map;
    }

    @Override
    protected Widget buildUI() {
        panel = new FramedPanel();
        panel.setHeaderVisible(false);
        panel.setButtonAlign(BoxLayoutPack.CENTER);
        panel.setWidth(UPLOADER_WIDTH);
        panel.setHeight(UPLOADER_HEIGHT);
        panel.getElement().setMargins(FORM_MARGIN);
        panel.setBorders(false);

        form = new FormPanel();
        form.setAction("ServletUpload");
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);
        panel.add(form);

        VerticalLayoutContainer vcontainer = new VerticalLayoutContainer();
        form.add(vcontainer);

        file = new FileUploadField();
        file.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                if (!file.getValue().matches(".*\\.xml")) {
                    Info.display("File Invalid", "XML File Required");
                    btnSubmit.disable();
                } else {
                    Info.display("File Changed", "You selected " + file.getValue());
                    btnSubmit.enable();
                }
            }
        });
        file.setName("uploadedfile");
        file.setAllowBlank(false);

        HtmlLayoutContainer uploadGuidance=new HtmlLayoutContainer(ToolTipResources.INSTANCE.getUploadFileTooltip());
        EditorFieldLabel fileField=new EditorFieldLabel(file, "Metadata file (*.xml)");
        vcontainer.add(uploadGuidance,new VerticalLayoutData(1,-1,new Margins(0,0, INSTRUCTIONS_BOTTOM_MARGIN,0)));
        vcontainer.add(new EditorFieldLabel(selector,"Standard"), new VerticalLayoutData(1,-1,new Margins(0,0,0,0)));
        vcontainer.add(fileField, new VerticalLayoutData(1,-1,new Margins(0,0,0,0)));

        btnSubmit = new TextButton("Open");
        btnSubmit.disable();
        btnCancel = new TextButton("Cancel");

        panel.addButton(btnSubmit);
        panel.addButton(btnCancel);

        return panel;
    }

    @Override
    protected void bindUI() {
        btnSubmit.addSelectHandler(new SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
                if (file.getValue().matches(".*\\.xml")) {
                    logger.info("Loading file...");
                    form.submit();
                }
            }

        });
        form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(SubmitCompleteEvent event) {
                getPresenter().fileUploaded(event.getResults(),selector.getSelectedStandard());
            }
        });
    }

}
