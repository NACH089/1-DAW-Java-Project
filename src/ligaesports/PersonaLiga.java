package ligaesports;

public abstract class PersonaLiga {
	private String id;
	private String nombre;
	private String nick;
	private int edad;
	private double salario;
	
	public PersonaLiga(String id, String nombre, String nick, int edad, double salario) {
		setId(id);
		setNombre(nombre);
		setNick(nick);
		setEdad(edad);
		setSalario(salario);
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNick() {
		return this.nick;
	}
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public int getEdad() {
		return this.edad;
	}
	
	public void setEdad(int edad) {
	    if (edad > 0) {
	        this.edad = edad;
	    }
	}
	
	public double getSalario() {
		return this.salario;
	}
	
	public void setSalario(double salario) {
	    if (salario > 0) {
	        this.salario = salario;
	    }
	}
	
	public abstract double calcularCosteMensual();
	
	public String mostrarResumen() {
	    return "RESUMEN" +
	    		"\n - Identificador: " +id+
	    		"\n - Nombre: " +nombre+
	    		"\n - Nickname: " +nick;
	}
	
	@Override
	public String toString() {
		return " - Identificador: " +id+
				"\n - Nombre: " +nombre+
				"\n - Nickname: " +nick+
				"\n - Edad: " +edad+
				"\n - Salario base: " +salario;
	}
}
