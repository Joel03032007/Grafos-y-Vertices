package fase2.estructuras;

import java.util.Locale;
import java.util.StringJoiner;
import fase2.modelo.TipoRelacion;
import fase2.modelo.Usuario;

import static fase2.modelo.TipoRelacion.AMISTAD;
import static fase2.modelo.TipoRelacion.SEGUIR;

/** Multilista de vértices con aristas enlazadas por origen. */
public class GrafoSocial {
    private static final class Vertice {
        final Usuario persona;
        final String clave;
        Vertice sig;
        Arista aristas;

        Vertice(String clave, Usuario persona, Vertice sig) {
            this.clave = clave;
            this.persona = persona;
            this.sig = sig;
        }
    }

    private static final class Arista {
        final Vertice destino;
        final TipoRelacion tipo;
        Arista sig;

        Arista(Vertice destino, TipoRelacion tipo, Arista sig) {
            this.destino = destino;
            this.tipo = tipo;
            this.sig = sig;
        }
    }

    private Vertice cabeza;

    public boolean registrarPersona(String usuario, String nombre) {
        if (usuario == null || usuario.isBlank() || nombre == null || nombre.isBlank()
                || usuario.indexOf(',') >= 0
                || usuario.codePoints().anyMatch(c -> Character.isWhitespace(c)
                        || Character.isSpaceChar(c))
                || buscar(usuario) != null) {
            return false;
        }
        cabeza = new Vertice(normalizar(usuario), new Usuario(usuario, nombre), cabeza);
        return true;
    }

    public boolean eliminarPersona(String usuario) {
        Vertice objetivo = buscar(usuario);
        if (objetivo == null) return false;

        Vertice anterior = null;
        for (Vertice v = cabeza; v != objetivo; v = v.sig) {
            quitarHacia(v, objetivo);
            anterior = v;
        }
        for (Vertice v = objetivo.sig; v != null; v = v.sig) {
            quitarHacia(v, objetivo);
        }

        if (anterior == null) cabeza = objetivo.sig;
        else anterior.sig = objetivo.sig;
        return true;
    }

    public boolean agregarAmistad(String a, String b) {
        return agregarRelacion(a, b, AMISTAD);
    }

    public boolean seguir(String a, String b) {
        return agregarRelacion(a, b, SEGUIR);
    }

    private boolean agregarRelacion(String a, String b, TipoRelacion tipo) {
        Vertice origen = buscar(a);
        Vertice destino = buscar(b);
        if (origen == null || destino == null || origen == destino
                || existe(origen, destino, tipo)) return false;

        insertar(origen, destino, tipo);
        if (tipo == AMISTAD) insertar(destino, origen, tipo);
        return true;
    }

    public boolean eliminarAmistad(String a, String b) {
        Vertice origen = buscar(a);
        Vertice destino = buscar(b);
        if (origen == null || destino == null || !quitarAristas(origen, destino, AMISTAD)) {
            return false;
        }
        quitarAristas(destino, origen, AMISTAD);
        return true;
    }

    public boolean dejarDeSeguir(String a, String b) {
        Vertice origen = buscar(a);
        Vertice destino = buscar(b);
        return origen != null && destino != null && quitarAristas(origen, destino, SEGUIR);
    }

    public String listarAmigos(String usuario) {
        Vertice v = buscar(usuario);
        return v == null ? null : listarSalientes(v, AMISTAD);
    }

    public String listarSeguidos(String usuario) {
        Vertice v = buscar(usuario);
        return v == null ? null : listarSalientes(v, SEGUIR);
    }

    public String listarSeguidores(String usuario) {
        Vertice destino = buscar(usuario);
        if (destino == null) return null;

        StringJoiner nombres = new StringJoiner(", ");
        for (Vertice v = cabeza; v != null; v = v.sig) {
            if (existe(v, destino, SEGUIR)) nombres.add(v.persona.getNombre());
        }
        return nombres.toString();
    }

    public int contarAmigos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) return -1;

        int cantidad = 0;
        for (Arista a = v.aristas; a != null; a = a.sig) {
            if (a.tipo == AMISTAD) cantidad++;
        }
        return cantidad;
    }

    public String mostrarRed() {
        int personas = 0;
        int aristasAmistad = 0;
        int seguimientos = 0;
        StringBuilder filas = new StringBuilder();
        for (Vertice v = cabeza; v != null; v = v.sig) {
            personas++;
            for (Arista a = v.aristas; a != null; a = a.sig) {
                if (a.tipo == AMISTAD) aristasAmistad++;
                else seguimientos++;
            }
            filas.append(v.persona).append(" | amigos: [")
                    .append(listarSalientes(v, AMISTAD)).append("] | sigue a: [")
                    .append(listarSalientes(v, SEGUIR)).append("]\n");
        }
        return "Personas: " + personas + " | Amistades: " + aristasAmistad / 2
                + " | Seguimientos: " + seguimientos + "\n" + filas;
    }

    private static String normalizar(String usuario) {
        return usuario.strip().toLowerCase(Locale.ROOT);
    }

    private Vertice buscar(String usuario) {
        if (usuario == null) return null;
        String clave = normalizar(usuario);
        for (Vertice v = cabeza; v != null; v = v.sig) {
            if (v.clave.equals(clave)) return v;
        }
        return null;
    }

    private static boolean existe(Vertice origen, Vertice destino, TipoRelacion tipo) {
        for (Arista a = origen.aristas; a != null; a = a.sig) {
            if (a.destino == destino && a.tipo == tipo) return true;
        }
        return false;
    }

    private static void insertar(Vertice origen, Vertice destino, TipoRelacion tipo) {
        origen.aristas = new Arista(destino, tipo, origen.aristas);
    }

    private static void quitarHacia(Vertice origen, Vertice destino) {
        quitarAristas(origen, destino, null);
    }

    /** Un tipo nulo elimina todas las relaciones hacia el destino. */
    private static boolean quitarAristas(Vertice origen, Vertice destino, TipoRelacion tipo) {
        Arista anterior = null;
        boolean huboCambios = false;
        for (Arista actual = origen.aristas; actual != null; actual = actual.sig) {
            if (actual.destino == destino && (tipo == null || actual.tipo == tipo)) {
                if (anterior == null) origen.aristas = actual.sig;
                else anterior.sig = actual.sig;
                huboCambios = true;
            } else {
                anterior = actual;
            }
        }
        return huboCambios;
    }

    private static String listarSalientes(Vertice origen, TipoRelacion tipo) {
        StringJoiner nombres = new StringJoiner(", ");
        for (Arista a = origen.aristas; a != null; a = a.sig) {
            if (a.tipo == tipo) nombres.add(a.destino.persona.getNombre());
        }
        return nombres.toString();
    }
}
