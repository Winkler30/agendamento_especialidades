package com.sus.agendamento.repository;

import com.sus.agendamento.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findByEspecialidadeAndStatus(String especialidade, String status);
}
