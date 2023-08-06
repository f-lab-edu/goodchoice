package com.flab.goodchoicebatch;

import com.flab.goodchoicebatch.infrastructure.CouponBatchWriterRepository;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class FailedCoupon {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CouponBatchWriterRepository couponBatchWriterRepository;

    private static final int chunkSize = 10;

    @Bean
    public Job failedCouponJob() {
        return jobBuilderFactory.get("failedCouponJob")
                .start(failedCouponStepJob())
                .build();
    }

    @Bean
    @JobScope
    public Step failedCouponStepJob() {
        return stepBuilderFactory.get("failedCouponStepJob")
                .<CouponIssueFailedEntity, CouponIssueFailedEntity>chunk(chunkSize)
                .reader(failedCouponReader(null))
                .writer(failedCouponWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<CouponIssueFailedEntity> failedCouponReader(@Value("#{jobParameters[couponId]}") Long couponId) {
        JpaPagingItemReader reader = new JpaPagingItemReader() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("SELECT c FROM CouponIssueFailedEntity c WHERE c.restoredYn = false AND c.couponId = " + couponId + " order by c.id asc");
        reader.setPageSize(chunkSize);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("reader");

        return reader;
    }

    @Bean
    @StepScope
    public ItemWriter<CouponIssueFailedEntity> failedCouponWriter() {
        return ((List<? extends CouponIssueFailedEntity> couponIssueFailedEntities) -> {
            couponBatchWriterRepository.createCouponIssue(couponIssueFailedEntities);
            couponBatchWriterRepository.modifyCouponIssueFailed(couponIssueFailedEntities);
        });
    }
}
