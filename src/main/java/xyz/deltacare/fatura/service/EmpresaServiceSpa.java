package xyz.deltacare.fatura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import xyz.deltacare.fatura.domain.Empresa;
import xyz.deltacare.fatura.dto.EmpresaDto;
import xyz.deltacare.fatura.mapper.EmpresaMapper;
import xyz.deltacare.fatura.repository.EmpresaRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmpresaServiceSpa implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private static final EmpresaMapper empresaMapper = EmpresaMapper.INSTANCE;
    private final Logger logger = LoggerFactory.getLogger(EmpresaServiceSpa.class);

    @Override
    //@Cacheable(value="empresa", key="#root.args")
    public List<EmpresaDto> pesquisar(Pageable pageable, String id, String cnpj, String nome) {
        return empresaRepository.findAll(pageable, id, cnpj, nome)
                .stream()
                .map(empresaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public List<EmpresaDto> pesquisarApi(Pageable pageable, String id, String cnpj, String nome)  {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8082/api/v1/empresas/?" +
                "page=" + pageable.getPageNumber() + "&" +
                "limit=" + pageable.getPageSize();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        Iterator<JsonNode> elementos = root.elements();
        List<EmpresaDto> empresasDto = new ArrayList<EmpresaDto>();

        while(elementos.hasNext()) {
            JsonNode elemento = elementos.next();
            EmpresaDto empresaDto = EmpresaDto.builder()
                    .codigo(elemento.path("id").toString().replace("\"",""))
                    .cnpj(elemento.path("cnpj").toString().replace("\"",""))
                    .nome(elemento.path("nome").toString().replace("\"",""))
                    .email(elemento.path("email").toString().replace("\"",""))
                    .logradouro(elemento.path("logradouro").toString().replace("\"",""))
                    .bairro(elemento.path("bairro").toString().replace("\"",""))
                    .uf(elemento.path("uf").toString().replace("\"",""))
                    .cep(elemento.path("cep").toString().replace("\"",""))
                    .build();
            empresasDto.add(empresaDto);
        }

        return empresasDto;
    }

    @Override
    public Empresa salvar(EmpresaDto empresaDto) {
        Optional<Empresa> empresa = empresaRepository.findByCnpj(empresaDto.getCnpj());
        if (empresa.isPresent()) {
            return empresa.get();
        } else {
            Empresa empresaASalvar = empresaMapper.toModel(empresaDto);
            Empresa empresaSalva = empresaRepository.save(empresaASalvar);
            return empresaSalva;
        }
    }

}
