package gov.nist.hit.ds.docentryeditor.client.widgets;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.EditorFieldLabel;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParserServices;
import gov.nist.hit.ds.docentryeditor.client.parser.XdsParserServicesAsync;
import gov.nist.hit.ds.docentryeditor.client.utils.Services.MetadataEditorRequestFactory;
import gov.nist.hit.ds.docentryeditor.shared.SaveInExtCacheRequest;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsMetadata;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;


/**
 * Created by onh2 on 3/31/17.
 */
public class SaveDialog extends Dialog {
    private static final int RIGHT_N_LEFT_LBL_MARGIN = 5;
    private static final int UP_N_DOWN_LBL_MARGIN = 10;

    private MetadataEditorRequestFactory requestFactory;
    // RPC services declaration
    private final XdsParserServicesAsync xdsParserServices = GWT.create(XdsParserServices.class);

    private final Logger logger = Logger.getLogger(SaveDialog.class.getName());

    private TextField testNameTF=new TextField();
    private HorizontalLayoutContainer radioContainer=new HorizontalLayoutContainer();
    ToggleGroup radioGroup=new ToggleGroup();

    private TextButton overwriteButton = new TextButton("Update existing data in Ext. Cache");
    private TextButton saveAsButton = new TextButton("Save as a new file in Ext. Cache");
    private TextButton downloadButton = new TextButton("Open file");
    private TextButton cancelButton = new TextButton("Cancel");

    private SaveInExtCacheRequest saveRequest;
    private String selectedTransactionType=null;

    @Inject
    public SaveDialog(MetadataEditorRequestFactory requestFactory){
        super();
        this.requestFactory=requestFactory;
        setBodyBorder(false);
        setHeadingText("Save");
        setHideOnButtonClick(true);
        setHeight(180);

        // delete the default button
        getButtonBar().remove(0);
        setModal(true);

        buildDialog();
        bindDialog();
    }

    private void buildDialog() {
        VerticalLayoutContainer container=new VerticalLayoutContainer();
        container.add(new HtmlLayoutContainer("Complete the following fields to saveAs in the external cache, or click download to just visualize the XML file."),new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(UP_N_DOWN_LBL_MARGIN, RIGHT_N_LEFT_LBL_MARGIN, UP_N_DOWN_LBL_MARGIN/2, RIGHT_N_LEFT_LBL_MARGIN)));

