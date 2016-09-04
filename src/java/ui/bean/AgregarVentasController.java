/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.bean;

/**
 *
 * @author Ever
 */
import static com.sun.xml.bind.util.CalendarConv.formatter;
import dal.entity.Cliente;
import dal.entity.DetalleVenta;
import dal.entity.Producto;
import dal.entity.Vendedor;
import dal.entity.Venta;
import dal.facade.ClienteFacade;
import dal.facade.DetalleVentaFacade;
import dal.facade.ProductoFacade;
import dal.facade.VendedorFacade;
import dal.facade.VentaFacade;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Ever
 */


@Named("AgregarVentasController")
@SessionScoped
public class AgregarVentasController implements Serializable{
    
    private String NroVenta;
    private String fechaVenta;
    //private Date fechaVentaDate;
    
    private  List<Cliente> clienteList= new ArrayList<Cliente>(); //Combo Clientes
    private  List<Vendedor> vendedorList= new ArrayList<Vendedor>();
    private Cliente idCliente;
    private Vendedor ciVendedor;
    
    private List<Producto> productoList= new ArrayList<Producto>();
    private Producto idProducto;
    private Integer cantidad;
    int c;
    
    private List<DetalleVenta> detalleVentaList= new ArrayList<DetalleVenta>();
    private DetalleVenta idDetalleVenta;
    private Integer subtotal;
    private int montoTotal;
    
    private Venta idVenta;
    
    @EJB
    private ClienteFacade clienteFacade = new ClienteFacade();
    
    @EJB
    private VendedorFacade vendedorFacade = new VendedorFacade();

    @EJB
    private ProductoFacade productoFacade = new ProductoFacade();
    
    @EJB
    private DetalleVentaFacade detalleVentaFacade = new DetalleVentaFacade();
    
    @EJB
    private VentaFacade ventaFacade = new VentaFacade();

    private Connection con=null;
    
    @PostConstruct
    void initialiseSession() {
        con = DataConnect.getConnection();
        this.cargarVista();
    }
    
    public void cargarVista() {
        int nuevaSeq= obtenerNroVenta() + 1;
        this.clienteList= clienteFacade.findAll();
        this.vendedorList= vendedorFacade.findAll();
        this.productoList= productoFacade.findAll();
        
        //this.detalleVentaList= detalleVentaFacade.findAll();
        
        if (nuevaSeq < 10) {
            this.NroVenta = "000" + String.valueOf(nuevaSeq);
        } else if (nuevaSeq < 100) {
            this.NroVenta = "0" + String.valueOf(nuevaSeq);
        } else {
            this.NroVenta = String.valueOf(nuevaSeq);
        }
        
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);
        this.fechaVenta= today;
    }
    
    public int obtenerNroVenta() {
        int ultimoValor= 0;
        try {
            PreparedStatement ps= con.prepareStatement("SELECT last_value FROM ventas_id_seq");
            ResultSet rs= ps.executeQuery();
            while (rs.next()){
                BigDecimal uv= rs.getBigDecimal("last_value");
                ultimoValor= uv.toBigInteger().intValue();
            }
            rs.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al obtener Secuencia -->" + ex.getMessage());
        }
        return ultimoValor;
    }
    
    public void Borrar(DetalleVenta detalle){
        this.detalleVentaList.remove(detalle);
        
    }
    
    public void agregarProducto() {
        
        DetalleVenta odt= new DetalleVenta();
        int tot = 0;
        this.cantidad= new Integer(c);
        odt.setCantidad(cantidad);
        tot=cantidad * idProducto.getPrecioUnitario();
        this.subtotal= new Integer(tot);
        odt.setIdProducto(idProducto);
        odt.setSubTotal(subtotal);
        this.detalleVentaList.add(odt);
        c=1;

    }
    
    public String montoTotal() {
        int total = 0;
 
        for(DetalleVenta detalle : getDetalleVentaList()) {
            total += detalle.getSubTotal() ;
        }
        montoTotal=total;
        return new DecimalFormat("###,###.###").format(total);
    }
    
    public void registrar() throws Exception{
        try{
            idVenta= new Venta();
            Date fechaVentaDate = Calendar.getInstance().getTime();
            
            
            idVenta.setIdCliente(idCliente);
            idVenta.setCiVendedor(ciVendedor);
            idVenta.setFechaVenta(fechaVentaDate);
            idVenta.setTotalVenta(montoTotal);
            //detalleVentaList= new ArrayList<DetalleVenta>();
            RegistrarConnection(idVenta, this.detalleVentaList);
            this.detalleVentaList.clear();
            c=1;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Venta Registrada exitosamente"));
        }catch (Exception e){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error al Registrar venta"));
        }finally{
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    public void RegistrarConnection(Venta venta, List<DetalleVenta> detalle) throws Exception{
        try {
            con.setAutoCommit(false);
            int ultimo= obtenerNroVenta();
            //int ultimoDetalle= obtenerNroVentaDetalle();
            
            java.util.Date f = venta.getFechaVenta();
            java.sql.Date sqlDate = new java.sql.Date(f.getTime());
            
            PreparedStatement ps3 = con.prepareStatement("INSERT INTO venta (fecha_venta, id_cliente, ci_vendedor, total_venta) values(?,?,?,?)");
            ps3.setDate(1, sqlDate);
            ps3.setInt(2, venta.getIdCliente().getIdCliente());
            ps3.setInt(3, venta.getCiVendedor().getCiVendedor());
            //ps3.setInt(4, ultimo);
            ps3.setInt(4, venta.getTotalVenta());
            ps3.executeUpdate();
            ps3.close();
            con.commit();
            
            for(DetalleVenta det : detalle){
                PreparedStatement ps2= con.prepareStatement("INSERT INTO detalle_venta (cantidad, id_producto, id_venta, sub_total) values(?,?,?,?)");
                ps2.setInt(1, det.getCantidad());
                ps2.setInt(2, det.getIdProducto().getIdProducto());
                ps2.setInt(3, ultimo);
                ps2.setInt(4, det.getSubTotal());
                //ps2.setInt(5, ultimoDetalle);
                ps2.executeUpdate();
                ps2.close();
            }
            con.commit();
            }catch (SQLException ex) {
                con.rollback();
                throw ex;
            }    
    }
    
    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    /*public Date getFechaVentaDate() {
        return fechaVentaDate;
    }

    public void setFechaVentaDate(Date fechaVentaDate) {
        this.fechaVentaDate = fechaVentaDate;
    }*/
    
    public Venta getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Venta idVenta) {
        this.idVenta = idVenta;
    }
    
    public String getNroVenta() {
        return NroVenta;
    }

    public void setNroVenta(String NroVenta) {
        this.NroVenta = NroVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public List<Vendedor> getVendedorList() {
        return vendedorList;
    }

    public void setVendedorList(List<Vendedor> vendedorList) {
        this.vendedorList = vendedorList;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Vendedor getCiVendedor() {
        return ciVendedor;
    }

    public void setCiVendedor(Vendedor ciVendedor) {
        this.ciVendedor = ciVendedor;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    public Producto getProducto() {
        return idProducto;
    }

    public void setProducto(Producto producto) {
        this.idProducto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public List<DetalleVenta> getDetalleVentaList() {
        return detalleVentaList;
    }

    public void setDetalleVentaList(ArrayList<DetalleVenta> detalleVentaList) {
        this.detalleVentaList = detalleVentaList;
    }

    public DetalleVenta getIdDetalleVenta() {
        return idDetalleVenta;
    }

    public void setIdDetalleVenta(DetalleVenta idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }
    
}
