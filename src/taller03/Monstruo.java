package taller03;


public abstract class Monstruo {
    protected String tipo;
    protected int vida;
    protected int velocidad;
    protected int fuerza;
    protected int resistencia;
    protected boolean usado;

 
    public Monstruo(String tipo, int vida, int velocidad, int fuerza, int resistencia) {
        this.tipo = tipo;
        this.vida = vida;
        this.velocidad = velocidad;
        this.fuerza = fuerza;
        this.resistencia = resistencia;
        this.usado = false;
    }

    public abstract double aceptar(VisitorMinijuego visitor);

    
    public abstract String obtenerResumenAtributos();

   
    public String getTipo() {
        return tipo;
    }

    public int getVida() {
        return vida;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getResistencia() {
        return resistencia;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }
}