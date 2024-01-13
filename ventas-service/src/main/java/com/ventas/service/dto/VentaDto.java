package com.ventas.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaDto {
    private int id;

    private String medicamento;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaVenta;

    private int cantidad;

    private double valorUnitario;

    private double valorTotal;

    @Transient
    private MedicamentoDto medicamentoDto;
}
