package com.flab.goodchoicebatch;

import com.flab.goodchoicebatch.infrastructure.CouponBatchWriterRepository;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueFailedRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.flab.goodchoicecoupon.domain.CouponType.DISCOUNT;
import static com.flab.goodchoicecoupon.domain.State.ACTIVITY;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {FailedCoupon.class, TestBatchConfig.class, CouponBatchWriterRepository.class})
class FailedCouponTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private CouponIssueFailedRepository couponIssueFailedRepository;

    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void failedCouponJob() throws Exception {
        CouponEntity couponEntity = CouponEntity.builder()
                .couponToken(UUID.randomUUID())
                .couponName("10%할인")
                .stock(100)
                .couponType(DISCOUNT)
                .discountValue(10)
                .state(ACTIVITY)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        couponRepository.save(couponEntity);

        CouponIssueFailedEntity couponIssueFailedEntity = CouponIssueFailedEntity.builder()
                .memberId(1L)
                .couponId(couponEntity.getId())
                .restoredYn(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        couponIssueFailedRepository.save(couponIssueFailedEntity);

        JobParameters parameters = new JobParametersBuilder()
                .addLong("couponId", couponEntity.getId())
                .addLong("chunkSize", 10L)
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}