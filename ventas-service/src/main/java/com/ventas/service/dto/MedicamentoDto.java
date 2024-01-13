package com.ventas.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MedicamentoDto {
    private int id;

    private String nombre;

    private String labFabrica;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaFabricacion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaVencimiento;

    private int stock;

    private double valor;
}
