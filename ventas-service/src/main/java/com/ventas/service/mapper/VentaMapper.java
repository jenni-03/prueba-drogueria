package com.ventas.service.mapper;

import com.ventas.service.dto.VentaDto;
import com.ventas.service.entity.Venta;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VentaMapper {
    Venta toEntity(VentaDto ventaDto);
    VentaDto toDto(Venta venta);
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(VentaDto ventaDto, @MappingTarget Venta venta);
}
