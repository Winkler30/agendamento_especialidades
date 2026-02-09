package com.sus.agendamento.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificacaoService {

    public void enviarLembrete(Long agendamentoId, String mensagem) {
        // Simulação de envio de notificação (Firebase/SMS/Email)
        log.info("ENVIANDO NOTIFICAÇÃO para Agendamento {}: {}", agendamentoId, mensagem);
    }
}
