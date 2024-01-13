package com.medicamentos.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "medicamento")
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
