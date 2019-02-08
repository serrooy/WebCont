package org.java.login.service;

import java.util.List;

import org.java.login.model.LogLine;
import org.java.login.model.User;
import org.java.login.repository.LogLineDao;
import org.java.login.repository.UserDao;
import org.java.login.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Admin {
	@Autowired
	LogLineDao logLineDao;

	@Autowired
	UserDao userDao;

	/**
	 * Constructor
	 */
	public Admin() {
		super();
	}

	/**
	 * Obtiene todos los usuarios
	 * 
	 * @param requestParams
	 * @return
	 */
	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	/**
	 * Registra un nuevo usuario
	 * 
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */
	public boolean register(String user, String pass) {
		return userDao.save(new User(user, pass)) != null;

	}

	/**
	 * Actualiza la password de un usuario
	 * 
	 * @param user
	 * @param pass
	 * @return
	 */
	public boolean update(String user, String pass) {

		// Buscamos el usuario a actualizar
		User usuario = userDao.findByUserLike(user);

		if (usuario == null)
			return false;

		// Modificamos su pass con la nueva
		usuario.setPass(pass);

		// Actualizamos el usuario
		return userDao.save(usuario) != null;
	}

	/**
	 * Elimina un usuario
	 * 
	 * @param user
	 * @return
	 */
	public boolean delete(String user) {

		// Buscamos el usuario a borrar
		User usuario = userDao.findByUserLike(user);
		if (usuario == null)
			return false;

		// Eliminamos todos los registros del usuario en el log
		List<LogLine> logLineList = logLineDao.findByNameLike(user);
		for (LogLine line : logLineList) {
			logLineDao.delete(line);
		}
		// Borramos el usuario
		userDao.delete(usuario);
		return true;

	}

	/**
	 * Limpia la tabal que le pasemos por parametros
	 * 
	 * @param tableName
	 * @return
	 */
	public boolean delAllRows(String tableName) {

		if (tableName.equalsIgnoreCase(Constantes.DBUSER)) {
			userDao.deleteAll();
		} else if (tableName.equalsIgnoreCase(Constantes.DBLOGUSER)) {
			logLineDao.deleteAll();
		}

		return true;
	}

}
