package fase2.app;

import fase2.estructuras.GrafoSocial;
import java.util.StringJoiner;

final class Demostracion {
    private Demostracion() {}

    static void ejecutar() {
        System.out.println("=== CONEXIÓN AMBATO | TABLA 3 ===");
        System.out.println("Paso 1. Registrar personas y amistades iniciales");
        GrafoSocial red = ConexionAmbato.cargar(System.out);
        System.out.print(red.mostrarRed());

        StringJoiner grados = new StringJoiner(", ");
        for (int i = 1; i <= 6; i++) grados.add(Integer.toString(red.contarAmigos("A0" + i)));
        System.out.println("Grados de A01 a A06: " + grados);

        System.out.println("\nPaso 2. seguir(A02, A01) = " + red.seguir("A02", "A01"));
        System.out.println("listarSeguidores(A01) = [" + red.listarSeguidores("A01") + "]");
        System.out.println("\nPaso 3. seguir(A06, A01) = " + red.seguir("A06", "A01"));
        System.out.println("listarSeguidos(A06) = [" + red.listarSeguidos("A06") + "]");
        System.out.println("listarSeguidores(A01) = [" + red.listarSeguidores("A01") + "]");
        System.out.println("contarAmigos(A06) = " + red.contarAmigos("A06"));

        System.out.println("\nPaso 4. listarAmigos(A04) = [" + red.listarAmigos("A04") + "]");
        System.out.println("contarAmigos(A04) = " + red.contarAmigos("A04"));
        System.out.println("\nPaso 5. eliminarAmistad(A04, A05) = " + red.eliminarAmistad("A04", "A05"));
        System.out.println("listarAmigos(A04) = [" + red.listarAmigos("A04") + "]");
        System.out.println("listarAmigos(A05) = [" + red.listarAmigos("A05") + "]");

        System.out.println("\nPaso 6. eliminarPersona(A01) = " + red.eliminarPersona("A01"));
        System.out.print(red.mostrarRed());
        System.out.println("listarSeguidores(A01) = " + red.listarSeguidores("A01"));
        System.out.println("\nPaso 7. registrarPersona(A02, Otro nombre) = "
                + red.registrarPersona("A02", "Otro nombre"));
        System.out.println("Paso 8. agregarAmistad(A03, A03) = " + red.agregarAmistad("A03", "A03"));
    }
}
