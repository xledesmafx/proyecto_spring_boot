package com.sistema.playaAutos.playaAutos.Controladora;

import com.sistema.playaAutos.playaAutos.Servicio.AutoServicio;
import com.sistema.playaAutos.playaAutos.modelos.Auto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ControladoraRest {

    @Autowired
    private AutoServicio autoServicio;

    public String comienzo(){
        return "index";
    }

    @GetMapping("/Opciones/Administrar")
    public String administrar(Model model) {
        List<Auto> autos = autoServicio.listaAutos();
        model.addAttribute("autos", autos);
        return "Opciones/administrar";
    }

    @GetMapping("/Opciones/catalogo")
    public String catalogo(Model model) {
        List<Auto> autos = autoServicio.listaAutos();
        model.addAttribute("autos", autos);
        return "Opciones/catalogo";
    }

    @GetMapping("/agregar")
    public String anexar(Model model){
        Auto auto = new Auto();
        model.addAttribute("auto", auto);
        return "Opciones/agregar";
    }

    @PostMapping("/guardar")
    public String salvar(@Valid Auto auto, Errors error){
        if(error.hasErrors()){
            return "Opciones/agregar";
        }
        autoServicio.guardar(auto);
        return "Opciones/agregar";
    }

    @GetMapping("/cambiar/{id}")
    public String cambiar(Auto auto, Model model){
        auto = auto = autoServicio.localizarAuto(auto);
        model.addAttribute("auto",auto);
        return "Opciones/agregar";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(Auto auto){
        autoServicio.borrar(auto);
        return "redirect:/Opciones/catalogo";
    }
}
