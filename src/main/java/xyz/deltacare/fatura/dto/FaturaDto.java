package xyz.deltacare.fatura.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaturaDto implements Serializable {

    private Long id;

    @NotNull
    @NotEmpty
    private String mes;

    @NotNull
    @NotEmpty
    private String documento;

    //@NotNull
    //@NotEmpty
    //private String pago;
}
