package taller03;

public class CarreraCementerio implements Minijuego{

	@Override
    public double visitar(Zombi zombi) {
        return (zombi.getVelocidad() * zombi.getResistencia()) / 100.0;
    }

	@Override
    public double visitar(Vampiro vampiro) {
        return (vampiro.getVelocidad() + 2.0 * vampiro.getVelocidadDeVuelo()) / 2.0;
    }

	@Override
    public double visitar(Momia momia) {
        return (momia.getVelocidad() * 100.0) / (100.0 + momia.getLongitudVendajes());
    }

	@Override
	public double visitar(HombreLobo hombreLobo) {
		return (hombreLobo.getVelocidad() + hombreLobo.getFerocidad() / 5.0) * (hombreLobo.getFaseLunar() / 4.0);
    }

	@Override
    public String getNombre() {
        return "Carrera del Cementerio";
    }

	@Override
    public String getDescripcion() {
        return "Recorre el cementerio de punta a punta antes de que el sepulturero te vea.";
    }

	@Override
    public String getHabilidadPremiada() {
        return "Volador";
    }
	

}
