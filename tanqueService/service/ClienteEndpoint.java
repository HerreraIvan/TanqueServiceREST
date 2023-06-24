/**
 * 
 */
package com.itq.tanqueService.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.itq.tanqueService.business.ClienteService;
import com.itq.tanqueService.business.TanqueService;
import com.itq.tanqueService.dto.Ack;
import com.itq.tanqueService.dto.Cliente;
import com.itq.tanqueService.dto.ClienteAlta;
import com.itq.tanqueService.dto.ClienteConsulta;
import com.itq.tanqueService.dto.ClienteDelete;
import com.itq.tanqueService.dto.ClienteUpdate;

/**
 * @author pauli
 *
 */

@Endpoint
public class ClienteEndpoint {
	@Autowired
	ClienteService clienteService;

	private static final String NAMESPACE_URI = "http://com.Tanque";

	// Create
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "clienteAlta")
	@ResponsePayload
	public Ack clienteRegistration(@Valid @RequestPayload ClienteAlta request) {
		Ack response = clienteService.insertCliente(request);
		return response;
	}

	// Read
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "clienteConsulta")
	    @ResponsePayload
	    public Cliente clienteShow(@Valid @RequestPayload ClienteConsulta request) {
	        Cliente cliente = new Cliente();
	        try {
	            cliente = clienteService.showCliente(request.getIdCliente());
	        } catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();	
	        } 
			return cliente;
	    }

	// Update
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "clienteUpdate")
	@ResponsePayload
	public Ack clienteUpdate(@Valid @RequestPayload ClienteUpdate request) {
		Ack ack = new Ack();

		//int codigo = clienteService.updateCliente(request);

		/*if (codigo == 202) {
			ack.setDescription("Cliente actualizado");
		} else {
			ack.setDescription("Cliente no encontrado, no se puede modificar");
		}
		ack.setCode(codigo);
		return ack;
	}*/
		try {
			int codigo = clienteService.updateCliente(request);
			if (codigo == 202) {
				ack.setDescription(codigo + ": Cliente actualizado");
			}else if (codigo == 400){
				ack.setDescription(codigo + ": Bad request");
			}
			else {
				ack.setDescription(codigo + ": Cliente no encontrado, no se pudo modificar");
			}
			
			ack.setCode(codigo);
		} catch (Exception e) {
		}
		return ack;
	}


	// Delete
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "clienteDelete")
	@ResponsePayload
	public Ack clienteDelete(@Valid @RequestPayload ClienteDelete request) {
		Ack ack = new Ack();
		int codigo = clienteService.deleteCliente(request);

		if (codigo == 201) { // 201 codigo de mensaje success
			ack.setDescription("Cliente borrado exitosamente");
		} else {
			ack.setDescription("Cliente no encontrado, no se pudo borrar");
		}
		ack.setCode(codigo);
		return ack;
	}
}
