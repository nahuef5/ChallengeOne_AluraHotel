package views;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controllers.*;
import dtos.*;
import entities.*;
import factory.ConnectionFactory;
import java.awt.event.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modeloReserva;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	String reserva;
	String huesped;

	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(tbReservas);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblTitulo = new JLabel("SISTEMA DE BÚSQUEDA");
		lblTitulo.setBounds(331, 62, 280, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Roboto Black", Font.BOLD, 24));
		contentPane.add(lblTitulo);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBounds(20, 169, 865, 328);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		contentPane.add(panel);

		tbHuespedes = new JTable();
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), tbHuespedes,
				null);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Numero de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Numero de Reserva");
		llenarTablaHuespedes();

		tbReservas = new JTable();
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), tbReservas,
				null);
		modeloReserva = (DefaultTableModel) tbReservas.getModel();
		modeloReserva.addColumn("Numero de Reserva");
		modeloReserva.addColumn("Fecha Check In");
		modeloReserva.addColumn("Fecha Check Out");
		modeloReserva.addColumn("Valor");
		modeloReserva.addColumn("Forma de Pago");
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		llenarTablaReservas();

		JLabel logo = new JLabel("");
		logo.setBounds(56, 51, 104, 107);
		logo.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		contentPane.add(logo);

		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(539, 159, 193, 2);
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2);
		/**
		 * btnbuscar JPanel que realiza busquedas.
		 * 
		 * eventos: - MouseClicked: Se activa al hacer clic en el JPanel. - limpia la
		 * tabla. - obtiene el texto del campo de texto llamado txtBuscar. - si el texto
		 * no esta vacio: - verifica si es un numero o si sigue el patron de nombre y
		 * apellido. - si es un numero, realiza una busqueda por numero de reserva. - si
		 * es un nombre y apellido, los separa y realiza una busqueda por nombre y
		 * apellido. - si el texto no sigue ninguno de los patrones, muestra un mensaje
		 * de error. - si el texto esta vacio, llena las tablas de reservas y huespedes.
		 */
		JPanel btnbuscar = new JPanel();
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarTabla();
				String parameter = txtBuscar.getText();
				if(!parameter.isEmpty()){
					if(isNumber(parameter) || parameter.matches("^[A-Z][a-zA-Z]{2,14} [A-Z][a-zA-Z]{2,14}$")){
						if(isNumber(parameter)){
							findByNumeroReserva(parameter);
						}
						else{
							String[] nombreApellido = parameter.split(" ");
							String nombre = nombreApellido[0];
							String apellido = nombreApellido[1];
							findByNombreYApellido(nombre, apellido);
						}
					}
					else{
						JOptionPane.showMessageDialog(null,
								"Debe colocar valores numericos o alfabeticos que\n"
										+ "representen nombre y apellido. Debe comenzar con mayusucla\n"
										+ "y tener un espacio entre ellos.");
					}
				}
				else{
					llenarTablaReservasId();
					llenarTablaHuespedesId();
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();

				if (filaReservas >= 0){
					updateReservas();
					limpiarTabla();
					llenarTablaReservas();
					llenarTablaHuespedes();
				}
				else if(filaHuespedes >= 0){
					updateHuesped();
					limpiarTabla();
					llenarTablaHuespedes();
					llenarTablaReservas();
				}
			}
		});
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();

				if (filaReservas >= 0){

					reserva = tbReservas.getValueAt(filaReservas, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar los datos?");

					if(confirmar == JOptionPane.YES_OPTION){

						try(Connection connection = new ConnectionFactory().connection()){
							var reservaController = new ReservaController(connection);

							String valor = tbReservas.getValueAt(filaReservas, 0).toString();
							var idReserva = Integer.valueOf(valor);
							reservaController.deleteReserva(idReserva);

							JOptionPane.showMessageDialog(contentPane, "Reserva eliminada");
							limpiarTabla();
							llenarTablaReservas();
							llenarTablaHuespedes();
						}
						catch(SQLException ex){
							JOptionPane.showMessageDialog(null, "Ocurrio un error con la conexion.");
							throw new RuntimeException("Error al generar la conexion");
						}
					}
				}
				else if(filaHuespedes >= 0){

					try(Connection connection = new ConnectionFactory().connection()){
						var huespedController = new HuespedController(connection);
						huesped = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
						int confirmarh = JOptionPane.showConfirmDialog(null, "¿Desea eliminar este huesped?");
						
						if(confirmarh == JOptionPane.YES_OPTION){
							String valor = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
							var numeroReserva = Integer.valueOf(valor);
							huespedController.deleteHuesped(numeroReserva);

							JOptionPane.showMessageDialog(contentPane, "Huesped eliminado");
							limpiarTabla();
							llenarTablaHuespedes();
							llenarTablaReservas();
						}

					}
					catch(SQLException ex){
						JOptionPane.showMessageDialog(null, "Ocurrio un error con la conexion.");
						throw new RuntimeException("Ocurrio un error con la conexion.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null,
							"Error fila no seleccionada, por favor realice una busqueda y seleccione una fila para eliminar");
				}
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}
	/**
	 * Recupera una lista de todas las reservas desde la base de datos.
	 *
	 * @return Lista de objetos Reserva que representa todas las reservas
	 *         almacenadas en la base de datos.
	 * @throws RuntimeException Si ocurre un error de SQL o de conexión.
	 */
	private List<Reserva> findAllReservas(){
		try(Connection connection = new ConnectionFactory().connection()){
			ReservaController reservaController = new ReservaController(connection);
			return reservaController.findAll();
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Ocurrio un error con la conexion.");
			throw new RuntimeException("Ocurrio un error con la conexion.");
		}
	}
	/**
	 * Busca una reserva por su ID en la base de datos.
	 *
	 * @return Objeto Reserva que corresponde al ID proporcionado.
	 * @throws NumberFormatException Si el texto en txtBuscar no puede convertirse a
	 *                               un entero.
	 * @throws RuntimeException      Si ocurre un error de SQL o de conexión.
	 */
	private Reserva findByIdReserva(){
		try(Connection connection = new ConnectionFactory().connection()){
			ReservaController reservaController = new ReservaController(connection);
			var id = Integer.valueOf(txtBuscar.getText());
			return reservaController.findById(id);
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Ocurrio un error con la conexion.");
			throw new RuntimeException("Ocurrio un error con la conexion.");
		}
	}
	/**
	 * Recupera una lista de todos los huéspedes desde la base de datos.
	 *
	 * @return Lista de objetos Huesped que representa todos los huéspedes
	 *         almacenados en la base de datos.
	 * @throws RuntimeException Si ocurre un error de SQL o de conexión.
	 */
	private List<Huesped> findAllHuesped(){
		try(Connection connection = new ConnectionFactory().connection()){
			HuespedController huespedController = new HuespedController(connection);
			return huespedController.readHuespedList();
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Ocurrio un error con la conexion.");
			throw new RuntimeException("Ocurrio un error con la conexion.");
		}
	}
	/**
	 * Busca un huésped por su ID en la base de datos.
	 *
	 * @return Objeto Huesped que corresponde al ID proporcionado.
	 * @throws NumberFormatException Si el texto en txtBuscar no puede convertirse a
	 *                               un entero.
	 * @throws RuntimeException      Si ocurre un error de SQL o de conexión.
	 */
	private Huesped findHuespedById(){
		try(Connection connection = new ConnectionFactory().connection()){
			HuespedController huespedController = new HuespedController(connection);
			var id = Integer.valueOf(txtBuscar.getText());
			return huespedController.getOne(id);
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Ocurrio un error con la conexion.");
			throw new RuntimeException("Ocurrio un error con la conexion.");
		}
	}
	private void limpiarTabla(){
		((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}
	/**
	 * Llena la tabla de reservas con datos de la base de datos.
	 *
	 * @throws RuntimeException Si se encuentran errores al procesar los datos.
	 */
	private void llenarTablaReservas(){
		List<Reserva> reservas = findAllReservas();
		try{
			reservas.forEach(reserva -> {
				modeloReserva.addRow(new Object[] { reserva.getId(), reserva.getFechaEntrada(),
						reserva.getFechaSalida(), reserva.getFormaPago(), reserva.getValor(), });
			});
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Debe colocar valores numericos.");
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Llena la tabla de reservas con una reserva específica encontrada por su ID.
	 *
	 * @throws RuntimeException Si se encuentran errores al procesar los datos.
	 */
	private void llenarTablaReservasId(){
		Reserva reserva = findByIdReserva();
		try{
			Vector<Object> rowData = new Vector<>();
			rowData.add(reserva.getId());
			rowData.add(reserva.getFechaEntrada());
			rowData.add(reserva.getFechaSalida());
			rowData.add(reserva.getFormaPago());
			rowData.add(reserva.getValor());
			modeloReserva.addRow(rowData);
		}
		catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * Verifica si una cadena de texto puede convertirse a un número entero.
	 *
	 * @param string La cadena de texto a verificar.
	 * @return true si la cadena es un número entero, false en caso contrario.
	 */
	private boolean isNumber(String string){
		try{
			Integer.parseInt(string);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	/**
	 * Busca una lista de huéspedes por su nombre y apellido en la base de datos.
	 *
	 * @param nombre   El nombre del huésped.
	 * @param apellido El apellido del huésped.
	 * @return Una lista de objetos Huesped que coinciden con el nombre y apellido
	 *         proporcionados.
	 * @throws RuntimeException Si ocurre un error de SQL o de conexión.
	 */
	private List<Huesped> findHuespedByNombreApellido(String nombre, String apellido){
		try(Connection connection = new ConnectionFactory().connection()){
			HuespedController huespedController = new HuespedController(connection);
			return huespedController.findByNombreAndApellido(nombre, apellido);
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Ocurrió un error con la conexión.");
			throw new RuntimeException("Ocurrió un error con la conexión.");
		}
	}
	/**
	 * Busca y muestra en la tabla información de huéspedes por nombre y apellido.
	 * Si se encuentran huéspedes con los nombres y apellidos proporcionados, se
	 * añaden a la tabla. Si no se encuentran resultados, se muestra un mensaje de
	 * alerta.
	 *
	 * @param nombre El nombre del huésped a buscar.
	 * @param apellido El apellido del huésped a buscar.
	 * @throws RuntimeException Si se produce un error al agregar información a la tabla.
	 */
	private void findByNombreYApellido(String nombre, String apellido){
		List<Huesped> huespeds = findHuespedByNombreApellido(nombre, apellido);
		if(!huespeds.isEmpty()) {
			try{
				huespeds.forEach(h -> {
					modeloHuesped.addRow(new Object[]{
							h.getId(),
							h.getNombre(),
							h.getApellido(),
							h.getFechaNacimiento(),
							h.getNacionalidad(),
							h.getTelefono(),
							h.getNumeroReserva() });
				});
			}
			catch(Exception e){
				throw new RuntimeException(e.getMessage());
			}
		}
		else JOptionPane.showMessageDialog(null, "No se encontro ningun huesped con ese nombre y apellido.");
	}
	/**
	 * Busca una reserva por su número de reserva y la agrega a la tabla de
	 * reservas.
	 *
	 * @param parametro El número de reserva a buscar.
	 */
	private void findByNumeroReserva(String parametro){
		try{
			int idReserva = Integer.parseInt(parametro);
			Reserva reserva = findReservaById(idReserva);
			
			if(reserva != null){
				Vector<Object> rowData = new Vector<>();
				rowData.add(reserva.getId());
				rowData.add(reserva.getFechaEntrada());
				rowData.add(reserva.getFechaSalida());
				rowData.add(reserva.getFormaPago());
				rowData.add(reserva.getValor());
				modeloReserva.addRow(rowData);
			}
			else JOptionPane.showMessageDialog(null, "No se encontró esa reserva");
		}
		catch(NumberFormatException e){
			JOptionPane.showMessageDialog(null, "Ingrese un número válido para la búsqueda de reserva");
		}
	}
	/**
	 * Busca una reserva por su ID en la base de datos.
	 *
	 * @param id El ID de la reserva a buscar.
	 * @return Objeto Reserva que corresponde al ID proporcionado.
	 * @throws RuntimeException Si ocurre un error de SQL o de conexión.
	 */
	private Reserva findReservaById(int id){
		try(Connection connection = new ConnectionFactory().connection()){
			ReservaController reservaController = new ReservaController(connection);
			return reservaController.findById(id);
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null, "Ocurrió un error con la conexión.");
			throw new RuntimeException("Ocurrió un error con la conexión.");
		}
	}
	/**
	 * Llena la tabla de huéspedes con datos obtenidos de la base de datos. Los
	 * datos incluyen: ID, Nombre, Apellido, Fecha de Nacimiento, Nacionalidad,
	 * Teléfono y Número de Reserva.
	 *
	 * @throws RuntimeException Si se encuentran errores al procesar los datos.
	 */
	private void llenarTablaHuespedes(){
		List<Huesped> huespeds = findAllHuesped();
		try{
			huespeds.forEach(h -> {
				modeloHuesped.addRow(new Object[] { h.getId(), h.getNombre(), h.getApellido(), h.getFechaNacimiento(),
						h.getNacionalidad(), h.getTelefono(), h.getNumeroReserva() });
			});
		}
		catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Llena la tabla de huéspedes con datos de un huésped específico obtenidos de
	 * la base de datos. Los datos incluyen: ID, Nombre, Apellido, Fecha de
	 * Nacimiento, Nacionalidad, Teléfono y Número de Reserva.
	 *
	 * @throws RuntimeException Si se encuentran errores al procesar los datos.
	 */
	private void llenarTablaHuespedesId(){
		Huesped huesped = findHuespedById();
		try{
			Vector<Object> rowData = new Vector<>();
			rowData.add(huesped.getId());
			rowData.add(huesped.getNombre());
			rowData.add(huesped.getApellido());
			rowData.add(huesped.getFechaNacimiento());
			rowData.add(huesped.getNacionalidad());
			rowData.add(huesped.getTelefono());
			rowData.add(huesped.getNumeroReserva());
			modeloHuesped.addRow(rowData);
		}
		catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
	/**
	 * Actualiza los datos de un huésped en la base de datos y muestra un mensaje de
	 * confirmación.
	 *
	 * @throws RuntimeException Si se encuentra un error de SQL o de conexión, o si
	 * los datos no son válidos.
	 */
	private void updateHuesped(){
		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
				.ifPresentOrElse(filaHuesped -> {
					try(Connection connection = new ConnectionFactory().connection()){
						HuespedController huespedController = new HuespedController(connection);
						Date nacimiento = Date
								.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString());

						Calendar calendar = Calendar.getInstance();
						calendar.setTime(nacimiento);
						int year = calendar.get(Calendar.YEAR);
						int month = calendar.get(Calendar.MONTH) + 1;
						int day = calendar.get(Calendar.DAY_OF_MONTH);

						LocalDate fechaNacimiento = LocalDate.of(year, month, day);
						if (!isValidFechaNacimiento(fechaNacimiento)) {
							JOptionPane.showMessageDialog(null, "Debe ser mayor de edad y tener menos de 99 años");
						}
						String nacionalidad = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4);
						String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
						Integer id = Integer
								.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());

						HuespedDTO dto = new HuespedDTO(fechaNacimiento, nacionalidad, telefono);
						huespedController.updateHuesped(id, dto);
						JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "Ocurrio un error con la conexion.");
						throw new RuntimeException("Ocurrio un error con la conexion.");
					}
				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));
	}
	/**
	 * Verifica si una fecha de nacimiento es válida (mayor de edad y menor de 99
	 * años).
	 *
	 * @param fechaNacimiento La fecha de nacimiento a verificar.
	 * @return true si la fecha es válida, false en caso contrario.
	 * @throws IllegalArgumentException Si la fecha de nacimiento no es válida.
	 */
	private boolean isValidFechaNacimiento(LocalDate fechaNacimiento){
		LocalDate now = LocalDate.now();
		var minDate = now.minusYears(18);
		var maxDate = now.minusYears(99);
		if(fechaNacimiento == null || (fechaNacimiento.isBefore(maxDate) || fechaNacimiento.isAfter(minDate)))
			throw new IllegalArgumentException(
					"fecha de nacimiento no valida. Debe ser mayor de edad y no puede superar los 99 años.");
		else return true;
	}
	/**
	 * Actualiza los datos de una reserva en la base de datos y muestra un mensaje
	 * de confirmación.
	 *
	 * @throws RuntimeException Si se encuentra un error de SQL o de conexión, o si
	 *                          los datos no son válidos.
	 */
	private void updateReservas(){
		Optional.ofNullable(modeloReserva.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresentOrElse(fila ->{

					try(Connection connection = new ConnectionFactory().connection()){
						ReservaController reservaController = new ReservaController(connection);

						Date entrada = Date
								.valueOf(modeloReserva.getValueAt(tbReservas.getSelectedRow(), 1).toString());
						Date salida = Date.valueOf(modeloReserva.getValueAt(tbReservas.getSelectedRow(), 2).toString());
						System.out.println("Fecha de salida obtenida de la tabla: " + salida);
						var fechaEntradaUtil = new java.util.Date(entrada.getTime());
						var fechaSalidaUtil = new java.util.Date(salida.getTime());

						Integer id = Integer
								.valueOf(modeloReserva.getValueAt(tbReservas.getSelectedRow(), 0).toString());
						if(isValidFechaEntrada(fechaEntradaUtil)
								&& isValidFechaSalida(fechaSalidaUtil, fechaEntradaUtil)){
							
							Calendar calendarEntrada = Calendar.getInstance();
							calendarEntrada.setTime(entrada);
							Calendar calendarSalida = Calendar.getInstance();
							calendarSalida.setTime(salida);

							int year = calendarEntrada.get(Calendar.YEAR);
							int month = calendarEntrada.get(Calendar.MONTH) + 1;
							int day = calendarEntrada.get(Calendar.DAY_OF_MONTH);
							int anio = calendarSalida.get(Calendar.YEAR);
							int mes = calendarSalida.get(Calendar.MONTH) + 1;
							int dia = calendarSalida.get(Calendar.DAY_OF_MONTH);

							LocalDate fechaEntrada = LocalDate.of(year, month, day);
							LocalDate fechaSalida = LocalDate.of(anio, mes, dia);

							ReservaDTO dto = new ReservaDTO(fechaEntrada, fechaSalida);

							reservaController.updateReserva(id, dto);

							JOptionPane.showMessageDialog(this, String.format("Reserva modificada con exitosamente"));
						}
						else{
							JOptionPane.showMessageDialog(null,
									"Fecha de salida: superior a la de entrada, sin pasarse de los 30 dias."
											+ "Fecha de entrada: igual o superior a la de hoy, con maximo de 5 años.");
						}
					}
					catch(SQLException e){
						JOptionPane.showMessageDialog(null, "Ocurrio un error con la conexion.");
						throw new RuntimeException("Ocurrio un error con la conexion.");
					}
				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));
	}
	/**
	 * Verifica si la fecha de entrada es válida. La fecha de entrada debe ser mayor
	 * o igual a la fecha actual y no puede exceder los 5 años a partir de la fecha
	 * actual.
	 *
	 * @param fechaEntrada La fecha de entrada a verificar.
	 * @return true si la fecha es válida, false en caso contrario.
	 * @throws IllegalArgumentException Si la fecha de entrada no es válida.
	 */
	private boolean isValidFechaEntrada(java.util.Date fechaEntrada){
		java.util.Date entradaMaxima = new java.util.Date(
				System.currentTimeMillis() + (5L * 365 * 24 * 60 * 60 * 1000));

		if(fechaEntrada == null || fechaEntrada.before(new java.util.Date()) || fechaEntrada.after(entradaMaxima)){
			throw new IllegalArgumentException(
					"Fecha de entrada debe ser mayor o igual a la actual y no puede exceder los 5 años.");
		}
		else return true;
	}
	/**
	 * Verifica si la fecha de salida es válida. La fecha de salida debe ser
	 * posterior a la fecha de entrada y no puede exceder los 30 días a partir de la
	 * fecha de entrada.
	 *
	 * @param fechaSalida  La fecha de salida a verificar.
	 * @param fechaEntrada La fecha de entrada asociada.
	 * @return true si la fecha es válida, false en caso contrario.
	 * @throws IllegalArgumentException Si la fecha de salida no es válida.
	 */
	private boolean isValidFechaSalida(java.util.Date fechaSalida, java.util.Date fechaEntrada){
		java.util.Date fechaLimite = new java.util.Date(fechaEntrada.getTime() + (30L * 24 * 60 * 60 * 1000));

		if(fechaSalida == null || fechaSalida.before(fechaEntrada) || fechaSalida.equals(fechaEntrada)
				|| fechaSalida.after(fechaLimite)){
			throw new IllegalArgumentException(
					"Fecha de salida debe ser posterior a la fecha de entrada y no puede exceder los 30 dias de la fecha de entrada.");
		}
		else return true;
	}
	/**
	 * Captura las coordenadas del ratón cuando se presiona el botón.
	 *
	 * @param evt El evento del ratón.
	 */
	private void headerMousePressed(java.awt.event.MouseEvent evt){
		xMouse = evt.getX();
		yMouse = evt.getY();
	}
	/**
	 * Permite arrastrar la ventana al mover el ratón mientras se mantiene
	 * presionado el botón.
	 *
	 * @param evt El evento del ratón.
	 */
	private void headerMouseDragged(java.awt.event.MouseEvent evt){
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}