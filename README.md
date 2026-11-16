# Grafos-y-Vertices
# Red social «Los Enlazados» · Grafo en Java

APE 4 · Fase 1 | Reto de grafos y vértices por grupos
Universidad Técnica de Ambato · Facultad de Ingeniería en Sistemas, Electrónica e Industrial · Estructura de Datos

## Descripción

Implementación en Java de una red social modelada como un grafo simple, no dirigido y no ponderado G = (V, E), con 6 usuarios y 5 amistades.

- V = {U1 Lucía, U2 Andrés, U3 Camila, U4 Javier, U5 Renata, U6 Fabián}
- E = {e1 = {Lucía, Andrés}, e2 = {Andrés, Camila}, e3 = {Lucía, Camila}, e4 = {Lucía, Javier}, e5 = {Javier, Renata}}

La red cumple las condiciones del reto:

- Tiene un ciclo: Lucía, Andrés, Camila, Lucía.
- Tiene un vértice aislado: Fabián, con grado 0.
- Tiene un vértice de grado mayor o igual a 3: Lucía, con grado 3.

## Estructura del proyecto

```
RedSocialGrafos/
├── src/
│   ├── Usuario.java
│   ├── Grafo.java
│   └── Main.java
└── README.txt
```

| Archivo | Función |
|---|---|
| `Usuario.java` | Guarda el id y el nombre de cada usuario. |
| `Grafo.java` | Contiene el arreglo de usuarios, la capacidad, la cantidad y la matriz de adyacencia, junto con todas las operaciones del grafo. |
| `Main.java` | Construye la red y muestra las respuestas a las 6 preguntas guía. |

## Operaciones principales

| Método | Descripción |
|---|---|
| `agregarUsuario(usuario)` | Registra un usuario en la red. |
| `agregarAmistad(a, b)` | Crea una arista entre dos usuarios. Rechaza lazos, duplicados y usuarios inexistentes. |
| `existeRelacion(a, b)` | Indica si existe amistad entre dos usuarios. |
| `vecinos(nombre)` | Devuelve los amigos de un usuario. |
| `grado(nombre)` | Devuelve la cantidad de amigos de un usuario. |
| `sumaGrados()` | Suma los grados de todos los vértices. |
| `aislados()` | Devuelve los usuarios sin amistades. |
| `masAmigos()` | Devuelve el usuario con mayor grado. |
| `camino(origen, destino)` | Busca un camino entre dos usuarios (recorrido en anchura). |
| `esConexo()` | Indica si todos los usuarios están conectados. |
| `cantidadComponentes()` | Cuenta los grupos de usuarios conectados entre sí. |
| `existeCiclo()` | Indica si el grafo tiene al menos un ciclo. |

## Reglas de validación

- Lazo: `agregarAmistad(Javier, Javier)` muestra `Error: un usuario no puede tener una amistad consigo mismo.`
- Duplicado: `agregarAmistad(Camila, Andrés)` muestra `La amistad entre Andrés y Camila ya existe.`
- Usuario inexistente: `agregarAmistad(Lucía, U7)` muestra `El usuario U7 no existe; regístrelo primero.`

En los tres casos el grafo se mantiene en 6 vértices y 5 aristas.

## Requisitos

- JDK 17 o superior

## Compilación y ejecución

Desde la carpeta raíz del proyecto:

```bash
javac -encoding UTF-8 -d out src/*.java
java -Dfile.encoding=UTF-8 -cp out Main
```

## Resultados esperados

- Grados: Lucía 3, Andrés 2, Camila 2, Javier 2, Renata 1, Fabián 0
- Suma de grados: 10 = 2 × 5 aristas
- Existe ciclo: verdadero
- Es conexo: falso (2 componentes: los cinco conectados y Fabián solo)
- Camino Renata a Andrés: Renata, Javier, Lucía, Andrés
- Camino Renata a Fabián: no existe

## Autor

Joel Tisalema
Ingeniería de Software · 3.er curso, paralelo B
Universidad Técnica de Ambato
