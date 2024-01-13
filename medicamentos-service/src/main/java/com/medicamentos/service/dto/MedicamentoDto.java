package com.medicamentos.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
