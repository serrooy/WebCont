package org.java.login.repository;

import java.util.List;

import org.java.login.model.LogLine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogLineDao extends CrudRepository<LogLine, Long> {
	
	//Instruccion JPA
    public List<LogLine> findByNameLike(String user);
    
   
}
