package app;

import estructuras.GrafoSocial;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--demo")) {
            Demostracion.ejecutar();
            return;
        }

        GrafoSocial red = ConexionAmbato.cargar(null);
        System.out.println("=== CONEXIÓN AMBATO | FASE 2 ===");
        System.out.print(red.mostrarRed());

        try (Scanner entrada = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {
                mostrarMenu();
                if (!entrada.hasNextLine()) return;
                String opcion = entrada.nextLine().strip();
                switch (opcion) {
                    case "1" -> informar(red.registrarPersona(
                            leer(entrada, "ID"), leer(entrada, "Nombre")));
                    case "2" -> informar(red.eliminarPersona(leer(entrada, "ID")));
                    case "3" -> informar(red.agregarAmistad(
                            leer(entrada, "ID de origen"), leer(entrada, "ID de destino")));
                    case "4" -> informar(red.eliminarAmistad(
                            leer(entrada, "ID de origen"), leer(entrada, "ID de destino")));
                    case "5" -> informar(red.seguir(
                            leer(entrada, "ID de quien sigue"), leer(entrada, "ID de la persona seguida")));
                    case "6" -> informar(red.dejarDeSeguir(
                            leer(entrada, "ID de quien sigue"), leer(entrada, "ID de la persona seguida")));
                    case "7" -> mostrarLista(red.listarAmigos(leer(entrada, "ID")));
                    case "8" -> mostrarLista(red.listarSeguidos(leer(entrada, "ID")));
                    case "9" -> mostrarLista(red.listarSeguidores(leer(entrada, "ID")));
                    case "10" -> {
                        int cantidad = red.contarAmigos(leer(entrada, "ID"));
                        System.out.println(cantidad < 0 ? "El usuario no existe." : "Amigos: " + cantidad);
                    }
                    case "11" -> System.out.print(red.mostrarRed());
                    case "12" -> Demostracion.ejecutar();
                    case "0" -> { return; }
                    default -> System.out.println("Opción no válida.");
                }
            }
        }
    }

    private static void mostrarMenu() {
        System.out.print("""

                1. Registrar persona       2. Eliminar persona
                3. Agregar amistad         4. Eliminar amistad
                5. Seguir                  6. Dejar de seguir
                7. Listar amigos           8. Listar seguidos
                9. Listar seguidores      10. Contar amigos
                11. Mostrar red           12. Demostración de la Tabla 3
                0. Salir
                Opción:\s""");
    }

    private static String leer(Scanner entrada, String etiqueta) {
        System.out.print(etiqueta + ": ");
        return entrada.hasNextLine() ? entrada.nextLine() : "";
    }

    private static void informar(boolean exito) {
        System.out.println(exito ? "Operación realizada."
                : "Operación rechazada. Comprueba los datos y el estado de la relación.");
    }

    private static void mostrarLista(String lista) {
        if (lista == null) System.out.println("El usuario no existe.");
        else if (lista.isEmpty()) System.out.println("Sin resultados.");
        else System.out.println(lista);
    }
}
