package xyz.deltacare.fatura.service;

import xyz.deltacare.fatura.domain.Fatura;
import xyz.deltacare.fatura.dto.EmpresaDto;

public interface FaturaServiceDoc {
    Fatura criar(EmpresaDto empresaDto);
}
