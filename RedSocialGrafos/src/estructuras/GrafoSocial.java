package estructuras;

import modelo.Usuario;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Grafo simple, no dirigido y no ponderado.
 * La matriz indica con true si dos usuarios son amigos.
 */
public class GrafoSocial {
    private final Usuario[] usuarios;
    private final boolean[][] amistades;
    private int cantidadUsuarios;
    private int cantidadAmistades;

    public GrafoSocial(int capacidad) {
        usuarios = new Usuario[capacidad];
        amistades = new boolean[capacidad][capacidad];
    }

    public String registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId().isBlank() || usuario.getNombre().isBlank()) {
            return "Error: el ID y el nombre son obligatorios.";
        }
        if (buscarIndice(usuario.getId()) != -1) {
            return "Error: el usuario " + usuario.getId() + " ya esta registrado.";
        }
        if (cantidadUsuarios == usuarios.length) {
            return "Error: se alcanzo la capacidad de la red.";
        }
        usuarios[cantidadUsuarios++] = usuario;
        return "Usuario registrado correctamente.";
    }

    public String agregarAmistad(String idA, String idB) {
        int a = buscarIndice(idA);
        int b = buscarIndice(idB);

        if (a == -1) return "Error: el usuario " + idA + " no existe; registrelo primero.";
        if (b == -1) return "Error: el usuario " + idB + " no existe; registrelo primero.";
        if (a == b) return "Error: un usuario no puede tener una amistad consigo mismo.";
        if (amistades[a][b]) {
            return "La amistad entre " + usuarios[a].getNombre() + " y "
                    + usuarios[b].getNombre() + " ya existe.";
        }

        amistades[a][b] = true;
        amistades[b][a] = true;
        cantidadAmistades++;
        return "Amistad registrada correctamente.";
    }

    public boolean existeRelacion(String idA, String idB) {
        int a = buscarIndice(idA);
        int b = buscarIndice(idB);
        return a != -1 && b != -1 && amistades[a][b];
    }

    public List<Usuario> vecinos(String id) {
        List<Usuario> resultado = new ArrayList<>();
        int indice = buscarIndice(id);
        if (indice == -1) return resultado;

        for (int i = 0; i < cantidadUsuarios; i++) {
            if (amistades[indice][i]) resultado.add(usuarios[i]);
        }
        return resultado;
    }

    public int grado(String id) {
        return vecinos(id).size();
    }

    public List<Usuario> camino(String origen, String destino) {
        int inicio = buscarIndice(origen);
        int fin = buscarIndice(destino);
        if (inicio == -1 || fin == -1) return new ArrayList<>();

        int[] anterior = new int[cantidadUsuarios];
        Arrays.fill(anterior, -1);
        boolean[] visitado = new boolean[cantidadUsuarios];
        Deque<Integer> cola = new ArrayDeque<>();
        cola.add(inicio);
        visitado[inicio] = true;

        while (!cola.isEmpty()) {
            int actual = cola.remove();
            if (actual == fin) break;
            for (int vecino = 0; vecino < cantidadUsuarios; vecino++) {
                if (amistades[actual][vecino] && !visitado[vecino]) {
                    visitado[vecino] = true;
                    anterior[vecino] = actual;
                    cola.add(vecino);
                }
            }
        }

        if (!visitado[fin]) return new ArrayList<>();
        List<Usuario> resultado = new ArrayList<>();
        for (int actual = fin; actual != -1; actual = anterior[actual]) {
            resultado.add(0, usuarios[actual]);
        }
        return resultado;
    }

    public List<Usuario> aislados() {
        List<Usuario> resultado = new ArrayList<>();
        for (int i = 0; i < cantidadUsuarios; i++) {
            if (grado(usuarios[i].getId()) == 0) resultado.add(usuarios[i]);
        }
        return resultado;
    }

    public Usuario conMayorGrado() {
        if (cantidadUsuarios == 0) return null;
        Usuario mayor = usuarios[0];
        for (int i = 1; i < cantidadUsuarios; i++) {
            if (grado(usuarios[i].getId()) > grado(mayor.getId())) mayor = usuarios[i];
        }
        return mayor;
    }

    public int sumaGrados() {
        int suma = 0;
        for (int i = 0; i < cantidadUsuarios; i++) suma += grado(usuarios[i].getId());
        return suma;
    }

    public boolean esConexo() {
        return cantidadUsuarios > 0 && contarVisitados(0) == cantidadUsuarios;
    }

    public int cantidadComponentes() {
        int componentes = 0;
        boolean[] visitado = new boolean[cantidadUsuarios];
        for (int i = 0; i < cantidadUsuarios; i++) {
            if (!visitado[i]) {
                componentes++;
                marcarComponente(i, visitado);
            }
        }
        return componentes;
    }

    public boolean existeCiclo() {
        boolean[] visitado = new boolean[cantidadUsuarios];
        for (int i = 0; i < cantidadUsuarios; i++) {
            if (!visitado[i] && tieneCiclo(i, -1, visitado)) return true;
        }
        return false;
    }

    public Usuario[] getUsuarios() {
        return Arrays.copyOf(usuarios, cantidadUsuarios);
    }

    public int getCantidadAmistades() {
        return cantidadAmistades;
    }

    private int buscarIndice(String id) {
        if (id == null) return -1;
        for (int i = 0; i < cantidadUsuarios; i++) {
            if (usuarios[i].getId().equalsIgnoreCase(id.trim())) return i;
        }
        return -1;
    }

    private int contarVisitados(int inicio) {
        boolean[] visitado = new boolean[cantidadUsuarios];
        marcarComponente(inicio, visitado);
        int total = 0;
        for (boolean valor : visitado) if (valor) total++;
        return total;
    }

    private void marcarComponente(int inicio, boolean[] visitado) {
        Deque<Integer> pendientes = new ArrayDeque<>();
        pendientes.add(inicio);
        visitado[inicio] = true;
        while (!pendientes.isEmpty()) {
            int actual = pendientes.remove();
            for (int vecino = 0; vecino < cantidadUsuarios; vecino++) {
                if (amistades[actual][vecino] && !visitado[vecino]) {
                    visitado[vecino] = true;
                    pendientes.add(vecino);
                }
            }
        }
    }

    private boolean tieneCiclo(int actual, int padre, boolean[] visitado) {
        visitado[actual] = true;
        for (int vecino = 0; vecino < cantidadUsuarios; vecino++) {
            if (!amistades[actual][vecino]) continue;
            if (!visitado[vecino] && tieneCiclo(vecino, actual, visitado)) return true;
            if (visitado[vecino] && vecino != padre) return true;
        }
        return false;
    }
}
