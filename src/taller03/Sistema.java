package taller03;

import java.util.ArrayList;
import java.util.LinkedList;

public class Sistema {
	private ArrayList<Monstruo> monstruos;
	private ArrayList<Jugador> jugadores;
	private LinkedList<RegistroHistorial> historial;
	private ArrayList<Minijuego> minijuegos;
	
	private String usuarioActual;
	private int puntajeSesion;
	private boolean[] minijuegosJugados;
	private int[] puntajesMinijuegos;
	
	
	public Sistema() {
		this.monstruos = new ArrayList<>();
		this.jugadores = new ArrayList<>();
		this.historial = new LinkedList<>();
		this.minijuegos = new ArrayList<>();
		
		
		this.minijuegos.add(new CarreraCementerio());
		this.minijuegos.add(new CazaCalabazas());
		this.minijuegos.add(new LanzaLapidas());
		this.minijuegos.add(new SobrevivirAmanecer());
		
		this.minijuegosJugados = new boolean[4];
        this.puntajesMinijuegos = new int[4];
        this.puntajeSesion = 0;
        this.usuarioActual = "";
        
	}
	private boolean enRango(int valor, int min, int max) {
        return valor >= min && valor <= max;
    }
	
	public boolean cargarMonstruo(String[] partes) {
        if (partes == null || partes.length < 6) {
            return false;
        }
        try {
            String tipo = partes[0].strip();
            int vida = Integer.parseInt(partes[1].strip());
            int velocidad = Integer.parseInt(partes[2].strip());
            int fuerza = Integer.parseInt(partes[3].strip());
            int resistencia = Integer.parseInt(partes[4].strip());
            
            if (!enRango(vida, 1, 200) || !enRango(velocidad, 1, 100) || !enRango(fuerza, 1, 100) || !enRango(resistencia, 1, 100)) {
                    return false;
                }
            
            if (tipo.equals("Zombi") && partes.length == 6) {
                int mordida = Integer.parseInt(partes[5].strip());
                if (!enRango(mordida, 1, 100)) return false;
                monstruos.add(new Zombi(vida, velocidad, fuerza, resistencia, mordida));
                return true;
            }
            
            else if (tipo.equals("Vampiro") && partes.length == 7) {
                int vuelo = Integer.parseInt(partes[5].strip());
                int sol = Integer.parseInt(partes[6].strip());
                if (!enRango(vuelo, 1, 100) || !enRango(sol, 1, 100)) return false;
                monstruos.add(new Vampiro(vida, velocidad, fuerza, resistencia, vuelo, sol));
                return true;

            }
            
            else if (tipo.equals("Momia") && partes.length == 6) {
                int vendajes = Integer.parseInt(partes[5].strip());
                if (!enRango(vendajes, 1, 300)) return false;
                monstruos.add(new Momia(vida, velocidad, fuerza, resistencia, vendajes));
                return true;

            }
            else if (tipo.equals("HombreLobo") && partes.length == 7) {
                int ferocidad = Integer.parseInt(partes[5].strip());
                int faseLunar = Integer.parseInt(partes[6].strip());
                if (!enRango(ferocidad, 1, 100) || !enRango(faseLunar, 1, 4)) return false;
                monstruos.add(new HombreLobo(vida, velocidad, fuerza, resistencia, ferocidad, faseLunar));
                return true;
            }
            return false;
        }
        catch (NumberFormatException e) {
            return false; 
        }
                 
	}
        
