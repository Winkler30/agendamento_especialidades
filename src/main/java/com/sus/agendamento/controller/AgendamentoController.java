package com.sus.agendamento.controller;

import com.sus.agendamento.entity.Agenda;
import com.sus.agendamento.entity.Agendamento;
import com.sus.agendamento.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamento")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Agenda>> getDisponiveis(@RequestParam String especialidade) {
        return ResponseEntity.ok(agendamentoService.buscarDisponiveis(especialidade));
    }

    @PostMapping("/criar")
    public ResponseEntity<Agendamento> criar(@RequestParam Long usuarioId, @RequestParam Long agendaId) {
        return ResponseEntity.ok(agendamentoService.criarAgendamento(usuarioId, agendaId));
    }
}
