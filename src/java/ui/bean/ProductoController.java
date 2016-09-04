package ui.bean;

import ui.bean.util.MobilePageController;
import dal.entity.Producto;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "productoController")
@ViewScoped
public class ProductoController extends AbstractController<Producto> {

    @Inject
    private TipoProductoController idTipoTipoProductoController;
    @Inject
    private MobilePageController mobilePageController;

    public ProductoController() {
        // Inform the Abstract parent controller of the concrete Producto Entity
        super(Producto.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idTipoTipoProductoController.setSelected(null);
    }

    /**
     * Sets the "items" attribute with a collection of DetalleVenta entities
     * that are retrieved from Producto?cap_first and returns the navigation
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

    /**
     * Sets the "selected" attribute of the TipoProducto controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdTipoTipoProducto(ActionEvent event) {
        if (this.getSelected() != null && idTipoTipoProductoController.getSelected() == null) {
            idTipoTipoProductoController.setSelected(this.getSelected().getIdTipoTipoProducto());
        }
    }
}
