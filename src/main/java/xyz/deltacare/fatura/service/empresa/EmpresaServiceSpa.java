package xyz.deltacare.fatura.service.empresa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
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
public class EmpresaServiceSpa implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private static final EmpresaMapper empresaMapper = EmpresaMapper.INSTANCE;
    @Value("${api.empresa.protocol}") private String protocol;
    @Value("${api.empresa.uri}") private String uri;
    @Value("${api.empresa.port}") private String port;

    public EmpresaServiceSpa(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

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
        String url = protocol + "://" + uri + ":" + port + "/api/v1/empresas/?" +
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
                    .qtdBeneficiarios(elemento.path("beneficiarios").size())
                    .nomePlano(elemento.path("produtos").get(0).path("plano").toString().replace("\"",""))
                    .nomeSubPlano(elemento.path("produtos").get(0).path("subplano").toString().replace("\"",""))
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
