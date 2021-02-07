package xyz.deltacare.fatura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDto implements Serializable {

    private String id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String codigo;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String cnpj;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String nome;

    @Email
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String logradouro;

    @Size(max = 255)
    private String bairro;

    @Size(max = 255)
    private String uf;

    @Size(max = 255)
    private String cep;

    @JsonProperty("faturas")
    private List<FaturaDto> faturas;

    private int qtdBeneficiarios;
    private String nomePlano;
    private String nomeSubPlano;

}