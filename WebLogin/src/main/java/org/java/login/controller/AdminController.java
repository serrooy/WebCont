package org.java.login.controller;

import java.util.List;
import java.util.Map;

import org.java.login.model.User;
import org.java.login.repository.LogLineDao;
import org.java.login.repository.UserDao;
import org.java.login.service.Admin;
import org.java.login.service.Web;
import org.java.login.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

	@Autowired
	LogLineDao logLineDao;

	@Autowired
	UserDao userDao;

	@Autowired
	Admin admin;
	
	@Autowired
	Web web;


	/**
	 * Consulta mediante una llamada JPA todos los usuarios de bd
	 * 
	 * @param model
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllUser", method = { RequestMethod.GET })
	public List<User> getAllUser(@RequestParam Map<String, String> requestParams) {
		return admin.getAllUser();
	}
	
	/**
	 * Consulta mediante una llamada JPA el log de un usuario
	 * 
	 * @param model
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/userlog", method = { RequestMethod.GET })
	public List<String> getUserLog(@RequestParam Map<String, String> requestParams) throws Exception {
		List<String> logUser = null;
		if (requestParams.get(Constantes.USERNAME) != null) {
			
			logUser=web.consultaLog(requestParams.get(Constantes.USERNAME));

		} else {
			throw new Exception(Constantes.LOS_PARAMETROS_INTRODUCIDOS_NO_SON_LOS_ESPERADOS);
		}
		return logUser;
	}

	/**
	 * Inserta mediante JPA un nuevo usuario
	 * 
	 * @param model
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */

	@ResponseBody
	@RequestMapping(value = "/register", method = { RequestMethod.POST })
	public String register(@RequestParam Map<String, String> requestParams) throws Exception {
		if (requestParams.get(Constantes.USERNAME) != null && requestParams.get(Constantes.PASS) != null) {

			String user = requestParams.get(Constantes.USERNAME);
			String pass = requestParams.get(Constantes.PASS);

			if(!admin.register(user, pass)) {
				throw new Exception("EL usuario no se pudo crear correctamente");
			}

		} else {
			throw new Exception(Constantes.LOS_PARAMETROS_INTRODUCIDOS_NO_SON_LOS_ESPERADOS);
		}
		return "El usuario " + requestParams.get(Constantes.USERNAME) + " ha sido creado correctamente";
	}

	/**
	 * Actualiza mediante JPA la contraseña de un usuario
	 * 
	 * @param model
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */

	@ResponseBody
	@RequestMapping(value = "/updatePass", method = { RequestMethod.PUT })
	public String upadte(@RequestParam Map<String, String> requestParams) throws Exception {
		if (requestParams.get(Constantes.USERNAME) != null && requestParams.get(Constantes.PASS) != null) {

			String user = requestParams.get(Constantes.USERNAME);
			String pass = requestParams.get(Constantes.PASS);

			if(!admin.update(user, pass)) {
				throw new Exception("EL usuario no se pudo actualizar correctamente");
			}

		} else {
			throw new Exception(Constantes.LOS_PARAMETROS_INTRODUCIDOS_NO_SON_LOS_ESPERADOS);
		}
		return "La contraseña del usuario " + requestParams.get(Constantes.USERNAME)
				+ " Ha sido actualizada correctamente";
	}

	/**
	 * Elimina un usuario mediante JPA
	 * 
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delUser", method = { RequestMethod.DELETE })
	public String delete(@RequestParam Map<String, String> requestParams) throws Exception {
		if (requestParams.get(Constantes.USERNAME) != null) {
			String user = requestParams.get(Constantes.USERNAME);
			if(!admin.delete(user)) {
				throw new Exception("EL usuario no se pudo eliminar correctamente");
			}

		} else {
			throw new Exception(Constantes.LOS_PARAMETROS_INTRODUCIDOS_NO_SON_LOS_ESPERADOS);
		}
		return "El usuario " + requestParams.get(Constantes.USERNAME) + " ha sido eliminado correctamente";
	}

	
	/**
	 * Borra todos los registros de la tabla indicada mediante JPA
	 * 
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/cleanTable", method = { RequestMethod.DELETE })
	public String delAllRows(@RequestParam Map<String, String> requestParams) throws Exception {
		if (requestParams.get(Constantes.TABLE_NAME) != null) {
			admin.delAllRows(requestParams.get(Constantes.TABLE_NAME));
		} else {
			throw new Exception(Constantes.LOS_PARAMETROS_INTRODUCIDOS_NO_SON_LOS_ESPERADOS);
		}
		return "Todos los registros de la tabla " + requestParams.get(Constantes.TABLE_NAME) + " han sido eliminados";
	}
}
