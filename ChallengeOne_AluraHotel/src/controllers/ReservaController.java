package controllers;
import java.sql.Connection;
import java.util.List;
import dtos.ReservaDTO;
import entities.Reserva;
import services.ReservaService;
/**
*
* @author Nahuel
*/
public class ReservaController{
    private final ReservaService reservaService;
    public ReservaController(Connection connection){
        this.reservaService=new ReservaService(connection);
    }
    public void saveReserva(ReservaDTO dto){
        reservaService.save(dto);
    }
    public Integer getLastIdReserva(){
        return reservaService.getLastIdReserva();
    }
    public void updateReserva(Integer id, ReservaDTO dto){
        reservaService.updateById(id, dto);
    }
    public List<Reserva>findAll(){
        return reservaService.findAll();
    }
    public Reserva findById(Integer id){
        return reservaService.findById(id);
    }
    public void deleteReserva(Integer id){
        reservaService.deleteById(id);
    }
}