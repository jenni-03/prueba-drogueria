package com.medicamentos.service.mapper;

import com.medicamentos.service.dto.MedicamentoDto;
import com.medicamentos.service.entity.Medicamento;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MedicamentoMapper {

    Medicamento toEntity(MedicamentoDto medicamentoDto);
    MedicamentoDto toDto(Medicamento medicamento);
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(MedicamentoDto medicamentoDto, @MappingTarget Medicamento medicamento);

}
