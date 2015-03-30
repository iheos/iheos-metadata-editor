package gov.nist.hit.ds.docentryeditor.client.widgets.uploader;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.info.Info;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;

import java.util.HashMap;
import java.util.Map;

public class FileUploadView extends AbstractView<FileUploadPresenter> {
    FileUploadField file;
    private FormPanel form;
    private FramedPanel panel;
    private TextButton btnSubmit;
    private TextButton btnCancel;

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
        // panel.setWidth(350);
        panel.getElement().setMargins(5);
        panel.setBorders(false);

        form = new FormPanel();
        form.setStyleName("form-margin");
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
                    // file.isValid();
                } else {
                    Info.display("File Changed", "You selected " + file.getValue());
                    btnSubmit.enable();
                }
            }
        });
        file.setName("uploadedfile");
        file.setAllowBlank(false);
        file.setWidth(250);

        vcontainer.add(new FieldLabel(file, "File"), new VerticalLayoutData(-1,
                -1));

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
                getPresenter().fileUploaded(event.getResults());
            }
        });
    }

}
