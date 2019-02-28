package org.java.login.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.java.login.model.DetUser;
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
		} else if (requestParams.get("cancelar") != null && requestParams.get("cancelar").equals("cancel")) {
			model.addAttribute("logUser", true);
			model.addAttribute("ventana", "2");
			// add Factura
		} else if (requestParams.get("addTipo") != null) {
			if (requestParams.get("continuar").equals("S")) {
				model.addAttribute("logUser", true);
				model.addAttribute("ventana", "0.1");
				model.addAttribute("listaUser", mainAdmin.getAllUser());
				model.addAttribute("addTipo", requestParams.get("addTipo"));
				model.addAttribute("addFecIni", requestParams.get("addFecIni"));
				model.addAttribute("addFecFin", requestParams.get("addFecFin"));
				model.addAttribute("addImporte", requestParams.get("addImporte"));
				model.addAttribute("addEstado", requestParams.get("addEstado"));
			} else if (requestParams.get("continuar").equals("N")) {
				try {
					String fac = requestParams.get("addTipo");
					Date fecIni = new SimpleDateFormat("yyyy-MM-dd").parse((String) requestParams.get("addFecIni"));
					Date fecFin = new SimpleDateFormat("yyyy-MM-dd").parse((String) requestParams.get("addFecFin"));
					double importe = Double.parseDouble(requestParams.get("addImporte"));
					int estado = requestParams.get("addEstado").equals("S") ? 1 : 0;
					List<String> userList = new ArrayList<>();
					for (String param : requestParams.values()) {
						if (param.startsWith("U@@")) {
							userList.add(param.substring(3));
						}
					}
					if (userList.isEmpty()) {
						throw new Exception("Ningun usuario declarado");
					}

					model = creaNuevaFactura(model, fac, fecIni, fecFin, importe, estado, userList);
					model.addAttribute("ventana", "0");
				} catch (Exception e) {
					model.addAttribute("logUser", true);
					model.addAttribute("respuesta", e.getMessage());
					model.addAttribute("ventana", "0");
					model.addAttribute("addTipo", requestParams.get("addTipo"));
					model.addAttribute("addFecIni", requestParams.get("addFecIni"));
					model.addAttribute("addFecFin", requestParams.get("addFecFin"));
					model.addAttribute("addImporte", requestParams.get("addImporte"));
					model.addAttribute("addEstado", requestParams.get("addEstado"));

				}
			} else {
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
				String idFac = null;
				if (requestParams.get("inputDetCon") != null) {
					idFac = requestParams.get("uidFac");
					if (requestParams.get("inputDetCon").equals("D")) {
						delUserFac(requestParams.get("usuario"), idFac);
					} else if (requestParams.get("inputDetCon").equals("P")) {
						payUserFac(requestParams.get("usuario"), idFac);
					}

					model.addAttribute("uidFac", requestParams.get("uidFac"));
				} else {
					idFac = requestParams.get("idFac");
					model.addAttribute("uidFac", requestParams.get("idFac"));
				}

				// Controlamos el estado de la factura
				if (controlPagados(idFac)) {
					if (requestParams.get("inputDetCon") == null || (!requestParams.get("inputDetCon").equals("D")
							&& !requestParams.get("inputDetCon").equals("P")))
						model.addAttribute("onlyRead", 1);// true
				} else {
					model.addAttribute("onlyRead", 0); // false
				}

				model.addAttribute("ufecIni", requestParams.get("ufecIni"));
				model.addAttribute("ufecFin", requestParams.get("ufecFin"));
				model.addAttribute("utipoFac", requestParams.get("utipoFac"));
				model.addAttribute("uestado", requestParams.get("uestado"));
				Factura fac = getFactura(idFac);
				model.addAttribute("factura", fac);
				model.addAttribute("listaUsuarios", getDetFactura(fac));

				return "/detalles.jsp";
				// volver de detalles
			} else if (accion.equals("U")) {
				if (requestParams.get("inputDetCon") != null && requestParams.get("inputDetCon").equals("D")) {
					eliminar(requestParams.get("uidFac"));
				}

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
					requestParams.put("conEstado", "T");

				}

				preFactura(model, requestParams);

			}
		}
		return Constantes.INDEX;
	}

	private boolean controlPagados(String idFac) {
		return mainService.contorlPagosTotal(Long.parseLong(idFac));

	}

	/**
	 * 
	 * @param idFac
	 * @return
	 */
	private Factura getFactura(String idFac) {
		return mainService.getFacturaById(Long.parseLong(idFac));
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
		if (id.startsWith("D@@")) {
			mainService.deleteFactura(id);
		} else {
			mainService.deleteFactura(Long.parseLong(id));
		}
	}

	/**
	 * 
	 * @param model
	 * @param fac
	 * @param fecIni
	 * @param fecFin
	 * @param importe
	 * @param estado
	 * @param userList
	 * @return
	 * @throws Exception
	 */
	private Model creaNuevaFactura(Model model, String fac, Date fecIni, Date fecFin, double importe, int estado,
			List<String> userList) throws Exception {
		model.addAttribute("logUser", true);
		if (mainService.validarFechas(fecIni, fecFin)) {// TODO
			Factura fact = mainService.crearFactura(fac, fecIni, fecFin, importe, estado);
			mainService.crearDetFac(fact, userList, estado);
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

	/**
	 * 
	 * @param fac
	 * @return
	 */
	private List<DetUser> getDetFactura(Factura fac) {
		return mainService.getDetFactura(fac);
	}

	/**
	 * 
	 * @param fac
	 * @return
	 */
	private boolean payUserFac(String userName, String idFac) {
		return mainService.pagarFacUser(userName, Long.parseLong(idFac));
	}

	private boolean delUserFac(String userName, String idFac) {
		return mainService.eliminarFacUser(userName, Long.parseLong(idFac));
	}
}
