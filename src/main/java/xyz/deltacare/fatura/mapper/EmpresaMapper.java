package xyz.deltacare.fatura.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.deltacare.fatura.dto.EmpresaDto;
import xyz.deltacare.fatura.domain.Empresa;

@Mapper
public interface EmpresaMapper {
    EmpresaMapper INSTANCE = Mappers.getMapper(EmpresaMapper.class);
    Empresa toModel(EmpresaDto empresaDto);
    EmpresaDto toDto(Empresa empresa);
}