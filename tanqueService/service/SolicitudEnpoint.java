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
import com.itq.tanqueService.dto.Ack;
import com.itq.tanqueService.dto.Cliente;
import com.itq.tanqueService.dto.ClienteConsulta;
import com.itq.tanqueService.dto.NuevaSolicitud;
import com.itq.tanqueService.dto.Solicitud;
import com.itq.tanqueService.dto.SolicitudDelete;
import com.itq.tanqueService.dto.SolicitudID;

/**
 * @author pauli
 *
 */

@Endpoint
public class SolicitudEnpoint {
	@Autowired
	SolicitudService solicitudService;

	private static final String NAMESPACE_URI = "http://com.Tanque";

	// Create
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "nuevaSolicitud")
	@ResponsePayload
	public Ack solicitudRegistration(@Valid @RequestPayload NuevaSolicitud request) {
		Ack ack = new Ack();
		ack = SolicitudService.addSolicitud(request);
		return ack;
	}

	// Read
		@PayloadRoot(namespace = NAMESPACE_URI, localPart = "solicitudID")
	    @ResponsePayload
	    public Solicitud solicitudShow(@Valid @RequestPayload SolicitudID request) {
			Solicitud solicitud = new Solicitud();
			try {
				solicitud = SolicitudService.buscarSolicitud(request.getIdSolicitud());
			} catch (Exception e) {
				System.out.println("403");
		        System.out.println("Solicitud no encontrada");
			}
			
			return solicitud;
	    }

	// Delete
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "solicitudDelete")
	@ResponsePayload
	public Ack solicitudDelete(@Valid @RequestPayload SolicitudDelete request) {
		Ack ack = new Ack();
		int codigo = SolicitudService.deleteSolicitud(request.getIdSolicitud());

		if (codigo == 205) { // 205 codigo de mensaje ded success
			ack.setDescription("Solicitud eliminada exitosamente");
		} else {
			ack.setDescription("Solicitud no encontrada, no se pudo borrar");
		}
		ack.setCode(codigo);
		return ack;
	}
}
