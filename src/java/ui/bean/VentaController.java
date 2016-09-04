package ui.bean;

import ui.bean.util.MobilePageController;
import dal.entity.Venta;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "ventaController")
@ViewScoped
public class VentaController extends AbstractController<Venta> {

    @Inject
    private ClienteController idClienteController;
    @Inject
    private VendedorController ciVendedorController;
    @Inject
    private MobilePageController mobilePageController;

    public VentaController() {
        // Inform the Abstract parent controller of the concrete Venta Entity
        super(Venta.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idClienteController.setSelected(null);
        ciVendedorController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Cliente controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdCliente(ActionEvent event) {
        if (this.getSelected() != null && idClienteController.getSelected() == null) {
            idClienteController.setSelected(this.getSelected().getIdCliente());
        }
    }

    /**
     * Sets the "selected" attribute of the Vendedor controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareCiVendedor(ActionEvent event) {
        if (this.getSelected() != null && ciVendedorController.getSelected() == null) {
            ciVendedorController.setSelected(this.getSelected().getCiVendedor());
        }
    }

    /**
     * Sets the "items" attribute with a collection of DetalleVenta entities
     * that are retrieved from Venta?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for DetalleVenta page
     */
    public String navigateDetalleVentaCollection() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("DetalleVenta_items", this.getSelected().getDetalleVentaCollection());
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/entity/detalleVenta/index";
    }

}
