package taller03;

//Nicolas Lucero - 22.221.136-0 - ICCI

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    private static final String ARCHIVO_MONSTRUOS = "Monstruos.txt";
    private static final String ARCHIVO_REGISTROS = "Registros.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Sistema sistema = new Sistema();

        cargarArchivoMonstruos(sistema);
        cargarArchivoRegistros(sistema);
        
        if (sistema.getCantidadMonstruos() == 0) {
            System.out.println("No hay monstruos validos cargados. Revisa Monstruos.txt antes de jugar.");
            scanner.close();
            return;
        }
        ejecutarMenuPrincipal(scanner, sistema);
        scanner.close();
    

	}

	private static void cargarArchivoMonstruos(Sistema sistema) {
	    File archivo = new File(ARCHIVO_MONSTRUOS);
	    if (!archivo.exists()) {
	        System.out.println("Advertencia: no se encontro " + ARCHIVO_MONSTRUOS + ".");
	        return;
	    }
	    int lineaNumero = 0;
        int validos = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineaNumero++;
                if (linea.strip().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(";");
                if (sistema.cargarMonstruo(partes)) {
                    validos++;
                } else {
                    System.out.println("Linea " + lineaNumero + " de " + ARCHIVO_MONSTRUOS + " descartada por formato o rango invalido.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer " + ARCHIVO_MONSTRUOS + ": " + e.getMessage());
        }

        System.out.println("Monstruos validos cargados: " + validos);
    }
	
	private static void cargarArchivoRegistros(Sistema sistema) {
        File archivo = new File(ARCHIVO_REGISTROS);
        if (!archivo.exists()) {
            System.out.println("Aviso: no se encontro " + ARCHIVO_REGISTROS + ". Se creara al guardar si corresponde.");
            return;
        }
        int lineaNumero = 0;
        int validos = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineaNumero++;
                if (linea.strip().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(";");
                if (sistema.cargarJugador(partes)) {
                    validos++;
                } else {
                    System.out.println("Linea " + lineaNumero + " de " + ARCHIVO_REGISTROS + " descartada por formato invalido.");
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error al leer " + ARCHIVO_REGISTROS + ": " + e.getMessage());
        }

        System.out.println("Registros validos cargados: " + validos);
    }
	private static void guardarArchivoRegistros(Sistema sistema) {
        ArrayList<String> lineas = sistema.obtenerLineasRegistros();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_REGISTROS))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Registros guardados en " + ARCHIVO_REGISTROS + ".");
        } catch (IOException e) {
            System.out.println("Error al guardar " + ARCHIVO_REGISTROS + ": " + e.getMessage());
        }
    }
	private static int leerEnteroEnRango(Scanner scanner, String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().strip();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Ingresa un numero entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Escribe un numero entero.");
            }
        }
    }
	
	private static void ejecutarMenuPrincipal(Scanner scanner, Sistema sistema) {
        boolean salirPrograma = false;

        while (!salirPrograma) {
            String nombreJugador = "";
            while (nombreJugador.isEmpty()) {
                System.out.print("\nIngresa tu nombre de jugador: ");
                nombreJugador = scanner.nextLine().strip();
                if (nombreJugador.isEmpty()) {
                    System.out.println("El nombre no puede estar vacio.");
                }
            }
            
            int recordPrevio = sistema.iniciarSesion(nombreJugador);
            if (recordPrevio >= 0) {
                System.out.println("Bienvenido de nuevo, " + sistema.getUsuarioActual()
                        + ". Tu record historico es: " + recordPrevio + " pts.");
            } else {
                System.out.println("Bienvenido, " + sistema.getUsuarioActual()
                        + ". Es tu primera partida registrada.");
            }
            
            boolean cerrarSesionActual = false;
            while (!cerrarSesionActual && !salirPrograma) {
                System.out.println("\n=== MENU DE SESION (" + sistema.getUsuarioActual()
                        + " | Puntaje sesion: " + sistema.getPuntajeSesion() + ") ===");
                System.out.println("1. Jugar minijuego");
                System.out.println("2. Ver catalogo de monstruos");
                System.out.println("3. Ver historial de la sesion");
                System.out.println("4. Ver ranking de jugadores");
                System.out.println("5. Cerrar sesion");
                System.out.println("6. Salir del programa");

                int opcion = leerEnteroEnRango(scanner, "Elige una opcion: ", 1, 6);
                
                switch (opcion) {
                case 1:
                	menuJugarMinijuego(scanner, sistema);
                    break;
                case 2:
                    mostrarCatalogoMonstruos(sistema);
                    break;
                case 3:
                    mostrarHistorialSesion(sistema);
                    break;
                case 4:
                    mostrarRankingJugadores(sistema);
                    break;
                case 5:
                    boolean actualizado = sistema.finalizarSesion();
                    if (actualizado) {
                        guardarArchivoRegistros(sistema);
                    } else {
                        System.out.println("Sesion cerrada sin nuevos records para guardar.");
                    }
                    cerrarSesionActual = true;
                    break;
                case 6:
                    boolean actualizadoSalir = sistema.finalizarSesion();
                    if (actualizadoSalir) {
                        guardarArchivoRegistros(sistema);
                    }
                    System.out.println("¡Hasta luego!");
                    salirPrograma = true;
                    break;
                default:
                    break;
                }
            }
        }
    }
	
	private static void menuJugarMinijuego(Scanner scanner, Sistema sistema) {
        ArrayList<Monstruo> disponibles = sistema.getMonstruosDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("Ya no tienes monstruos disponibles en esta sesion.");
            return;
        }
        
        boolean hayMinijuegosPendientes = false;
        System.out.println("\n--- Minijuegos ---");
        for (int i = 0; i < sistema.getCantidadMinijuegos(); i++) {
            Minijuego mj = sistema.getMinijuego(i);
            if (sistema.estaMinijuegoJugado(i)) {
                System.out.println((i + 1) + ". " + mj.getNombre()
                        + " [JUGADO - " + sistema.getPuntajeMinijuego(i) + " pts]");
            } else {
                System.out.println((i + 1) + ". " + mj.getNombre()
                        + " (" + mj.getDescripcion() + ")");
                hayMinijuegosPendientes = true;
            }
        }
        
        if (!hayMinijuegosPendientes) {
            System.out.println("Ya completaste todos los minijuegos en esta sesion.");
            return;
        }
        
        int indiceElegido;
        while (true) {
            indiceElegido = leerEnteroEnRango(scanner, "Selecciona un minijuego (1-4): ", 1, sistema.getCantidadMinijuegos()) - 1;
            if (!sistema.estaMinijuegoJugado(indiceElegido)) {
                break;
            }
            System.out.println("Ese minijuego ya fue jugado en esta sesion. Elige otro.");
        }
        
        Minijuego minijuego = sistema.getMinijuego(indiceElegido);

        System.out.println("\n--- Monstruos disponibles ---");
        for (int i = 0; i < disponibles.size(); i++) {
            Monstruo m = disponibles.get(i);
            double potencial = sistema.calcularPotencial(m, minijuego);
            double potencialRedondeado = Math.round(potencial * 100.0) / 100.0;
            System.out.println((i + 1) + ". " + m.obtenerResumenAtributos() + " | Potencial: " + potencialRedondeado);
        }
        
        int idxMonstruo = leerEnteroEnRango(scanner, "Elige un monstruo: ", 1, disponibles.size()) - 1;
        Monstruo monstruoElegido = disponibles.get(idxMonstruo);

        ArrayList<String> habilidades = sistema.obtenerHabilidadesDisponibles(monstruoElegido);
        String habilidadElegida = "Ninguna";
        
        if (!habilidades.isEmpty()) {
            System.out.println("\n--- Habilidades disponibles ---");
            for (int i = 0; i < habilidades.size(); i++) {
                System.out.println((i + 1) + ". " + habilidades.get(i));
            }
            System.out.println((habilidades.size() + 1) + ". No usar habilidad");

            int opcionHab = leerEnteroEnRango(scanner, "Elige una opcion: ", 1, habilidades.size() + 1);
            if (opcionHab <= habilidades.size()) {
                habilidadElegida = habilidades.get(opcionHab - 1);
            }
        }
        else {
            System.out.println("Este monstruo no posee habilidades especiales.");
        }
        String[] resultado = sistema.jugarMinijuego(indiceElegido, monstruoElegido, habilidadElegida);
        if (!resultado[0].isEmpty()) {
            System.out.println("\n" + resultado[0]);
        }
        if (!resultado[1].isEmpty()) {
            System.out.println(resultado[1]);
        }
        System.out.println("Puntaje obtenido en el minijuego: " + resultado[2] + " pts.");
    }
	
	private static void mostrarCatalogoMonstruos(Sistema sistema) {
        ArrayList<Monstruo> lista = sistema.getMonstruos();
        System.out.println("\n--- Catalogo de Monstruos ---");
        for (int i = 0; i < lista.size(); i++) {
            Monstruo m = lista.get(i);
            String estado;
            if (m.isUsado()) {
                estado = "Usado en sesion";
            } else {
                estado = "Disponible";
            }
            System.out.println((i + 1) + ". " + m.obtenerResumenAtributos() + " [" + estado + "]");
        }
    }
        
	private static void mostrarHistorialSesion(Sistema sistema) {
        LinkedList<RegistroHistorial> hist = sistema.getHistorial();
        System.out.println("\n--- Historial de la Sesion ---");
        if (hist.isEmpty()) {
            System.out.println("Aun no has jugado minijuegos en esta sesion.");
            return;
        }
        int numero = 1;
        for (RegistroHistorial reg : hist) {
            System.out.println(numero + ". " + reg.obtenerResumen());
            numero++;
        }
    }
	
	private static void mostrarRankingJugadores(Sistema sistema) {
        ArrayList<Jugador> ranking = sistema.obtenerRankingOrdenado();
        System.out.println("\n--- Ranking Historico ---");
        if (ranking.isEmpty()) {
            System.out.println("No hay jugadores registrados todavia.");
            return;
            
        }
        for (int i = 0; i < ranking.size(); i++) {
            Jugador j = ranking.get(i);
            System.out.println((i + 1) + ". " + j.getNombre() + " - " + j.getPuntajeMaximo() + " pts");
        }
        
	}
	
	

}
