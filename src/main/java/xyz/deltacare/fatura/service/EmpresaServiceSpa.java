package xyz.deltacare.fatura.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import xyz.deltacare.fatura.dto.EmpresaDto;
import xyz.deltacare.fatura.mapper.EmpresaMapper;
import xyz.deltacare.fatura.repository.EmpresaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmpresaServiceSpa implements EmpresaService {

    private final EmpresaRepository repository;
    private static final EmpresaMapper mapper = EmpresaMapper.INSTANCE;

    @Override
    @Cacheable(value="empresa", key="#root.args")
    public List<EmpresaDto> pesquisar(Pageable pageable, String id, String cnpj, String nome) {
        return repository.findAll(pageable, id, cnpj, nome)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
