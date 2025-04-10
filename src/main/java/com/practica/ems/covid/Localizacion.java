package com.practica.ems.covid;


import java.util.LinkedList;
import java.util.stream.Collectors;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsLocalizationNotFoundException;
import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

public class Localizacion {
	LinkedList<PosicionPersona> lista;

	public Localizacion() {
		super();
		this.lista = new LinkedList <>();
	}
	
	public LinkedList<PosicionPersona> getLista() {
		return lista;
	}

	public void addLocalizacion (PosicionPersona p) throws EmsDuplicateLocationException {
		try {
			findLocalizacion(p.getDocumento(), p.getFechaPosicion().getFecha().toString(),p.getFechaPosicion().getHora().toString() );
			throw new EmsDuplicateLocationException();
		}catch(EmsLocalizationNotFoundException e) {
			lista.add(p);
		}
	}
	
	public int findLocalizacion (String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {
	    int cont = 0;
		for (PosicionPersona posicionPersona : lista) {
			cont++;
			FechaHora fechaHora = FechaHora.parsearFecha(fecha, hora);
			if (posicionPersona.getDocumento().equals(documento) &&
				posicionPersona.getFechaPosicion().equals(fechaHora)) {
				return cont;
			}
		}
	    throw new EmsLocalizationNotFoundException();
	}

	@Override
	public String toString() {
		return lista.stream().map(PosicionPersona::toString).collect(Collectors.joining());
	}

}
