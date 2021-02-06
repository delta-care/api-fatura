package xyz.deltacare.fatura.service;

import org.springframework.data.domain.Pageable;
import xyz.deltacare.fatura.dto.EmpresaDto;

import java.util.List;

public interface EmpresaService {
    List<EmpresaDto> pesquisar(Pageable pageable, String id, String cnpj, String nome);
}
