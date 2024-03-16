package com.flab.goodchoicebatch;

import com.flab.goodchoicebatch.infrastructure.CouponBatchWriterRepository;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBatchTest
@SpringBootTest(classes = {FailedCoupon.class, TestBatchConfig.class, CouponBatchWriterRepository.class})
class FailedCouponMockTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @MockBean
    private CouponBatchWriterRepository couponBatchWriterRepository;

    @MockBean(name = "itemReader")
    private JpaPagingItemReader<CouponIssueFailedEntity> mockItemReader;

    @BeforeEach
    void setUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void failedCouponJob() throws Exception {
        CouponIssueFailedEntity couponIssueFailedEntity = CouponIssueFailedEntity.builder()
                .memberId(1L)
                .couponId(1L)
                .restoredYn(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(mockItemReader.read()).thenReturn(couponIssueFailedEntity);
        doNothing().when(couponBatchWriterRepository).createCouponIssue(anyList());
        doNothing().when(couponBatchWriterRepository).modifyCouponIssueFailed(anyList());

        JobParameters parameters = new JobParametersBuilder().addLong("couponId", 1L).toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }
}