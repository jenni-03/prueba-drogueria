package com.ventas.service.controller;

import com.ventas.service.dto.VentaDto;
import com.ventas.service.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ventas")
public class VentaController {
    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(ventaService.obtenerVentas(), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> getVentaById(@PathVariable("id") int id){
        VentaDto ventaDto = ventaService.obtenerVenta(id);

        if (ventaDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ventaDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveVenta(@RequestBody VentaDto ventaDto){
        VentaDto ventaSaved = ventaService.crearVenta(ventaDto);
        return new ResponseEntity<>(ventaSaved, HttpStatus.CREATED);
    }

    @GetMapping ("/medicamento/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<VentaDto> getVentasXMedicamento(@PathVariable ("id")String id){
        return ventaService.ventasXMedicamento(id);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteVenta(@PathVariable ("id")String id){
        ventaService.eliminarVenta(id);
        return new ResponseEntity<>("Las ventas del medicamento: " + id + " fueron eliminadas satisfactoriamente", HttpStatus.ACCEPTED);
    }
}
