package ui.bean;

import ui.bean.util.MobilePageController;
import dal.entity.Usuario;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "usuarioController")
@ViewScoped
public class UsuarioController extends AbstractController<Usuario> {

    @Inject
    private RolController codigoRolUsuarioController;
    @Inject
    private VendedorController ciVendedorUsuarioController;
    @Inject
    private MobilePageController mobilePageController;

    public UsuarioController() {
        // Inform the Abstract parent controller of the concrete Usuario Entity
        super(Usuario.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        codigoRolUsuarioController.setSelected(null);
        ciVendedorUsuarioController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Rol controller in order to display
     * its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareCodigoRolUsuario(ActionEvent event) {
        if (this.getSelected() != null && codigoRolUsuarioController.getSelected() == null) {
            codigoRolUsuarioController.setSelected(this.getSelected().getCodigoRolUsuario());
        }
    }

    /**
     * Sets the "selected" attribute of the Vendedor controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareCiVendedorUsuario(ActionEvent event) {
        if (this.getSelected() != null && ciVendedorUsuarioController.getSelected() == null) {
            ciVendedorUsuarioController.setSelected(this.getSelected().getCiVendedorUsuario());
        }
    }
}
