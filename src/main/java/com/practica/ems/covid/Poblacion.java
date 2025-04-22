package com.practica.ems.covid;

import com.practica.excecption.EmsDuplicatePersonException;
import com.practica.excecption.EmsPersonNotFoundException;
import com.practica.genericas.FechaHora;
import com.practica.genericas.Persona;

import java.util.LinkedList;

public class Poblacion {

	final LinkedList <Persona> lista;

	public Poblacion () {
		super();
		this.lista = new LinkedList <>();
	}

	public LinkedList <Persona> getLista () {
		return lista;
	}

	public void addPersona (Persona persona) throws EmsDuplicatePersonException {
		try {
			findPersona(persona.getDocumento());
			throw new EmsDuplicatePersonException();
		} catch (EmsPersonNotFoundException e) {
			lista.add(persona);
		}
	}

	public void delPersona (String documento) throws EmsPersonNotFoundException {
		int pos;
		/*
		  Busca la persona por documento, en caso de encontrarla
		  devuelve la posición dentro de la lista, sino está lanza
		  una excepción
		 */
		try {
			pos = findPersona(documento);
		} catch (EmsPersonNotFoundException e) {
			throw new EmsPersonNotFoundException();
		}
		lista.remove(--pos);
	}

	public int findPersona (String documento) throws EmsPersonNotFoundException {
		int cont = 0;
		for (Persona persona : lista) {
			cont++;
			if (persona.getDocumento().equals(documento)) {
				return cont;
			}
		}
		throw new EmsPersonNotFoundException();
	}

	public void printPoblacion () {

		for (int i = 0; i < lista.size(); i++) {
			FechaHora fecha = lista.get(i).getFechaNacimiento();
			// Documento
			System.out.printf("%d;%s;", i, lista.get(i).getDocumento());
			// nombre y apellidos
			System.out.printf("%s,%s;", lista.get(i).getApellidos(), lista.get(i).getNombre());
			// correo electrónico
			System.out.printf("%s;", lista.get(i).getEmail());
			// Códifo postal
			System.out.printf("%s,%s;", lista.get(i).getDireccion(), lista.get(i).getCp());
			// Fecha de nacimiento
			System.out.printf("%02d/%02d/%04d\n", fecha.getFecha().getDia(),
							  fecha.getFecha().getMes(),
							  fecha.getFecha().getAnio());
		}
	}

	@Override
	public String toString () {
		StringBuilder cadena = new StringBuilder();
		for (Persona persona : lista) {
			FechaHora fecha = persona.getFechaNacimiento();
			// Documento
			cadena.append(String.format("%s;", persona.getDocumento()));
			// nombre y apellidos
			cadena.append(String.format("%s,%s;", persona.getApellidos(), persona.getNombre()));
			// correo electrónico
			cadena.append(String.format("%s;", persona.getEmail()));
			// Direccion y código postal
			cadena.append(String.format("%s,%s;", persona.getDireccion(), persona.getCp()));
			// Fecha de nacimiento
			cadena.append(String.format("%02d/%02d/%04d\n", fecha.getFecha().getDia(),
										fecha.getFecha().getMes(),
										fecha.getFecha().getAnio()));
		}
		return cadena.toString();
	}

}
