package com.sample.batch.jobs.birthdayMail;

import com.sample.batch.context.BatchContext;
import com.sample.batch.listener.BaseJobExecutionListener;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.val;
import org.springframework.batch.core.JobExecution;

public class BirthdayMailJobListener extends BaseJobExecutionListener {

  @Override
  protected String getBatchId() {
    return "BATCH_003";
  }

  @Override
  protected String getBatchName() {
    return "バースデーメール送信";
  }

  @Override
  protected void before(JobExecution jobExecution, BatchContext context) {
    // 前日を対象とする
    val yesterday = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
    context.getAdditionalInfo().putIfAbsent("targetDate", yesterday);
  }

  @Override
  protected void after(JobExecution jobExecution, BatchContext context) {
    // 終了する直前に呼び出される
  }
}