	public boolean cargarJugador(String[] partes) {
        if (partes == null || partes.length != 2) {
            return false;
        }
        try {
            String nombre = partes[0].strip();
            int puntaje = Integer.parseInt(partes[1].strip());
            if (nombre.isEmpty() || puntaje < 0) {
                return false;
            }
            jugadores.add(new Jugador(nombre, puntaje));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
	
	}
	
	public int iniciarSesion(String nombreUsuario) {
        this.usuarioActual = nombreUsuario.strip();
        this.puntajeSesion = 0;
        this.historial.clear();
        this.minijuegosJugados = new boolean[4];
        this.puntajesMinijuegos = new int[4];

        for (Monstruo m : monstruos) {
            m.setUsado(false);
        }

        for (Jugador j : jugadores) {
            if (j.getNombre().equalsIgnoreCase(this.usuarioActual)) {
                this.usuarioActual = j.getNombre();
                return j.getPuntajeMaximo();
            }
        }
        return -1;
    }
	
	public String getUsuarioActual() {
        return usuarioActual;
    }

    public int getPuntajeSesion() {
        return puntajeSesion;
    }

    public int getCantidadMinijuegos() {
        return minijuegos.size();
    }
    
    public Minijuego getMinijuego(int indice) {
        if (indice < 0 || indice >= minijuegos.size()) {
            return null;
        }
        return minijuegos.get(indice);
    }
    
    public boolean estaMinijuegoJugado(int indice) {
        if (indice < 0 || indice >= minijuegosJugados.length) {
            return true;
        }
        return minijuegosJugados[indice];
    }
    
    public int getPuntajeMinijuego(int indice) {
        if (indice < 0 || indice >= puntajesMinijuegos.length) {
            return 0;
        }
        return puntajesMinijuegos[indice];
    }
	
    public ArrayList<Monstruo> getMonstruosDisponibles() {
        ArrayList<Monstruo> disponibles = new ArrayList<>();
        for (Monstruo m : monstruos) {
            if (!m.isUsado()) {
                disponibles.add(m);
            }
        }
        return disponibles;
    }
    
    public double calcularPotencial(Monstruo monstruo, Minijuego minijuego) {
        if (monstruo == null || minijuego == null) {
            return 0.0;
        }
        return monstruo.aceptar(minijuego);
    }
    
    public ArrayList<String> obtenerHabilidadesDisponibles(Monstruo monstruo) {
        ArrayList<String> habilidades = new ArrayList<>();
        if (monstruo == null) {
            return habilidades;
        }
        if (monstruo instanceof Mordedor) {
            habilidades.add("Morder");
        }
        if (monstruo instanceof Volador) {
            habilidades.add("Volar");
        }
        if (monstruo instanceof Regenerable) {
            habilidades.add("Regenerarse");
        }
        if (monstruo instanceof Furioso) {
            habilidades.add("Enfurecerse");
        }
        return habilidades;
    }
    
    public String[] jugarMinijuego(int indiceMinijuego, Monstruo monstruo, String nombreHabilidad) {
        Minijuego minijuego = minijuegos.get(indiceMinijuego);
        double potencial = calcularPotencial(monstruo, minijuego);
        
        String mensajeHabilidad = "";
        String mensajeEfecto = "";
        String habilidadRegistrada = "Ninguna";
        double multiplicador = 1.0;
        
        if (nombreHabilidad != null && !nombreHabilidad.equals("Ninguna")) {
            habilidadRegistrada = nombreHabilidad;

            if (nombreHabilidad.equals("Morder") && monstruo instanceof Mordedor) {
                mensajeHabilidad = ((Mordedor) monstruo).morder();
            } else if (nombreHabilidad.equals("Volar") && monstruo instanceof Volador) {
                mensajeHabilidad = ((Volador) monstruo).volar();
            } else if (nombreHabilidad.equals("Regenerarse") && monstruo instanceof Regenerable) {
                mensajeHabilidad = ((Regenerable) monstruo).regenerarse();
            } else if (nombreHabilidad.equals("Enfurecerse") && monstruo instanceof Furioso) {
                mensajeHabilidad = ((Furioso) monstruo).enfurecerse();
            }
            
            if (nombreHabilidad.equals(minijuego.getHabilidadPremiada())) {
                multiplicador = 1.5;
                mensajeEfecto = "¡La habilidad fue efectiva! Puntaje x1.5";
            } else {
                multiplicador = 1.0;
                mensajeEfecto = "La habilidad no tuvo efecto en este minijuego.";
            }
        }
        
        int puntaje = (int) Math.round(potencial * 10.0 * multiplicador);
        
        monstruo.setUsado(true);
        minijuegosJugados[indiceMinijuego] = true;
        puntajesMinijuegos[indiceMinijuego] = puntaje;
        puntajeSesion += puntaje;
        
        historial.add(new RegistroHistorial(
                minijuego.getNombre(), monstruo.getTipo(), habilidadRegistrada, puntaje));
        
        return new String[] { mensajeHabilidad, mensajeEfecto, String.valueOf(puntaje) };
    }
    
    public ArrayList<Monstruo> getMonstruos() {
        return monstruos;
    }
    
    public LinkedList<RegistroHistorial> getHistorial() {
        return historial;
    }
    
    public ArrayList<Jugador> obtenerRankingOrdenado() {
        ArrayList<Jugador> ranking = new ArrayList<>();
        for (Jugador j : jugadores) {
            ranking.add(j);
        }
        for (int i = 0; i < ranking.size() - 1; i++) {
            for (int j = 0; j < ranking.size() - 1 - i; j++) {
                Jugador actual = ranking.get(j);
                Jugador siguiente = ranking.get(j + 1);
                
                boolean debeIntercambiar = false;
                if (siguiente.getPuntajeMaximo() > actual.getPuntajeMaximo()) {
                    debeIntercambiar = true;
                } else if (siguiente.getPuntajeMaximo() == actual.getPuntajeMaximo()
                        && siguiente.getNombre().compareToIgnoreCase(actual.getNombre()) < 0) {
                    debeIntercambiar = true;
                }
                if (debeIntercambiar) {
                    ranking.set(j, siguiente);
                    ranking.set(j + 1, actual);
                }
            }
        }
        return ranking;
    }
    
    public ArrayList<String> obtenerLineasRegistros() {
        ArrayList<String> lineas = new ArrayList<>();
        for (Jugador j : jugadores) {
            lineas.add(j.aFormatoArchivo());
        }
        return lineas;
    }
    

}
