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

import com.itq.tanqueService.business.SolicitudService;
import com.itq.tanqueService.business.TanqueService;

import com.itq.tanqueService.dto.Ack;
import com.itq.tanqueService.dto.Solicitud;
import com.itq.tanqueService.dto.SolicitudID;
import com.itq.tanqueService.dto.Tanque;
import com.itq.tanqueService.dto.TanqueAlta;
import com.itq.tanqueService.dto.TanqueConsulta;
import com.itq.tanqueService.dto.TanqueDelete;
import com.itq.tanqueService.dto.TanqueUpdate;

/**
 * @author herre
 *
 */

@Endpoint
public class TanqueEndpoint {
	@Autowired
	TanqueService tanqueService;

	private static final String NAMESPACE_URI = "http://com.Tanque";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "tanqueAlta")
	@ResponsePayload
	public Ack tanqueRegistration(@Valid @RequestPayload TanqueAlta request) {
		Ack ack = new Ack();
		try {
			ack = TanqueService.insertTanque(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block

		}
		return ack;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "tanqueConsulta")
	@ResponsePayload
	public Tanque tanqueShow(@Valid @RequestPayload TanqueConsulta request) {
		Tanque tanque = new Tanque();
		try {
			tanque = TanqueService.showTanque(request);
		} catch (Exception e) {
		}
		return tanque;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "tanqueDelete")
	@ResponsePayload
	public Ack tanqueDelete(@Valid @RequestPayload TanqueDelete request) {
		Ack ack = new Ack();
		int codigo = TanqueService.deleteTanque(request);

		if (codigo == 203) {
			ack.setDescription(codigo + ": Tanque borrado exitosamente");
		} else {
			ack.setDescription(codigo + ": Tanque no encontrado, no se pudo borrar");
		}
		ack.setCode(codigo);
		return ack;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "tanqueUpdate")
	@ResponsePayload
	public Ack tanqueUpdate(@Valid @RequestPayload TanqueUpdate request) {
		Ack ack = new Ack();
		try {
			int codigo = TanqueService.updateTanque(request);
			if (codigo == 204) {
				ack.setDescription(codigo + ": Tanque actualizado");
			}else if (codigo == 400){
				ack.setDescription(codigo + ": Bad request");
			}
			else {
				ack.setDescription(codigo + ": Tanque no encontrado, no se pudo modificar");
			}
			
			ack.setCode(codigo);
		} catch (Exception e) {
		}

		return ack;
	}
}
