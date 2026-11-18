package taller03;

public class HombreLobo extends Monstruo implements Furioso, Mordedor{
	private int ferocidad;
	private int faseLunar;
	public HombreLobo(String tipo, int vida, int velocidad, int fuerza, int resistencia, int ferocidad, int faseLunar) {
		super(tipo, vida, velocidad, fuerza, resistencia);
		this.ferocidad = ferocidad;
		this.faseLunar = faseLunar;
	}
	@Override
    public String morder() {
        return "¡El hombre lobo lanza un mordisco!";
    }
	
	@Override
    public String enfurecerse() {
        return "¡El hombre lobo aúlla perdiendo el control por la furia!";
	}
	@Override
    public double aceptar(VisitorMinijuego visitor) {
        return visitor.visitar(this);
    
	}
	@Override
    public String obtenerResumenAtributos() {
        return tipo + " | Vida " + vida + " | Vel " + velocidad + " | Fue " + fuerza + " | Res " + resistencia + " | Ferocidad " + ferocidad + " | Luna " + faseLunar;
    }
	public int getFerocidad() {
		return ferocidad;
	}
	public int getFaseLunar() {
		return faseLunar;
	}
	
	

}
