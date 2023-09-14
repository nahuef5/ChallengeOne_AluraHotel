package controllers;
import java.sql.Connection;
import java.util.List;
import dtos.HuespedDTO;
import entities.Huesped;
import services.HuespedService;
/**
*
* @author Nahuel
*/
public class HuespedController{
    private final HuespedService huespedService;
    public HuespedController(Connection connection){
        this.huespedService=new HuespedService(connection);
    }
    public void saveHuesped(HuespedDTO dto) {
        huespedService.save(dto);
    }
    public void deleteHuesped(Integer id) {
        huespedService.deleteById(id);
    }
    public void updateHuesped(Integer id, HuespedDTO dto) {
        huespedService.updateById(id, dto);
    }
    public List<Huesped> readHuespedList() {
        return huespedService.findAll();
    }
    public Huesped getOne(Integer id){
        return huespedService.findById(id);
    }
    public List<Huesped> findByNombreAndApellido(String nombre, String apellido){
        return huespedService.findByNombreAndApellido(nombre, apellido);
    }
    public Huesped findByNumeroReserva(Integer numeroReserva){
        return huespedService.findByNumeroReserva(numeroReserva);
    }
    public int getLastIdReserva(){
        return huespedService.getIdReserva();
    }
}