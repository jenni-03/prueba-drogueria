package com.medicamentos.service.feignclients;

import com.medicamentos.service.dto.VentaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient (name = "ventas-service", url = "http://localhost:8002")
public interface VentaFeignClient {
    @PostMapping("/api/ventas")
    VentaDto saveVenta(@RequestBody VentaDto ventaDto);

}
