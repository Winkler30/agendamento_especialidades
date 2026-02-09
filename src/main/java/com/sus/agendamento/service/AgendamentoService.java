package com.sus.agendamento.service;

import com.sus.agendamento.entity.Agenda;
import com.sus.agendamento.entity.Agendamento;
import com.sus.agendamento.repository.AgendaRepository;
import com.sus.agendamento.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final AgendaRepository agendaRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public List<Agenda> buscarDisponiveis(String especialidade) {
        return agendaRepository.findByEspecialidadeAndStatus(especialidade, "DISPONIVEL");
    }

    @Transactional
    public Agendamento criarAgendamento(Long usuarioId, Long agendaId) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new RuntimeException("Agenda não encontrada"));

        if (!"DISPONIVEL".equals(agenda.getStatus())) {
            throw new RuntimeException("Horário não está mais disponível");
        }

        agenda.setStatus("RESERVADO");
        agendaRepository.save(agenda);

        Agendamento agendamento = new Agendamento();
        agendamento.setUsuarioId(usuarioId);
        agendamento.setAgenda(agenda);
        agendamento.setDataCriacao(LocalDateTime.now());
        agendamento.setStatus("PENDENTE");

        Agendamento salvo = agendamentoRepository.save(agendamento);

        // Notificar via Kafka
        kafkaTemplate.send("agendamento-criado", "Agendamento ID: " + salvo.getId());

        return salvo;
    }
}
