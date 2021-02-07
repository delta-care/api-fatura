package xyz.deltacare.fatura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import xyz.deltacare.fatura.domain.Empresa;
import xyz.deltacare.fatura.domain.Fatura;
import xyz.deltacare.fatura.dto.EmpresaDto;
import xyz.deltacare.fatura.repository.FaturaRepository;

import java.util.List;

@Service
public class FaturaServiceSpa implements FaturaService {

    private final EmpresaService empresaService;
    private final FaturaServiceDoc faturaServiceDoc;
    private final FaturaRepository faturaRepository;

    public FaturaServiceSpa(@Qualifier("empresaServiceSpa") EmpresaService empresaService,
                            @Qualifier("faturaServiceDocCaelum") FaturaServiceDoc faturaServiceDoc,
                            @Autowired FaturaRepository faturaRepository) {
        this.empresaService = empresaService;
        this.faturaServiceDoc = faturaServiceDoc;
        this.faturaRepository = faturaRepository;
    }

    @Override
    public void criarFaturasMesCorrente() {
        List<EmpresaDto> empresasDto = empresaService.pesquisarApi(PageRequest.of(0, 100), "", "", "");
        empresasDto.forEach(empresaDto -> {
            Fatura fatura = faturaServiceDoc.criar(empresaDto);
            Empresa empresa = empresaService.salvar(empresaDto);
            fatura.setEmpresa(empresa);
            salvar(fatura);
        });
    }

    private void salvar(Fatura fatura) {
        faturaRepository.save(fatura);
    }

}
