package com.sistema.playaAutos.playaAutos.Servicio;

import com.sistema.playaAutos.playaAutos.modelos.Auto;

import java.util.List;

public interface AutoServicio {
    public List<Auto> listaAutos();

    public void guardar(Auto auto);

    public void  borrar(Auto auto);

    public Auto localizarAuto(Auto auto);
}
