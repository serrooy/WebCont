package org.java.login.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Factura")
public class Factura {
    public Factura() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
	
	@Column(name = "FACTURA", nullable = false)
	private String factura;
    
	@Column(name = "FECINI", nullable = false)
	private Date fecIni;
    
	@Column(name = "FECFIN", nullable = false)
	private Date fecFin;
	
	@Column(name = "IMPORTE", nullable = false)
	private double importe;
	
	@Column(name = "IMAGEN", nullable = true)
	private String imagen;
	
	@Column(name = "ESTADO", nullable = false)
	private int estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecIni() {
		return fecIni;
	}

	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}
}
