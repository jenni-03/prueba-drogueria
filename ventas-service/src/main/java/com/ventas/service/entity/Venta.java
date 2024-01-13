package com.ventas.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ventas.service.dto.MedicamentoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
