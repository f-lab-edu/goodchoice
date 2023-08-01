package com.flab.goodchoicebatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.flab.goodchoicepoint.domain.Point;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
// @ConditionalOnProperty(name = "job.name", havingValue = JOB_NAME)
public class ExpiredPoint {

    static final String JOB_NAME = "expiredPointJob";
    static final String STEP_NAME = "expiredJobStep";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    private int chunkSize = 10;

    @Bean
    public Job expiredPointJob() {
        return jobBuilderFactory.get(JOB_NAME)
                // .preventRestart()
                .start(expiredJobStep())
                .build();
    }

    @Bean
    @JobScope
    public Step expiredJobStep() {
        return stepBuilderFactory.get(STEP_NAME)
                .<Point, Point>chunk(chunkSize)
                .reader(expiredPointReader())
                .processor(expiredPointProcessor())
                .writer(expiredPointWriter())
                // .allowStartIfComplete(true)
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Point> expiredPointReader() {
        JpaPagingItemReader<Point> reader = new JpaPagingItemReader<Point>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("SELECT p FROM Point p where p.status = 'VALID'");
        reader.setPageSize(chunkSize);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("expiredPointReader");

        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<Point, Point> expiredPointProcessor() {
        return new org.springframework.batch.item.ItemProcessor<Point, Point>() {
            @Override
            public Point process(Point point) throws Exception {
                log.info("Current Point={}", point);
                return point.setInactive();
            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Point> expiredPointWriter() {
        JpaItemWriter<Point> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

}
