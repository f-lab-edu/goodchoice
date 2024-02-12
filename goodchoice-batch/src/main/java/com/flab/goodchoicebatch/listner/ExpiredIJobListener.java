package com.flab.goodchoicebatch.listner;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ExpiredIJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("---------------> Before job execution");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("---------------> After Job");
    }

}
