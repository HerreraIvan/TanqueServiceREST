//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.3.2 
// Visite <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2022.12.11 a las 12:31:51 AM CST 
//


package com.itq.tanqueService.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="idTanque"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int"&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="capacidad" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="tiempoLlenado" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ubicacion"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int"&gt;
 *               &lt;minInclusive value="1"/&gt;
 *               &lt;maxInclusive value="3"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="cantidadActual" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idTanque",
    "nombre",
    "capacidad",
    "tiempoLlenado",
    "ubicacion",
    "cantidadActual"
})
@XmlRootElement(name = "tanque")
public class Tanque {

    protected int idTanque;
    @XmlElement(required = true)
    protected String nombre;
    protected int capacidad;
    protected int tiempoLlenado;
    protected int ubicacion;
    protected int cantidadActual;

    /**
     * Obtiene el valor de la propiedad idTanque.
     * 
     */
    public int getIdTanque() {
        return idTanque;
    }

    /**
     * Define el valor de la propiedad idTanque.
     * 
     */
    public void setIdTanque(int value) {
        this.idTanque = value;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad capacidad.
     * 
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Define el valor de la propiedad capacidad.
     * 
     */
    public void setCapacidad(int value) {
        this.capacidad = value;
    }

    /**
     * Obtiene el valor de la propiedad tiempoLlenado.
     * 
     */
    public int getTiempoLlenado() {
        return tiempoLlenado;
    }

    /**
     * Define el valor de la propiedad tiempoLlenado.
     * 
     */
    public void setTiempoLlenado(int value) {
        this.tiempoLlenado = value;
    }

    /**
     * Obtiene el valor de la propiedad ubicacion.
     * 
     */
    public int getUbicacion() {
        return ubicacion;
    }

    /**
     * Define el valor de la propiedad ubicacion.
     * 
     */
    public void setUbicacion(int value) {
        this.ubicacion = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidadActual.
     * 
     */
    public int getCantidadActual() {
        return cantidadActual;
    }

    /**
     * Define el valor de la propiedad cantidadActual.
     * 
     */
    public void setCantidadActual(int value) {
        this.cantidadActual = value;
    }

}
