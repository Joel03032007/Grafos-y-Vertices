package app;

import estructuras.GrafoSocial;
import java.io.PrintStream;

final class ConexionAmbato {
    private static final String[] NOMBRES = {"Sofía", "Mateo", "Valeria", "Andrés", "Camila", "Diego"};
    private static final String[][] AMISTADES = {
        {"A01", "A02"}, {"A02", "A03"}, {"A03", "A04"}, {"A04", "A01"}, {"A04", "A05"}
    };

    private ConexionAmbato() {}

    static GrafoSocial cargar(PrintStream salida) {
        GrafoSocial red = new GrafoSocial();
        for (int i = 0; i < NOMBRES.length; i++) {
            String id = "A0" + (i + 1);
            boolean registrado = red.registrarPersona(id, NOMBRES[i]);
            if (salida != null) {
                salida.printf("registrarPersona(%s, %s) = %b%n", id, NOMBRES[i], registrado);
            }
        }
        for (String[] extremos : AMISTADES) {
            boolean agregada = red.agregarAmistad(extremos[0], extremos[1]);
            if (salida != null) {
                salida.printf("agregarAmistad(%s, %s) = %b%n", extremos[0], extremos[1], agregada);
            }
        }
        return red;
    }
}
