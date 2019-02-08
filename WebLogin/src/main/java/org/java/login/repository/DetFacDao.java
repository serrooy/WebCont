package org.java.login.repository;

import java.util.List;

import org.java.login.model.DetFac;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetFacDao extends CrudRepository<DetFac, Long> {
   
    public List<DetFac> findByIdFac(int id);
    
    public List<DetFac> findByIdUser(int id);

   
}
