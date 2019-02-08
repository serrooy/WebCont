package org.java.login.repository;


import org.java.login.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
 
	
	  public User findByUserLike(String user);
	  
	  public User findByUserAndPassLike(String user,String pass);
}
