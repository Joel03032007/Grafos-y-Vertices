# Conexión Ambato · Fase 2

Aplicación de consola en Java para administrar personas, amistades recíprocas y seguimientos dirigidos con listas de adyacencia enlazadas mediante nodos propios. Se reutiliza el modelo `Usuario` de la Fase 1 y se mantienen los paquetes `app`, `estructuras` y `modelo`. El estado inicial sigue la Tabla 1 del informe de Fase 2.

## Compilar y ejecutar

Desde la carpeta `Fase2`, en PowerShell, con JDK 17 o posterior:

```powershell
chcp 65001
$archivosJava = Get-ChildItem -Path src,test -Recurse -Filter *.java
javac --release 17 -encoding UTF-8 -d out $archivosJava.FullName
java -cp out app.Main
```

El menú empieza con los seis usuarios de la guía y permite ingresar datos y cambiar sus relaciones durante la ejecución. Usa un ID único sin espacios ni comas, por ejemplo `A07`. Los cambios son en memoria y al reiniciar se carga el ejemplo inicial. La opción 12 ejecuta la demostración en una red independiente.

## Demostración y pruebas

```powershell
java -cp out app.Main --demo
java -cp out app.PruebasGrafo
```

La demostración imprime los ocho pasos de la Tabla 3. Las pruebas comprueban esos resultados y validan duplicados, lazos, limpieza de referencias y la independencia entre amistad y seguimiento.

## Archivos

- `src/app/` contiene el menú, los datos de ejemplo y la demostración.
- `src/estructuras/GrafoSocial.java` implementa las listas enlazadas y las operaciones.
- `src/modelo/` contiene el usuario y los tipos de relación.
- `test/app/PruebasGrafo.java` contiene las pruebas automatizadas.
