package com.practica.ems.covid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsDuplicatePersonException;
import com.practica.excecption.EmsInvalidNumberOfDataException;
import com.practica.excecption.EmsInvalidTypeException;
import com.practica.excecption.EmsLocalizationNotFoundException;
import com.practica.excecption.EmsPersonNotFoundException;
import com.practica.genericas.Constantes;
import com.practica.genericas.Coordenada;
import com.practica.genericas.FechaHora;
import com.practica.genericas.Persona;
import com.practica.genericas.PosicionPersona;
import com.practica.lista.ListaContactos;

public class ContactosCovid {

	private Poblacion poblacion;
	private Localizacion localizacion;
	private ListaContactos listaContactos;

	public ContactosCovid () {
		this.poblacion = new Poblacion();
		this.localizacion = new Localizacion();
		this.listaContactos = new ListaContactos();
	}

	public Poblacion getPoblacion () {
		return poblacion;
	}

	public Localizacion getLocalizacion () {
		return localizacion;
	}

	public ListaContactos getListaContactos () {
		return listaContactos;
	}

	public void resetData () {
		this.poblacion = new Poblacion();
		this.localizacion = new Localizacion();
		this.listaContactos = new ListaContactos();
	}

	public void loadData (String data, boolean reset) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException,
															 EmsDuplicatePersonException, EmsDuplicateLocationException {
		// borro información anterior
		if (reset) {
			resetData();
		}
		String datas[] = dividirEntrada(data);
		examineData(datas);
	}

	public void examineData (String[] datas) throws EmsDuplicatePersonException, EmsDuplicateLocationException, EmsInvalidTypeException, EmsInvalidNumberOfDataException {
		for (String linea : datas) {
			String[] datos = this.dividirLineaData(linea);
			if (datos[0].equals("PERSONA")) {
				if (datos.length != Constantes.MAX_DATOS_PERSONA) {
					throw new EmsInvalidNumberOfDataException("El número de datos para PERSONA es menor de 8");
				}
				this.poblacion.addPersona(this.crearPersona(datos));
			} else if (datos[0].equals("LOCALIZACION")) {
				if (datos.length != Constantes.MAX_DATOS_LOCALIZACION) {
					throw new EmsInvalidNumberOfDataException("El número de datos para LOCALIZACION es menor de 6");
				}
				PosicionPersona pp = this.crearPosicionPersona(datos);
				this.localizacion.addLocalizacion(pp);
				this.listaContactos.insertarNodoTemporal(pp);
			} else {
				throw new EmsInvalidTypeException();
			}
		}
	}

	public void loadDataFile (String fichero, boolean reset) {
		try (FileReader fr = new FileReader(fichero); BufferedReader br = new BufferedReader(fr)) {
			if (reset) {
				resetData();
			}
			String data;
			while ((data = br.readLine()) != null) {
				String[] datas = dividirEntrada(data.trim());
				examineData(datas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int findPersona (String documento) throws EmsPersonNotFoundException {
		return this.poblacion.findPersona(documento);
	}

	public int findLocalizacion (String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {
		return this.localizacion.findLocalizacion(documento, fecha, hora);

	}

	public List <PosicionPersona> localizacionPersona (String documento) throws EmsPersonNotFoundException {
		return this.localizacion.localizacionPersona(documento);
	}

	public void delPersona (String documento) throws EmsPersonNotFoundException {
		this.poblacion.delPersona(documento);
	}

	private String[] dividirEntrada (String input) {
		return input.split("\\n");
	}

	private String[] dividirLineaData (String data) {
		return data.split(";");
	}

	private Persona crearPersona (String[] data) {
		Persona persona = new Persona();
		persona.setDocumento(data[1]);
		persona.setNombre(data[2]);
		persona.setApellidos(data[3]);
		persona.setEmail(data[4]);
		persona.setDireccion(data[5]);
		persona.setCp(data[6]);
		persona.setFechaNacimiento(FechaHora.parsearFecha(data[7]));
		return persona;
	}

	private PosicionPersona crearPosicionPersona (String[] data) {
		PosicionPersona posicionPersona = new PosicionPersona();
		posicionPersona.setDocumento(data[1]);
		posicionPersona.setFechaPosicion(FechaHora.parsearFecha(data[2], data[3]));
		posicionPersona.setCoordenada(new Coordenada(Float.parseFloat(data[4]), Float.parseFloat(data[5])));
		return posicionPersona;
	}

}
