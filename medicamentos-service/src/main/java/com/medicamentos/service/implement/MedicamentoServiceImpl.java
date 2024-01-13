package com.medicamentos.service.implement;

import com.medicamentos.service.dto.MedicamentoDto;
import com.medicamentos.service.dto.VentaDto;
import com.medicamentos.service.entity.Medicamento;
import com.medicamentos.service.feignclients.VentaFeignClient;
import com.medicamentos.service.mapper.MedicamentoMapper;
import com.medicamentos.service.repository.MedicamentoRepository;
import com.medicamentos.service.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoMapper medicamentoMapper;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    VentaFeignClient ventaFeignClient;

    String urlVentas = "http://localhost:8002/api/ventas/medicamento/";

    @Override
    public List<MedicamentoDto> obtenerMedicamentos() {
        List<Medicamento> medicamentos = medicamentoRepository.findAll();

        if(medicamentos.isEmpty()) {
            throw new RuntimeException("No hay medicamentos registrados");
        }
        return medicamentos.stream()
                .map(medicamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicamentoDto obtenerMedicamento(int id) {
        return medicamentoRepository.findById(id)
                .map(medicamentoMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));
    }

    @Transactional
    @Override
    public MedicamentoDto crearMedicamento(MedicamentoDto medicamentoDto) {
        Medicamento medicamento = medicamentoMapper.toEntity(medicamentoDto);
        Medicamento saveMedicamento = null;

        if (medicamentoRepository.findByNombre(medicamento.getNombre()).isEmpty()){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                medicamento.setFechaFabricacion(dateFormat.parse(dateFormat.format(medicamento.getFechaFabricacion())));
                medicamento.setFechaVencimiento(dateFormat.parse(dateFormat.format(medicamento.getFechaVencimiento())));

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            saveMedicamento = medicamentoRepository.save(medicamento);
        }
        else {
            throw new RuntimeException("El medicamento ya ha sido registrado");
        }

        return medicamentoMapper.toDto(saveMedicamento);
    }

    @Transactional
    @Override
    public MedicamentoDto editarMedicamento(int id, MedicamentoDto medicamentoDto) {
        Medicamento medicamento = medicamentoMapper.toEntity(medicamentoDto);
        Medicamento updatedMedicamento = medicamentoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("El medicamento no existe")
        );

        if(updatedMedicamento.getNombre() != null)
            updatedMedicamento.setNombre(medicamento.getNombre());

        if(updatedMedicamento.getLabFabrica() != null)
            updatedMedicamento.setLabFabrica(medicamento.getLabFabrica());

        if(updatedMedicamento.getFechaFabricacion() != null)
            updatedMedicamento.setFechaFabricacion(medicamento.getFechaFabricacion());

        if(updatedMedicamento.getFechaVencimiento() != null)
            updatedMedicamento.setFechaVencimiento(medicamento.getFechaVencimiento());

        updatedMedicamento.setStock(medicamento.getStock());
        updatedMedicamento.setValor(medicamento.getValor());
        medicamentoRepository.save(updatedMedicamento);

        return medicamentoMapper.toDto(updatedMedicamento);
    }

    @Override
    public void eliminarMedicamento(int id) {
        Medicamento medicamento = medicamentoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("La venta que desea eliminar no existe")
        );

        medicamentoRepository.deleteById(id);
    }

    @Override
    public List<VentaDto> obtenerVentas(int medicamentoId){
        List<VentaDto> ventas = restTemplate.getForObject(urlVentas + medicamentoId, List.class);
        return ventas;
    }

    @Override
    public VentaDto guardarVenta(String medicamentoId, VentaDto ventaDto){
        ventaDto.setMedicamento(medicamentoId);
        VentaDto saveVenta = ventaFeignClient.saveVenta(ventaDto);
        return saveVenta;
    }

}
