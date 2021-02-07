package xyz.deltacare.fatura.service.fatura;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import xyz.deltacare.fatura.domain.Empresa;
import xyz.deltacare.fatura.domain.Fatura;
import xyz.deltacare.fatura.dto.EmpresaDto;
import xyz.deltacare.fatura.dto.ProdutoDto;
import xyz.deltacare.fatura.repository.FaturaRepository;
import xyz.deltacare.fatura.service.produto.ProdutoService;
import xyz.deltacare.fatura.service.empresa.EmpresaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
public class FaturaServiceSpa implements FaturaService {

    private final EmpresaService empresaService;
    private final ProdutoService produtoService;
    private final FaturaServiceDoc faturaServiceDoc;
    private final FaturaRepository faturaRepository;
    private final Logger logger = LoggerFactory.getLogger(FaturaServiceSpa.class);

    public FaturaServiceSpa(EmpresaService empresaService,
                            ProdutoService produtoService,
                            FaturaServiceDoc faturaServiceDoc,
                            FaturaRepository faturaRepository) {
        this.empresaService = empresaService;
        this.produtoService = produtoService;
        this.faturaServiceDoc = faturaServiceDoc;
        this.faturaRepository = faturaRepository;
    }

    @Override
    public void criarFaturasMesCorrente() throws JsonProcessingException {
        List<ProdutoDto> produtosDto = produtoService.pesquisarApi(PageRequest.of(0, 100), "", "");
        List<EmpresaDto> empresasDto = empresaService.pesquisarApi(PageRequest.of(0, 100), "", "", "");

        empresasDto.forEach(empresaDto -> {
            logger.info("Processando a empresa " + empresaDto.getCodigo() + ".");
            Empresa empresa = empresaService.salvar(empresaDto);
            if (!empresaPossuiFaturaMesCorrente(empresa)) {
                BigDecimal valorBoleto = definirValorBoleto(empresaDto, produtosDto);
                Fatura fatura = faturaServiceDoc.criar(empresa, valorBoleto);
                fatura.setEmpresa(empresa);
                salvar(fatura);
            } else {
                logger.info("Empresa " + empresaDto.getCodigo() + " já possui fatura no mês corrente.");
            }
        });

    }

    private BigDecimal definirValorBoleto(EmpresaDto empresaDto, List<ProdutoDto> produtosDto) {
        return produtosDto.stream()
                .filter(produtoDto -> Objects.equals(produtoDto.getPlano(), empresaDto.getNomePlano()))
                .filter(produtoDto -> produtoDto.getSubplano().equals(empresaDto.getNomeSubPlano()))
                .findFirst()
                .orElse(ProdutoDto.builder().valor(new BigDecimal(0)).build())
                .getValor()
                .multiply(new BigDecimal(empresaDto.getQtdBeneficiarios()));
    }

    private void salvar(Fatura fatura) {
        faturaRepository.save(fatura);
    }

    private boolean empresaPossuiFaturaMesCorrente(Empresa empresa) {
        String mesCorrente = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL_STANDALONE,
                new Locale("pt", "BR"));

        Optional<Fatura> fatura = faturaRepository.findByEmpresaAndMes(empresa, mesCorrente);

        return fatura.isPresent();
    }
}
