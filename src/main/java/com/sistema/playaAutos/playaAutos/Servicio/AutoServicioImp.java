package com.sistema.playaAutos.playaAutos.Servicio;

import com.sistema.playaAutos.playaAutos.dao.vehiculoDao;
import com.sistema.playaAutos.playaAutos.modelos.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutoServicioImp implements AutoServicio{

    @Autowired  //EL CONTROLADOR UTILIZA LA CAPA DE INDIVIDUOSERVICIOIMP
    private vehiculoDao vehiculodao;

    @Override
    @Transactional(readOnly = true)
    public List<Auto> listaAutos() {
        return (List<Auto>) vehiculodao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Auto auto) {
        vehiculodao.save(auto);
    }

    @Override
    @Transactional
    public void borrar(Auto auto) {
            vehiculodao.delete(auto);
        }

    @Override
    public Auto localizarAuto(Auto auto) {
        return vehiculodao.findById(auto.getId()).orElse(null);
    }
}
