/*	*** PARA PROBAR EL PROGRAMA Y NO TENER QUE INTRODUCIR TANTOS DATOS POR TECLADO ***
 * 		En la clase "Liga" en "private static final int MAX_EQUIPOS = 6", cambia de 6 a 2.
 * 		De este modo solo se deberán introducir los datos de: 
 * 			- 2 equipos
 * 			- 2 entrenadores (y asiganr estos a cada equipo)
 * 			- 10 jugadores (5 por cada equipo y convocarlos para cada equipo).
 * 		Después de esto ya podrán generarse partidos, disputarse y mostar campeón, estadísticas, etc.  */

package _Liga_E_Sports_;

import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);
		int opcion;

		try {
			Liga liga = new Liga("Liga 1GSY");

			do {
				System.out.println("\n===== MENÚ PRINCIPAL LIGA E-SPORTS =====");
				System.out.println("1. Gestión de equipos y plantillas");
				System.out.println("2. Gestión de personas");
				System.out.println("3. Calendario y partidos");
				System.out.println("4. Incidencias y sanciones");
				System.out.println("5. Clasificación, estadísticas e historial");
				System.out.println("6. Salir");
				opcion = leerEnteroEnRango(teclado, "Seleccione una opción: ", 1, 6);

				switch (opcion) {
				case 1:
					menuEquipos(teclado, liga);
					break;
				case 2:
					menuPersonas(teclado, liga);
					break;
				case 3:
					menuPartidos(teclado, liga);
					break;
				case 4:
					menuIncidencias(teclado, liga);
					break;
				case 5:
					menuEstadisticas(teclado, liga);
					break;
				case 6:
					System.out.println("Saliendo del programa...");
					break;
				default:
					System.out.println("Opción no válida");
				}

			} while (opcion != 6);
			
			teclado.close();
			
		} catch (Exception e) {
			System.out.println("Error inesperado. El programa se cerrará.");
		}
	}

	private static void menuPersonas(Scanner teclado, Liga liga) {
		int opcion;
		do {
			System.out.println("\n===== GESTIÓN DE PERSONAS =====");
			System.out.println("1. Crear entrenador");
			System.out.println("2. Crear jugador");
			System.out.println("3. Mostrar personas");
			System.out.println("4. Buscar persona por ID");
			System.out.println("5. Modificar nick");
			System.out.println("6. Modificar salario");
			System.out.println("7. Eliminar persona");
			System.out.println("8. Volver");
			opcion = leerEnteroEnRango(teclado, "Seleccione una opción: ", 1, 8);

			switch (opcion) {
			case 1:
				try {
					crearEntrenador(teclado, liga);
				} catch (PersonaDuplicadaException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				try {
					crearJugador(teclado, liga);
				} catch (PersonaDuplicadaException e) {
					System.out.println(e.getMessage());
				} catch (EquipoCompletoException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println(liga.listarPersonas());
				break;
			case 4:
				buscarPersona(teclado, liga);
				break;
			case 5:
				modificarNick(teclado, liga);
				break;
			case 6:
				modificarSalario(teclado, liga);
				break;
			case 7:
				eliminarPersona(teclado, liga);
				break;
			case 8:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Opción no válida");
			}

		} while (opcion != 8);
	}

	private static void menuEquipos(Scanner teclado, Liga liga) {
		int opcion;
		do {
			System.out.println("\n===== GESTIÓN DE EQUIPOS Y PLANTILLAS =====");
			System.out.println("1. Crear equipo");
			System.out.println("2. Asignar entrenador a equipo");
			System.out.println("3. Mostrar equipos");
			System.out.println("4. Mostrar alineación");
			System.out.println("5. Convocar titular");
			System.out.println("6. Convocar titulares automáticamente");
			System.out.println("7. Eliminar jugador de un equipo");
			System.out.println("8. Mostrar coste total de un equipo");
			System.out.println("9. Entrenar jugadores de un equipo");
			System.out.println("10. Fichar jugador");
			System.out.println("11. Volver");
			opcion = leerEnteroEnRango(teclado, "Seleccione una opción: ", 1, 11);

			switch (opcion) {
			case 1:
				try {
					crearEquipo(teclado, liga);
				} catch (EquipoDuplicadoException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				asignarEntrenador(teclado, liga);
				break;
			case 3:
				System.out.println(liga.mostrarEquipos());
				break;
			case 4:
				mostrarAlineacion(teclado, liga);
				break;
			case 5:
				try {
					convocarTitular(teclado, liga);
				} catch (RolNoDisponibleException e) {
					System.out.println(e.getMessage());
				} catch (JugadorSancionadoException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 6:
				try {
					convocarAutomaticamente(teclado, liga);
				} catch (RolNoDisponibleException e) {
					System.out.println(e.getMessage());
				} catch (JugadorSancionadoException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 7:
				eliminarJugadorEquipo(teclado, liga);
				break;
			case 8:
				mostrarCosteEquipo(teclado, liga);
				break;
			case 9:
				entrenarJugadoresEquipo(teclado, liga);
				break;
			case 10:
				try {
					ficharJugador(teclado, liga);
				} catch (EquipoCompletoException e) {
					System.out.println(e.getMessage());
				}
				break;

			case 11:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Opción no válida");
			}

		} while (opcion != 11);
	}

	private static void menuPartidos(Scanner teclado, Liga liga) {
		int opcion;
		do {
			System.out.println("\n===== CALENDARIO Y PARTIDOS =====");
			System.out.println("1. Generar partidos automáticos");
			System.out.println("2. Mostrar calendario");
			System.out.println("3. Mostrar jornada concreta");
			System.out.println("4. Mostrar partidos pendientes");
			System.out.println("5. Mostrar siguiente partido pendiente");
			System.out.println("6. Disputar siguiente partido");
			System.out.println("7. Volver");
			opcion = leerEnteroEnRango(teclado, "Seleccione una opción: ", 1, 7);

			switch (opcion) {
			case 1:
				try {
					liga.generarPartidosAutomaticos();
					System.out.println("Partidos generados automáticamente");
				} catch (PartidoInvalidoException e) {
					System.out.println(e.getMessage());
				} catch (PartidoDuplicadoException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				System.out.println(liga.mostrarCalendario());
				break;
			case 3:
				int jornadaConsultar = leerEnteroEnRango(teclado, "Introduce el número de jornada: ", 1, 100);
				System.out.println(liga.mostrarJornada(jornadaConsultar));
				break;
			case 4:
				System.out.println(liga.mostrarPartidosPendientes());
				break;
			case 5:
				try {
					Partido siguientePendiente = liga.mostrarSiguientePartido();
					System.out.println(siguientePendiente);
				} catch (ColaPartidosVaciaException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					System.out.println("No se pudo mostrar el siguiente partido.");
				}
				break;
			case 6:
				try {
					disputarSiguientePartido(liga);
				} catch (ColaPartidosVaciaException e) {
					System.out.println(e.getMessage());
				} catch (PartidoInvalidoException e) {
					System.out.println(e.getMessage());
				} catch (Exception e) {
					System.out.println("Se ha producido un error inesperado al disputar el partido.");
				}
				break;
			case 7:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Opción no válida");
			}

		} while (opcion != 7);
	}

	private static void menuIncidencias(Scanner teclado, Liga liga) {
		int opcion;
		do {
			System.out.println("\n===== INCIDENCIAS Y SANCIONES =====");
			System.out.println("1. Mostrar incidencias");
			System.out.println("2. Resolver incidencia");
			System.out.println("3. Volver");
			opcion = leerEnteroEnRango(teclado, "Seleccione una opción: ", 1, 3);

			switch (opcion) {
			case 1:
				System.out.println(liga.listarIncidencias());
				break;
			case 2:
				resolverIncidencia(teclado, liga);
				break;
			case 3:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Opción no válida");
			}

		} while (opcion != 3);
	}

	private static void menuEstadisticas(Scanner teclado, Liga liga) {
		int opcion;
		do {
			System.out.println("\n===== CLASIFICACIÓN, ESTADÍSTICAS E HISTORIAL =====");
			System.out.println("1. Mostrar clasificación");
			System.out.println("2. Mostrar estadísticas de la liga");
			System.out.println("3. Mostrar historial");
			System.out.println("4. Deshacer última acción");
			System.out.println("5. Mostrar campeón");
			System.out.println("6. Reiniciar temporada");
			System.out.println("7. Volver");
			opcion = leerEnteroEnRango(teclado, "Seleccione una opción: ", 1, 7);

			switch (opcion) {
			case 1:
				System.out.println(liga.mostrarClasificacion());
				break;
			case 2:
				System.out.println(liga.mostrarEstadisticasLiga());
				break;
			case 3:
				System.out.println(liga.mostrarHistorial());
				break;
			case 4:
				System.out.println(liga.deshacerUltimaAccion());
				break;
			case 5:
				try {
					liga.asignarCampeonAutomatico();
					System.out.println(liga.mostrarCampeon());
				} catch (Exception e) {
					System.out.println("No se pudo calcular el campeón.");
				}
				break;
			case 6:
				liga.reiniciarTemporada();
				System.out.println("Temporada reiniciada correctamente");
				break;
			case 7:
				System.out.println("Volviendo al menú principal...");
				break;
			default:
				System.out.println("Opción no válida");
			}

		} while (opcion != 7);
	}

	private static void crearEntrenador(Scanner teclado, Liga liga) throws PersonaDuplicadaException {
		teclado.nextLine();

		System.out.print("ID del entrenador: ");
		String idEntrenador = teclado.nextLine();

		String nombreEntrenador = leerTextoSoloLetras(teclado, "Nombre: ");

		System.out.print("Nick: ");
		String nickEntrenador = teclado.nextLine();

		int edadEntrenador = leerEnteroEnRango(teclado, "Edad: ", 18, 100);

		double salarioEntrenador = leerDoubleMinimo(teclado, "Salario: ", 1);

		int experienciaEntrenador = leerEnteroEnRango(teclado, "Experiencia: ", 0, 100);

		System.out.println("Seleccione especialidad:");
		System.out.println("1. ATAQUE");
		System.out.println("2. DEFENSA");
		System.out.println("3. ESTRATEGIA");
		System.out.println("4. MOTIVACION");
		System.out.println("5. ANALISIS");

		int opcionEspecialidad = leerEnteroEnRango(teclado, "Opción: ", 1, 5);

		Especialidad especialidadEntrenador = null;

		switch (opcionEspecialidad) {
		case 1:
			especialidadEntrenador = Especialidad.ATAQUE;
			break;
		case 2:
			especialidadEntrenador = Especialidad.DEFENSA;
			break;
		case 3:
			especialidadEntrenador = Especialidad.ESTRATEGIA;
			break;
		case 4:
			especialidadEntrenador = Especialidad.MOTIVACION;
			break;
		case 5:
			especialidadEntrenador = Especialidad.ANALISIS;
			break;
		}

		Entrenador nuevoEntrenador = new Entrenador(
				idEntrenador,
				nombreEntrenador,
				nickEntrenador,
				edadEntrenador,
				salarioEntrenador,
				experienciaEntrenador,
				especialidadEntrenador
		);

		liga.añadirPersona(nuevoEntrenador);
		System.out.println("Entrenador creado correctamente");
	}

	private static void crearJugador(Scanner teclado, Liga liga) throws PersonaDuplicadaException, EquipoCompletoException {
		teclado.nextLine();

		System.out.print("ID del jugador: ");
		String idJugador = teclado.nextLine();

		String nombreJugador = leerTextoSoloLetras(teclado, "Nombre: ");

		System.out.print("Nick: ");
		String nickJugador = teclado.nextLine();

		int edadJugador = leerEnteroEnRango(teclado, "Edad: ", 16, 100);

		double salarioJugador = leerDoubleMinimo(teclado, "Salario: ", 1);

		System.out.println("Seleccione rol:");
		System.out.println("1. TANQUE");
		System.out.println("2. CURADOR");
		System.out.println("3. DPS_FISICO");
		System.out.println("4. DPS_MAGICO");
		System.out.println("5. SOPORTE");
		int opcionRol = leerEnteroEnRango(teclado, "Opción: ", 1, 5);
		Rol rolJugador = null;

		switch (opcionRol) {
		case 1:
			rolJugador = Rol.TANQUE;
			break;
		case 2:
			rolJugador = Rol.CURADOR;
			break;
		case 3:
			rolJugador = Rol.DPS_FISICO;
			break;
		case 4:
			rolJugador = Rol.DPS_MAGICO;
			break;
		case 5:
			rolJugador = Rol.SOPORTE;
			break;
		}

		if (rolJugador == null) {
			System.out.println("Rol no válido. No se ha creado el jugador");
			return;
		}

		int nivelMecanico = leerEnteroEnRango(teclado, "Nivel mecánico: ", 0, 100);

		int nivelEstrategico = leerEnteroEnRango(teclado, "Nivel estratégico: ", 0, 100);

		teclado.nextLine();

		System.out.print("Nombre del equipo: ");
		String nombreEquipoJugador = teclado.nextLine();

		Equipo equipoJugador = liga.buscarEquipoPorNombre(nombreEquipoJugador);

		if (equipoJugador != null) {
			Jugador nuevoJugador = new Jugador(
					idJugador,
					nombreJugador,
					nickJugador,
					edadJugador,
					salarioJugador,
					rolJugador,
					nivelMecanico,
					nivelEstrategico
			);

			equipoJugador.añadirJugador(nuevoJugador);
			liga.añadirPersona(nuevoJugador);

			System.out.println("Jugador creado correctamente");
		} else {
			System.out.println("No existe ese equipo");
		}
	}
	
	private static void entrenarJugadoresEquipo(Scanner teclado, Liga liga) {
		teclado.nextLine();
		System.out.print("Nombre del equipo: ");
		
		String nombreEquipoEntrenar = teclado.nextLine();
		Equipo equipoEntrenar = liga.buscarEquipoPorNombre(nombreEquipoEntrenar);

		if (equipoEntrenar != null) {
			int entrenados = equipoEntrenar.entrenarJugadores();
			System.out.println("Jugadores entrenados: " + entrenados);
		} else {
			System.out.println("No existe ese equipo");
		}
	}

	private static void crearEquipo(Scanner teclado, Liga liga) throws EquipoDuplicadoException {
		teclado.nextLine();

		String nombreEquipo = leerTextoSoloLetras(teclado, "Nombre del equipo: ");

		String ciudad = leerTextoSoloLetras(teclado, "Ciudad: ");

		double presupuesto = leerDoubleMinimo(teclado, "Presupuesto: ", 1);

		Equipo nuevoEquipo = new Equipo(nombreEquipo, ciudad, presupuesto, null);
		liga.añadirEquipo(nuevoEquipo);

		System.out.println("Equipo creado correctamente");
	}

	private static void asignarEntrenador(Scanner teclado, Liga liga) {
		teclado.nextLine();

		System.out.print("ID del entrenador: ");
		String idEntrenadorAsignar = teclado.nextLine();

		PersonaLiga personaEntrenador = liga.buscarPersonaPorId(idEntrenadorAsignar);

		System.out.print("Nombre del equipo: ");
		String nombreEquipoAsignar = teclado.nextLine();

		Equipo equipoAsignar = liga.buscarEquipoPorNombre(nombreEquipoAsignar);

		if (personaEntrenador != null && equipoAsignar != null && personaEntrenador instanceof Entrenador) {
			Entrenador entrenadorAsignar = (Entrenador) personaEntrenador;
			equipoAsignar.setEntrenador(entrenadorAsignar);
			System.out.println("Entrenador asignado correctamente");
		} else {
			System.out.println("No se ha encontrado un entrenador válido o el equipo no existe");
		}
	}

	private static void buscarPersona(Scanner teclado, Liga liga) {
		teclado.nextLine();

		System.out.print("ID de la persona: ");
		String idBuscarPersona = teclado.nextLine();

		PersonaLiga personaBuscada = liga.buscarPersonaPorId(idBuscarPersona);

		if (personaBuscada != null) {
			System.out.println(personaBuscada);
		} else {
			System.out.println("No existe ninguna persona con ese ID");
		}
	}

	private static void modificarNick(Scanner teclado, Liga liga) {
		teclado.nextLine();

		System.out.print("ID de la persona: ");
		String idModificarNick = teclado.nextLine();

		System.out.print("Nuevo nick: ");
		String nuevoNick = teclado.nextLine();

		if (liga.modificarNickPersona(idModificarNick, nuevoNick)) {
			System.out.println("Nick modificado correctamente");
		} else {
			System.out.println("No existe ninguna persona con ese ID");
		}
	}

	private static void modificarSalario(Scanner teclado, Liga liga) {
		teclado.nextLine();

		System.out.print("ID de la persona: ");
		String idModificarSalario = teclado.nextLine();

		System.out.print("Nuevo salario: ");
		double nuevoSalario = leerDoubleMinimo(teclado, "Nuevo salario: ", 1);

		if (liga.modificarSalarioPersona(idModificarSalario, nuevoSalario)) {
			System.out.println("Salario modificado correctamente");
		} else {
			System.out.println("No existe ninguna persona con ese ID");
		}
	}

	private static void eliminarPersona(Scanner teclado, Liga liga) {
		teclado.nextLine();

		System.out.print("ID de la persona a eliminar: ");
		String idEliminarPersona = teclado.nextLine();

		if (liga.eliminarPersona(idEliminarPersona)) {
			System.out.println("Persona eliminada correctamente");
		} else {
			System.out.println("No existe ninguna persona con ese ID");
		}
	}

	private static void mostrarAlineacion(Scanner teclado, Liga liga) {
		teclado.nextLine();

		System.out.print("Nombre del equipo: ");
		String nombreEquipoAlineacion = teclado.nextLine();

		Equipo equipoAlineacion = liga.buscarEquipoPorNombre(nombreEquipoAlineacion);

		if (equipoAlineacion != null) {
			System.out.println(equipoAlineacion.mostrarAlineacion());
		} else {
			System.out.println("No existe ese equipo");
		}
	}

	private static void convocarTitular(Scanner teclado, Liga liga) throws RolNoDisponibleException, JugadorSancionadoException {
		teclado.nextLine();

		System.out.print("Nombre del equipo: ");
		String nombreEquipoConvocar = teclado.nextLine();

		Equipo equipoConvocar = liga.buscarEquipoPorNombre(nombreEquipoConvocar);

		if (equipoConvocar != null) {
			System.out.print("ID del jugador a convocar como titular: ");
			String idTitular = teclado.nextLine();

			if (equipoConvocar.convocarTitular(idTitular)) {
				System.out.println("Jugador convocado como titular");
			} else {
				System.out.println("No se pudo convocar al jugador");
			}
		} else {
			System.out.println("No existe ese equipo");
		}
	}

	private static void convocarAutomaticamente(Scanner teclado, Liga liga) throws RolNoDisponibleException, JugadorSancionadoException {
		teclado.nextLine();

		System.out.print("Nombre del equipo: ");
		String nombreEquipoAuto = teclado.nextLine();

		Equipo equipoAuto = liga.buscarEquipoPorNombre(nombreEquipoAuto);

		if (equipoAuto != null) {
			int convocados = equipoAuto.convocarTitularesAutomaticamente();
			System.out.println("Titulares convocados automáticamente: " + convocados);
		} else {
			System.out.println("No existe ese equipo");
		}
	}

	private static void eliminarJugadorEquipo(Scanner teclado, Liga liga) {
		teclado.nextLine();

		System.out.print("Nombre del equipo: ");
		String nombreEquipoEliminarJugador = teclado.nextLine();

		Equipo equipoEliminarJugador = liga.buscarEquipoPorNombre(nombreEquipoEliminarJugador);

		if (equipoEliminarJugador != null) {
			System.out.print("ID del jugador a eliminar: ");
			String idJugadorEliminar = teclado.nextLine();

			if (equipoEliminarJugador.eliminarJugador(idJugadorEliminar)) {
				System.out.println("Jugador eliminado correctamente del equipo");
			} else {
				System.out.println("No existe ningún jugador con ese ID en ese equipo");
			}
		} else {
			System.out.println("No existe ese equipo");
		}
	}
	
	private static void ficharJugador(Scanner teclado, Liga liga) throws EquipoCompletoException {
		teclado.nextLine();

		System.out.print("ID del jugador a fichar: ");
		String idJugador = teclado.nextLine();

		System.out.print("Nombre del equipo destino: ");
		String nombreEquipoDestino = teclado.nextLine();

		if (liga.ficharJugador(idJugador, nombreEquipoDestino)) {
			System.out.println("Jugador fichado correctamente");
		} else {
			System.out.println("No se pudo realizar el fichaje");
		}
	}
	
	private static void mostrarCosteEquipo(Scanner teclado, Liga liga) {
		teclado.nextLine();

		System.out.print("Nombre del equipo: ");
		String nombreEquipoCoste = teclado.nextLine();

		Equipo equipoCoste = liga.buscarEquipoPorNombre(nombreEquipoCoste);

		if (equipoCoste != null) {
			System.out.println("Coste total del equipo " +
					equipoCoste.getNombre() + ": " +
					equipoCoste.calcularCosteTotal() + " €");
		} else {
			System.out.println("No existe ese equipo");
		}
	}

	private static void disputarSiguientePartido(Liga liga) throws ColaPartidosVaciaException, PartidoInvalidoException {
		Partido siguiente = liga.disputarSiguientePartido();
		siguiente.registrarResultadoAleatorio();

		Jugador jugadorSancionadoAuto = siguiente.generarJugadorAleatorioDelPartido();

		Incidencia incidenciaAuto;

		if (siguiente.getEquipoLocal().buscarJugadorPorId(jugadorSancionadoAuto.getId()) != null) {
			incidenciaAuto = new Incidencia(
					"I" + siguiente.getId(),
					TipoIncidencia.SANCION,
					"Conducta antideportiva",
					siguiente.getEquipoLocal(),
					jugadorSancionadoAuto,
					siguiente
			);
		} else {
			incidenciaAuto = new Incidencia(
					"I" + siguiente.getId(),
					TipoIncidencia.SANCION,
					"Conducta antideportiva",
					siguiente.getEquipoVisitante(),
					jugadorSancionadoAuto,
					siguiente
			);
		}

		incidenciaAuto.aplicarSancion();
		liga.registrarIncidencia(incidenciaAuto);

		System.out.println(siguiente.mostrarResultado());
		System.out.println("Incidencia generada automáticamente");
	}

	private static void resolverIncidencia(Scanner teclado, Liga liga) {
		teclado.nextLine();

		System.out.print("ID de la incidencia: ");
		String idIncidenciaResolver = teclado.nextLine();

		Incidencia incidenciaResolver = liga.buscarIncidenciaPorId(idIncidenciaResolver);

		if (incidenciaResolver != null) {
			incidenciaResolver.resolverIncidencia();
			System.out.println("Incidencia resuelta correctamente");
		} else {
			System.out.println("No existe ninguna incidencia con ese ID");
		}
	}
	private static String leerTextoSoloLetras(Scanner teclado, String mensaje) {
		String texto;
		boolean valido;
		do {
			System.out.print(mensaje);
			texto = teclado.nextLine();
			valido = true;
			if (texto.isEmpty()) {
				valido = false;
			} else {
				for (int i = 0; i < texto.length(); i++) {
					char c = texto.charAt(i);

					if (!Character.isLetter(c) && c != ' ') {
						valido = false;
					}
				}
			}
			if (!valido) {
				System.out.println("Dato no válido. Solo se permiten letras y espacios.");
			}
		} while (!valido);
		return texto;
	}
	
	private static int leerEnteroEnRango(Scanner teclado, String mensaje, int minimo, int maximo) {
		int numero = 0;
		boolean valido;
		do {
			System.out.print(mensaje);
			valido = teclado.hasNextInt();
			if (valido) {
				numero = teclado.nextInt();

				if (numero < minimo || numero > maximo) {
					valido = false;
					System.out.println("Dato no válido. Debe estar entre " + minimo + " y " + maximo + ".");
				}
			} else {
				System.out.println("Dato no válido. Debe introducir un número entero.");
				teclado.nextLine();
			}
		} while (!valido);
		return numero;
	}
	
	private static double leerDoubleMinimo(Scanner teclado, String mensaje, double minimo) {
		double numero = 0;
		boolean valido;
		do {
			System.out.print(mensaje);
			valido = teclado.hasNextDouble();
			if (valido) {
				numero = teclado.nextDouble();
				if (numero < minimo) {
					valido = false;
					System.out.println("Dato no válido. Debe ser mayor o igual que " + minimo + ".");
				}
			} else {
				System.out.println("Dato no válido. Debe introducir un número.");
				teclado.nextLine();
			}
		} while (!valido);
		return numero;
	}
}