package taller03;

public class Vampiro extends Monstruo implements Mordedor, Volador {
    private int velocidadDeVuelo;
    private int resistenciaAlSol;

    public Vampiro(int vida, int velocidad, int fuerza, int resistencia, int velocidadDeVuelo, int resistenciaAlSol) {
        super("Vampiro", vida, velocidad, fuerza, resistencia);
        this.velocidadDeVuelo = velocidadDeVuelo;
        this.resistenciaAlSol = resistenciaAlSol;
    }

    @Override
    public double aceptar(VisitorMinijuego visitor) {
        return visitor.visitar(this);
    }

    @Override
    public String morder() {
        return "¡El vampiro utiliza sus colmillos y empieza a morder!";
    }

    @Override
    public String volar() {
        return "¡El vampiro se eleva por los aires y vuela!";
    }

    @Override
    public String obtenerResumenAtributos() {
        return tipo + " | Vida " + vida + " | Vel " + velocidad +  " | Fue " + fuerza + " | Res " + resistencia +  " | Vuelo " + velocidadDeVuelo + " | Sol " + resistenciaAlSol;
    }

    public int getVelocidadDeVuelo() {
        return velocidadDeVuelo;
    }

    public int getResistenciaAlSol() {
        return resistenciaAlSol;
    }
}