package ligaesports;

public class Incidencia {
	private String id;
	private TipoIncidencia tipo;
	private String descripcion;
	private Equipo equipo;
	private Jugador jugador;
	private Partido partido;
	private boolean resuelta;

	public Incidencia(String id, TipoIncidencia tipo, String descripcion, Equipo equipo, Jugador jugador, Partido partido) {
		this.id = id;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.equipo = equipo;
		this.jugador = jugador;
		this.partido = partido;
		this.resuelta = false;
	}
	
	public String getId() {
		return id;
	}

	public TipoIncidencia getTipo() {
		return tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Equipo getEquipo() {
		return equipo;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public Partido getPartido() {
		return partido;
	}

	public boolean esResuelta() {
		return resuelta;
	}
	
	public void resolverIncidencia() {
		if (resuelta) {
			return;
		}
		resuelta = true;
		if (jugador != null &&
			(tipo == TipoIncidencia.SANCION || tipo == TipoIncidencia.EXPULSION)) {
			jugador.setSancionado(false);
		}
	}
	
	public void aplicarSancion() {
		if (resuelta) {
			return;
		}
		if (jugador == null) {
			return;
		}
		if (tipo == TipoIncidencia.SANCION || tipo == TipoIncidencia.EXPULSION) {
			jugador.setSancionado(true);
		}
	}
	
	
	
	@Override
	public String toString() {
		String texto = "Incidencia " + id +
				"\nTipo: " + tipo +
				"\nDescripción: " + descripcion;
		if (equipo != null) {
			texto += "\nEquipo: " + equipo.getNombre();
		}
		if (jugador != null) {
			texto += "\nJugador: " + jugador.getNombre();
		}
		if (partido != null) {
			texto += "\nPartido: " + partido.getId();
		}
		texto += "\nResuelta: " + resuelta;
		return texto;
	}
}
