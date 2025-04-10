package com.practica.genericas;

public class Coordenada {

	private final float latitud;
	private final float longitud;

	public Coordenada (float latitud, float longitud) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public float getLatitud () {
		return latitud;
	}

	public float getLongitud () {
		return longitud;
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(latitud);
		result = prime * result + Float.floatToIntBits(longitud);
		return result;
	}

	@Override
	public boolean equals (Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordenada other = (Coordenada) obj;
		if (Float.floatToIntBits(latitud) != Float.floatToIntBits(other.latitud))
			return false;
		return Float.floatToIntBits(longitud) == Float.floatToIntBits(other.longitud);
	}

	@Override
	public String toString () {
		return String.format("%.4f;%.4f\n", this.getLatitud(),
							 this.getLongitud());
	}

}
