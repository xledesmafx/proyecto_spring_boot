package com.sistema.playaAutos.playaAutos.Controladora;

import com.sistema.playaAutos.playaAutos.Servicio.AutoServicio;
import com.sistema.playaAutos.playaAutos.Servicio.IUploadFileService;
import com.sistema.playaAutos.playaAutos.modelos.Auto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
public class ControladoraRest {

    @Autowired
    private AutoServicio autoServicio;

    @Autowired
    private IUploadFileService uploadFileService;

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



    @GetMapping("/regresar")
    public String redireccionarAAdministrar(Model model) {
        List<Auto> autos = autoServicio.listaAutos();
        model.addAttribute("autos", autos);

        return "/Opciones/administrar";
    }



    @PostMapping("/guardar")
    public String saveMeme(@Validated @ModelAttribute("auto") Auto auto, BindingResult result, Model model,
                           @RequestParam("file") MultipartFile image, RedirectAttributes flash, SessionStatus status)
            throws Exception {

        boolean guardadoExitoso=false;
        if (result.hasErrors()) {
            System.out.println(result.getFieldError());
            return "Opciones/agregar";
        } else {
            if (!image.isEmpty()) {
                if (auto.getId() > 0 && auto.getImage() != null && auto.getImage().length() > 0) {
                    uploadFileService.delete(auto.getImage());
                }
                String uniqueFileName = uploadFileService.copy(image);
                auto.setImage(uniqueFileName);
            }
            autoServicio.guardar(auto);
            guardadoExitoso = true;
            status.setComplete();
            model.addAttribute("auto", new Auto());
        }
        if (guardadoExitoso) {
            model.addAttribute("mensaje", "¡Los datos se han guardado correctamente!");
        } else {
            model.addAttribute("error", "¡Hubo un problema al guardar los datos!");
        }
        return "Opciones/agregar";
    }

    @GetMapping("/cambiar/{id}")
    public String cambiar(Auto auto, Model model){
        auto = auto = autoServicio.localizarAuto(auto);
        model.addAttribute("auto",auto);
        return "Opciones/agregar";
    }



    @GetMapping("/borrar/{id}")
    public String borrar(Auto auto, Model model){
        autoServicio.borrar(auto);
        List<Auto> autos = autoServicio.listaAutos();
        model.addAttribute("autos", autos);
        return "/Opciones/administrar";
    }

    @GetMapping("/home")
    public String home(){
        return "/index.html";
    }

    @GetMapping("/Opciones/catalogo/{filename}")
    public ResponseEntity<Resource> goImage(@PathVariable String filename){
        Resource resource = null;
        try {
            resource = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
