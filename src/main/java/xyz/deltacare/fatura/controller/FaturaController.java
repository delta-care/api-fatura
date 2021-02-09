package xyz.deltacare.fatura.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xyz.deltacare.fatura.dto.EmpresaDto;
import xyz.deltacare.fatura.service.empresa.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faturas")
public class FaturaController implements FaturaControllerDocs {

    private final EmpresaService service;

    @Value( "${api.produto.protocol}" )
    private String protocol;

    @Value( "${api.produto.uri}" )
    private String uri;

    @Value( "${api.produto.port}" )
    private String port;

    private final Logger logger = LoggerFactory.getLogger(FaturaController.class);

    public FaturaController(@Qualifier("empresaServiceSpa") EmpresaService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmpresaDto> pesquisar(
            @RequestParam(value="page", required = false, defaultValue = "0") int page,
            @RequestParam(value="limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value="codigo", required = false, defaultValue = "") String id,
            @RequestParam(value="nome", required = false, defaultValue = "") String nome,
            @RequestParam(value="cnpj", required = false, defaultValue = "") String cnpj
    ) {
        logger.info(">>>>>>>>>>> " + protocol);
        logger.info(">>>>>>>>>>> " + uri);
        logger.info(">>>>>>>>>>> " + port);
        Pageable pageable = PageRequest.of(page, limit);
        return service.pesquisar(pageable, id, cnpj, nome);
    }

}
