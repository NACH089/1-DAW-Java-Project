package ligaesports;

import java.util.ArrayList;

public class Equipo {
	private String nombre;
	private String ciudad;
	private double presupuesto;
	private int victorias;
	private int derrotas;
	private int puntosFavor;
	private int puntosContra;
	private Entrenador entrenador;
	private ArrayList<Jugador> jugadores;
	private Jugador[] titulares;
	private ArrayList<Jugador> suplentes;

	private static final int MAX_JUGADORES = 10;
	private static final int NUM_TITULARES = 5;

	public Equipo(String nombre, String ciudad, double presupuesto, Entrenador entrenador) {
	    this.nombre = nombre;
	    this.ciudad = ciudad;
	    this.presupuesto = presupuesto;
	    this.entrenador = entrenador;

	    this.jugadores = new ArrayList<>();
	    this.titulares = new Jugador[NUM_TITULARES];
	    this.suplentes = new ArrayList<>();

	    this.victorias = 0;
	    this.derrotas = 0;
	    this.puntosFavor = 0;
	    this.puntosContra = 0;
	}
	
	public int getVictorias() {
		return victorias;
	}
	
	public int getDerrotas() {
		return derrotas;
	}
	
	public int getPuntosFavor() {
		return puntosFavor;
	}
	
