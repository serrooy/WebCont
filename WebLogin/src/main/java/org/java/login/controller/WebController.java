package org.java.login.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.java.login.model.Factura;
import org.java.login.service.Admin;
import org.java.login.service.Web;
import org.java.login.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

	public static final String LOG_USU = "logUsu";

	@Autowired
	Web mainService;

	@Autowired
	Admin mainAdmin;

	/**
	 * Carga la clase por defecto, el index.
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String init() {

		return Constantes.INDEX;
	}

	/**
	 * Metodo que valida el usuario y la contraseña
	 * 
	 * @param model
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method = { RequestMethod.POST })
	public String accionV(Model model, @RequestParam Map<String, String> requestParams) throws Exception {

		// Login
		if (requestParams.get(Constantes.USERNAME) != null && requestParams.get(Constantes.PASS) != null) {

			String user = requestParams.get(Constantes.USERNAME);
			String pass = requestParams.get(Constantes.PASS);
			model = valUsu(user, pass, model);

			// Consulta log usuario
		} else if (requestParams.get(LOG_USU) != null) {
			String logUsu = requestParams.get(LOG_USU);
			model = getLogUsu(logUsu, model);

			// add Factura
		} else if (requestParams.get("addTipo") != null) {
			if (requestParams.get("continuar").equals("S")) {
				model.addAttribute("logUser", true);
				model.addAttribute("ventana", "0.1");
				model.addAttribute("listaUser",mainAdmin.getAllUser());
				model.addAttribute("addTipo", requestParams.get("addTipo"));
				model.addAttribute("addFecIni", requestParams.get("addFecIni"));
				model.addAttribute("addFecFin", requestParams.get("addFecFin"));
				model.addAttribute("addImporte", requestParams.get("addImporte"));
				model.addAttribute("addEstado", requestParams.get("addEstado"));
			} else if(requestParams.get("continuar").equals("N")){
				try {
					String fac = requestParams.get("addTipo");
					Date fecIni = new SimpleDateFormat("yyyy-MM-dd").parse((String) requestParams.get("addFecIni"));
					Date fecFin = new SimpleDateFormat("yyyy-MM-dd").parse((String) requestParams.get("addFecFin"));
					double importe = Double.parseDouble(requestParams.get("addImporte"));
					int estado = requestParams.get("addEstado").equals("S") ? 1 : 0;
					model = creaNuevaFactura(model, fac, fecIni, fecFin, importe, estado);
					model.addAttribute("ventana", "0");
				} catch (Exception e) {
					e.printStackTrace();
					model.addAttribute("ventana", "1");
					model.addAttribute("addTipo", requestParams.get("addTipo"));
					model.addAttribute("addFecIni", requestParams.get("addFecIni"));
					model.addAttribute("addFecFin", requestParams.get("addFecFin"));
					model.addAttribute("addImporte", requestParams.get("addImporte"));
					model.addAttribute("addEstado", requestParams.get("addEstado"));

				}
			}else {
				model.addAttribute("logUser", true);
			}
			// consulta factura
		} else if (requestParams.get("conTipo") != null || requestParams.get("conFecIni") != null
				|| requestParams.get("conFecFin") != null || requestParams.get("conEstado") != null) {
			model = preFactura(model, requestParams);
			// Modificaciones y detalles
		} else if (requestParams.get("inputCon") != null) {
			String accion = (String) requestParams.get("inputCon");
			model.addAttribute("logUser", true);
			if (accion.equals("M")) {
				try {

					for (String id : requestParams.values()) {
						if (id.startsWith("D@@")) {
							eliminar(id);
						}
						if (id.startsWith("P@@")) {
							pagar(id);
						}

					}
					model.addAttribute("respuesta", "Los registro han sido modificado correctamente");
					model.addAttribute("ventana", "0");
				} catch (Exception e) {
					model.addAttribute("ventana", "1");
					model.addAttribute("respuesta", "Ha ocurrido un error, no se ha podido realizar la accion");
				}
				// Ir a detalles
			} else if (accion.equals("C")) {
				model.addAttribute("logUser", true);
				model.addAttribute("ufecIni", requestParams.get("ufecIni"));
				model.addAttribute("ufecFin", requestParams.get("ufecFin"));
				model.addAttribute("utipoFac", requestParams.get("utipoFac"));
				model.addAttribute("uestado", requestParams.get("uestado"));

				return "/detalles.jsp";
				// volver de detalles
			} else if (accion.equals("U")) {
				if (requestParams.get("utipoFac") != null) {
					requestParams.put("conTipo", requestParams.get("utipoFac"));

				}
				if (requestParams.get("ufecIni") != null) {
					requestParams.put("conFecIni", requestParams.get("ufecIni"));

				}
				if (requestParams.get("ufecFin") != null) {
					requestParams.put("conFecFin", requestParams.get("ufecFin"));

				}
				if (requestParams.get("uestado") != null) {
					requestParams.put("conEstado", requestParams.get("uestado"));

				}

				preFactura(model, requestParams);

			}

		}

		return Constantes.INDEX;
	}

	/**
	 * 
	 * @param model
	 * @param requestParams
	 * @return
	 * @throws ParseException
	 */
	private Model preFactura(Model model, Map<String, String> requestParams) throws ParseException {
		String fac = requestParams.get("conTipo");
		model.addAttribute("conTipo", requestParams.get("conTipo"));
		Date fecIni = null;
		if (requestParams.get("conFecIni") != null && !requestParams.get("conFecIni").isEmpty()) {
			model.addAttribute("conFecIni", requestParams.get("conFecIni"));
			fecIni = new SimpleDateFormat("yyyy-MM-dd").parse((String) requestParams.get("conFecIni"));

		}
		Date fecFin = null;
		if (requestParams.get("conFecFin") != null && !requestParams.get("conFecFin").isEmpty()) {
			model.addAttribute("conFecFin", requestParams.get("conFecFin"));
			fecFin = new SimpleDateFormat("yyyy-MM-dd").parse((String) requestParams.get("conFecFin"));
		}
		Integer estado = null;
		if (!requestParams.get("conEstado").equalsIgnoreCase("T")) {
			model.addAttribute("conEstado", requestParams.get("conEstado"));
			if (requestParams.get("conEstado").equalsIgnoreCase("S"))
				estado = 1;
			else
				estado = 0;
		}
		model = buscarFactura(model, fac, estado, fecIni, fecFin);
		model.addAttribute("ventana", "2");
		return model;
	}

	/**
	 * 
	 * @param model
	 * @param fac
	 * @param estado
	 * @param fecIni
	 * @param fecFin
	 * @return
	 */
	private Model buscarFactura(Model model, String fac, Integer estado, Date fecIni, Date fecFin) {
		model.addAttribute("logUser", true);
		List<Factura> out = mainService.buscarFactura(fac, estado, fecIni, fecFin);
		model.addAttribute("listaResultados", out);
		return model;
	}

	/**
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private void eliminar(String id) throws Exception {
		mainService.deleteFactura(id);
	}

	/**
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private void pagar(String id) throws Exception {
		mainService.pagar(id);
	}

	/**
	 * 
	 * @param model
	 * @param fac
	 * @param fecIni
	 * @param fecFin
	 * @param importe
	 * @param estado
	 * @return
	 * @throws Exception
	 */
	private Model creaNuevaFactura(Model model, String fac, Date fecIni, Date fecFin, double importe, int estado)
			throws Exception {
		model.addAttribute("logUser", true);
		if (mainService.validarFechas(fecIni, fecFin)) {
			mainService.crearFactura(fac, fecIni, fecFin, importe, estado);
			model.addAttribute("respuesta", "El registro ha sido creado correctamente");
		} else {
			throw new Exception();
		}

		return model;
	}

	/**
	 * 
	 * @param logUsu
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	private Model getLogUsu(String logUsu, Model model) throws SQLException {
		List<String> listaLog = mainService.consultaLog(logUsu);
		model.addAttribute(Constantes.LISTA_LOG, listaLog);
		model.addAttribute(Constantes.USUARIO_VALIDO, logUsu);
		model.addAttribute(Constantes.FLAG_LOG, "S");
		model.addAttribute(Constantes.VAROUT, true);
		return model;
	}

	/**
	 * 
	 * @param user
	 * @param pass
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	private Model valUsu(String user, String pass, Model model) throws SQLException {
		boolean result = mainService.validarUser(user, pass);

		if (result) {
			model.addAttribute("logUser", true);
		}

		return model;
	}

}
