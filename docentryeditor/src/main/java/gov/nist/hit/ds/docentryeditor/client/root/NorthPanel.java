package gov.nist.hit.ds.docentryeditor.client.root;

import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.Padding;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;
import gov.nist.hit.ds.docentryeditor.client.widgets.configuration.ConfigurationDialog;
import gov.nist.hit.ds.docentryeditor.client.widgets.environment.EnvironmentSelectionWidget;
import gov.nist.hit.ds.docentryeditor.client.widgets.session.SessionSelectionWidget;

import javax.inject.Inject;

// not used anymore
public class NorthPanel extends ContentPanel {
    private TextButton configBtn=new TextButton("Configure",AppImages.INSTANCE.delete());
    private EnvironmentSelectionWidget environmentSelector;
    private SessionSelectionWidget sessionSelector;
    @Inject
    private ConfigurationDialog configurationDialog;

    @Inject
    public NorthPanel(EnvironmentSelectionWidget environmentSelector,SessionSelectionWidget sessionSelector) {
        this.environmentSelector=environmentSelector;
        this.sessionSelector=sessionSelector;

        setHeaderVisible(false);
        setBorders(false);
        HBoxLayoutContainer btnContainer=new HBoxLayoutContainer();
        btnContainer.setPadding(new Padding(5,20,5,5));
        btnContainer.setHBoxLayoutAlign(HBoxLayoutContainer.HBoxLayoutAlign.MIDDLE);

        configBtn.setHeight(28);
        configBtn.setWidth(85);
        configBtn.setIconAlign(ButtonCell.IconAlign.LEFT);

        btnContainer.add(environmentSelector,new BoxLayoutContainer.BoxLayoutData(new Margins(0, 65, 0, 0)));
        btnContainer.add(sessionSelector,new BoxLayoutContainer.BoxLayoutData(new Margins(0, 10, 0, 0)));
        btnContainer.add(configBtn);

        this.add(btnContainer);
        bindUI();
    }

    private void bindUI() {
        configBtn.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                configurationDialog.show();
            }
        });
    }

    public void start() {
        bindUI();
    }

}
