package com.practica.ems.covid;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsLocalizationNotFoundException;
import com.practica.excecption.EmsPersonNotFoundException;
import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Localizacion {

	final LinkedList <PosicionPersona> lista;

	public Localizacion () {
		super();
		this.lista = new LinkedList <>();
	}

	public LinkedList <PosicionPersona> getLista () {
		return lista;
	}

	public void addLocalizacion (PosicionPersona p) throws EmsDuplicateLocationException {
		try {
			findLocalizacion(p.getDocumento(), p.getFechaPosicion().getFecha().toString(),
							 p.getFechaPosicion().getHora().toString());
			throw new EmsDuplicateLocationException();
		} catch (EmsLocalizationNotFoundException e) {
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
	public String toString () {
		return lista.stream().map(PosicionPersona::toString).collect(Collectors.joining());
	}

	public List <PosicionPersona> localizacionPersona (String documento) throws EmsPersonNotFoundException {
		List <PosicionPersona> lista = this.lista.stream()
												 .filter(p -> p.getDocumento().equals(documento))
												 .collect(Collectors.toList());
		if (lista.isEmpty())
			throw new EmsPersonNotFoundException();
		else
			return lista;
	}

}
