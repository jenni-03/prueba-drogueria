package com.ventas.service.implement;

import com.ventas.service.dto.MedicamentoDto;
import com.ventas.service.dto.VentaDto;
import com.ventas.service.entity.Venta;
import com.ventas.service.mapper.VentaMapper;
import com.ventas.service.repository.VentaRepository;
import com.ventas.service.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaMapper ventaMapper;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    RestTemplate restTemplate;

    String urlMedicamentos = "http://localhost:8001/api/medicamentos/";

    @Override
    public List<VentaDto> obtenerVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        if (ventas.isEmpty()){
            throw new RuntimeException("No hay ventas");
        }

        for (int i = 0 ; i < ventas.size() ; i++) {
            MedicamentoDto medicamentoDto = restTemplate.getForObject(
                    urlMedicamentos + ventas.get(i).getMedicamento(),
                    MedicamentoDto.class
            );
            ventas.get(i).setMedicamentoDto(medicamentoDto);
        }

        return ventas.stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public VentaDto obtenerVenta(int id) {
        Venta venta = ventaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No existe la venta indicada")
        );
        MedicamentoDto medicamentoDto = restTemplate.getForObject(
                urlMedicamentos + venta.getMedicamento(),
                MedicamentoDto.class
        );
        venta.setMedicamentoDto(medicamentoDto);
        return ventaMapper.toDto(venta);
    }

    @Transactional
    @Override
    public VentaDto crearVenta(VentaDto ventaDto) {
        MedicamentoDto medicamentoDto = restTemplate.getForObject(
                urlMedicamentos + ventaDto.getMedicamento(),
                MedicamentoDto.class
        );

        if (medicamentoDto == null){
            throw new RuntimeException("El medicamento indicado no existe");
        } else {
            if (!hayStock(medicamentoDto.getStock()-ventaDto.getCantidad())){
                throw new RuntimeException("No hay stock disponible para el medicamento indicado");
            }
            ventaDto.setValorUnitario(medicamentoDto.getValor());
            ventaDto.setValorTotal(medicamentoDto.getValor() * ventaDto.getCantidad());
            medicamentoDto.setStock(medicamentoDto.getStock()-ventaDto.getCantidad());
        }
        Venta venta = ventaMapper.toEntity(ventaDto);

        ventaRepository.save(venta);

        //Actualizamos el inventario
        restTemplate.put(
                urlMedicamentos + ventaDto.getMedicamento(),
                medicamentoDto,
                MedicamentoDto.class
        );

        return ventaMapper.toDto(venta);
    }

    @Override
    public boolean hayStock(int unidades) {
        return (unidades >= 0);
    }

    @Override
    public List<VentaDto> ventasXMedicamento(String id) {
        List<Venta> ventas = ventaRepository.findByMedicamento(id);
        if (ventas.isEmpty()){
            throw new RuntimeException("No existen ventas para este medicamento");
        }
        return ventas.stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public void eliminarVenta(String id) {
        List<Venta> ventas = ventaRepository.findByMedicamento(id);

        if (!ventas.isEmpty()){
            for (Venta venta: ventas) {
                ventaRepository.deleteById(venta.getId());
            }
        } else {
            throw new RuntimeException("No existen ventas para el medicamento " + id);
        }

    }
}
