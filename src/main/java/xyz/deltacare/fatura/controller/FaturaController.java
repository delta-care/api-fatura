package xyz.deltacare.fatura.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xyz.deltacare.fatura.dto.EmpresaDto;
import xyz.deltacare.fatura.service.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/faturas")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FaturaController implements FaturaControllerDocs {

    private final EmpresaService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmpresaDto> pesquisar(
            @RequestParam(value="page", required = false, defaultValue = "0") int page,
            @RequestParam(value="limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value="codigo", required = false, defaultValue = "") String id,
            @RequestParam(value="nome", required = false, defaultValue = "") String nome,
            @RequestParam(value="cnpj", required = false, defaultValue = "") String cnpj
    ) {
        Pageable pageable = PageRequest.of(page, limit);
        return service.pesquisar(pageable, id, cnpj, nome);
    }

}
