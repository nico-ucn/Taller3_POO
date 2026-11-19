package taller03;

public class SobrevivirAmanecer implements Minijuego{

	@Override
    public double visitar(Zombi zombi) {
        return (zombi.getResistencia() * zombi.getVida()) / 100.0;
    }

	@Override
    public double visitar(Vampiro vampiro) {
        return (vampiro.getResistencia() * vampiro.getResistenciaAlSol()) / 100.0;
    }

	@Override
    public double visitar(Momia momia) {
        return ((momia.getResistencia() + momia.getVida()) / 2.0) * (1.0 + (momia.getLongitudVendajes() / 300.0));
    }

	@Override
    public double visitar(HombreLobo hombreLobo) {
        return ((hombreLobo.getResistencia() + hombreLobo.getVida()) / 2.0) * ((5.0 - hombreLobo.getFaseLunar()) / 4.0);
    }

	@Override
    public String getNombre() {
        return "Sobrevivir al Amanecer";
    }

	@Override
    public String getDescripcion() {
        return "Aguanta en pie cuando salgan los primeros rayos de sol.";
    }
	
	@Override
    public String getHabilidadPremiada() {
        return "Regenerable";
    }

}
