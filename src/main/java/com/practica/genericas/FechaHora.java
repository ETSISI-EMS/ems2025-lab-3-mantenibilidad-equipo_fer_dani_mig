package com.practica.genericas;

import java.time.LocalDateTime;

public class FechaHora implements Comparable <FechaHora> {

	public static FechaHora parsearFecha (String fecha, String hora) {
		int dia, mes, anio;
		String[] valores = fecha.split("/");
		dia = Integer.parseInt(valores[0]);
		mes = Integer.parseInt(valores[1]);
		anio = Integer.parseInt(valores[2]);
		int minuto, segundo;
		valores = hora.split(":");
		minuto = Integer.parseInt(valores[0]);
		segundo = Integer.parseInt(valores[1]);
		return new FechaHora(dia, mes, anio, minuto, segundo);
	}

	public static class Fecha {

		private final int dia;
		private final int mes;
		private final int anio;

		public Fecha (int dia, int mes, int anio) {
			super();
			this.dia = dia;
			this.mes = mes;
			this.anio = anio;
		}

		public int getDia () {
			return dia;
		}

		public int getMes () {
			return mes;
		}

		public int getAnio () {
			return anio;
		}

		@Override
		public String toString () {
			return String.format("%2d/%02d/%4d", dia, mes, anio);
		}

		@Override
		public boolean equals (Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Fecha other = (Fecha) obj;
			return this.anio == other.anio && this.mes == other.mes && this.dia == other.dia;
		}

	}

	public static class Hora {

		private final int hora;
		private final int minuto;

		public Hora (int hora, int minuto) {
			super();
			this.hora = hora;
			this.minuto = minuto;
		}

		public int getHora () {
			return hora;
		}

		public int getMinuto () {
			return minuto;
		}

		@Override
		public String toString () {
			return String.format("%02d:%02d", hora, minuto);
		}

		@Override
		public boolean equals (Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Hora other = (Hora) obj;
			return this.hora == other.hora && this.minuto == other.minuto;
		}

	}

	Fecha fecha;
	Hora hora;

	public FechaHora (int dia, int mes, int anio, int hora, int minuto) {
		this.fecha = new Fecha(dia, mes, anio);
		this.hora = new Hora(hora, minuto);
	}

	public Fecha getFecha () {
		return fecha;
	}

	public Hora getHora () {
		return hora;
	}

	@Override
	public int hashCode () {
		return Constantes.HASH_PRIME + ((hora == null) ? 0 : hora.hashCode());
	}

	@Override
	public boolean equals (Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FechaHora other = (FechaHora) obj;
		return this.fecha.equals(other.fecha) && this.hora.equals(other.hora);
	}

	@Override
	public int compareTo (FechaHora o) {
		LocalDateTime dateTime1 = LocalDateTime.of(this.getFecha().getAnio(), this.getFecha().getMes(),
												   this.getFecha().getDia(),
												   this.getHora().getHora(), this.getHora().getMinuto());
		LocalDateTime dateTime2 = LocalDateTime.of(o.getFecha().getAnio(), o.getFecha().getMes(), o.getFecha().getDia(),
												   o.getHora().getHora(), o.getHora().getMinuto());

		return dateTime1.compareTo(dateTime2);
	}

	@Override
	public String toString () {
		return String.format("%s;%s", this.fecha.toString(), this.hora.toString());
	}

}
