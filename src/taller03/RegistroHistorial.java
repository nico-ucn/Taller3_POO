package taller03;

public class RegistroHistorial {
	private String nombreMinijuego;
	private String tipoMonstruo;
	private String habilidadUsada;
	private int puntaje;
	
	public RegistroHistorial(String nombreMinijuego, String tipoMonstruo, String habilidadUsada, int puntaje) {
		this.nombreMinijuego = nombreMinijuego;
		this.tipoMonstruo = tipoMonstruo;
		this.habilidadUsada = habilidadUsada;
		this.puntaje = puntaje;
	}

	public String getNombreMinijuego() {
		return nombreMinijuego;
	}

	public String getTipoMonstruo() {
		return tipoMonstruo;
	}

	public String getHabilidadUsada() {
		return habilidadUsada;
	}

	public int getPuntaje() {
		return puntaje;
	}
	
	public String obtenerResumen() {
		return nombreMinijuego + " | " + tipoMonstruo + " | Habilidad: " + habilidadUsada + " | " + puntaje + " pts";
	}
	
	
	

}
