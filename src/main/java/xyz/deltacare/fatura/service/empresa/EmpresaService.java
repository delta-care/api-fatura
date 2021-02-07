package xyz.deltacare.fatura.service.empresa;

import org.springframework.data.domain.Pageable;
import xyz.deltacare.fatura.domain.Empresa;
import xyz.deltacare.fatura.dto.EmpresaDto;

import java.math.BigDecimal;
import java.util.List;

public interface EmpresaService {
    List<EmpresaDto> pesquisar(Pageable pageable, String id, String cnpj, String nome);
    List<EmpresaDto> pesquisarApi(Pageable pageable, String id, String cnpj, String nome);
    Empresa salvar(EmpresaDto empresaDto);
}
