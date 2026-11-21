package app;

import estructuras.GrafoSocial;
import java.util.Objects;

public class PruebasGrafo {
    private static int aprobadas;

    public static void main(String[] args) {
        GrafoSocial red = ConexionAmbato.cargar(null);
        comprobar("1. Seis usuarios y cinco amistades", true,
                red.mostrarRed().startsWith("Personas: 6 | Amistades: 5 | Seguimientos: 0\n"));
        int[] grados = {2, 2, 2, 3, 1, 0};
        for (int i = 0; i < grados.length; i++) {
            String id = "A0" + (i + 1);
            comprobar("Grado de " + id, grados[i], red.contarAmigos(id));
        }

        comprobar("2. Mateo sigue a Sofía", true, red.seguir("A02", "A01"));
        comprobar("Seguidor de Sofía", "Mateo", red.listarSeguidores("A01"));
        comprobar("El seguimiento no es recíproco", "", red.listarSeguidos("A01"));
        comprobar("3. Diego sigue a Sofía", true, red.seguir("A06", "A01"));
        comprobar("Seguidores de Sofía", "Diego, Mateo", red.listarSeguidores("A01"));
        comprobar("Seguidos de Diego", "Sofía", red.listarSeguidos("A06"));
        comprobar("Diego sigue sin amistades", 0, red.contarAmigos("A06"));
        comprobar("4. Amigos de Andrés", "Camila, Sofía, Valeria", red.listarAmigos("A04"));
        comprobar("5. Eliminar amistad", true, red.eliminarAmistad("A04", "A05"));
        comprobar("Extremo Andrés", "Sofía, Valeria", red.listarAmigos("A04"));
        comprobar("Extremo Camila", "", red.listarAmigos("A05"));
        comprobar("6. Eliminar a Sofía", true, red.eliminarPersona("A01"));
        comprobar("Usuario eliminado", null, red.listarAmigos("A01"));
        comprobar("Seguimiento de Mateo retirado", "", red.listarSeguidos("A02"));
        comprobar("Seguimiento de Diego retirado", "", red.listarSeguidos("A06"));
        comprobar("Amistad de Mateo retirada", "Valeria", red.listarAmigos("A02"));
        comprobar("Amistad de Andrés retirada", "Valeria", red.listarAmigos("A04"));
        comprobar("7. ID duplicado con distinta capitalización", false,
                red.registrarPersona("a02", "Otro nombre"));
        comprobar("8. Autoamistad", false, red.agregarAmistad("A03", "A03"));

        comprobar("ID con espacio", false, red.registrarPersona("A 07", "Lucía"));
        comprobar("ID con coma", false, red.registrarPersona("A,07", "Lucía"));
        comprobar("ID vacío", false, red.registrarPersona("", "Lucía"));
        comprobar("Registro de una séptima cuenta", true, red.registrarPersona("A07", "Lucía"));
        comprobar("Mismo vínculo, tipo distinto", true, red.seguir("a02", "a03"));
        comprobar("Seguimiento duplicado", false, red.seguir("A02", "A03"));
        comprobar("Amistad duplicada en sentido inverso", false, red.agregarAmistad("A03", "A02"));
        comprobar("Destino inexistente", false, red.seguir("A02", "A99"));
        comprobar("Autoseguimiento", false, red.seguir("A02", "A02"));
        comprobar("Dejar de seguir", true, red.dejarDeSeguir("A02", "A03"));
        comprobar("Seguidores actualizados", "", red.listarSeguidores("A03"));
        comprobar("La amistad se conserva", "Valeria", red.listarAmigos("A02"));
        comprobar("Eliminar vértice de cabeza", true, red.eliminarPersona("A07"));
        comprobar("Contar amigos de usuario inexistente", -1, red.contarAmigos("A07"));
        System.out.println("\nTotal: " + aprobadas + " comprobaciones aprobadas.");
    }

    private static void comprobar(String caso, Object esperado, Object obtenido) {
        if (!Objects.equals(esperado, obtenido)) {
            throw new AssertionError(caso + ": esperado [" + esperado + "], obtenido [" + obtenido + "]");
        }
        aprobadas++;
        System.out.println("OK | " + caso);
    }
}
