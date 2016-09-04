package ui.bean;

import ui.bean.util.MobilePageController;
import dal.entity.TipoProducto;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "tipoProductoController")
@ViewScoped
public class TipoProductoController extends AbstractController<TipoProducto> {

    @Inject
    private MobilePageController mobilePageController;

    public TipoProductoController() {
        // Inform the Abstract parent controller of the concrete TipoProducto Entity
        super(TipoProducto.class);
    }

    /**
     * Sets the "items" attribute with a collection of Producto entities that
     * are retrieved from TipoProducto?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Producto page
     */
    public String navigateProductoCollection() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Producto_items", this.getSelected().getProductoCollection());
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/entity/producto/index";
    }

}
