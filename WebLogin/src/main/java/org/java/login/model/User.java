package org.java.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DBUSER")
public class User {
	
	public User() {}
	
	public User(String name, String pass) {
		super();
		this.user = name;
		this.pass = pass;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER", nullable = false)
	private String user;

	@Column(name = "PASS", nullable = false)
	private String pass;

	public String getName() {
		return user;
	}

	public void setName(String name) {
		this.user = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
