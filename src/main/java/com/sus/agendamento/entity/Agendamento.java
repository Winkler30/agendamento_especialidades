package com.sus.agendamento.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long usuarioId;
    
    @OneToOne
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;
    
    private LocalDateTime dataCriacao;
    private String status; // PENDENTE, CONFIRMADO, CANCELADO
}
