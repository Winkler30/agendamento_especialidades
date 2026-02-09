package com.sus.agendamento.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaxaScheduler {

    private final KafkaTemplate<String, String> kafkaTemplate;

    // Roda a cada 10 minutos
    @Scheduled(fixedRate = 600000)
    public void verificarTaxas() {
        log.info("Iniciando verificação periódica de taxas de lotação...");
        
        // Simulação: Enviando comando para unidades atualizarem suas taxas via Kafka
        // Em um cenário real, isso poderia consultar um serviço externo ou disparar um evento
        kafkaTemplate.send("atualizacao-taxas", "SOLICITACAO_GLOBAL:SYNC");
        
        log.info("Solicitação de atualização de taxas enviada ao Kafka.");
    }
}
