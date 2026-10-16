package _Liga_E_Sports_;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Liga {
	private String nombre;
	private ArrayList<Equipo> equipos;
	private ArrayList<Partido> partidos;
	private ArrayList<Incidencia> incidencias;
	private ArrayList<PersonaLiga> personas;
	private Queue<Partido> partidosPendientes;
	private Stack<String> historialAcciones;
	private Equipo campeon;
	private Partido[][] calendario;
	private static final int MAX_EQUIPOS = 6;

	public Liga(String nombre) {
		this.nombre = nombre;
		this.equipos = new ArrayList<>();
		this.partidos = new ArrayList<>();
		this.incidencias = new ArrayList<>();
		this.personas = new ArrayList<>();
		this.partidosPendientes = new LinkedList<>();
		this.historialAcciones = new Stack<>();
		this.campeon = null;
		this.calendario = null;
	}
	
	public void añadirEquipo(Equipo equipo) throws EquipoDuplicadoException {
		if (equipos.size() >= MAX_EQUIPOS) {
			System.out.println("No se pueden añadir más de " + MAX_EQUIPOS + " equipos");
			return;
		}
		if (equipo != null) {
			for (Equipo e : equipos) {
				if (e.getNombre().equals(equipo.getNombre())) {
					throw new EquipoDuplicadoException("Ya existe un equipo con ese nombre");
				}
			}
			equipos.add(equipo);
			historialAcciones.push(" - Equipo añadido: " + equipo.getNombre());
		}
	}
	
	public void añadirPersona(PersonaLiga persona) throws PersonaDuplicadaException {
		if (persona != null) {
			for (PersonaLiga p : personas) {
				if (p.getId().equals(persona.getId())) {
					throw new PersonaDuplicadaException("Ya existe una persona con ese identificador");
				}
			}
			personas.add(persona);
			historialAcciones.push("Persona añadida: " + persona.getNombre());
		}
	}
	
	public void crearCalendario(int jornadas, int partidosPorJornada) {
		calendario = new Partido[jornadas][partidosPorJornada];
	} /* Este método crea una matriz bidimensional de objetos Partido, donde:
			- Cada fila representa una jornada.
			- Cada columna representa un partido dentro de esa jornada. */
	
	
	public void añadirPartidoACalendario(int jornada, int posicion, Partido partido) {
		if (calendario != null &&
			jornada >= 0 && jornada < calendario.length &&
			posicion >= 0 && posicion < calendario[jornada].length) {
			calendario[jornada][posicion] = partido;
		}
	} /* Este método permite insertar un partido en una posición concreta del calendario. Antes de hacerlo, comprueba que:
			- El calendario existe.
			- La jornada es válida.
			- La posición es válida.
		 Si todo es correcto, el partido se almacena en la matriz. Es una forma segura de trabajar con arrays bidimensionales. */
	
	
	public String mostrarCalendario() {
		String texto = "\nCALENDARIO:\n";
		if (calendario == null) {
			return texto + " - Calendario no creado\n";
		}
		for (int i = 0; i < calendario.length; i++) {
			texto += "Jornada " + (i + 1) + ":\n";
			for (int j = 0; j < calendario[i].length; j++) {
				if (calendario[i][j] != null) {
					texto += " - " + calendario[i][j].getId() + ": " +
							calendario[i][j].getEquipoLocal().getNombre() +
							" vs " +
							calendario[i][j].getEquipoVisitante().getNombre() + "\n";
				} else {
					texto += " - (sin partido)\n";
				}
			}
			texto += "\n";
		}
		return texto;
	}
	
	public String mostrarJornada(int jornada) {
		String texto = "\nJORNADA " + jornada + ":\n";
		if (calendario == null) {
			return texto + " - Calendario no creado\n"; // Comprueba si existe el calendario.
		}
		if (jornada < 1 || jornada > calendario.length) {
			return texto + " - Jornada no válida\n"; // Comprueba que la jornada sea válida.
		}
		int indiceJornada = jornada - 1; // Convertir a índice del array (las jornadas empiezan en 1, pero los arrays empiezan en 0).
		for (int i = 0; i < calendario[indiceJornada].length; i++) { // Recorre todas las posiciones de esa jornada.
			if (calendario[indiceJornada][i] != null) { // Verifica que en esa posición haya un objeto Partido.
				texto += " - " + calendario[indiceJornada][i].getId() + ": " +
						calendario[indiceJornada][i].getEquipoLocal().getNombre() +
						" vs " +
						calendario[indiceJornada][i].getEquipoVisitante().getNombre(); // Se añade ID del partido y el nombre de ambos equipos.
				if (calendario[indiceJornada][i].esDisputado()) {
					texto += " | Resultado: " +
							calendario[indiceJornada][i].getPuntuacionLocal() +
							" - " +
							calendario[indiceJornada][i].getPuntuacionVisitante(); // Si el partido ya se jugó se muestra el resultado.
				} else {
					texto += " | Pendiente"; // Si no "Pendiente".
				}
				texto += "\n";
			} else {
				texto += " - (sin partido)\n"; // Indica que esa posición del calendario está vacía.
			}
		}
		return texto; // Devuelve toda la información construida.
	}
	
	public boolean modificarSalarioPersona(String id, double nuevoSalario) {
		PersonaLiga persona = buscarPersonaPorId(id);
		if (persona != null) {
			persona.setSalario(nuevoSalario);
			historialAcciones.push("Salario modificado de: " + persona.getNombre());
			return true;
		}
		return false;
	}
	
	public boolean modificarNickPersona(String id, String nuevoNick) {
		PersonaLiga persona = buscarPersonaPorId(id);
		if (persona != null) {
			persona.setNick(nuevoNick);
			historialAcciones.push("Nick modificado de: " + persona.getNombre());
			return true;
		}
		return false;
	}
	
	public boolean eliminarPersona(String id) {
		PersonaLiga persona = buscarPersonaPorId(id);
		if (persona != null) {
			personas.remove(persona);
			historialAcciones.push("Persona eliminada: " + persona.getNombre());
			return true;
		}
		return false;
	}
	
	public PersonaLiga buscarPersonaPorId(String id) {
		for (PersonaLiga p : personas) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}
	
	public String listarPersonas() {
		String texto = "\nPERSONAS DE LA LIGA:\n";
		if (personas.isEmpty()) {
			texto += " - No hay personas registradas\n";
		} else {
			for (PersonaLiga p : personas) {
				texto += p.toString() + "\n\n";
			}
		}
		return texto;
	}
	
	public String mostrarEquipos() {
		String texto = "EQUIPOS:\n";
		for (Equipo e : equipos) {
			texto += e.toString() + "\n\n";
		}
		return texto;
	}
	
	public Equipo buscarEquipoPorNombre(String nombre) {
		for (Equipo e : equipos) {
			if (e.getNombre().equals(nombre)) {
				return e;
			}
		}
		return null;
	}
	
	public Equipo obtenerEquipoConMasVictorias() {
		if (equipos.isEmpty()) {
			return null;
		}
		Equipo mejor = equipos.get(0);
		for (Equipo e : equipos) {
			if (e.getVictorias() > mejor.getVictorias()) {
				mejor = e;
			}
		}
		return mejor;
	}
	
	public void generarPartidosAutomaticos() throws PartidoInvalidoException, PartidoDuplicadoException {
		int totalPartidos = (equipos.size() * (equipos.size() - 1)) / 2; // Calcula cuántos partidos habrá en total.
		crearCalendario(totalPartidos, 1);
		int jornada = 1;
		int posicionCalendario = 0;
		for (int i = 0; i < equipos.size(); i++) {
			for (int j = i + 1; j < equipos.size(); j++) {
				String idPartido = "P" + jornada;
				Equipo local = equipos.get(i);
				Equipo visitante = equipos.get(j);
				crearPartido(idPartido, jornada, local, visitante);
				Partido partido = buscarPartidoPorId(idPartido);
				añadirPartidoACalendario(posicionCalendario, 0, partido);
				jornada++;
				posicionCalendario++;
			}
		}
		historialAcciones.push("Calendario generado automáticamente");
	} /* Este método:
			1º Calcula cuántos partidos hacen falta.
			2º Crea un calendario con una jornada por partido.
			3º Recorre todos los equipos.
			4º Genera todos los enfrentamientos posibles sin repetir.
			5º Crea cada partido.
			6º Lo añade al calendario.
			7º Guarda la acción en el historial. */
	
	
	public void crearPartido(String id, int jornada, Equipo local, Equipo visitante) throws PartidoInvalidoException, PartidoDuplicadoException {
		if (buscarPartidoPorId(id) != null) {
			throw new PartidoDuplicadoException("Ya existe un partido con ese identificador");
		}
		Partido partido = new Partido(id, jornada, local, visitante);
		partidos.add(partido);
		partidosPendientes.offer(partido); // Se añade el partido al final de la cola para que se dispute más adelante.
		historialAcciones.push(" - Partido creado: " + id +
				"\n - Equipo local: " + local.getNombre() +
				"\n - Equipo visitante: " + visitante.getNombre());
	}
	
	public Partido buscarPartidoPorId(String id) {
		for (Partido p : partidos) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}
	
	public Partido mostrarSiguientePartido() throws ColaPartidosVaciaException {
		if (partidosPendientes.isEmpty()) {
			throw new ColaPartidosVaciaException("No hay partidos pendientes");
		}
		return partidosPendientes.element(); // ".element()" Devuelve el primer partido de la cola "partidosPendientes" sin eliminarlo.
	}
	
	public Partido disputarSiguientePartido() throws ColaPartidosVaciaException {
		if (partidosPendientes.isEmpty()) {
			throw new ColaPartidosVaciaException("No hay partidos pendientes para disputar");
		}
		Partido partido = partidosPendientes.remove();
		historialAcciones.push(" - Partido disputado: " + partido.getId());
		return partido;
	} /* Este método:
			1º Comprueba si quedan partidos pendientes.
			2º Lanza una excepción si la cola está vacía.
			3º Extrae el primer partido de la cola.
			4º Guarda la acción en el historial.
			5º Devuelve el partido extraído. */
	
	
	public String mostrarPartidosPendientes() {
		String texto = "PARTIDOS PENDIENTES:\n";
		if (partidosPendientes.isEmpty()) {
			texto += "No hay partidos pendientes\n";
		} else {
			for (Partido p : partidosPendientes) {
				texto += p.toString() + "\n\n";
			}
		}
		return texto;
	}
	
	public void registrarIncidencia(Incidencia incidencia) {
		if (incidencia != null) {
			incidencias.add(incidencia);
			historialAcciones.push(" - Incidencia registrada: " + incidencia.getId());
		}
	}
	
	public String listarIncidencias() {
		String texto = "\nINCIDENCIAS:\n";
		if (incidencias.isEmpty()) {
			texto += "No hay incidencias registradas\n";
		} else {
			for (Incidencia i : incidencias) {
				texto += i.toString() + "\n\n";
			}
		}
		return texto;
	}
	
	public String buscarIncidenciasPorJugador(String idJugador) {
		String texto = "INCIDENCIAS DEL JUGADOR " + idJugador + ":\n";
		boolean encontrada = false;
		for (Incidencia i : incidencias) {
			if (i.getJugador() != null && i.getJugador().getId().equals(idJugador)) {
				texto += i.toString() + "\n\n";
				encontrada = true;
			}
		}
		if (!encontrada) {
			texto += "No hay incidencias para ese jugador\n";
		}
		return texto;
	}
	
	public String buscarIncidenciasPorEquipo(String nombreEquipo) {
		String texto = "INCIDENCIAS DEL EQUIPO " + nombreEquipo + ":\n";
		boolean encontrada = false;
		for (Incidencia i : incidencias) {
			if (i.getEquipo() != null && i.getEquipo().getNombre().equals(nombreEquipo)) {
				texto += i.toString() + "\n\n";
				encontrada = true;
			}
		}
		if (!encontrada) {
			texto += "No hay incidencias para ese equipo\n";
		}
		return texto;
	}
	
	public Incidencia buscarIncidenciaPorId(String id) {
		for (Incidencia i : incidencias) {
			if (i.getId().equals(id)) {
				return i;
			}
		}
		return null;
	}
	
	public String mostrarHistorial() {
		String texto = "HISTORIAL DE ACCIONES:\n";
		if (historialAcciones.empty()) {
			texto += "No hay acciones registradas\n";
		} else {
			for (String accion : historialAcciones) {
				texto += accion + "\n";
			}
		}
		return texto;
	}
	
	public String deshacerUltimaAccion() {
		if (historialAcciones.empty()) {
			return "No hay acciones para deshacer";
		}
		return "Acción deshecha: " + historialAcciones.pop();
	}
	
	public String mostrarClasificacion() {
		String texto = "CLASIFICACIÓN:\n";
		boolean[] mostrado = new boolean[equipos.size()]; // Se crea un array de booleanos con el mismo tamaño que la lista de equipos. Indica si un equipo ya ha sido añadido a la clasificación.
		for (int pos = 0; pos < equipos.size(); pos++) {
			int indiceMejor = -1; // Guarda la posición del mejor equipo que todavía no ha sido mostrado. Se inicia con -1 para indicar que todavía no se ha encontrado ninguno.
			for (int i = 0; i < equipos.size(); i++) {
				if (!mostrado[i]) {
					if (indiceMejor == -1) {
						indiceMejor = i; // Si todavía no hay ningún candidato, el equipo actual pasa a ser el mejor provisional.
					} else {
						Equipo actual = equipos.get(i); // actual: equipo que se está evaluando.
						Equipo mejor = equipos.get(indiceMejor); // mejor: mejor equipo encontrado hasta ese momento.
						if (actual.calcularPuntosTotales() > mejor.calcularPuntosTotales()) { // Compara por puntos.
							indiceMejor = i;
						} else if (actual.calcularPuntosTotales() == mejor.calcularPuntosTotales()
								&& actual.getVictorias() > mejor.getVictorias()) { // Compara por victorias.
							indiceMejor = i;
						} else if (actual.calcularPuntosTotales() == mejor.calcularPuntosTotales()
								&& actual.getVictorias() == mejor.getVictorias()
								&& actual.calcularDiferenciaPuntos() > mejor.calcularDiferenciaPuntos()) { // Compara por diferencia de puntos.
							indiceMejor = i;
						} else if (actual.calcularPuntosTotales() == mejor.calcularPuntosTotales()
								&& actual.getVictorias() == mejor.getVictorias()
								&& actual.calcularDiferenciaPuntos() == mejor.calcularDiferenciaPuntos()
								&& actual.getNombre().compareTo(mejor.getNombre()) < 0) { // Compara alfabéticamente.
							indiceMejor = i;
						}
					}
				}
			}
			if (indiceMejor != -1) {
				Equipo equipo = equipos.get(indiceMejor);
				mostrado[indiceMejor] = true;
				texto += (pos + 1) + ". " +
						equipo.getNombre() + "\t" +
						"Victorias: " + equipo.getVictorias() + "\t" +
						"Derrotas: " + equipo.getDerrotas() + "\t" +
						"Puntos: " + equipo.calcularPuntosTotales() + "\t" +
						"PF: " + equipo.getPuntosFavor() + "\t" +
						"PC: " + equipo.getPuntosContra() + "\t" +
						"DIF: " + equipo.calcularDiferenciaPuntos() + "\n";
			}
		}
		return texto;
	} /* Este método:
			1º Recorre todos los equipos.
			2º Selecciona el mejor equipo no mostrado.
			3º Los ordena por:
			   - Puntos.
			   - Victorias.
			   - Diferencia de puntos.
			   - Nombre alfabético.
			4º Construye el texto de la clasificación.
			5º Devuelve el resultado final. */
	
	
	public void asignarCampeonAutomatico() {
		if (campeon != null) {
			return;
		}
		Equipo mejor = obtenerEquipoConMasVictorias();
		if (mejor != null) {
			campeon = mejor;
			if (mejor.getEntrenador() != null) {
				mejor.getEntrenador().sumarTitulo();
			}
			historialAcciones.push(" - Campeón asignado automáticamente: " + mejor.getNombre());
		}
	}
	
	public String mostrarCampeon() {
		if (campeon == null) {
			return "\nCAMPEÓN DE LA LIGA:\n - Todavía no hay campeón asignado\n";
		}
		String texto = "\nCAMPEÓN DE LA LIGA:\n" +
				" - Equipo: " + campeon.getNombre();
		if (campeon.getEntrenador() != null) {
			texto += "\n - Entrenador: " + campeon.getEntrenador().getNombre();
		} else {
			texto += "\n - Entrenador: No asignado";
		}
		return texto + "\n";
	}
	
	public void reiniciarTemporada() {
		for (Equipo e : equipos) {
			e.reiniciarEstadisticasTemporada();
		}
		partidos.clear();
		incidencias.clear();
		partidosPendientes.clear();
		calendario = null;
		campeon = null;
		historialAcciones.push(" - Temporada reiniciada");
	}
	
	public Entrenador obtenerEntrenadorMasLaureado() {
		if (equipos.isEmpty()) {
			return null;
		}
		Entrenador mejor = null;
		for (Equipo e : equipos) {
			if (e.getEntrenador() != null) {
				if (mejor == null || e.getEntrenador().getTitulosGanados() > mejor.getTitulosGanados()) {
					mejor = e.getEntrenador();
				}
			}
		}
		return mejor;
	}
	
	public String mostrarEstadisticasLiga() {
		if (campeon == null && partidosPendientes.isEmpty() && !partidos.isEmpty()) {
			asignarCampeonAutomatico();
		}
		String texto = "\nESTADÍSTICAS DE LA LIGA:\n";
		Equipo mejorEquipo = obtenerEquipoConMasVictorias();
		if (mejorEquipo != null) {
			texto += " - Equipo con más victorias: " + mejorEquipo.getNombre() +
					" (" + mejorEquipo.getVictorias() + " victorias)\n";
		} else {
			texto += " - No hay equipos registrados\n";
		}
		Entrenador mejorEntrenador = obtenerEntrenadorMasLaureado();
		if (mejorEntrenador != null) {
			texto += " - Entrenador más laureado: " + mejorEntrenador.getNombre() +
					" (" + mejorEntrenador.getTitulosGanados() + " títulos)\n";
		} else {
			texto += " - No hay entrenadores registrados\n";
		}
		return texto;
	}
	
	public boolean ficharJugador(String idJugador, String nombreEquipoDestino) throws EquipoCompletoException {
		PersonaLiga persona = buscarPersonaPorId(idJugador);
		Equipo equipoDestino = buscarEquipoPorNombre(nombreEquipoDestino);

		if (persona == null || equipoDestino == null || !(persona instanceof Jugador)) {
			return false;
		}
		Jugador jugador = (Jugador) persona;
		for (Equipo e : equipos) {
			e.eliminarJugador(idJugador);
		}
		equipoDestino.añadirJugador(jugador);
		historialAcciones.push(" - Jugador fichado: " + jugador.getNombre() +
				" por " + equipoDestino.getNombre());
		return true;
	}
	
	@Override
	public String toString() {
		return "Liga: " + nombre +
				"\nNúmero de equipos: " + equipos.size() +
				"\nNúmero de partidos: " + partidos.size() +
				"\nPartidos pendientes: " + partidosPendientes.size();
	}
}