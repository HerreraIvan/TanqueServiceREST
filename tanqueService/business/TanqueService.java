/**
 * 
 */
package com.itq.tanqueService.business;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.itq.tanqueService.dto.Ack;
import com.itq.tanqueService.dto.Tanque;
import com.itq.tanqueService.dto.TanqueAlta;
import com.itq.tanqueService.dto.TanqueConsulta;
import com.itq.tanqueService.dto.TanqueDelete;
import com.itq.tanqueService.dto.TanqueUpdate;

@Component
public class TanqueService {
	// array que guarda los elementos tanque
	private static ArrayList<Tanque> listaTank = new ArrayList<Tanque>();
	// variable que permite la autonumeracion del id del tanque
	static int contTanque = 1;

	public static Ack insertTanque(TanqueAlta tanque) throws Exception{
		Tanque tank = new Tanque();
		Ack ack = new Ack();

		try {
			tank.setIdTanque(contTanque);
			tank.setNombre(tanque.getNombre());
			if(tanque.getUbicacion() > 0 && tanque.getUbicacion() <=3) {
				tank.setUbicacion(tanque.getUbicacion());
			}else {
				throw new Exception("Bad request: Ubicación inválida");
			}
			String nombre = tanque.getNombre();
			int recurso[] = recursosTanque(nombre);
			tank.setCapacidad(recurso[0]);
			tank.setTiempoLlenado(recurso[1]);
			if(tanque.getCantidadActual() > 0 && tanque.getCantidadActual() <= tank.getCapacidad()) {
				tank.setCantidadActual(tanque.getCantidadActual());
			}else {
				throw new Exception("Bad request: La cantidad establecida no es válida");
			}
			
			listaTank.add(tank);
			contTanque++;
			ack.setCode(200);
			ack.setDescription("Tanque creado");
		} catch (Exception e) {
			ack.setCode(400);
			ack.setDescription(e.getMessage());
		}

		return ack;

	}

	public static Tanque showTanque(TanqueConsulta idTanque) throws Exception {
		for (Tanque tanque : listaTank) {
			if (tanque.getIdTanque() == idTanque.getIdTanque()) {
				return tanque;
			}
		}
		throw new Exception("402: Tanque: " + idTanque.getIdTanque() + " no existe");
	}

	public static int deleteTanque(TanqueDelete idTanque) {
		for (Tanque tanque : listaTank) {
			if (tanque.getIdTanque() == idTanque.getIdTanque()) {
				listaTank.remove(tanque);
				return 203;
			}
		}
		return 402;
	}

	public static int updateTanque(TanqueUpdate idTanque) {
		for (int i = 0; i < listaTank.size(); i++) {
			
			if (listaTank.get(i).getIdTanque() == idTanque.getIdTanque()) {
				
				try {
				
					listaTank.get(i).setNombre(idTanque.getNombre());
					int recurso[] = recursosTanque(idTanque.getNombre());
					listaTank.get(i).setCapacidad(recurso[0]);
					listaTank.get(i).setTiempoLlenado(recurso[1]);
					if(idTanque.getCantidadActual() > 0 && idTanque.getCantidadActual() <= listaTank.get(i).getCapacidad()) {
						listaTank.get(i).setCantidadActual(idTanque.getCantidadActual());
					}else {
						return 400;
					}
					
					if(idTanque.getUbicacion() > 0 && idTanque.getUbicacion() <=3) {
						listaTank.get(i).setUbicacion(idTanque.getUbicacion());
					}else {
						return 400;
					}
					
					return 204;
				} catch (Exception e) {
					return 400;
				}
			}
		}
		return 402;
	}

	// Metodo para actualizar los recursos determinados del tanque dependiendo del
	// nombre
	private static int[] recursosTanque(String nombre) throws Exception {
		int capacidad = 0, tiempoLlenado = 0;
		switch (nombre) {
		case "Tank1":
			capacidad = 10000;
			tiempoLlenado = 10;
			break;
		case "Tank2":
			capacidad = 20000;
			tiempoLlenado = 20;
			break;
		case "Tank3":
			capacidad = 3000;
			tiempoLlenado = 3;
			break;
		default:
			throw new Exception("400: Bad request: Nombre de tanque no válido");
		}

		return new int[] { capacidad, tiempoLlenado };
	}

	public static ArrayList<Tanque> getList() {
		return listaTank;
	}
}
