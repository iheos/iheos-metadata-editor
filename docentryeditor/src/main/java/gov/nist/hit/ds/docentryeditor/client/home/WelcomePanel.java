package gov.nist.hit.ds.docentryeditor.client.home;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;
import gov.nist.hit.ds.docentryeditor.client.resources.ToolTipResources;
import gov.nist.hit.ds.docentryeditor.client.widgets.PageWarningPanel;
import gov.nist.hit.ds.docentryeditor.client.widgets.uploader.FileUploadDialog;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

import javax.inject.Inject;

public class WelcomePanel extends VerticalLayoutContainer {
    public static final int BUTTONS_MARGINS = 50;
    public static final int BUTTONS_CONTAINER_HEIGHT = 120;

    @Inject
    protected EventBus eventBus;
    @Inject
    FileUploadDialog fileUploadDialog;
    @Inject
    PlaceController placeController;

    private HomeIconButton newFolderBtn;
    private HomeIconButton newDocEntryBtn;
    private HomeIconButton loadPreFilledDocEntryBtn;
    private HomeIconButton uploadFileBtn;
    private HomeIconButton editSubSetBtn;

    /**
     * Default constructor.
     */
    public WelcomePanel() {
        buildUI();
        bindUI();
    }

    /**
     * Method that builds the widget.
     */
    private void buildUI() {
        // set container's parameters
        this.setStyleName("padding-10");
        setBorders(false);

        // set html welcome explanation panel
        HtmlLayoutContainer htmlExplanationTitle=new HtmlLayoutContainer("<br/><h2>Welcome to the XDS Document Entry Editor</h2>");
        HtmlLayoutContainer htmlExplanation=new HtmlLayoutContainer("<br/>This application was developed by the National Institute of Standards and Technology " +
                "to generate custom XDS metadata for testing purposes. This software is based on the specification " +
                "<a href='http://www.ihe.net/uploadedFiles/Documents/ITI/IHE_ITI_TF_Rev10.0_Vol3_FT_2013-09-27.pdf' target='_blank'>IHE IT Infrastructure Technical Framework Volume 3 (ITI TF-3)</a>.<br/>" +
                "To start, use the buttons below or the left panel to manipulate metadata objects. <br/><br/>");

        // set home buttons to create xds elements
        newFolderBtn = new HomeIconButton("New folder",AppImages.INSTANCE.newFolder());
        // temporary disable new folder btn until it s implemented.
        newFolderBtn.disable();
        editSubSetBtn=new HomeIconButton("Edit submission set",AppImages.INSTANCE.editSubSet());
        editSubSetBtn.setToolTip(ToolTipResources.INSTANCE.getEditSubSetTooltip());
        newDocEntryBtn = new HomeIconButton("New doc. entry",AppImages.INSTANCE.filePlus());
        newDocEntryBtn.setToolTip(ToolTipResources.INSTANCE.getNewDocEntryTooltip());
        loadPreFilledDocEntryBtn = new HomeIconButton("New pre-filled doc. entry",AppImages.INSTANCE.preFilled());
        loadPreFilledDocEntryBtn.setToolTip(ToolTipResources.INSTANCE.getNewPrefilledDocEntryTooltip());
        uploadFileBtn = new HomeIconButton("Upload submission set",AppImages.INSTANCE.loadFile());
        uploadFileBtn.setToolTip(ToolTipResources.INSTANCE.getUploadFileTooltip());

        // - organize buttons layout
        VerticalLayoutContainer submissionSetButtonsContainer = new VerticalLayoutContainer();
        submissionSetButtonsContainer.add(uploadFileBtn,new VerticalLayoutData(1,-1,new Margins(0,0,BUTTONS_MARGINS,0)));
        submissionSetButtonsContainer.add(editSubSetBtn,new VerticalLayoutData(1,-1,new Margins(BUTTONS_MARGINS,0,0,0)));
        VerticalLayoutContainer folderButtonsContainer=new VerticalLayoutContainer();
        folderButtonsContainer.add(newFolderBtn,new VerticalLayoutData(1,-1,new Margins(0,0,BUTTONS_MARGINS,0)));
        VerticalLayoutContainer docEntryButtonsContainer=new VerticalLayoutContainer();
        docEntryButtonsContainer.add(newDocEntryBtn,new VerticalLayoutData(1,-1,new Margins(0,0,BUTTONS_MARGINS,0)));
        docEntryButtonsContainer.add(loadPreFilledDocEntryBtn,new VerticalLayoutData(1,-1,new Margins(BUTTONS_MARGINS,0,0,0)));

		// add home buttons to a Horizontal container
		HorizontalLayoutContainer buttonsHContainer= new HorizontalLayoutContainer();
        buttonsHContainer.add(submissionSetButtonsContainer,new HorizontalLayoutContainer.HorizontalLayoutData(0.33,-1,new Margins(0, BUTTONS_MARGINS,0,0)));
        buttonsHContainer.add(folderButtonsContainer,new HorizontalLayoutContainer.HorizontalLayoutData(0.33,-1,new Margins(0, BUTTONS_MARGINS,0, BUTTONS_MARGINS)));
        buttonsHContainer.add(docEntryButtonsContainer,new HorizontalLayoutContainer.HorizontalLayoutData(0.33,-1,new Margins(0,0,0, BUTTONS_MARGINS)));

		// Set home page warning panel
		PageWarningPanel warningPanel = new PageWarningPanel("<strong style='font-size:1.15em'>Warning:</strong> This software is still under development.<br/>" +
				"<ul><li>The functionalities Folder and Association are not implemented yet.</li>" +
				"</ul>");

		this.add(htmlExplanationTitle);
		this.add(htmlExplanation);
		this.add(buttonsHContainer, new VerticalLayoutData(1, BUTTONS_CONTAINER_HEIGHT));
		this.add(warningPanel, new VerticalLayoutData(1, -1));
        this.setScrollMode(ScrollSupport.ScrollMode.AUTO);
	}

    /**
     * Method that ties actions with the view.
     */
	private void bindUI() {
		newDocEntryBtn.addSelectHandler(new SelectEvent.SelectHandler() {
			@Override
			public void onSelect(SelectEvent selectEvent) {
                ((MetadataEditorEventBus) eventBus).fireCreateNewDocEntryEvent(new XdsDocumentEntry());
			}
		});
		loadPreFilledDocEntryBtn.addSelectHandler(new SelectEvent.SelectHandler() {
			@Override
			public void onSelect(SelectEvent selectEvent) {
                ((MetadataEditorEventBus) eventBus).fireLoadPreFilledDocEntryEvent();
			}
		});
		uploadFileBtn.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                fileUploadDialog.init();
                fileUploadDialog.show();
            }
        });
        editSubSetBtn.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent selectEvent) {
                ((MetadataEditorEventBus) eventBus).fireSelectSubmissionSetEvent();
            }
        });
	}

	@Override
	public Widget asWidget() {
		return this;
	}

}
