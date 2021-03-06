package gov.nist.hit.ds.docentryeditor.client.editor.widgets.codedterm;

import com.google.gwt.event.logical.shared.*;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import gov.nist.hit.ds.docentryeditor.client.editor.EditionMode;
import gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodes;
import gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodesParser;
import gov.nist.hit.ds.docentryeditor.shared.model.CodedTerm;

import java.util.ArrayList;
import java.util.List;

/**
 * ComboBox widget that handles predefined coded terms.
 *
 * @see gov.nist.hit.ds.docentryeditor.client.parser.PredefinedCodes
 */
public class PredefinedCodedTermComboBox extends ComboBox<CodedTerm> {

    private static final int WIDGET_HEIGHT = 22;
    private TextButton editCustomedValueBtn = new TextButton("Edit custom value");
    private List<Integer> customedValuesIndexes = new ArrayList<Integer>();
    private int selectedIndex = -1;

    /**
     * Predefined Coded Term Combo Box constructor.
     * @param predefinedCodes type of predefined codes to be handled by the widget (classCode, formatCode...)
     */
    public PredefinedCodedTermComboBox(PredefinedCodes predefinedCodes) {
        super(new ListStore<CodedTerm>(new ModelKeyProvider<CodedTerm>() {
            @Override
            public String getKey(CodedTerm item) {
                if (item == null) {
                    return "NULL";
                }
                return item.toString();
            }
        }), new LabelProvider<CodedTerm>() {
            /**
             * Method that builds a label value to be displayed in the combobox for a CodedTerm object.
             * @param codedTerm coded term entity
             * @return label to be display in the combobox
             */
            @Override
            public String getLabel(CodedTerm codedTerm) {
                String s = new String();
                if (codedTerm.getDisplayName() != null && !"".equals(codedTerm.getDisplayName().toString())) {
                    s += codedTerm.getDisplayName().toString();
                }
                if (codedTerm.getCode() != null && !"".equals(codedTerm.getCode().toString()) ) {
                    if (!s.isEmpty()) {
                        s += "  -  ";
                    }
                    s += codedTerm.getCode().toString();
                }
                if (codedTerm.getCodingScheme() != null && !"".equals(codedTerm.getCodingScheme().toString())) {
                    if (!s.isEmpty()) {
                        s += "  -  ";
                    }
                    s += codedTerm.getCodingScheme().toString();
                }
                return s;
            }
        });

        // empty the list of values of the combo box.
        getStore().clear();

        this.setEmptyText("Select a code...");
        this.setToolTip("Display format of the coded term is: \"display name\", \"code\" and \"coding scheme\".");

        // retrieve required predefined coded terms regarding the predefined code type specified.
        List<CodedTerm> l = PredefinedCodesParser.INSTANCE.getCodes(predefinedCodes);

        // set the list of value of the combobox.
        getStore().add(new CodedTerm("", "Custom Value", ""));
        getStore().addAll(l);

        setTriggerAction(ComboBoxCell.TriggerAction.ALL);
        setForceSelection(true);
        setTypeAhead(true);

        editCustomedValueBtn.disable();

        setText("");
        clear();

        bind();
    }

