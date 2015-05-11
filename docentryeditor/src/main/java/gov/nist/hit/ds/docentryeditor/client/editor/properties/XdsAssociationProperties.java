package gov.nist.hit.ds.docentryeditor.client.editor.properties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import gov.nist.hit.ds.docentryeditor.shared.model.XdsAssociation;

/**
 * Created by onh2 on 5/11/2015.
 */
public interface XdsAssociationProperties extends PropertyAccess<XdsAssociation> {
    XdsAssociationProperties PROPS = GWT.create(XdsAssociationProperties.class);
    /**
     * Returns the KeyProvider for XdsAssociation. It is consider that
     * XdsAssociation's attribute "id" will be the key of the entity.
     *
     * @return a KeyProvider for XdsAssociation
     */
    @Editor.Path("id")
    ModelKeyProvider<XdsAssociation> key();

    /**
     * Returns ValueProvider for XdsAssociation id. It handles the
     * access to the id attribute of XdsAssociation.
     *
     * @return XdsAssociation ValueProvier for id
     */
    @Editor.Path("id.string")
    ValueProvider<XdsAssociation, String> id();
}
