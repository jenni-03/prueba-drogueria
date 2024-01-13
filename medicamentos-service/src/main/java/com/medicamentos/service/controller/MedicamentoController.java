package com.medicamentos.service.controller;

import com.medicamentos.service.dto.MedicamentoDto;
import com.medicamentos.service.dto.VentaDto;
import com.medicamentos.service.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(medicamentoService.obtenerMedicamentos(), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> getMedicamentoById(@PathVariable("id") int id){
        MedicamentoDto medicamentoDto = medicamentoService.obtenerMedicamento(id);

        if (medicamentoDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(medicamentoDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveMedicamento(@RequestBody MedicamentoDto medicamentoDto){
        MedicamentoDto medicamentoSaved = medicamentoService.crearMedicamento(medicamentoDto);
        return new ResponseEntity<>(medicamentoSaved, HttpStatus.CREATED);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> updateMedicamento(@PathVariable ("id")int id, @RequestBody MedicamentoDto medicamentoDto){
        medicamentoService.editarMedicamento(id, medicamentoDto);
        return new ResponseEntity<>(medicamentoService.obtenerMedicamento(id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteMedicamento(@PathVariable ("id")int id){
        medicamentoService.eliminarMedicamento(id);
        return new ResponseEntity<>("El medicamento se elimino satisfactoriamente", HttpStatus.ACCEPTED);
    }

    //Obtener venta por un medicamento
    @GetMapping("/ventas/{medicamentoId}")
    public ResponseEntity<?> getVentas(@PathVariable ("medicamentoId")int medicamentoId){
        MedicamentoDto medicamentoDto = medicamentoService.obtenerMedicamento(medicamentoId);
        if (medicamentoDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<VentaDto> ventas = medicamentoService.obtenerVentas(medicamentoId);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    //Registrar venta para un medicamento
    @PostMapping("/ventas/{medicamentoId}")
    public ResponseEntity<?> saveVenta(@PathVariable ("medicamentoId")String medicamentoId, @RequestBody VentaDto ventaDto){
        VentaDto ventaDto1 = medicamentoService.guardarVenta(medicamentoId, ventaDto);
        if (ventaDto1 == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ventaDto1, HttpStatus.CREATED);
    }
}
