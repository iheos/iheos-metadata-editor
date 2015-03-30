package gov.nist.hit.ds.docentryeditor.client.widgets;

import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;

/**
 * This class is an extend of ConfirmMessageBox with with a pre-configured message for deletion.
 * @see com.sencha.gxt.widget.core.client.box.ConfirmMessageBox
 */
public class ConfirmDeleteDialog extends ConfirmMessageBox {

	public ConfirmDeleteDialog(String valueToDelete) {
		super("Confirm delete dialog", "Are you sure you want to delete this value: " + valueToDelete);
	}

    public ConfirmDeleteDialog(String boxTitle,String message){
        super(boxTitle,message);

    }

}
