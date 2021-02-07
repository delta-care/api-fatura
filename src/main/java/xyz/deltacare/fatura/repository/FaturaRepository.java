package xyz.deltacare.fatura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.deltacare.fatura.domain.Empresa;
import xyz.deltacare.fatura.domain.Fatura;

import java.util.Optional;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {
    Optional<Fatura> findByEmpresaAndMes(Empresa empresa, String mes);
}
