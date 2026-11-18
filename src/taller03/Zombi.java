package taller03;

public class Zombi extends Monstruo implements Mordedor, Regenerable{
	private int fuerzaMordida;

	public Zombi(String tipo, int vida, int velocidad, int fuerza, int resistencia, int fuerzaMordida) {
		super(tipo, vida, velocidad, fuerza, resistencia);
		this.fuerzaMordida = fuerzaMordida;
	}

	@Override
    public String regenerarse() {
		
        return "¡El zombi Usa la habilidad de regenerarse!";
    }
	@Override
    public String morder() {
		
        return "¡El zombi clava sus dientes con una fuerza de mordida brutal!";
    }

	@Override
	public double aceptar(VisitorMinijuego visitor) {
		
		return visitor.visitar(this);
	}

	@Override
    public String obtenerResumenAtributos() {
		
        return (tipo + " | Vida " + vida + " | Vel " + velocidad +  " | Fue " + fuerza + " | Res " + resistencia +  " | Mordida " + fuerzaMordida);
    }

}
