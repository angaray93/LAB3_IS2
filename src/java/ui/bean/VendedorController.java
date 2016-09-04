package ui.bean;

import ui.bean.util.MobilePageController;
import dal.entity.Vendedor;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "vendedorController")
@ViewScoped
public class VendedorController extends AbstractController<Vendedor> {

    @Inject
    private MobilePageController mobilePageController;

    public VendedorController() {
        // Inform the Abstract parent controller of the concrete Vendedor Entity
        super(Vendedor.class);
    }

    /**
     * Sets the "items" attribute with a collection of Venta entities that are
     * retrieved from Vendedor?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Venta page
     */
    public String navigateVentaCollection() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Venta_items", this.getSelected().getVentaCollection());
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/entity/venta/index";
    }

    /**
     * Sets the "items" attribute with a collection of Usuario entities that are
     * retrieved from Vendedor?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Usuario page
     */
    public String navigateUsuarioCollection() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Usuario_items", this.getSelected().getUsuarioCollection());
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/entity/usuario/index";
    }

}
