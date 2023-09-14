package tests;
/**
*
* @author Nahuel
*/
public class DatabaseTest{

    public static void main(String[] args){
        /*
        ReservaController reservaController = new ReservaController();
        //RESERVA 
        LocalDate fechaEntrada = LocalDate.of(2026, Month.APRIL, 01);
        LocalDate fechaSalida = LocalDate.of(2026, Month.APRIL, 30);
        String formaPago = "Efectivo";
        ReservaDTO dto = new ReservaDTO(fechaEntrada, fechaSalida, formaPago);
        
        reservaController.saveReserva(dto);
        try ( Connection connection = new ConnectionFactory().connection()) {
        var reservaController = new ReservaController(connection);
        //GUARDAR
         LocalDate fechaEntrada = LocalDate.of(2024, Month.JUNE, 5);
        LocalDate fechaSalida = LocalDate.of(2024, Month.JUNE, 11);
        String formaPago = "Tarjeta de Credito";
        ReservaDTO dto = new ReservaDTO(fechaEntrada, fechaSalida, formaPago);
        reservaController.saveReserva(dto);
        
        //LISTA
        List<Reserva>
        
        r = reservaController.findAll();
        r.forEach((rese) -> {
        System.out.println("\n--------------------------------");
        System.out.println(rese.toString());
        System.out.println("\n--------------------------------");
        });
        
        //OBJETO
        Reserva r = reservaController.findById(12);
        System.out.println(r.toString());
        
        //UPDATE
        LocalDate fechaEntrada = LocalDate.of(2026, Month.APRIL, 01);
        LocalDate fechaSalida = LocalDate.of(2026, Month.APRIL, 3);
        
        ReservaDTO dto = new ReservaDTO(fechaEntrada, fechaSalida);
        reservaController.updateReserva(11, dto);
        }
        }
        catch(SQLException e){
        throw new RuntimeException("No");
        }
        LocalDate fechaEntrada = LocalDate.of(2023, Month.NOVEMBER, 05);
        LocalDate fechaSalida = LocalDate.of(2023, Month.NOVEMBER, 12);
        
        ReservaDTO dto = new ReservaDTO(fechaEntrada, fechaSalida);
        
        reservaController.updateReserva (1, dto);
        
        //HUESPED
        
        try(Connection connection = new ConnectionFactory().connection()
        
        ){
        var huespedController = new HuespedController(connection);
        LocalDate fechaNacimiento = LocalDate.of(2005, 1, 26);
        String nombre = "Vin";
        String apellido = "Vin";
        var nacionalidad = "argentina";
        var telefono = "351-9988777";
        HuespedDTO dtoHuesped = new HuespedDTO(nombre, apellido, fechaNacimiento, nacionalidad, telefono);
        Huesped h = new Huesped(dtoHuesped.getNombre(),
        dtoHuesped.getApellido(),
        dtoHuesped.getFechaNacimiento(),
        dtoHuesped.getNacionalidad(),
        dtoHuesped.getTelefono());
        huespedController.saveHuesped(dtoHuesped);
        //DELETE
        huespedController
        .deleteHuesped(11);
        //LISTA
        List<Huesped>
        
        huespedes = huespedController.findByNombreAndApellido("Nahuel", "Funes");
        .readHuespedList();
        for (Huesped huesped : huespedes) {
        System.out.println("\n--------------------------------");
        System.out.println(huesped.toString());
        System.out.println("\n--------------------------------");
        }
        //OBJETO
        Huesped h = huespedController.getOne(3);
        System.out.println(h.toString());
        UPDATE
        var fechaNacimiento = LocalDate.of(1998, 1, 16);
        var nacionalidad = "argentino";
        var telefono = "351-2757908";
        HuespedDTO dtoHuesped = new HuespedDTO(fechaNacimiento, nacionalidad, telefono);
        huespedController.updateHuesped(1, dtoHuesped);
        }
        catch(SQLException e){
        throw new RuntimeException("No");
        }
        */
    }
}