package xyz.deltacare.fatura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.deltacare.fatura.domain.Fatura;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {
}
