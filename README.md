# 1-DAW-Java-Project

Java programming project based on a simulation of the E-Sports League.

This was my final Java programming project in the first year of the WAD (Web Application Development) degree course. It was based on a simulation of the E-Sports League, with a few modifications.

Este fue mi proyecto final de programación con Java en el 1º año del grado DAW (Desarrollo de Aplicaciones Web). Basado en una simulación de la Liga E-Sports con algunas modificaciones.

---

## Características

Aplicación de consola con un menú interactivo que permite:

- **Gestión de equipos y plantillas** — alta, baja y consulta de equipos y de sus jugadores.
- **Gestión de personas** — jugadores y entrenadores, con roles y especialidades.
- **Calendario y partidos** — organización por jornadas y registro de resultados.
- **Incidencias y sanciones** — control de incidencias y jugadores sancionados.
- **Clasificación, estadísticas e historial** — puntos a favor/en contra, clasificación y consulta del historial.

## Estructura del proyecto

```
src/
├── module-info.java
└── _Liga_E_Sports_/
    ├── Principal.java          # Punto de entrada y menús
    ├── Liga.java               # Lógica central de la liga
    ├── Equipo.java
    ├── Partido.java
    ├── Incidencia.java
    ├── PersonaLiga.java        # Clase abstracta base
    ├── Jugador.java            # extends PersonaLiga implements Entrenable
    ├── Entrenador.java         # extends PersonaLiga
    ├── Entrenable.java         # Interfaz
    ├── Rol.java, Especialidad.java, TipoIncidencia.java
    └── *Exception.java         # 8 excepciones propias del dominio
```

El diseño usa **herencia** (`PersonaLiga` → `Jugador` / `Entrenador`), una **interfaz** (`Entrenable`) y **excepciones personalizadas** para cada regla de negocio.

## Requisitos

- **JDK 17 o superior** (el proyecto está configurado para Java 21 en Eclipse).
- Eclipse IDE (opcional, el proyecto incluye `.project` y `.classpath`).

## Compilar y ejecutar

### Opción 1: Eclipse

`File → Import → Existing Projects into Workspace` y selecciona la carpeta del repositorio. Después, `Run → Run` sobre `Principal.java`.

### Opción 2: Línea de comandos

El código fuente está en **UTF-8** (contiene acentos y la letra `ñ`), así que es **imprescindible** indicar la codificación al compilar en Windows:

```powershell
# Compilar
javac -encoding UTF-8 -d bin (Get-ChildItem src -Recurse -Filter *.java).FullName

# Ejecutar
java -cp bin _Liga_E_Sports_.Principal
```

En Linux/macOS:

```bash
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -cp bin _Liga_E_Sports_.Principal
```

> Si omites `-encoding UTF-8`, la compilación falla con `illegal character: '\u00b1'` en métodos como `añadirJugador()`.

## Autor

**Nacho** — [@NACH089](https://github.com/NACH089)

## Licencia

Este proyecto se distribuye bajo la licencia [MIT](LICENSE).
