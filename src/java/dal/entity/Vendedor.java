/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ever
 */
@Entity
@Table(name = "vendedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vendedor.findAll", query = "SELECT v FROM Vendedor v"),
    @NamedQuery(name = "Vendedor.findByCiVendedor", query = "SELECT v FROM Vendedor v WHERE v.ciVendedor = :ciVendedor"),
    @NamedQuery(name = "Vendedor.findByNomVendedor", query = "SELECT v FROM Vendedor v WHERE v.nomVendedor = :nomVendedor"),
    @NamedQuery(name = "Vendedor.findByApeVendedor", query = "SELECT v FROM Vendedor v WHERE v.apeVendedor = :apeVendedor")})
public class Vendedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ci_vendedor")
    private Integer ciVendedor;
    @Size(max = 20)
    @Column(name = "nom_vendedor")
    private String nomVendedor;
    @Size(max = 20)
    @Column(name = "ape_vendedor")
    private String apeVendedor;
    @OneToMany(mappedBy = "ciVendedor")
    private Collection<Venta> ventaCollection;
    @OneToMany(mappedBy = "ciVendedorUsuario")
    private Collection<Usuario> usuarioCollection;

    public Vendedor() {
    }

    public Vendedor(Integer ciVendedor) {
        this.ciVendedor = ciVendedor;
    }

    public Integer getCiVendedor() {
        return ciVendedor;
    }

    public void setCiVendedor(Integer ciVendedor) {
        this.ciVendedor = ciVendedor;
    }

    public String getNomVendedor() {
        return nomVendedor;
    }

    public void setNomVendedor(String nomVendedor) {
        this.nomVendedor = nomVendedor;
    }

    public String getApeVendedor() {
        return apeVendedor;
    }

    public void setApeVendedor(String apeVendedor) {
        this.apeVendedor = apeVendedor;
    }

    @XmlTransient
    public Collection<Venta> getVentaCollection() {
        return ventaCollection;
    }

    public void setVentaCollection(Collection<Venta> ventaCollection) {
        this.ventaCollection = ventaCollection;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciVendedor != null ? ciVendedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendedor)) {
            return false;
        }
        Vendedor other = (Vendedor) object;
        if ((this.ciVendedor == null && other.ciVendedor != null) || (this.ciVendedor != null && !this.ciVendedor.equals(other.ciVendedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dal.facade.Vendedor[ ciVendedor=" + ciVendedor + " ]";
    }
    
}
