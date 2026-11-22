package fase2.modelo;

/** Representa un vertice de la red social. */
public class Usuario {
    private final String id;
    private final String nombre;

    public Usuario(String id, String nombre) {
        this.id = id.trim().toUpperCase();
        this.nombre = nombre.trim();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
