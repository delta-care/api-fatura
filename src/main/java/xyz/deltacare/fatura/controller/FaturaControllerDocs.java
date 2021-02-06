package xyz.deltacare.fatura.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.deltacare.fatura.dto.EmpresaDto;

import java.util.List;

@Api("Gestão de Fatura")
public interface FaturaControllerDocs {

    @ApiOperation(value = "Pesquisar faturas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fatura(s) encontrada(s) com sucesso.")
    })
    List<EmpresaDto> pesquisar(
            @RequestParam(value="page", required = false, defaultValue = "0") int page,
            @RequestParam(value="limit", required = false, defaultValue = "10") int limit,
            @RequestParam(value="codigo", required = false, defaultValue = "") String id,
            @RequestParam(value="nome", required = false, defaultValue = "") String nome,
            @RequestParam(value="cnpj", required = false, defaultValue = "") String cnpj
    );

}