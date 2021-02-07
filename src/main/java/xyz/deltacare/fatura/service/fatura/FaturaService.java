package xyz.deltacare.fatura.service.fatura;

import com.fasterxml.jackson.core.JsonProcessingException;
import xyz.deltacare.fatura.dto.FaturaDto;

public interface FaturaService {
    void criarFaturasMesCorrente() throws JsonProcessingException;
}
