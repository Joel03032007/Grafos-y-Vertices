package app;

import estructuras.GrafoSocial;
import modelo.Usuario;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner ENTRADA = new Scanner(System.in);

    public static void main(String[] args) {
        GrafoSocial red = crearRedInicial();
        System.out.println("\n=== RED SOCIAL: CLUB DE LECTURA ===");
        System.out.println("Grafo simple, no dirigido y no ponderado");
        System.out.println("Se cargaron " + red.getUsuarios().length + " usuarios y "
                + red.getCantidadAmistades() + " amistades.\n");

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            switch (opcion) {
                case 1 -> registrarUsuario(red);
                case 2 -> registrarAmistad(red);
                case 3 -> consultarRelacion(red);
                case 4 -> mostrarVecinos(red);
                case 5 -> mostrarGrados(red);
                case 6 -> mostrarCamino(red);
                case 7 -> mostrarResumen(red);
                case 8 -> listarUsuarios(red);
                case 0 -> System.out.println("Programa finalizado.");
                default -> System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private static GrafoSocial crearRedInicial() {
        GrafoSocial red = new GrafoSocial(20);
        red.registrarUsuario(new Usuario("U1", "Ana"));
        red.registrarUsuario(new Usuario("U2", "Bruno"));
        red.registrarUsuario(new Usuario("U3", "Carla"));
        red.registrarUsuario(new Usuario("U4", "David"));
        red.registrarUsuario(new Usuario("U5", "Elisa"));
        red.registrarUsuario(new Usuario("U6", "Fernando"));

        // Ciclo: Ana - Bruno - Carla - David - Ana.
        red.agregarAmistad("U1", "U2");
        red.agregarAmistad("U2", "U3");
        red.agregarAmistad("U3", "U4");
        red.agregarAmistad("U4", "U1");
        // Ana tiene grado 3 y Fernando queda aislado.
        red.agregarAmistad("U1", "U5");
        return red;
    }

    private static void mostrarMenu() {
        System.out.println("\n1. Registrar usuario");
        System.out.println("2. Agregar amistad");
        System.out.println("3. Consultar relacion");
        System.out.println("4. Mostrar vecinos");
        System.out.println("5. Consultar grado");
        System.out.println("6. Buscar camino");
        System.out.println("7. Mostrar resumen del grafo");
        System.out.println("8. Listar usuarios");
        System.out.println("0. Salir");
    }

    private static void registrarUsuario(GrafoSocial red) {
        String id = leerTexto("ID (ejemplo U7): ");
        String nombre = leerTexto("Nombre: ");
        System.out.println(red.registrarUsuario(new Usuario(id, nombre)));
    }

    private static void registrarAmistad(GrafoSocial red) {
        String primero = leerTexto("ID del primer usuario: ");
        String segundo = leerTexto("ID del segundo usuario: ");
        System.out.println(red.agregarAmistad(primero, segundo));
    }

    private static void consultarRelacion(GrafoSocial red) {
        String primero = leerTexto("ID del primer usuario: ");
        String segundo = leerTexto("ID del segundo usuario: ");
        System.out.println(red.existeRelacion(primero, segundo)
                ? "Verdadero: si existe la amistad."
                : "Falso: no existe la amistad o algun usuario no esta registrado.");
    }

    private static void mostrarVecinos(GrafoSocial red) {
        String id = leerTexto("ID del usuario: ");
        List<Usuario> vecinos = red.vecinos(id);
        if (vecinos.isEmpty()) {
            System.out.println("No tiene vecinos o el usuario no existe.");
            return;
        }
        System.out.println("Vecinos: " + vecinos);
    }

    private static void mostrarGrados(GrafoSocial red) {
        for (Usuario usuario : red.getUsuarios()) {
            System.out.println(usuario + " | grado: " + red.grado(usuario.getId()));
        }
    }

    private static void mostrarCamino(GrafoSocial red) {
        String origen = leerTexto("ID de origen: ");
        String destino = leerTexto("ID de destino: ");
        List<Usuario> camino = red.camino(origen, destino);
        System.out.println(camino.isEmpty() ? "No existe un camino entre esos usuarios." : "Camino: " + camino);
    }

    private static void mostrarResumen(GrafoSocial red) {
        Usuario mayor = red.conMayorGrado();
        System.out.println("\n--- RESUMEN ---");
        System.out.println("Vertices: " + red.getUsuarios().length);
        System.out.println("Aristas: " + red.getCantidadAmistades());
        System.out.println("Suma de grados: " + red.sumaGrados() + " = 2 x " + red.getCantidadAmistades());
        System.out.println("Tiene ciclo: " + (red.existeCiclo() ? "si" : "no"));
        System.out.println("Es conexo: " + (red.esConexo() ? "si" : "no"));
        System.out.println("Componentes: " + red.cantidadComponentes());
        System.out.println("Aislados: " + red.aislados());
        System.out.println("Mayor grado: " + mayor + " (" + red.grado(mayor.getId()) + ")");
    }

    private static void listarUsuarios(GrafoSocial red) {
        for (Usuario usuario : red.getUsuarios()) System.out.println(usuario);
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return ENTRADA.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero.");
            }
        }
    }
}
