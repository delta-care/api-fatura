package xyz.deltacare.fatura.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fatura")
public class Fatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mes;
    private String documento;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Fatura))
            return false;

        Fatura other = (Fatura) o;

        return id != null &&
                id.equals(other.getId());
    }
}

