package xyz.deltacare.fatura.service.fatura;

import xyz.deltacare.fatura.domain.Empresa;
import xyz.deltacare.fatura.domain.Fatura;

import java.math.BigDecimal;

public interface FaturaServiceDoc {
    Fatura criar(Empresa empresa, BigDecimal valorBoleto);
}
