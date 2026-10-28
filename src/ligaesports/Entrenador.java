package ligaesports;

public class Entrenador extends PersonaLiga {
	private int experiencia;
	private Especialidad especialidad;
	private int victoriasTotales;
	private int titulosGanados;
	
	public Entrenador(String id, String nombre, String nick, int edad, double salario, int experiencia, Especialidad especialidad) {
		super(id, nombre, nick, edad, salario);
		setExperiencia(experiencia);
		this.especialidad = especialidad;
		setVictoriasTotales(0);
		setTitulosGanados(0);
	}

	public int getExperiencia() {
		return this.experiencia;
	}

	public void setExperiencia(int experiencia) {
		if (experiencia >= 0) {
			this.experiencia = experiencia;
		}
	}

	public Especialidad getEspecialidad() {
		return this.especialidad;
	}

	public int getVictoriasTotales() {
		return this.victoriasTotales;
	}

	public void setVictoriasTotales(int victoriasTotales) {
		if (victoriasTotales >= 0) {
			this.victoriasTotales = victoriasTotales;
		}
	}
	
	public int getTitulosGanados() {
		return titulosGanados;
	}

	public void setTitulosGanados(int titulosGanados) {
		if (titulosGanados >= 0) {
			this.titulosGanados = titulosGanados;
		}
	}
	
	public void sumarVictoria() {
		victoriasTotales++;
	}
	
	public void entrenarJugador(Jugador j) {
	    if (j != null) {
	        j.entrenar();
	    }
	}
	
	public void sumarTitulo() {
		titulosGanados++;
	}
	
	@Override
	public double calcularCosteMensual() {
		return getSalario();
	}

	@Override
	public String toString() {
		return super.toString() +
				"\n - Experiencia: " + experiencia +
				"\n - Especialidad: " + especialidad +
				"\n - Victorias totales: " + victoriasTotales +
				"\n - Títulos ganados: " + titulosGanados;
	}
}
