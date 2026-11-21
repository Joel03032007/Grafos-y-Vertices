# Red social "Club de Lectura"

Implementacion en Java del APE 4 - Fase 1 de Estructura de Datos. El programa representa una red social como un grafo simple, no dirigido y no ponderado.

## Reto representado

La red tiene 6 vertices y 5 aristas:

- `U1 Ana`, `U2 Bruno`, `U3 Carla`, `U4 David`, `U5 Elisa`, `U6 Fernando`.
- `e1 = {Ana, Bruno}`
- `e2 = {Bruno, Carla}`
- `e3 = {Carla, David}`
- `e4 = {David, Ana}`
- `e5 = {Ana, Elisa}`

Con esta informacion se cumplen las condiciones:

- Ciclo: Ana -> Bruno -> Carla -> David -> Ana.
- Vertice aislado: Fernando, con grado 0.
- Mayor grado: Ana, con grado 3.
- Componentes: dos; el grupo conectado y Fernando por separado.
- Suma de grados: `3 + 2 + 2 + 2 + 1 + 0 = 10 = 2 x 5`.

## Organizacion de carpetas

```text
RedSocialGrafos/
|- src/
|  |- app/
|  |  `- Main.java              # Menu e ingreso de datos
|  |- estructuras/
|  |  `- GrafoSocial.java       # Matriz, relaciones y recorridos
|  `- modelo/
|     `- Usuario.java           # Datos de cada vertice
|- out/                         # Clases compiladas, no se versiona
`- README.md
```

Separar `modelo`, `estructuras` y `app` ayuda a entender el proyecto sin mezclar los datos, la logica del grafo y la interfaz de consola.

## Funcionalidades del menu

1. Registrar usuarios nuevos.
2. Agregar amistades y rechazar lazos, duplicados o usuarios inexistentes.
3. Consultar si existe una relacion.
4. Mostrar vecinos.
5. Consultar el grado de todos los usuarios.
6. Buscar un camino usando recorrido en anchura (BFS).
7. Mostrar resumen: ciclos, aislados, componentes, conectividad y suma de grados.
8. Listar usuarios.

## Compilar y ejecutar

El proyecto esta configurado para JDK 26. Comprueba que `java -version` y
`javac -version` indiquen la version 26. Estos comandos solo consultan la version;
no cambian la configuracion. En VS Code, selecciona JDK 26 para el proyecto desde
`Java: Configure Java Runtime`.

Desde la carpeta raiz del repositorio:

```powershell
$archivos = Get-ChildItem -Recurse -Filter *.java RedSocialGrafos\src | ForEach-Object { $_.FullName }
javac --release 26 -encoding UTF-8 -d RedSocialGrafos\out $archivos
java '-Dfile.encoding=UTF-8' -cp RedSocialGrafos\out app.Main
```

La opcion `--release 26` genera clases que requieren Java 26 o superior para
ejecutarse; no se pueden ejecutar con Java 17, 21 o 25. Si aparece
`UnsupportedClassVersionError`, comprueba que el Java utilizado para ejecutar
sea 26 o superior. La configuracion del proyecto en `.classpath` y los ajustes
del compilador en `.settings/org.eclipse.jdt.core.prefs` tambien usan Java 26.

## Pruebas manuales sugeridas

- En la opcion 7 deben aparecer 6 vertices, 5 aristas, suma 10, ciclo `si`, conectividad `no`, 2 componentes y Fernando como aislado.
- En la opcion 2 prueba `U1` y `U1` para validar el lazo.
- Prueba `U1` y `U2` para validar una amistad duplicada.
- Prueba `U1` y `U9` para validar un usuario inexistente.

## Autor

Proyecto academico para Estructura de Datos - tercer semestre.

## Fase 2

[Conexión Ambato: código, instrucciones y pruebas](Fase2/README.md).

## Documentación

[Informe de la Fase 2 en Word](docs/Informe_APE4_Fase2.docx).
