package views;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import controllers.HuespedController;
import dtos.HuespedDTO;
import factory.ConnectionFactory;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.Format;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("serial")
public class RegistroHuesped extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtNreserva;
	private JDateChooser txtFechaN;
	private JComboBox<Format> txtNacionalidad;
	private JLabel labelExit;
	private JLabel labelAtras;
	private int xMouse, yMouse;
	private int id;
	private final String[] nacionalities=new String[] { "afgano-afgana", "alemán-", "alemana",
			"árabe-árabe", "argentino-argentina", "australiano-australiana", "belga-belga", "boliviano-boliviana",
			"brasileño-brasileña", "camboyano-camboyana", "canadiense-canadiense", "chileno-chilena", "chino-china",
			"colombiano-colombiana", "coreano-coreana", "costarricense-costarricense", "cubano-cubana",
			"danés-danesa", "ecuatoriano-ecuatoriana", "egipcio-egipcia", "salvadoreño-salvadoreña",
			"escocés-escocesa", "español-española", "estadounidense-estadounidense", "estonio-estonia",
			"etiope-etiope", "filipino-filipina", "finlandés-finlandesa", "francés-francesa", "galés-galesa",
			"griego-griega", "guatemalteco-guatemalteca", "haitiano-haitiana", "holandés-holandesa",
			"hondureño-hondureña", "indonés-indonesa", "inglés-inglesa", "iraquí-iraquí", "iraní-iraní",
			"irlandés-irlandesa", "israelí-israelí", "italiano-italiana", "japonés-japonesa", "jordano-jordana",
			"laosiano-laosiana", "letón-letona", "letonés-letonesa", "malayo-malaya", "marroquí-marroquí",
			"mexicano-mexicana", "nicaragüense-nicaragüense", "noruego-noruega", "neozelandés-neozelandesa",
			"panameño-panameña", "paraguayo-paraguaya", "peruano-peruana", "polaco-polaca", "portugués-portuguesa",
			"puertorriqueño-puertorriqueño", "dominicano-dominicana", "rumano-rumana", "ruso-rusa", "sueco-sueca",
			"suizo-suiza", "tailandés-tailandesa", "taiwanes-taiwanesa", "turco-turca", "ucraniano-ucraniana",
			"uruguayo-uruguaya", "venezolano-venezolana", "vietnamita-vietnamita" };
	public RegistroHuesped(Integer idReserva){

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(RegistroHuesped.class.getResource("/imagenes/lOGO-50PX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 634);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane.setLayout(null);

		JPanel header = new JPanel();
		header.setLayout(null);
		header.setBackground(SystemColor.text);
		header.setOpaque(false);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNombre.setBounds(560, 135, 285, 33);
		txtNombre.setBackground(Color.WHITE);
		txtNombre.setColumns(10);
		txtNombre.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtNombre);

		txtApellido = new JTextField();
		txtApellido.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtApellido.setBounds(560, 204, 285, 33);
		txtApellido.setColumns(10);
		txtApellido.setBackground(Color.WHITE);
		txtApellido.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtApellido);

		txtFechaN = new JDateChooser();
		txtFechaN.setBounds(560, 278, 285, 36);
		txtFechaN.getCalendarButton()
				.setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaN.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaN.setDateFormatString("yyyy-MM-dd");
		contentPane.add(txtFechaN);

		txtNacionalidad = new JComboBox();
		txtNacionalidad.setBounds(560, 350, 289, 36);
		txtNacionalidad.setBackground(SystemColor.text);
		txtNacionalidad.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNacionalidad.setModel(new DefaultComboBoxModel(nacionalities));
		contentPane.add(txtNacionalidad);

		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(562, 119, 253, 14);
		lblNombre.setForeground(SystemColor.textInactiveText);
		lblNombre.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblNombre);

		JLabel lblApellido = new JLabel("APELLIDO");
		lblApellido.setBounds(560, 189, 255, 14);
		lblApellido.setForeground(SystemColor.textInactiveText);
		lblApellido.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblApellido);

		JLabel lblNacimiento = new JLabel("FECHA DE NACIMIENTO");
		lblNacimiento.setBounds(560, 256, 255, 14);
		lblNacimiento.setForeground(SystemColor.textInactiveText);
		lblNacimiento.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblNacimiento);

		JLabel lblNacionalidad = new JLabel("NACIONALIDAD");
		lblNacionalidad.setBounds(560, 326, 255, 14);
		lblNacionalidad.setForeground(SystemColor.textInactiveText);
		lblNacionalidad.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblNacionalidad);

		JLabel lblTelefono = new JLabel("TELÉFONO");
		lblTelefono.setBounds(562, 406, 253, 14);
		lblTelefono.setForeground(SystemColor.textInactiveText);
		lblTelefono.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtTelefono.setBounds(560, 424, 285, 33);
		txtTelefono.setColumns(10);
		txtTelefono.setBackground(Color.WHITE);
		txtTelefono.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtTelefono);

		JLabel lblNewLabel_4 = new JLabel("REGISTRO HUÉSPED");
		lblNewLabel_4.setBounds(606, 55, 234, 42);
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.PLAIN, 23));
		contentPane.add(lblNewLabel_4);

		JLabel lblNreserva = new JLabel("NÚMERO DE RESERVA");
		lblNreserva.setBounds(560, 474, 253, 14);
		lblNreserva.setForeground(SystemColor.textInactiveText);
		lblNreserva.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblNreserva);

		txtNreserva = new JTextField();

		txtNreserva.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNreserva.setBounds(560, 495, 285, 33);
		txtNreserva.setColumns(10);
		txtNreserva.setBackground(Color.WHITE);
		txtNreserva.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtNreserva.setEditable(false);
		System.out.println(idReserva);
		String id = String.valueOf(idReserva);
		txtNreserva.setText(id);
		contentPane.add(txtNreserva);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(560, 170, 289, 2);
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2);

		JSeparator separator_1_2_1 = new JSeparator();
		separator_1_2_1.setBounds(560, 240, 289, 2);
		separator_1_2_1.setForeground(new Color(12, 138, 199));
		separator_1_2_1.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_1);

		JSeparator separator_1_2_2 = new JSeparator();
		separator_1_2_2.setBounds(560, 314, 289, 2);
		separator_1_2_2.setForeground(new Color(12, 138, 199));
		separator_1_2_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_2);

		JSeparator separator_1_2_3 = new JSeparator();
		separator_1_2_3.setBounds(560, 386, 289, 2);
		separator_1_2_3.setForeground(new Color(12, 138, 199));
		separator_1_2_3.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_3);

		JSeparator separator_1_2_4 = new JSeparator();
		separator_1_2_4.setBounds(560, 457, 289, 2);
		separator_1_2_4.setForeground(new Color(12, 138, 199));
		separator_1_2_4.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_4);

		JSeparator separator_1_2_5 = new JSeparator();
		separator_1_2_5.setBounds(560, 529, 289, 2);
		separator_1_2_5.setForeground(new Color(12, 138, 199));
		separator_1_2_5.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_5);

		JPanel btnguardar = new JPanel();
		btnguardar.setBounds(723, 560, 122, 35);
		btnguardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				guardarHuesped();
			}
		});
		btnguardar.setLayout(null);
		btnguardar.setBackground(new Color(12, 138, 199));
		contentPane.add(btnguardar);
		btnguardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		JLabel labelGuardar = new JLabel("GUARDAR");
		labelGuardar.setHorizontalAlignment(SwingConstants.CENTER);
		labelGuardar.setForeground(Color.WHITE);
		labelGuardar.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelGuardar.setBounds(0, 0, 122, 35);
		btnguardar.add(labelGuardar);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 489, 634);
		panel.setBackground(new Color(12, 138, 199));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel imagenFondo = new JLabel("");
		imagenFondo.setBounds(0, 121, 479, 502);
		panel.add(imagenFondo);
		imagenFondo.setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/registro.png")));

		JLabel logo = new JLabel("");
		logo.setBounds(194, 39, 104, 107);
		panel.add(logo);
		logo.setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/Ha-100px.png")));
	}

	private void guardarHuesped(){
		try(Connection connection = new ConnectionFactory().connection()){
            HuespedController huespedController = new HuespedController(connection);
            
            if(txtFechaN.getDate() != null && !txtNombre.equals("") && !txtApellido.equals("")
    				&& !txtTelefono.equals("") && isValidName(txtNombre.getText()) && isValidName(txtApellido.getText())){
            	if(isValidTelefono(txtTelefono.getText()) ) {
            		var nombre=txtNombre.getText();
                    var apellido=txtApellido.getText();
                    
                    var nacimiento= ((JTextField) txtFechaN.getDateEditor().getUiComponent()).getText();
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fechaNacimiento = LocalDate.parse(nacimiento, formatter);
                    if(isValidFechaNacimiento(fechaNacimiento)) {
                    	var nacionalidad=txtNacionalidad.getSelectedItem().toString();
                        var telefono=txtTelefono.getText();
                        HuespedDTO dtoHuesped = new HuespedDTO(nombre, apellido, fechaNacimiento, nacionalidad, telefono);
                        
                        huespedController.saveHuesped(dtoHuesped);
                        
                        Exito exito = new Exito();
            			exito.setVisible(true);
            			dispose();
                    }
            	}
            }
            else JOptionPane.showMessageDialog(this, "Debes llenar todos los campos.");
       }
       catch(SQLException e){
    	   	JOptionPane.showMessageDialog(null, "Ocurrio un error con la conexion.");
       		throw new RuntimeException("Ocurrio un error con la conexion.");
       }
	}
	private boolean isValidFechaNacimiento(LocalDate fechaNacimiento){
        LocalDate now=LocalDate.now();
        var minDate=now.minusYears(18);
        var maxDate=now.minusYears(99);
        if(
            fechaNacimiento==null
                ||
            (fechaNacimiento.isBefore(maxDate) || fechaNacimiento.isAfter(minDate))) {
        	JOptionPane.showMessageDialog(null, 
        			"fecha de nacimiento no valida.\n"
        			+ "Debe ser mayor de edad y no puede superar los 99 años.");
        	throw new IllegalArgumentException("fecha de nacimiento no valida. Debe ser mayor de edad y no puede superar los 99 años."
            );
        }
        else return true;
    }
	private boolean isValidTelefono(String telefono){
        String regex="^[0-9]{10}$";
        if(telefono==null || !telefono.matches(regex)) {
        	JOptionPane.showMessageDialog(null,"formato de telefono no valido.\n"
        			+ "Tienen que ser 10 caracteres numericos.");
        	throw new IllegalArgumentException("formato de telefono no valido");
        }
        else return true;
    }
	private boolean isValidName(String name){
        String regex="^[A-Z][a-z]{2,14}$";
        if(!name.matches(regex)) {
        	
        	JOptionPane.showMessageDialog(null,"Formato de nombre o apellido no valido.\n"
        			+ "Tienen que iniciar con mayusculas y ser minimo 3 caracteres alfabeticos");
            throw new IllegalArgumentException(
                    "debe contener minimo 3 caracteres y maximo 15. Debe no debe tener caracteres especiales ni numeros.");
        }
        else return true;
    }
}