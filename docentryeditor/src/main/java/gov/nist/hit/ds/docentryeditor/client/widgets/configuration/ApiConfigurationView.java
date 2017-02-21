package gov.nist.hit.ds.docentryeditor.client.widgets.configuration;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import gov.nist.hit.ds.docentryeditor.client.editor.widgets.EditorFieldLabel;
import gov.nist.hit.ds.docentryeditor.client.generics.abstracts.AbstractView;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by onh2 on 2/15/17.
 */
public class ApiConfigurationView extends AbstractView<ApiConfigurationPresenter> {
    private FramedPanel panel;
    @Inject
    private TextField hostName;
    @Inject
    private TextField contextName; //(/xdstools2)
    @Inject
    private TextField portNumber;

    private TextButton saveBtn;
    private TextButton cancelBtn;

    @Override
    protected Map<String, Widget> getPathToWidgetsMap() {
        return null;
    }

    @Override
    protected Widget buildUI() {
        panel = new FramedPanel();
        panel.setHeaderVisible(false);
        panel.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);
        panel.setBorders(false);

        VerticalLayoutContainer container = new VerticalLayoutContainer();
        EditorFieldLabel hostNameFL = new EditorFieldLabel(hostName, "Host name");
        container.add(hostNameFL, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        EditorFieldLabel contextNameFL = new EditorFieldLabel(contextName, "Context name");
        container.add(contextNameFL, new VerticalLayoutContainer.VerticalLayoutData(1, -1));
        EditorFieldLabel portNumberFL = new EditorFieldLabel(portNumber, "Port number");
        portNumber.setWidth(60);
        container.add(portNumberFL);

        saveBtn = new TextButton("Save");
        cancelBtn = new TextButton("Cancel");

        panel.add(container);
        panel.addButton(saveBtn);
        panel.addButton(cancelBtn);
        return panel;
    }

    @Override
    protected void bindUI() {
        saveBtn.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                presenter.saveApiConfiguration(hostName.getText(), contextName.getText(), portNumber.getText());
            }
        });
        ApiConfigurationData config=presenter.retrieveConfigurationData();
        hostName.setText(config.getHost());
        contextName.setText(config.getContext());
        portNumber.setText(config.getPort());
    }

    public TextButton getSaveButton() {
        return saveBtn;
    }

    public TextButton getCancelButton() {
        return cancelBtn;
    }
}
