package com.sus.agendamento.batch;

import com.sus.agendamento.entity.Agendamento;
import com.sus.agendamento.service.NotificacaoService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class NotificacaoBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final NotificacaoService notificacaoService;

    @Bean
    public JpaPagingItemReader<Agendamento> reader() {
        return new JpaPagingItemReaderBuilder<Agendamento>()
                .name("agendamentoReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Agendamento a WHERE a.status = 'PENDENTE'")
                .pageSize(10)
                .build();
    }

    @Bean
    public ItemProcessor<Agendamento, Agendamento> processor() {
        return agendamento -> {
            // Lógica para verificar se faltam 15, 7 ou 1 dia
            // Para simplificação do MVP, processamos todos os pendentes
            return agendamento;
        };
    }

    @Bean
    public ItemWriter<Agendamento> writer() {
        return agendamentos -> {
            for (Agendamento a : agendamentos) {
                notificacaoService.enviarLembrete(a.getId(), "Lembrete de consulta para o dia " + a.getAgenda().getDataHora());
            }
        };
    }

    @Bean
    public Step step1() {
        return new StepBuilder("notificacaoStep", jobRepository)
                .<Agendamento, Agendamento>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job notificacaoJob() {
        return new JobBuilder("notificacaoJob", jobRepository)
                .start(step1())
                .build();
    }
}
