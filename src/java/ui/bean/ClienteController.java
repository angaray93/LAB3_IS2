package ui.bean;

import ui.bean.util.MobilePageController;
import dal.entity.Cliente;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "clienteController")
@ViewScoped
public class ClienteController extends AbstractController<Cliente> {

    @Inject
    private MobilePageController mobilePageController;

    public ClienteController() {
        // Inform the Abstract parent controller of the concrete Cliente Entity
        super(Cliente.class);
    }

    /**
     * Sets the "items" attribute with a collection of Venta entities that are
     * retrieved from Cliente?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Venta page
     */
    public String navigateVentaCollection() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Venta_items", this.getSelected().getVentaCollection());
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/entity/venta/index";
    }

}
