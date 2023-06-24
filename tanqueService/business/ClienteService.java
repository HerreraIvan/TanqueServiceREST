/**
 * 
 */
package com.itq.tanqueService.business;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.itq.tanqueService.dto.Ack;
import com.itq.tanqueService.dto.Cliente;
import com.itq.tanqueService.dto.ClienteAlta;
import com.itq.tanqueService.dto.ClienteDelete;
import com.itq.tanqueService.dto.ClienteUpdate;
import com.itq.tanqueService.dto.Tanque;

@Component
public class ClienteService {

	// array que guarda los elementos cliente
	private static ArrayList<Cliente> listaClient = new ArrayList<Cliente>();
	// variable que permite la autonumeracion del id del cliente
	private static int contCliente = 1;

	public Ack insertCliente(ClienteAlta cliente) {
		Ack ack = new Ack();

		try {
			Cliente client = new Cliente();
			client.setIdCliente(contCliente);
			if (cliente.getNombre().matches("^[a-zA-Z]+$")) {
				client.setNombre(cliente.getNombre());
			} else {
				throw new Exception("Nombre de cliente no válido");
			}
			if (cliente.getUbicacion() > 0 && cliente.getUbicacion() <= 3) {
				client.setUbicacion(cliente.getUbicacion());
			} else {
				throw new Exception("Bad request: Ubicación inválida");
			}
			// client.setUbicacion(cliente.getUbicacion());
			client.setDireccion(cliente.getDireccion());
			if (cliente.getCURP().matches(
					"^([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$")) {
				client.setCURP(cliente.getCURP());
			} else {
				throw new Exception("Bad request: CURP inválida");
			}
			// client.setCURP(cliente.getCURP());
			int ubicacion = cliente.getUbicacion();
			int idTanque = asignarTanque(ubicacion);
			client.setIdTanque(idTanque);
			listaClient.add(client);
			contCliente++;
			ack.setCode(200);
			ack.setDescription("Cliente creado");
		} catch (Exception e) {
			ack.setCode(400);
			ack.setDescription(e.getMessage());
		}
		return ack;
	}

	public static Cliente showCliente(int idCliente) throws Exception {
		for (Cliente cliente : listaClient) {
			if (cliente.getIdCliente() == idCliente) {
				return cliente;
			}
		}
		throw new Exception("Cliente: " + idCliente + " no existe");
	}

	public int deleteCliente(ClienteDelete idCliente) {
		for (Cliente cliente : listaClient) {
			if (cliente.getIdCliente() == idCliente.getIdCliente()) {
				listaClient.remove(cliente);
				return 201;
			}
		}
		return 401;
	}

	public int updateCliente(ClienteUpdate idCliente) {
		for (int i = 0; i < listaClient.size(); i++) {
			if (listaClient.get(i).getIdCliente() == idCliente.getIdCliente()) {
				try {

					
					if (idCliente.getNombre().matches("^[a-zA-Z]+$")) {
						listaClient.get(i).setNombre(idCliente.getNombre());
					} else {
						throw new Exception("Nombre de cliente no válido");
					}
					if (idCliente.getUbicacion() > 0 && idCliente.getUbicacion() <= 3) {
						listaClient.get(i).setUbicacion(idCliente.getUbicacion());
					} else {
						throw new Exception("Bad request: Ubicación inválida");
					}
					// client.setUbicacion(cliente.getUbicacion());
					listaClient.get(i).setDireccion(idCliente.getDireccion());
					if (idCliente.getCURP().matches(
							"^([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$")) {
						listaClient.get(i).setCURP(idCliente.getCURP());
					} else {
						throw new Exception("Bad request: CURP inválida");
					}
					listaClient.get(i).setIdTanque(asignarTanque(idCliente.getUbicacion()));
					return 202;
				} catch (Exception e) {
					return 400;
				}
			}
		}
		return 401;
	}

	// asigna un tanque al cliente, dependiendo de su ubicación private static
	int asignarTanque(int ubicacion) throws Exception {
		boolean bandera = false;
		int idTanque = 0;
		ArrayList<Tanque> listaTank = TanqueService.getList();

		if (listaTank.size() != 0) {
			for (Tanque tank : listaTank) {
				if (tank.getUbicacion() == ubicacion) {
					bandera = true;
					idTanque = tank.getIdTanque();
					break;
				}
			}
			if (!bandera) {
				if (ubicacion < 3) {
					ubicacion++;
					idTanque = asignarTanque(ubicacion);
				} else {
					ubicacion = 1;
					idTanque = asignarTanque(ubicacion);
				}
			}
		} else {
			throw new Exception("No hay tanques disponibles");
		}
		return idTanque;
	}

	public static ArrayList<Cliente> getList() {
		return listaClient;
	}
}