    /**
     * This method binds the UI widgets w/ actions.
     */
    private void bind() {
        this.addSelectionHandler(new SelectionHandler<CodedTerm>() {
            @Override
            public void onSelection(SelectionEvent<CodedTerm> event) {
                blur();
                flush();
                doAutoValidate();
            }
        });
        this.addBeforeSelectionHandler(new BeforeSelectionHandler<CodedTerm>() {
            @Override
            public void onBeforeSelection(BeforeSelectionEvent<CodedTerm> event) {
                if ("Custom Value".equals(event.getItem().getDisplayName().toString())) {
                    final CodedTermPopUpEditor codedTermPopUpEditor = new CodedTermPopUpEditor();

                    codedTermPopUpEditor.getOkButton().addSelectHandler(new SelectEvent.SelectHandler() {
                        @Override
                        public void onSelect(SelectEvent event) {
                            CodedTerm added = codedTermPopUpEditor.getEditor().save();
                            if (!added.isOneFieldEmpty()) {
                                getStore().add(added);
                                select(added);
                                setValue(added);
                                customedValuesIndexes.add(getStore().indexOf(added));
                                redraw(true);
                                editCustomedValueBtn.enable();
                                codedTermPopUpEditor.hide();
                            }
                        }
                    });
                    codedTermPopUpEditor.getCancelButton().addSelectHandler(new SelectEvent.SelectHandler() {
                        @Override
                        public void onSelect(SelectEvent event) {
                            resetSelection();
                        }
                    });
                }
                selectedIndex = getStore().indexOf(event.getItem());
                if (customedValuesIndexes.contains(selectedIndex)) {
                    editCustomedValueBtn.enable();
                } else {
                    editCustomedValueBtn.disable();
                }
            }
        });
        editCustomedValueBtn.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                final CodedTermPopUpEditor codedTermPopUpEditor = new CodedTermPopUpEditor();
                codedTermPopUpEditor.getOkButton().setText("Save changes");
                final CodedTerm currentValue = getCurrentValue();
                codedTermPopUpEditor.getEditor().edit(currentValue);
                codedTermPopUpEditor.getOkButton().addSelectHandler(new SelectEvent.SelectHandler() {
                    @Override
                    public void onSelect(SelectEvent event) {
                        CodedTerm updated = codedTermPopUpEditor.getEditor().save();
                        if (!updated.isOneFieldEmpty()) {
                            customedValuesIndexes.remove(customedValuesIndexes.indexOf(getStore().indexOf(currentValue)));
                            getStore().remove(currentValue);
                            getStore().add(updated);
                            select(updated);
                            setValue(updated);
                            customedValuesIndexes.add(getStore().indexOf(updated));
                            redraw(true);
                            editCustomedValueBtn.enable();
                            codedTermPopUpEditor.hide();
                        }
                    }
                });
            }
        });
    }

    /**
     * This method renders the PredefinedCodedTermComboBox as a Widget (w/ edit button).
     * @return PredefinedCodedTermComboBox as a Widget.
     */
    public Widget getDisplay() {
        SimpleContainer scon=new SimpleContainer();
        HorizontalLayoutContainer hcon = new HorizontalLayoutContainer();
        hcon.add(super.asWidget(), new HorizontalLayoutContainer.HorizontalLayoutData(1, -1, new Margins(0, 2, 0, 0)));
        hcon.add(editCustomedValueBtn, new HorizontalLayoutContainer.HorizontalLayoutData(-1, -1, new Margins(0, 1, 0, 2)));
        scon.setHeight(WIDGET_HEIGHT);
        scon.add(hcon);
        return scon;
    }

    /**
     * this method reset the value selection of the combobox (deselectAll).
     */
    private void resetSelection() {
        setValue(null);
        redraw(true);
    }

    private class CodedTermPopUpEditor extends Dialog {

        private static final int EDITOR_MARGIN = 10;
        private final TextButton cancelButton;
        private final TextButton okButton;
        private final CodedTermEditorWidget editor;

        private static final int POPUP_WIDTH=600;
        private static final int POPUP_HEIGHT=180;
        private static final int BUTTON_WIDTH=80;
        private static final int BUTTON_HEIGHT=30;

        public CodedTermPopUpEditor() {
            super();
            setBodyBorder(false);
            setHeadingText("Custom Coded Term");
            setHideOnButtonClick(true);
            setWidth(POPUP_WIDTH);
            setHeight(POPUP_HEIGHT);

            FramedPanel fpanel = new FramedPanel();
            fpanel.setBorders(false);
            fpanel.setHeaderVisible(false);

            editor = new CodedTermEditorWidget();
            editor.initEditorDriver();
            editor.editNew();
            editor.setAllowBlanks(false, false, false);

            okButton = new TextButton("Add");
            okButton.setWidth(BUTTON_WIDTH);
            okButton.setHeight(BUTTON_HEIGHT);
            cancelButton = new TextButton("Cancel");
            cancelButton.setWidth(BUTTON_WIDTH);
            cancelButton.setHeight(BUTTON_HEIGHT);
            fpanel.addButton(okButton);
            fpanel.addButton(cancelButton);
            fpanel.setButtonAlign(BoxLayoutContainer.BoxLayoutPack.CENTER);

            VerticalLayoutContainer vcon = new VerticalLayoutContainer();
            vcon.add(editor, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(EDITOR_MARGIN)));
            fpanel.add(vcon);

            add(fpanel);

            // delete the default button
            getButtonBar().remove(0);
            setModal(true);
            show();

            getEditor().setFocus();

            bind();
        }

        private void bind() {
            cancelButton.addSelectHandler(new SelectEvent.SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    editor.setEditionMode(EditionMode.NODATA);
                    hide();
                }
            });
        }

        public TextButton getCancelButton() {
            return cancelButton;
        }

        public CodedTermEditorWidget getEditor() {
            return editor;
        }

        public TextButton getOkButton() {
            return okButton;
        }
    }

}
