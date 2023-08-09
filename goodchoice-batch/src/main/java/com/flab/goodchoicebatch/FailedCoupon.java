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

    private final static int PAGE_OFFSET_NUMBER = 0;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CouponBatchWriterRepository couponBatchWriterRepository;

    @Bean
    public Job failedCouponJob() {
        return jobBuilderFactory.get("failedCouponJob")
                .start(failedCouponStepJob(null))
                .build();
    }

    @Bean
    @JobScope
    public Step failedCouponStepJob(@Value("#{jobParameters[chunkSize]}") Long chunkSize) {
        return stepBuilderFactory.get("failedCouponStepJob")
                .<CouponIssueFailedEntity, CouponIssueFailedEntity>chunk(chunkSize.intValue())
                .reader(failedCouponReader(null, null))
                .writer(failedCouponWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<CouponIssueFailedEntity> failedCouponReader(@Value("#{jobParameters[couponId]}") Long couponId, @Value("#{jobParameters[chunkSize]}") Long chunkSize) {
        JpaPagingItemReader reader = new JpaPagingItemReader() {
            @Override
            public int getPage() {
                return PAGE_OFFSET_NUMBER;
            }
        };

        reader.setQueryString("SELECT c FROM CouponIssueFailedEntity c WHERE c.restoredYn = false AND c.couponId = " + couponId + " order by c.id asc");
        reader.setPageSize(chunkSize.intValue());
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
