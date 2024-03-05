package com.sistema.playaAutos.playaAutos.Controladora;

import com.sistema.playaAutos.playaAutos.Servicio.AutoServicioImp;
import com.sistema.playaAutos.playaAutos.modelos.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/vehiculo")
public class ControladoraApiRest {

    //----------------METODOS DEL API REST-------------------------

    @Autowired
    AutoServicioImp autoServicioImp;

    @GetMapping()
    public ArrayList<Auto> obtenerVehiculos(){
        return autoServicioImp.obteneraAutos();
    }

    @PostMapping()
    public Auto guardarVehiculo(@RequestBody Auto auto){
        return this.autoServicioImp.guardarAutos(auto);
    }

    @GetMapping( path = "/{id}")
    public Optional<Auto> obtenerVehiculoPorId(@PathVariable("id") Long id){
        return this.autoServicioImp.obtenerPorId(id);
    }

    @DeleteMapping( path = "/{id}")
    public String eliminarPorId(@PathVariable("id") Long id){
        boolean ok = this.autoServicioImp.eliminarVehiculo(id);
        if(ok){
            return "SE ELIMINO CORRECTAMENTE CON ID" + id;
        }else{
            return "NO SE PUDO ELIMINAR EL USUARIO CON ID" + id;
        }
    }

}
