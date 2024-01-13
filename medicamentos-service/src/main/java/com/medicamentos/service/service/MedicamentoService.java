package com.medicamentos.service.service;

import com.medicamentos.service.dto.MedicamentoDto;
import com.medicamentos.service.dto.VentaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicamentoService {

    public List<MedicamentoDto> obtenerMedicamentos();

    public MedicamentoDto obtenerMedicamento(int id);

    public MedicamentoDto crearMedicamento(MedicamentoDto medicamentoDto);

    public MedicamentoDto editarMedicamento(int id, MedicamentoDto medicamentoDto);

    public void eliminarMedicamento(int id);

    public List<VentaDto> obtenerVentas(int medicamentoId);

    public VentaDto guardarVenta(String medicamentoId, VentaDto ventaDto);
}
