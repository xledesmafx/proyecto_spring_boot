package com.sistema.playaAutos.playaAutos.Controladora;

import com.sistema.playaAutos.playaAutos.Servicio.AutoServicioImp;
import com.sistema.playaAutos.playaAutos.modelos.Auto;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@RunWith(MockitoJUnitRunner.class)
public class ControladoraApiRestTest {
    private MockMvc mockMvc;

    @Mock
    private AutoServicioImp autoServicioImp;

    @InjectMocks
    private ControladoraApiRest controladoraApiRest;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controladoraApiRest).build();
    }

    @Test
    public void testObtenerVehiculos() {
        // Datos de prueba
        Auto auto1 = new Auto();
        auto1.setId(1);
        auto1.setMarca("Toyota");
        auto1.setModelo("Corolla");
        auto1.setColor("Blanco");
        auto1.setTipo("Sedan");
        auto1.setKilometraje("5000 km");
        auto1.setAnho(2018);
        auto1.setDescripcion("Vehiculo en excelente estado");
        auto1.setEstado("Nuevo");
        auto1.setPrecio(25000.00);
        auto1.setImage("imagen1.jpg");

        Auto auto2 = new Auto();
        auto2.setId(2);
        auto2.setMarca("Honda");
        auto2.setModelo("Civic");
        auto2.setColor("Negro");
        auto2.setTipo("Sedan");
        auto2.setKilometraje("8000 km");
        auto2.setAnho(2020);
        auto2.setDescripcion("Vehiculo semi nuevo");
        auto2.setEstado("Usado");
        auto2.setPrecio(30000.00);
        auto2.setImage("imagen2.jpg");

        ArrayList<Auto> vehiculos = new ArrayList<>(Arrays.asList(auto1, auto2));

        when(autoServicioImp.obteneraAutos()).thenReturn(vehiculos);

        ArrayList<Auto> resultado = controladoraApiRest.obtenerVehiculos();

        assertEquals(vehiculos.size(), resultado.size());
        assertEquals(vehiculos.get(0).getMarca(), resultado.get(0).getMarca());
        assertEquals(vehiculos.get(0).getModelo(), resultado.get(0).getModelo());
        assertEquals(vehiculos.get(0).getAnho(), resultado.get(0).getAnho());
        assertEquals(vehiculos.get(1).getMarca(), resultado.get(1).getMarca());
        assertEquals(vehiculos.get(1).getModelo(), resultado.get(1).getModelo());
        assertEquals(vehiculos.get(1).getAnho(), resultado.get(1).getAnho());
    }

    @Test
    public void testGuardarVehiculo() throws Exception {
        // Datos de prueba
        Auto auto = new Auto();
        auto.setMarca("Toyota");
        auto.setModelo("Corolla");
        auto.setAnho(2018);

        // Mockear el servicio para que devuelva el auto guardado
        when(autoServicioImp.guardarAutos(auto)).thenReturn(auto);

        // Llamar al método del controlador y enviar una solicitud HTTP simulada
        mockMvc.perform(post("/vehiculo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"marca\": \"Toyota\", \"modelo\": \"Corolla\", \"anho\": 2018}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verificar que el método del servicio fue llamado con el auto adecuado
        verify(autoServicioImp, times(1)).guardarAutos(auto);
    }

    @Test
    public void testEliminarPorId() throws Exception {
        // ID de prueba
        Long id = 1L;

        // Mockear el servicio para que devuelva true al eliminar el vehículo
        when(autoServicioImp.eliminarVehiculo(id)).thenReturn(true);

        // Llamar al método del controlador y enviar una solicitud HTTP simulada
        mockMvc.perform(delete("/vehiculo/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SE ELIMINO CORRECTAMENTE CON ID" + id)));

        // Verificar que el método del servicio fue llamado con el ID adecuado
        verify(autoServicioImp, times(1)).eliminarVehiculo(id);
    }
}

