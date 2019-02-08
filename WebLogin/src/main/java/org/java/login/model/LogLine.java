package org.java.login.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//@Data genera en ejecucion todos los metodos getter y setter ademas del constructor y el tostring

@Entity
@Table(name = "DBLOGUSER")
public class LogLine {
	
	
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;
	
    @Column(name = "NAME", length = 64, nullable = false)
    private String name;
 
    @Column(name = "FECHALOG", nullable = false)
    private Timestamp fechaLog;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFechaLog() {
		return fechaLog;
	}

	public void setFechaLog(Timestamp fechaLog) {
		this.fechaLog = fechaLog;
	}

	@Override
	public String toString() {
		return "LogLine [name=" + name + ", fechaLog=" + fechaLog + "]";
	}

}
