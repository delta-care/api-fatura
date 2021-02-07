package xyz.deltacare.fatura.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Pageable;
import xyz.deltacare.fatura.domain.Empresa;
import xyz.deltacare.fatura.dto.EmpresaDto;

import java.util.Iterator;
import java.util.List;

public interface EmpresaService {
    List<EmpresaDto> pesquisar(Pageable pageable, String id, String cnpj, String nome);
    List<EmpresaDto> pesquisarApi(Pageable pageable, String id, String cnpj, String nome);
    Empresa salvar(EmpresaDto empresaDto);
}
