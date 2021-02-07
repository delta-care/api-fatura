package xyz.deltacare.fatura.service.produto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.deltacare.fatura.dto.ProdutoDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProdutoServiceSpa implements ProdutoService {
    @Override
    public List<ProdutoDto> pesquisarApi(Pageable pageable, String codigo, String nome) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8083/api/v1/produtos/?" +
                "page=" + pageable.getPageNumber() + "&" +
                "limit=" + pageable.getPageSize();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        Iterator<JsonNode> elementos = root.elements();
        List<ProdutoDto> produtosDto = new ArrayList<>();

        while(elementos.hasNext()) {
            JsonNode elemento = elementos.next();
            ProdutoDto produtoDto = ProdutoDto.builder()
                    .plano(elemento.path("plano").toString().replace("\"",""))
                    .subplano(elemento.path("subplano").toString().replace("\"",""))
                    .valor(new BigDecimal(elemento.path("valor").toString().replace("\"","")))
                    .build();
            produtosDto.add(produtoDto);
        }

        return produtosDto;
    }
}
