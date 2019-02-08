package org.java.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DETFAC")
public class DetFac {
	
	public DetFac() {}
	
	@Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
	
	@Column(name = "IDUSER", nullable = false)
	private Long idUser;
	
	@Column(name = "IDFAC", nullable = false)
	private Long idFac;
	
	@Column(name = "ESTADO", nullable = false)
	private int estado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdFac() {
		return idFac;
	}

	public void setIdFac(Long idFac) {
		this.idFac = idFac;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}



}
