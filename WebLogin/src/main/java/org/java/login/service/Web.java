package org.java.login.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.java.login.model.DetFac;
import org.java.login.model.DetUser;
import org.java.login.model.Factura;
import org.java.login.model.LogLine;
import org.java.login.model.User;
import org.java.login.repository.DetFacDao;
import org.java.login.repository.FacturaDao;
import org.java.login.repository.LogLineDao;
import org.java.login.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ListFactoryBean;
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
	public void deleteFactura(Long id) {
		facturaDao.deleteById(id);
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

	/**
	 * 
	 * @param fac
	 * @param listUser
	 * @param estado 
	 */
	public void crearDetFac(Factura fac, List<String> listUser, int estado) {
		for (String user : listUser) {
			DetFac detalle = new DetFac();
			User usuario = userDao.findByUserLike(user);
			if (usuario != null) {
				detalle.setIdFac(fac.getId());
				detalle.setIdUser(usuario.getId());
				detalle.setEstado(estado);
				detFacDao.save(detalle);
			}
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Factura getFacturaById(Long id) {
		return facturaDao.findById(id).get();
	}

	/**
	 * 
	 * @param fac
	 * @return
	 */
	public List<DetUser> getDetFactura(Factura fac) {
		List<DetUser> out = new ArrayList<>();
		List<DetFac> usuarios = detFacDao.findByIdFac(fac.getId());
		for (DetFac u : usuarios) {
			User user = userDao.findById(u.getIdUser()).get();
			out.add(new DetUser(user.getName(), u.getEstado()));
		}
		return out;
	}

	/**
	 * Cambia el estado de un usuario a Factura Pagada
	 * @param userName
	 * @param idFac
	 * @return
	 */
	public boolean pagarFacUser(String userName, Long idFac) {

		User user = userDao.findByUserLike(userName);
		if (user == null) {
			return false;
		}

		List<DetFac> listUser = detFacDao.findByIdUserAndIdFac(user.getId(), idFac);
		if (listUser == null || listUser.isEmpty()) {
			return false;
		}
		DetFac facMod = listUser.get(0);
		facMod.setEstado(1);
		detFacDao.save(facMod);

		return true;
	}
	
	/**
	 * Elimina un usuario de una factura
	 * @param userName
	 * @param idFac
	 * @return
	 */
	public boolean eliminarFacUser(String userName, Long idFac) {

		User user = userDao.findByUserLike(userName);
		if (user == null) {
			return false;
		}

		List<DetFac> listUser = detFacDao.findByIdUserAndIdFac(user.getId(), idFac);
		if (listUser == null || listUser.isEmpty()) {
			return false;
		}
		 detFacDao.delete(listUser.get(0));

		return true;
	}
	
	public boolean contorlPagosTotal(Long idFac) {

		List<DetFac> listUser = detFacDao.findByIdFac(idFac);
		if (listUser == null || listUser.isEmpty()) {
			return false;
		}
		boolean totalPay=true;
		for(DetFac user : listUser) {
			if(user.getEstado()==0) {
				totalPay=false;
				break;
			}
		}
		
		if(totalPay) {
			Optional<Factura> listFac = facturaDao.findById(idFac);
			if(listFac.get()!=null) {
				Factura fac = listFac.get();
				fac.setEstado(1);
				facturaDao.save(fac);
			}
		}

		return totalPay;
	}

}