	public int getPuntosContra() {
		return puntosContra;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getCiudad() {
		return ciudad;
	}

	public double getPresupuesto() {
		return presupuesto;
	}

	public ArrayList<Jugador> getJugadores() {
		return jugadores;
	}

	public ArrayList<Jugador> getSuplentes() {
		return suplentes;
	}
	
	public Entrenador getEntrenador() {
		return entrenador;
	}
	
	public Jugador[] getTitulares() {
		return titulares;
	}
	
	public int calcularPuntosTotales() {
		return victorias * 3;
	}
	
	public void añadirJugador(Jugador j) throws EquipoCompletoException {
		if (j != null && !jugadores.contains(j)) {
			if (jugadores.size() < MAX_JUGADORES) {
				jugadores.add(j);
				suplentes.add(j);
			} else {
				throw new EquipoCompletoException("El equipo ya tiene el máximo de jugadores permitido");
			}
		}
	}

	public Jugador buscarJugadorPorId(String id) {
		for (Jugador j : jugadores) {
			if (j.getId().equals(id)) {
				return j;
			}
		}
		return null;
	}

	public boolean eliminarJugador(String id) {
		Jugador jugador = buscarJugadorPorId(id);
		if (jugador == null) {
			return false;
		}
		jugadores.remove(jugador);
		suplentes.remove(jugador);
		for (int i = 0; i < titulares.length; i++) {
			if (titulares[i] == jugador) {
				titulares[i] = null;
			}
		}
		return true;
	}

	public boolean rolTitularOcupado(Rol rol) {
		for (int i = 0; i < titulares.length; i++) {
			if (titulares[i] != null && titulares[i].getRol() == rol) {
				return true;
			}
		}
		return false;
	}

	public boolean convocarTitular(String id) throws RolNoDisponibleException, JugadorSancionadoException {
		Jugador j = buscarJugadorPorId(id);
		if (j == null) {
			System.out.println("No existe ningún jugador con ese ID en el equipo");
			return false;
		}
		if (j.esSancionado()) {
			throw new JugadorSancionadoException("El jugador está sancionado y no puede ser titular");
		}
		if (j.getEstado().equals("lesionado")) {
			System.out.println("El jugador está lesionado y no puede ser titular");
			return false;
		}
		if (rolTitularOcupado(j.getRol())) {
			throw new RolNoDisponibleException("Ya existe un titular con el rol " + j.getRol());
		}
		for (int i = 0; i < titulares.length; i++) {
			if (titulares[i] == null) {
				titulares[i] = j;
				suplentes.remove(j);
				return true;
			}
		}
		System.out.println("No hay huecos libres en titulares");
		return false;
	}
	
	public int convocarTitularesAutomaticamente() throws RolNoDisponibleException, JugadorSancionadoException {
		// Libera los huecos de titulares que estén sancionados o lesionados y los devuelve a suplentes.
		// Sin esto, un titular sancionado se queda ocupando su hueco para siempre y el equipo
		// nunca vuelve a tener una alineación válida en toda la temporada.
		for (int i = 0; i < titulares.length; i++) {
			Jugador titular = titulares[i];
			if (titular != null && (titular.esSancionado() || titular.getEstado().equals("lesionado"))) {
				titulares[i] = null;
				if (!suplentes.contains(titular)) {
					suplentes.add(titular);
				}
			}
		}

		int convocados = 0;
		for (Jugador j : jugadores) {
			if (j != null &&
				!j.esSancionado() &&
				j.getEstado().equals("activo") &&
				!rolTitularOcupado(j.getRol())) {
				if (convocarTitular(j.getId())) {
					convocados++;
				}
			}
		}
		return convocados;
	}

	public void convocarSuplente(String id) throws JugadorSancionadoException {
		Jugador j = buscarJugadorPorId(id);
		if (j == null) {
			System.out.println("No existe ningún jugador con ese ID");
			return;
		}
		if (j.esSancionado()) {
			throw new JugadorSancionadoException("El jugador está sancionado");
		}
		if (j.getEstado().equals("lesionado")) {
			System.out.println("El jugador está lesionado");
			return;
		}
		for (int i = 0; i < titulares.length; i++) {
			if (titulares[i] != null && titulares[i].equals(j)) {
				System.out.println("El jugador ya es titular");
				return;
			}
		}
		if (suplentes.contains(j)) {
			System.out.println("El jugador ya está convocado como suplente");
			return;
		}
		suplentes.add(j);
	}

	public boolean validarTitulares() {
		for (int i = 0; i < titulares.length; i++) {
			if (titulares[i] == null) {
				return false;
			}
			if (titulares[i].esSancionado()) {
				return false;
			}
			if (titulares[i].getEstado().equals("lesionado")) {
				return false;
			}
		}
		for (int i = 0; i < titulares.length; i++) {
			for (int j = i + 1; j < titulares.length; j++) {
				if (titulares[i].getRol() == titulares[j].getRol()) {
					return false;
				}
			}
		}
		return true;
	}

	public String mostrarPlantilla() {
		String texto = "PLANTILLA DEL EQUIPO: " + nombre + "\n";

		for (Jugador j : jugadores) {
			texto += j.toString() + "\n";
		}

		return texto;
	}

	public String mostrarAlineacion() {
		String texto = "TITULARES:\n";
		for (int i = 0; i < titulares.length; i++) {
			if (titulares[i] != null) {
				texto += titulares[i].getNombre() +
						" - " + titulares[i].getRol() +
						" - Estado: " + titulares[i].getEstado() +
						" - Sancionado: " + titulares[i].esSancionado() + "\n";
			} else {
				texto += "Hueco libre\n";
			}
		}
		texto += "\nSUPLENTES:\n";
		if (suplentes.isEmpty()) {
			texto += "No hay suplentes\n";
		} else {
			for (Jugador j : suplentes) {
				texto += j.getNombre() +
						" - " + j.getRol() +
						" - Estado: " + j.getEstado() +
						" - Sancionado: " + j.esSancionado() + "\n";
			}
		}
		return texto;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}
	
	public double calcularCosteTotal() {
		double total = 0;
		if (entrenador != null) {
			total += entrenador.calcularCosteMensual();
		}
		for (Jugador j : jugadores) {
			total += j.calcularCosteMensual();
		}
		return total;
	}
	
	public int entrenarJugadores() {
		int entrenados = 0;
		for (Jugador j : jugadores) {
			if (entrenador != null) {
				entrenador.entrenarJugador(j);
			} else {
				j.entrenar();
			}
			entrenados++;
		}
		return entrenados;
	}
	
	public void sumarVictoria() {
	    victorias++;
	    if (entrenador != null) {
	        entrenador.sumarVictoria();
	    }
	}

	public void sumarDerrota() {
	    derrotas++;
	}

	public void añadirPuntosFavor(int puntos) {
	    if (puntos >= 0) {
	        puntosFavor += puntos;
	    }
	}

	public void añadirPuntosContra(int puntos) {
	    if (puntos >= 0) {
	        puntosContra += puntos;
	    }
	}
	
	public int calcularDiferenciaPuntos() {
		return puntosFavor - puntosContra;
	}
	
	public void reiniciarEstadisticasTemporada() {
		victorias = 0;
		derrotas = 0;
		puntosFavor = 0;
		puntosContra = 0;

		for (Jugador j : jugadores) {
			j.reiniciarEstadoTemporada();
		}
	}
	
	@Override
	public String toString() {
	    return "Equipo: " + nombre +
	           "\n Ciudad: " + ciudad +
	           "\n Presupuesto: " + presupuesto +
	           "\n Victorias: " + victorias +
	           "\n Derrotas: " + derrotas +
	           "\n Puntos a favor: " + puntosFavor +
	           "\n Puntos en contra: " + puntosContra;
	}
}