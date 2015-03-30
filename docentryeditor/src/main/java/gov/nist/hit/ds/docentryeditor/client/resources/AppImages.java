package gov.nist.hit.ds.docentryeditor.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Application image resources.
 */
public interface AppImages extends ClientBundle {
    AppImages INSTANCE = GWT.create(AppImages.class);

    @Source("baseResources/add.gif")
    ImageResource add();

    @Source("baseResources/clear.gif")
    ImageResource clear();

    @Source("baseResources/delete.gif")
    ImageResource delete();

    @Source("baseResources/checkbox.png")
    ImageResource checkbox();

    //    @Source("baseResources/help.ico.gif")
    @Source("baseResources/comment.png")
    ImageResource help();

    @Source("baseResources/file.png")
    ImageResource file();

    @Source("baseResources/save-disk.png")
    ImageResource save();

    @Source("baseResources/save-2.png")
    ImageResource saveBW();

    @Source("baseResources/Warning-128.png")
    ImageResource warning();

    @Source("baseResources/home.png")
    ImageResource home();

    @Source("baseResources/folder-32.png")
    ImageResource folder();

    @Source("baseResources/file-plus.png")
    ImageResource filePlus();

    @Source("baseResources/prefilled.png")
    ImageResource preFilled();

    @Source("baseResources/load-file.png")
    ImageResource loadFile();

    @Source("baseResources/load-file-16.png")
    ImageResource loadFile12px();

    @Source("baseResources/refresh.gif")
    ImageResource refresh();

    @Source("baseResources/back-16.png")
    ImageResource back();

}
