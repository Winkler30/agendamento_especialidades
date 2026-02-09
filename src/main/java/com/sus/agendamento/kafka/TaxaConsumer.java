package com.sus.agendamento.kafka;

import com.sus.agendamento.entity.TaxaAtendimento;
import com.sus.agendamento.repository.TaxaAtendimentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaxaConsumer {

    private final TaxaAtendimentoRepository taxaRepository;

    @KafkaListener(topics = "atualizacao-taxas", groupId = "agendamento-group")
    public void listen(String message) {
        log.info("Recebido evento de taxa: {}", message);

        try {
            if ("SOLICITACAO_GLOBAL:SYNC".equals(message)) {
                log.info("Mensagem global recebida, executando rotina de sincronização...");

                List<TaxaAtendimento> taxas = taxaRepository.findAll();
                taxas.forEach(t -> {
                    t.setTempoEsperaMinutos(t.getFila() * 5);
                    t.setUltimaAtualizacao(LocalDateTime.now());
                });
                taxaRepository.saveAll(taxas);

                log.info("Sincronização de todas as taxas concluída.");
                return;
            }

            String[] parts = message.split(":");
            if (parts.length != 3) {
                log.warn("Mensagem inválida recebida do Kafka: {}", message);
                return;
            }

            String unidade = parts[0];

            Integer fila;
            Integer tempo;
            try {
                fila = Integer.parseInt(parts[1]);
                tempo = Integer.parseInt(parts[2]);
            } catch (NumberFormatException nfe) {
                log.warn("Valores inválidos na mensagem Kafka: {}", message, nfe);
                return;
            }

            TaxaAtendimento taxa = taxaRepository.findByUnidade(unidade)
                    .orElse(new TaxaAtendimento());

            taxa.setUnidade(unidade);
            taxa.setFila(fila);
            taxa.setTempoEsperaMinutos(tempo);
            taxa.setUltimaAtualizacao(LocalDateTime.now());

            taxaRepository.save(taxa);
            log.info("Taxa atualizada com sucesso para a unidade {}", unidade);

        } catch (Exception e) {
            log.error("Erro inesperado ao processar mensagem Kafka: {}", message, e);
        }
    }
}
