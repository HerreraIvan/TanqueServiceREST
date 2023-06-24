/**
 * 
 */
package com.itq.tanqueService.business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.itq.tanqueService.dto.Ack;
import com.itq.tanqueService.dto.Cliente;
import com.itq.tanqueService.dto.NuevaSolicitud;
import com.itq.tanqueService.dto.Solicitud;
import com.itq.tanqueService.dto.Tanque;

/**
 * @author pauli
 *
 */

@Component
public class SolicitudService {
	// lista donde se van a gurdar todas las solicitudas
	private static ArrayList<Solicitud> listaSolicitud = new ArrayList<Solicitud>();
	// variable que permite la autonumeracion del id de solicitud
	static int contSolicitud = 1;
	
	public static Ack addSolicitud(NuevaSolicitud solicitud) {
		Ack ack = new Ack();
		try {
			Solicitud solicit = new Solicitud();
			solicit.setIdSolicitud(contSolicitud);
			// saca la hora y fecha del sistema
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDateTime now = LocalDateTime.now();
			solicit.setIdCliente(solicitud.getIdCliente());
			if(solicitud.getLitros() > 0) {
				solicit.setLitros(solicitud.getLitros());
			}
			else {
				ack.setCode(400);
				ack.setDescription("Cantidad de litros no válida");
				return ack;
			}
			
			solicit.setFecha(dtf.format(now).toString());
			
			int idTanque = buscarIdTanque(solicitud.getIdCliente());
			solicit.setIdTanque(idTanque);
			
			
			if(servicioDisponible(idTanque, solicitud.getLitros()))
			{
				listaSolicitud.add(solicit);
				contSolicitud++;
				ack.setCode(201);
				ack.setDescription("Servicio de agua administrado correctamente");
			}
			else
			{
				ack.setCode(400);
				ack.setDescription("El servicio no se encuentra disponible ahora, vuelva más tarde");
			}
		} catch (Exception e) {
			ack.setCode(400);
			ack.setDescription(e.getMessage());
		}
		return ack;
	}
	
	public static Solicitud buscarSolicitud(int idSolicitud) throws Exception {
		for (Solicitud solicitud : listaSolicitud) {
			if (solicitud.getIdSolicitud() == idSolicitud) {
				return solicitud;
			}
		}
		throw new Exception("Solicitud: " + idSolicitud + " no existe");
	}
	
	public static int deleteSolicitud(int idSolicitud) {
		for (Solicitud solicitud : listaSolicitud) {
			if (solicitud.getIdSolicitud() == idSolicitud) {
				listaSolicitud.remove(solicitud);
				return 205;
			}
		}
		return 403;
	}
	
	private static int buscarIdTanque(int idCliente) throws Exception {
		ArrayList<Cliente> listaCliente = ClienteService.getList();

		ClienteService.showCliente(idCliente);
		for (Cliente cliente : listaCliente) {
			if (cliente.getIdCliente() == idCliente)
				return cliente.getIdTanque();
		}
		throw new Exception("Cliente no existe");
	}

	
	public static boolean servicioDisponible(int idTanque, int litrosSolicitados) {
		// Array de la lista de tanques que permite obtener la hora de cada uno de los tanques
		ArrayList<Tanque> listaTank = TanqueService.getList();
		
		// se saca la hora actual del sistema
		LocalDateTime hora = LocalDateTime.now();  
		// Checa la hora 
		for(Tanque tanque : listaTank) {
			if(tanque.getIdTanque() == idTanque)
			{
				if(hora.getHour() == 0 && hora.getMinute() < tanque.getTiempoLlenado()) 
					return false;
				else
				{
					// Checa que sea disponible para la cantidad de agua en un tanque o si la cantidad
					// dentro del tanque es igual a 0
					if(tanque.getCantidadActual() < litrosSolicitados || tanque.getCantidadActual() == 0)
					{
						// Se tiene que buscar otro tanque que pueda cumplir con la peticion
						try {
							return buscarTanqueProximo(litrosSolicitados);
						} catch (Exception e) {
							return false;
						}
					}
					else
					{
						// Se actualiza la cantidad de litros en el tanque.
						int litrosActuales = tanque.getCantidadActual();
						tanque.setCantidadActual(litrosActuales - litrosSolicitados);
						return true;
					}	
				}
			}
		}
		return false;
	}
	
	private static boolean buscarTanqueProximo(int litrosSolicitados) throws Exception
	{
		// Array de la lista de tanques que permite obtener la hora de cada uno de los tanques
		ArrayList<Tanque> listaTank = TanqueService.getList();
		
		for(Tanque tanque : listaTank)
		{
			if(tanque.getCantidadActual() >= litrosSolicitados)
			{
				// Se actualiza la cantidad de litros en el tanque.
				int litrosActuales = tanque.getCantidadActual();
				tanque.setCantidadActual(litrosActuales - litrosSolicitados);
				return true;
			}
		}
		throw new Exception("No hay tanques disponibles que puedan surtir esta peticion");
	}
}
