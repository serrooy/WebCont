package org.java.login.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.java.login.model.DetFac;
import org.java.login.model.Factura;
import org.java.login.model.LogLine;
import org.java.login.model.User;
import org.java.login.repository.DetFacDao;
import org.java.login.repository.FacturaDao;
import org.java.login.repository.LogLineDao;
import org.java.login.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Web {

	@Autowired
	LogLineDao logLineDao;

	@Autowired
	FacturaDao facturaDao;

	@Autowired
	UserDao userDao;

	@Autowired
	DetFacDao detFacDao;

	/**
	 * Constructor
	 */
	public Web() {
		super();
	}

	/**
	 * Consulta los logs de un usuario y los formatea
	 * 
	 * @param logUsu
	 * @return
	 */
	public List<String> consultaLog(String logUsu) {
		List<LogLine> listaLog = logLineDao.findByNameLike(logUsu);
		List<String> listaSalida = new ArrayList<>();
		for (LogLine line : listaLog) {
			String rawFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(line.getFechaLog());
			String[] arrFecha = rawFecha.split(" ");

			listaSalida.add("El usuario " + line.getName() + " se conecto el [" + arrFecha[0] + "] a las ["
					+ arrFecha[1] + "]");
		}
		return listaSalida;

	}

	/**
	 * Valida a un usuario
	 * 
	 * @param user
	 * @param pass
	 * @return
	 */

	public boolean validarUser(String user, String pass) {
		if (userDao.findByUserAndPassLike(user, pass) != null) {
			LogLine line = new LogLine();
			line.setName(user);
			line.setFechaLog(new Timestamp(new Date().getTime()));
			logLineDao.save(line);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param fecIni
	 * @param fecFin
	 * @return
	 */
	public boolean validarFechas(Date fecIni, Date fecFin) {
		// TODO validar fechas
		return true;
	}

	/**
	 * 
	 * @param fac
	 * @param fecIni
	 * @param fecFin
	 * @param importe
	 * @param estado
	 */
	public Factura crearFactura(String fac, Date fecIni, Date fecFin, double importe, int estado) {
		Factura f = new Factura();
		f.setFactura(fac);
		f.setEstado(estado);
		f.setFecFin(fecFin);
		f.setFecIni(fecIni);
		f.setImporte(importe);
		return facturaDao.save(f);

	}

	/**
	 * 
	 * @param fac
	 * @param estado
	 * @param fecIni
	 * @param fecFin
	 * @return
	 */
	public List<Factura> buscarFactura(String fac, Integer estado, Date fecIni, Date fecFin) {
		List<Factura> out = new ArrayList<>();

		// Busqueda factura
		List<Factura> outFac = new ArrayList<>();
		if (fac.equals("T")) {
			outFac = (List<Factura>) facturaDao.findAll();
		} else {
			outFac = facturaDao.findByfacturaLike(fac);
		}

		// Busqueda fec ini
		List<Factura> outFecIni = new ArrayList<>();
		if (fecIni != null) {
			outFecIni = facturaDao.findByfecIniGreaterThanEqual(fecIni);
		}

		// Busqueda fec fin
		List<Factura> outFecFin = new ArrayList<>();
		if (fecFin != null) {
			outFecFin = facturaDao.findByfecFinLessThanEqual(fecFin);
		}

		// Busqueda Estado
		List<Factura> outEstado = new ArrayList<>();
		if (estado != null) {
			outEstado = facturaDao.findByestado(estado);
		}

		for (Factura res : outFac) {
			boolean all1 = false;
			boolean all2 = false;
			boolean all3 = false;
			Long id = res.getId();

			// Estado
			if (estado != null) {
				all1 = find(id, outEstado);
			} else {
				all1 = true;
			}

			// fec ini
			if (fecIni != null) {
				all2 = find(id, outFecIni);
			} else {
				all2 = true;
			}

			// fec Fin
			if (fecFin != null) {
				all3 = find(id, outFecFin);
			} else {
				all3 = true;
			}

			if (all1 && all2 && all3) {
				out.add(res);
			}
		}
		System.out.println("");
		return out;
	}

	/**
	 * 
	 * @param id
	 * @param lista
	 * @return
	 */
	private boolean find(Long id, List<Factura> lista) {

		for (Factura f : lista) {
			if (f.getId() == id) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * @param id
	 */
	public void deleteFactura(String id) {
		facturaDao.deleteById(Long.parseLong(id.substring(3)));
	}

	/**
	 * 
	 * @param id
	 */
	public void pagar(String id) {
		Long index = Long.parseLong(id.substring(3));
		Factura factura = facturaDao.findById(index).get();
		if (factura != null) {
			factura.setEstado(1);
			facturaDao.save(factura);
		}
	}

	public void crearDetFac(Factura fac, List<String> listUser) {
		for (String user : listUser) {
			DetFac detalle = new DetFac();
			User usuario = userDao.findByUserLike(user);
			if (usuario != null) {
				detalle.setIdFac(fac.getId());
				detalle.setIdUser(usuario.getId());
				detalle.setEstado(0);
				detFacDao.save(detalle);
			}
		}
	}

}
