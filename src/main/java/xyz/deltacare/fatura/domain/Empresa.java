package xyz.deltacare.fatura.domain;

import lombok.*;
import org.springframework.data.domain.Auditable;
import xyz.deltacare.fatura.domain.Fatura;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String codigo;
    private String cnpj;
    private String nome;
    private String email;
    private String logradouro;
    private String bairro;
    private String uf;
    private String cep;

    @OneToMany(mappedBy = "empresa")
    private Set<Fatura> faturas;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Empresa))
            return false;

        Empresa other = (Empresa) o;

        return id != null &&
                id.equals(other.getId());
    }
}
