package gov.nist.hit.ds.docentryeditor.client.home;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.ToggleButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import gov.nist.hit.ds.docentryeditor.client.event.MetadataEditorEventBus;
import gov.nist.hit.ds.docentryeditor.client.resources.AppImages;
import gov.nist.hit.ds.docentryeditor.client.widgets.PageWarningPanel;
import gov.nist.hit.ds.docentryeditor.client.widgets.uploader.FileUploadDialog;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsDocumentEntry;

import javax.inject.Inject;

public class WelcomePanel extends VerticalLayoutContainer {

    @Inject
    protected EventBus eventBus;

	@Inject
	FileUploadDialog fileUploadDialog;

    @Inject
    PlaceController placeController;

	private ToggleButton newFolderBtn;
	private ToggleButton newDocEntryBtn;
	private ToggleButton loadPreFilledDocEntryBtn;
	private ToggleButton loadDocEntryFileBtn;

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
		HtmlLayoutContainer htmlWelcome=new HtmlLayoutContainer("<br/><br/>" + "<div style='font-family:Helvetica;font-size:2em;font-weight:bold;color:#777777;text-align:center'>" + "Welcome !"
				+ "</div>");
		HtmlLayoutContainer htmlExplanationTitle=new HtmlLayoutContainer("<br/><h2>Welcome to the XDS Document Entry Editor</h2>");
		HtmlLayoutContainer htmlExplanation=new HtmlLayoutContainer("<br/>This application was developed by the National Institute of Standards and Technology " +
				"to generate custom XDS metadata for testing purposes. This software is based on the specification " +
				"<a href='http://www.ihe.net/uploadedFiles/Documents/ITI/IHE_ITI_TF_Rev10.0_Vol3_FT_2013-09-27.pdf' target='_blank'>IHE IT Infrastructure Technical Framework Volume 3 (ITI TF-3)</a>.<br/>" +
				"To start, use the buttons below or the left panel to create Document Entry objects. <br/><br/>");

		// set home buttons to create xds elements
		newFolderBtn = new ToggleButton("New folder");
		newFolderBtn.setIcon(AppImages.INSTANCE.folder());
		newFolderBtn.setIconAlign(ButtonCell.IconAlign.TOP);
		// temporary disable new folder btn until it s implemented.
		newFolderBtn.disable();
		newDocEntryBtn = new ToggleButton("New doc. entry");
		newDocEntryBtn.setIcon(AppImages.INSTANCE.filePlus());
		newDocEntryBtn.setIconAlign(ButtonCell.IconAlign.TOP);
		loadPreFilledDocEntryBtn = new ToggleButton("Load pre-filled doc. entry");
		loadPreFilledDocEntryBtn.setIcon(AppImages.INSTANCE.preFilled());
		loadPreFilledDocEntryBtn.setIconAlign(ButtonCell.IconAlign.TOP);
		// temporary disable new folder btn until it s implemented.
//		loadPreFilledDocEntryBtn.disable();
		loadDocEntryFileBtn = new ToggleButton("Upload doc. entry");
		loadDocEntryFileBtn.setIcon(AppImages.INSTANCE.loadFile());
		loadDocEntryFileBtn.setIconAlign(ButtonCell.IconAlign.TOP);
        loadPreFilledDocEntryBtn.setMinWidth(50);

		// add home buttons to a Horizontal container
		HorizontalLayoutContainer buttonsHContainer= new HorizontalLayoutContainer();
		buttonsHContainer.add(newFolderBtn, new HorizontalLayoutContainer.HorizontalLayoutData(0.25, 75, new Margins(5)));
		buttonsHContainer.add(newDocEntryBtn, new HorizontalLayoutContainer.HorizontalLayoutData(0.25, 75, new Margins(5)));
		buttonsHContainer.add(loadPreFilledDocEntryBtn, new HorizontalLayoutContainer.HorizontalLayoutData(0.25, 75, new Margins(5)));
		buttonsHContainer.add(loadDocEntryFileBtn, new HorizontalLayoutContainer.HorizontalLayoutData(0.25, 75, new Margins(5)));

		// Set home page warning panel
		PageWarningPanel warningPanel = new PageWarningPanel("<strong style='font-size:1.15em'>Warning:</strong> This software is still under development.<br/>" +
				"<ul><li>The functionalities Folder and Association are not implemented yet.</li>" +
				"</ul>");

//		this.add(htmlWelcome);
		this.add(htmlExplanationTitle);
		this.add(htmlExplanation);
		this.add(buttonsHContainer, new VerticalLayoutData(1, -1));
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
		loadDocEntryFileBtn.addSelectHandler(new SelectEvent.SelectHandler() {
			@Override
			public void onSelect(SelectEvent selectEvent) {
				fileUploadDialog.init();
				fileUploadDialog.show();
			}
		});
	}

	@Override
	public Widget asWidget() {
		return this;
	}

}
