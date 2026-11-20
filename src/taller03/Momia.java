package taller03;

public class Momia extends Monstruo implements Regenerable, Furioso {
	private int longitudVendajes;
	
	
	

	public Momia(int vida, int velocidad, int fuerza, int resistencia, int longitudVendajes) {
		super("Momia", vida, velocidad, fuerza, resistencia);
		this.longitudVendajes = longitudVendajes;
	}

	@Override
    public String enfurecerse() {
        return "¡La momia se llena de furia!";
    }

	@Override
    public String regenerarse() {
        return "¡La momia usa la habilidad de regenerarse!";
    }

	@Override
    public double aceptar(VisitorMinijuego visitor) {
        return visitor.visitar(this);
    }
	

	@Override
    public String obtenerResumenAtributos() {
        return tipo + " | Vida " + vida + " | Vel " + velocidad + " | Fue " + fuerza + " | Res " + resistencia +  " | Vendajes " + longitudVendajes;
    }
	public int getLongitudVendajes() {
        return longitudVendajes;
    }

}
