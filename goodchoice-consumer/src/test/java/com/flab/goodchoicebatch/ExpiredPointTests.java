package com.flab.goodchoicebatch;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.flab.goodchoicepoint.domain.Point;
import com.flab.goodchoicepoint.infrastructure.PointRepository;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = { ExpiredPoint.class, TestBatchConfig.class })
public class ExpiredPointTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PointRepository pointRepository;

    @Test
    public void 포인트의_상태가_변경된다() throws Exception {
        // given
        for (long i = 0; i < 50; i++) {
            pointRepository.save(Point.builder()
                    .idx(i)
                    .memberId(1L)
                    .itemId(94L)
                    .amount("3000")
                    .build());
        }

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // then
        Assertions.assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        Assertions.assertThat(pointRepository.findExpirPoints().size()).isEqualTo(50);

    }
}
