package org.java.login.model;

public class DetUser {

	private String name;
	private int estado;

	public DetUser() {
	}

	public DetUser(String name, int estado) {
		this.name = name;
		this.estado = estado;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
}