        EditorFieldLabel testNameFieldLabel=new EditorFieldLabel(testNameTF,"Test name");
        testNameTF.setAllowBlank(false);
        EditorFieldLabel transactionTypeFieldLabel=new EditorFieldLabel(radioContainer,"Select transaction type");
        container.add(transactionTypeFieldLabel,new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(UP_N_DOWN_LBL_MARGIN/2, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN,0, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN)));
        container.add(testNameFieldLabel,new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(0, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN,UP_N_DOWN_LBL_MARGIN, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN)));

        add(container);

        addButtons();
    }

    private void addButtons(){
        addButton(overwriteButton);
        overwriteButton.disable();
        addButton(saveAsButton);
        saveAsButton.disable();
        addButton(downloadButton);
        addButton(cancelButton);
    }

    private void bindDialog() {
        populateTransactionTypeCombo();
        cancelButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                hide();
            }
        });
        downloadButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                download();
                hide();
            }
        });
        saveAsButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                saveRequest.setTransactionType(selectedTransactionType);
                saveRequest.setTestName(testNameTF.getText());
                saveAs(saveRequest);
                hide();
            }
        });
        testNameTF.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                if (!(testNameTF.getText().isEmpty() || "".equals(testNameTF.getText())) && selectedTransactionType!=null){
                    saveAsButton.enable();
                }else {
                    saveAsButton.disable();
                }
            }
        });
        radioGroup.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
            @Override
            public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
                ToggleGroup group = (ToggleGroup) event.getSource();
                Radio radio = (Radio) group.getValue();
                selectedTransactionType=radio.getBoxLabel();
                if (!testNameTF.getText().isEmpty() && selectedTransactionType!=null){
                    saveAsButton.enable();
                }else {
                    saveAsButton.disable();
                }
            }
        });
        overwriteButton.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                overwriteMetadata();
            }
        });
    }

    private void overwriteMetadata() {
        xdsParserServices.updateMetadataInExtCache(saveRequest, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failure while saving file!");
            }

            @Override
            public void onSuccess(Void result) {
                logger.info("File saved successfully!");
                Info.display("Metadata saved","Metadata in Ext. cache updated succesfully.");
                hide();
            }
        });
    }

    private void saveAs(SaveInExtCacheRequest saveRequest) {
        xdsParserServices.saveInExternalCache(saveRequest, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failure while saving file!");
            }

            @Override
            public void onSuccess(Void result) {
                logger.info("File saved successfully!");
                Info.display("Metadata saved","Metadata added successfully in Ext. cache");
            }
        });
    }

    private void download() {
        XdsMetadata m = saveRequest.getMetadata();
        // rpc server call to translate metadata java object into an ebRim xml String.
        xdsParserServices.toEbRim(m, new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.warning(throwable.getMessage());
            }

            @Override
            public void onSuccess(String s) {
                // request factory server call to physically saveAs the file on the server.
                requestFactory.saveFileRequestContext().saveAsXMLFile(s).fire(new Receiver<String>() {
                    @Override
                    public void onSuccess(String response) {
                        logger.info("saveAsXMLFile call succeed");
                        logger.info("Generated filename sent to the client \n" + response);
                        logger.info("File's URL: " + GWT.getHostPageBaseURL() + "files/" + response);
                        // FIXME think about this later
                        // view.openFileSavedPopup(response);
                        openFileSavedPopup(response);
                    }
                });
            }
        });
    }

    /**
     * This method open new browser window with the ebRim XML file
     * representing the XDS Metadata being saved.
     * @param fileNameOnServer name of the ebRim XML file of XDS Metadata.
     */
    public void openFileSavedPopup(String fileNameOnServer) {
        String fileURI=GWT.getHostPageBaseURL() + "files/" + fileNameOnServer;
        Window.open(fileURI, fileNameOnServer + " Metadata File", "enabled");
        Dialog d = new Dialog();
        HTMLPanel htmlP = new HTMLPanel("<a href='" + fileURI + "'>" + fileURI + "</a>");
        VerticalLayoutContainer vp = new VerticalLayoutContainer();
        vp.add(new Label("Your download is in progress, please allow this application to open popups with your browser..."),
                new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(UP_N_DOWN_LBL_MARGIN, RIGHT_N_LEFT_LBL_MARGIN, UP_N_DOWN_LBL_MARGIN, RIGHT_N_LEFT_LBL_MARGIN)));
        vp.add(htmlP, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(UP_N_DOWN_LBL_MARGIN, RIGHT_N_LEFT_LBL_MARGIN, UP_N_DOWN_LBL_MARGIN, RIGHT_N_LEFT_LBL_MARGIN)));
        d.add(vp);
        d.setPredefinedButtons(Dialog.PredefinedButton.OK);
        d.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
        d.setHideOnButtonClick(true);
        d.setHeadingText("XML Metadata File Download");
        d.show();
    }

    /**
     * Request values for the transaction type section.
     */
    private void populateTransactionTypeCombo() {
        requestFactory.extCacheRequestContext().getTransactionTypes().fire(new Receiver<List<String>>() {
            @Override
            public void onSuccess(List<String> response) {
                for (String s:response) {
                    Radio radio=new Radio();
                    radio.setBoxLabel(s);
                    radio.setName(s);
                    radioGroup.add(radio);
                    radioContainer.add(radio);
                }
            }
        });
    }

    public void setSaveRequest(SaveInExtCacheRequest saveRequest) {
        this.saveRequest = saveRequest;
        if (saveRequest.getFilePath()!=null){
            overwriteButton.enable();
        }
    }
}
