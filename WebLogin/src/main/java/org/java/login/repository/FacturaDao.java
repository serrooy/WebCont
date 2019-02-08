package org.java.login.repository;

import java.util.Date;
import java.util.List;

import org.java.login.model.Factura;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaDao extends CrudRepository<Factura, Long> {
   
	//Instruccion JPA
    public List<Factura> findByfacturaLike(String factura);
    
    public List<Factura> findByfecIniGreaterThanEqual(Date fecIni);
    
    public List<Factura> findByfecFinLessThanEqual(Date fecFin);
    
    public List<Factura> findByestado(int estado);
    
    public List<Factura> findById(int id);

   
}
