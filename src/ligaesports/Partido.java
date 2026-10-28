package ligaesports;

public class Partido {
	private String id;
	private int jornada;
	private Equipo equipoLocal;
	private Equipo equipoVisitante;
	private int puntuacionLocal;
	private int puntuacionVisitante;
	private Jugador mvp;
	private boolean disputado;
	
	public Partido(String id, int jornada, Equipo equipoLocal, Equipo equipoVisitante) throws PartidoInvalidoException{
		if (equipoLocal == equipoVisitante) {
			throw new PartidoInvalidoException("Un equipo no puede jugar contra sí mismo");
		}
		this.id = id;
		this.jornada = jornada;
		this.equipoLocal = equipoLocal;
		this.equipoVisitante = equipoVisitante;
		this.puntuacionLocal = 0;
		this.puntuacionVisitante = 0;
		this.mvp = null;
		this.disputado = false;
	}
	
	public String getId() {
		return id;
	}

	public int getJornada() {
		return jornada;
	}

	public Equipo getEquipoLocal() {
		return equipoLocal;
	}

	public Equipo getEquipoVisitante() {
		return equipoVisitante;
	}

	public int getPuntuacionLocal() {
		return puntuacionLocal;
	}

	public int getPuntuacionVisitante() {
		return puntuacionVisitante;
	}

	public Jugador getMvp() {
		return mvp;
	}

	public boolean esDisputado() {
		return disputado;
	}
	
	public void registrarResultadoAleatorio() throws PartidoInvalidoException {
		if (!equipoLocal.validarTitulares()) {
			throw new PartidoInvalidoException("El equipo local no tiene una alineación válida");
		}
		if (!equipoVisitante.validarTitulares()) {
			throw new PartidoInvalidoException("El equipo visitante no tiene una alineación válida");
		}
		int puntosLocal = 0;
		int puntosVisitante = 0;
		while (puntosLocal < 3 && puntosVisitante < 3) {
			int ganadorSet = (int) (Math.random() * 2);
			if (ganadorSet == 0) {
				puntosLocal++;
			} else {
				puntosVisitante++;
			}
		}
		Jugador mvpAleatorio = generarMvpAleatorio(puntosLocal, puntosVisitante);
		registrarResultado(puntosLocal, puntosVisitante, mvpAleatorio);
	}
	
	public Jugador generarJugadorAleatorioDelPartido() throws PartidoInvalidoException {
		if (!disputado) {
			throw new PartidoInvalidoException("No se puede generar una sanción si el partido no se ha disputado");
		}
		Jugador[] titularesLocal = equipoLocal.getTitulares();
		Jugador[] titularesVisitante = equipoVisitante.getTitulares();
		Jugador jugadorElegido = null;
		do {
			int equipoAleatorio = (int) (Math.random() * 2);
			int indiceAleatorio = (int) (Math.random() * titularesLocal.length);
			if (equipoAleatorio == 0) {
				jugadorElegido = titularesLocal[indiceAleatorio];
			} else {
				jugadorElegido = titularesVisitante[indiceAleatorio];
			}
		} while (jugadorElegido == null || jugadorElegido == mvp);
		return jugadorElegido;
	}
	
	private Jugador generarMvpAleatorio(int puntosLocal, int puntosVisitante) throws PartidoInvalidoException {
		Equipo ganador;
		if (puntosLocal > puntosVisitante) {
			ganador = equipoLocal;
		} else if (puntosVisitante > puntosLocal) {
			ganador = equipoVisitante;
		} else {
			throw new PartidoInvalidoException("No puede generarse MVP si el partido está empatado");
		}
		Jugador[] titulares = ganador.getTitulares();
		if (!ganador.validarTitulares()) {
			throw new PartidoInvalidoException("No se puede generar MVP porque el equipo ganador no tiene titulares válidos");
		}
		int indice = (int) (Math.random() * titulares.length);
		return titulares[indice];
	}
	
	public void registrarResultado(int puntuacionLocal, int puntuacionVisitante, Jugador mvp) throws PartidoInvalidoException {
		if (disputado) {
			throw new PartidoInvalidoException("El partido ya ha sido disputado");
		}
		if (!equipoLocal.validarTitulares()) {
			throw new PartidoInvalidoException("El equipo local no tiene una alineación válida");
		}
		if (!equipoVisitante.validarTitulares()) {
			throw new PartidoInvalidoException("El equipo visitante no tiene una alineación válida");
		}
		if (puntuacionLocal < 0 || puntuacionVisitante < 0) {
			throw new PartidoInvalidoException("La puntuación no puede ser negativa");
		}
		if (puntuacionLocal == puntuacionVisitante) {
			throw new PartidoInvalidoException("No puede haber empate en este tipo de competición");
		}
		Equipo ganador;
		if (puntuacionLocal > puntuacionVisitante) {
			ganador = equipoLocal;
		} else {
			ganador = equipoVisitante;
		}
		if (mvp != null && ganador.buscarJugadorPorId(mvp.getId()) == null) {
			throw new PartidoInvalidoException("El MVP debe pertenecer al equipo ganador");
		}
		this.puntuacionLocal = puntuacionLocal;
		this.puntuacionVisitante = puntuacionVisitante;
		this.mvp = mvp;
		this.disputado = true;

		equipoLocal.añadirPuntosFavor(puntuacionLocal);
		equipoLocal.añadirPuntosContra(puntuacionVisitante);
		equipoVisitante.añadirPuntosFavor(puntuacionVisitante);
		equipoVisitante.añadirPuntosContra(puntuacionLocal);

		if (puntuacionLocal > puntuacionVisitante) {
			equipoLocal.sumarVictoria();
			equipoVisitante.sumarDerrota();
		} else if (puntuacionVisitante > puntuacionLocal) {
			equipoVisitante.sumarVictoria();
			equipoLocal.sumarDerrota();
		}
		
		actualizarJugadoresEquipo(equipoLocal);
		actualizarJugadoresEquipo(equipoVisitante);
		
		if (mvp != null) {
			mvp.sumarMvp();
		}
	}
	
	public Equipo calcularGanador() {
		if (!disputado) {
			return null;
		}
		if (puntuacionLocal > puntuacionVisitante) {
			return equipoLocal;
		} else {
			return equipoVisitante;
		}
	}
	
	private void actualizarJugadoresEquipo(Equipo equipo) {
		Jugador[] titulares = equipo.getTitulares();
		for (int i = 0; i < titulares.length; i++) {
			if (titulares[i] != null) {
				titulares[i].jugarPartido();
			}
		}
		for (Jugador j : equipo.getSuplentes()) {
			j.pasarPartidoSinJugar();
		}
	}
	
	public String mostrarResultado() {
		if (!disputado) {
			return "El partido todavía no se ha disputado";
		}
		String texto = "\nRESULTADO DEL PARTIDO:\n" +
				" - Partido: " + id +
				"\n - Jornada: " + jornada +
				"\n - " + equipoLocal.getNombre() + " " + puntuacionLocal +
				" - " +
				puntuacionVisitante + " " + equipoVisitante.getNombre() +
				"\n - Ganador: " + calcularGanador().getNombre();
		if (mvp != null) {
			texto += "\n - MVP del partido: " + mvp.getNombre();
		}
		return texto + "\n";
	}
	
	@Override
	public String toString() {
		return "Partido: " + id +
				"\nJornada: " + jornada +
				"\nLocal: " + equipoLocal.getNombre() +
				"\nVisitante: " + equipoVisitante.getNombre() +
				"\nDisputado: " + disputado;
	}
}
