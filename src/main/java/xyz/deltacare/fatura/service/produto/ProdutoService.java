package xyz.deltacare.fatura.service.produto;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import xyz.deltacare.fatura.dto.ProdutoDto;

import java.util.List;

public interface ProdutoService {
    List<ProdutoDto> pesquisarApi(Pageable pageable, String codigo, String nome) throws JsonProcessingException;
}
