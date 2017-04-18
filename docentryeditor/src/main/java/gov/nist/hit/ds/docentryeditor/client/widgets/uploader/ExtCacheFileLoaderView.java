package gov.nist.hit.ds.docentryeditor.client.widgets.uploader;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.EditorFieldLabel;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by onh2 on 4/18/17.
 */
public class ExtCacheFileLoaderView extends AbstractView<ExtCacheFileLoaderPresenter> {
    private static final int FORM_MARGIN = 5;
    private static final int UPLOADER_WIDTH=500;
    private static final int UPLOADER_HEIGHT=150;
    private static final int RIGHT_N_LEFT_LBL_MARGIN = 5;
    private static final int UP_N_DOWN_LBL_MARGIN = 10;

    private FormPanel form;
    private FramedPanel panel;
    private TextButton btnSubmit;
    private TextButton btnCancel;

    private ToggleGroup radioGroup=new ToggleGroup();
    private HorizontalLayoutContainer radioContainer=new HorizontalLayoutContainer();
    private SimpleComboBox<String> testNamesCombo = new SimpleComboBox<String>(new StringLabelProvider<>());
    private SimpleComboBox<String> sectionsCombo = new SimpleComboBox<String>(new StringLabelProvider<>());
    private VerticalLayoutContainer sectionsContainer=new VerticalLayoutContainer();

    @Override
    protected Map<String, Widget> getPathToWidgetsMap() {
        Map<String, Widget> map = new HashMap<String, Widget>();
        return map;
    }

    @Override
    protected Widget buildUI() {
        panel = new FramedPanel();
        panel.setHeaderVisible(false);
        panel.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
        panel.setWidth(UPLOADER_WIDTH);
        panel.setHeight(UPLOADER_HEIGHT);
//        panel.getElement().setMargins(FORM_MARGIN);
        panel.setBorders(false);

        form = new FormPanel();
        form.setAction("ServletUpload");
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);
        panel.add(form);

        VerticalLayoutContainer vcontainer = new VerticalLayoutContainer();
        form.add(vcontainer);

        EditorFieldLabel transactionTypeFieldLabel=new EditorFieldLabel(radioContainer,"Select transaction type");
        vcontainer.add(transactionTypeFieldLabel,new VerticalLayoutContainer.VerticalLayoutData(-1, -1, new Margins(UP_N_DOWN_LBL_MARGIN/2, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN,0, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN)));
        EditorFieldLabel testFilesComboFL=new EditorFieldLabel(testNamesCombo,"Select testdata instance");
        vcontainer.add(testFilesComboFL,new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(UP_N_DOWN_LBL_MARGIN/2, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN,0, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN)));
        vcontainer.add(sectionsContainer,new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(UP_N_DOWN_LBL_MARGIN/2, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN,0, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN)));

        btnSubmit = new TextButton("Open");
        btnSubmit.disable();
        btnCancel = new TextButton("Cancel");

        panel.addButton(btnSubmit);
        panel.addButton(btnCancel);

        return panel;
    }

    @Override
    protected void bindUI() {
        // retrieve and populate transaction type radios
        presenter.retrieveTransactionTypes();
        radioGroup.addValueChangeHandler(new ValueChangeHandler<HasValue<Boolean>>() {
            @Override
            public void onValueChange(ValueChangeEvent<HasValue<Boolean>> event) {
                testNamesCombo.clear();
                ToggleGroup group = (ToggleGroup) event.getSource();
                Radio radio = (Radio) group.getValue();
                String selectedTransactionType=radio.getBoxLabel();
                testNamesCombo.clear();
                presenter.retrieveTestdataInstanceNames(selectedTransactionType);
            }
        });
        testNamesCombo.addSelectionHandler(new SelectionHandler<String>() {
            @Override
            public void onSelection(SelectionEvent<String> event) {
                presenter.retrieveSections(event.getSelectedItem());
            }
        });
        btnSubmit.addSelectHandler(new SelectEvent.SelectHandler() {

            @Override
            public void onSelect(SelectEvent event) {
            }

        });
    }

    public void addTransacationRadio(String s) {
        Radio radio=new Radio();
        radio.setBoxLabel(s);
        radio.setName(s);
        radioGroup.add(radio);
        radioContainer.add(radio);
    }

    public void clearTransactionTypeRadioGroup() {
        radioContainer.clear();
        radioContainer.clear();
    }

    public void populateTestdataCombo(List<String> instances) {
        testNamesCombo.add(instances);
        sectionsContainer.clear();
    }

    public void showSections(List<String> sections) {
        sectionsCombo.add(sections);
        EditorFieldLabel sectionsComboFL=new EditorFieldLabel(sectionsCombo,"Select section");
        sectionsContainer.add(sectionsComboFL,new VerticalLayoutContainer.VerticalLayoutData(1, -1, new Margins(UP_N_DOWN_LBL_MARGIN/2, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN,0, RIGHT_N_LEFT_LBL_MARGIN+RIGHT_N_LEFT_LBL_MARGIN)));
    }

    @Override
    public Widget asWidget(){
        return panel.asWidget();
    }
}
