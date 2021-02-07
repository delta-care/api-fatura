package xyz.deltacare.fatura.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import xyz.deltacare.fatura.service.fatura.FaturaService;

@Controller
public class FaturaScheduler {

    private final FaturaService faturaService;
    private Boolean executou = false;

    public FaturaScheduler(FaturaService faturaService) {
        this.faturaService = faturaService;
    }

    @SneakyThrows
    @Scheduled(cron = "* * * * * *")
    //@Scheduled(cron = "0 0 0 ? 1/1 MON#1")
    private void criar() {
        if (!this.executou) faturaService.criarFaturasMesCorrente();
        this.executou = true;
    }

}
