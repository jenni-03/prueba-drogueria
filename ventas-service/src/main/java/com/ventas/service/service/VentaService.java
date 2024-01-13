package com.ventas.service.service;

import com.ventas.service.dto.VentaDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VentaService {
    public List<VentaDto> obtenerVentas();

    public VentaDto obtenerVenta(int id);

    public VentaDto crearVenta(VentaDto ventaDto);

    public boolean hayStock(int unidades);

    public List<VentaDto> ventasXMedicamento(String id);

    public void eliminarVenta(String id);
}
