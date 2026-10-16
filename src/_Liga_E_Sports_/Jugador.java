package _Liga_E_Sports_;

public class Jugador extends PersonaLiga implements Entrenable{
	private Rol rol;
	private int nivelMecanico;
	private int nivelEstrategico;
	private int partidosJugados;
	private int partidosSeguidos;
	private int partidosReposo;
	private int mvpTotales;
	private boolean sancionado;
	private String estado;
	
	public Jugador(String id, String nombre, String nick, int edad, double salario, Rol rol, int nivelMecanico, int nivelEstrategico) {
		super(id, nombre, nick, edad, salario);
		setRol(rol);
		setNivelMecanico(nivelMecanico);
		setNivelEstrategico(nivelEstrategico);
		setPartidosJugados(0);
		setPartidosSeguidos(0);
		setPartidosReposo(0);
		setMvpTotales(0);
		setSancionado(false);
		setEstado("activo");
	}
	
	public Rol getRol() {
		return this.rol;
	}
	
	public void setRol(Rol rol) {
	    if (rol != null) {
	        this.rol = rol;
	    }
	}
	
	public int getNivelMecanico() {
		return this.nivelMecanico;
	}
	
	public void setNivelMecanico(int nivelMecanico) {
		if (nivelMecanico >= 0 && nivelMecanico <= 100) {
	        this.nivelMecanico = nivelMecanico;
	    }
	}
	
	public int getNivelEstrategico() {
		return this.nivelEstrategico;
	}
	
	public void setNivelEstrategico(int nivelEstrategico) {
		if (nivelEstrategico >= 0 && nivelEstrategico <= 100) {
	        this.nivelEstrategico = nivelEstrategico;
	    }
	}
	
	public int getPartidosJugados() {
		return this.partidosJugados;
	}
	
	public void setPartidosJugados(int partidosJugados) {
		if (partidosJugados >= 0) {
			this.partidosJugados = partidosJugados;
		}
	}
	
	public int getPartidosSeguidos() {
		return this.partidosSeguidos;
	}
	
	public void setPartidosSeguidos(int partidosSeguidos) {
		if (partidosSeguidos >= 0) {
			this.partidosSeguidos = partidosSeguidos;
		}
	}
	
	public int getPartidosReposo() {
	    return this.partidosReposo;
	}

	public void setPartidosReposo(int partidosReposo) {
	    if (partidosReposo >= 0) {
	        this.partidosReposo = partidosReposo;
	    }
	}
	
	public int getMvpTotales() {
		return this.mvpTotales;
	}
	
	public void setMvpTotales(int mvpTotales) {
		if (mvpTotales >= 0) {
			this.mvpTotales = mvpTotales;
		}
	}
	
	public boolean esSancionado() {
		return this.sancionado;
	}
	
	public void setSancionado(boolean sancionado) {
		this.sancionado = sancionado;
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public void setEstado(String estado) {
		if (estado.equals("activo") || estado.equals("lesionado")) {
			this.estado = estado;
		}
	}
	
	public void jugarPartido() {
	    if (estado.equals("activo")) {
	        partidosJugados++;
	        partidosSeguidos++;

	        if (partidosSeguidos >= 5) {
	            estado = "lesionado";
	            partidosReposo = 3;
	        }
	    }
	}
	
	public void pasarPartidoSinJugar() {
	    if (estado.equals("lesionado")) {
	        if (partidosReposo > 0) {
	            partidosReposo--;
	        }

	        if (partidosReposo == 0) {
	            estado = "activo";
	            partidosSeguidos = 0;
	        }
	    }
	}
	
	public void reiniciarEstadoTemporada() {
		partidosSeguidos = 0;
		partidosReposo = 0;
		sancionado = false;
		estado = "activo";
	}
	
	public void sumarMvp() {
		mvpTotales++;
	}
	
	@Override
	public void entrenar() {
		if (nivelMecanico < 100) {
			nivelMecanico++;
		}
		if (nivelEstrategico < 100) {
			nivelEstrategico++;
		}
	}

	@Override
	public double calcularRendimiento() {
		return (nivelMecanico + nivelEstrategico) / 2.0;
	}
	
	@Override
	public double calcularCosteMensual() {
		return getSalario();
	}
	
	@Override
	public String toString() {
	    return super.toString() +
	            "\n - Rol: " + rol +
	            "\n - Nivel mecánico: " + nivelMecanico +
	            "\n - Nivel estratégico: " + nivelEstrategico +
	            "\n - Rendimiento: " + calcularRendimiento() +
	            "\n - Partidos jugados: " + partidosJugados +
	            "\n - Partidos seguidos: " + partidosSeguidos +
	            "\n - Partidos de reposo: " + partidosReposo +
	            "\n - MVP totales: " + mvpTotales +
	            "\n - Sancionado: " + sancionado +
	            "\n - Estado: " + estado;
	}
}