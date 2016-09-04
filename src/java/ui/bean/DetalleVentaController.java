package ui.bean;

import ui.bean.util.MobilePageController;
import dal.entity.DetalleVenta;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "detalleVentaController")
@ViewScoped
public class DetalleVentaController extends AbstractController<DetalleVenta> {

    @Inject
    private ProductoController idProductoController;
    @Inject
    private VentaController idVentaController;
    @Inject
    private MobilePageController mobilePageController;

    public DetalleVentaController() {
        // Inform the Abstract parent controller of the concrete DetalleVenta Entity
        super(DetalleVenta.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idProductoController.setSelected(null);
        idVentaController.setSelected(null);
    }

    /**
     * Sets the "selected" attribute of the Producto controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdProducto(ActionEvent event) {
        if (this.getSelected() != null && idProductoController.getSelected() == null) {
            idProductoController.setSelected(this.getSelected().getIdProducto());
        }
    }

    /**
     * Sets the "selected" attribute of the Venta controller in order to display
     * its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdVenta(ActionEvent event) {
        if (this.getSelected() != null && idVentaController.getSelected() == null) {
            idVentaController.setSelected(this.getSelected().getIdVenta());
        }
    }
}
