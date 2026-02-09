package com.sus.agendamento.repository;

import com.sus.agendamento.entity.TaxaAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TaxaAtendimentoRepository extends JpaRepository<TaxaAtendimento, Long> {
    Optional<TaxaAtendimento> findByUnidade(String unidade);
}
