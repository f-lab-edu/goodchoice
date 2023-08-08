package com.flab.goodchoicebatch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.flab.goodchoicebatch.listner.ExpiredChunkListener;
import com.flab.goodchoicebatch.listner.ExpiredIJobListener;
import com.flab.goodchoicebatch.listner.ExpiredStepListener;
import com.flab.goodchoicepoint.domain.Point;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javax.batch.api.chunk.ItemReader;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ExpiredPointJobConfig {

    private int chunkSize = 10;

    static final String JOB_NAME = "expiredPointJob";

    static final String STEP_NAME = "expiredJobStep";

    private final DataSource dataSource;

    private final KafkaTemplate<String, Point> salesInfoKafkaTemplate;

    @Bean
    public Job expiredPointJob(JobBuilderFactory jobBuilderFactory, ExpiredIJobListener ExpiredIJobListener,
            Step expiredJobStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .preventRestart()
                .incrementer(new RunIdIncrementer())
                .start(expiredJobStep)
                .listener(ExpiredIJobListener)
                .build();
    }

    @Bean
    @JobScope
    public Step expiredPointJobStep(StepBuilderFactory stepBuilderFactory, ExpiredChunkListener inactiveChunkListener,
            ExpiredStepListener inativeStepListener, TaskExecutor taskExecutor)
            throws Exception {
        return stepBuilderFactory.get(STEP_NAME)
                .<Point, Future<Point>>chunk(chunkSize)
                .reader(expiredPointReader())
                .writer(asyncItemWriter())
                .listener(inativeStepListener)
                .taskExecutor(taskExecutor)
                .throttleLimit(2)
                .allowStartIfComplete(true)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(15);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix("Thread N-> :");
        return executor;
    }

    @Bean
    @StepScope
    public JdbcCursorItemReader<Point> expiredPointReader() throws Exception {
        String sql = "SELECT * FROM point WHERE expire_at >= date_add(now(), interval 1 day)";

        return new JdbcCursorItemReaderBuilder<Point>()
                .name("expiredPointReader")
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .verifyCursorPosition(false)
                .sql(sql)
                .rowMapper(new BeanPropertyRowMapper<>(Point.class))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Point, Point> expiredPointProcessor() {
        return new org.springframework.batch.item.ItemProcessor<Point, Point>() {
            @Override
            public Point process(Point point) throws Exception {
                log.info("Current Point={}", point.getIdx());
                point.setInactive();
                return point;
            }
        };
    }

    @Bean
    public AsyncItemWriter<Point> asyncItemWriter() {
        var asyncWriter = new AsyncItemWriter<Point>();
        asyncWriter.setDelegate(salesInfoKafkaItemWriter());
        return asyncWriter;
    }

    @Bean
    @SneakyThrows
    public KafkaItemWriter<String, Point> salesInfoKafkaItemWriter() {
        var kafkaItemWriter = new KafkaItemWriter<String, Point>();
        kafkaItemWriter.setKafkaTemplate(salesInfoKafkaTemplate);
        kafkaItemWriter.setItemKeyMapper(salesInfo -> String.valueOf(salesInfo.getIdx()));
        kafkaItemWriter.setDelete(Boolean.FALSE);
        kafkaItemWriter.afterPropertiesSet();
        return kafkaItemWriter;
    }

}
