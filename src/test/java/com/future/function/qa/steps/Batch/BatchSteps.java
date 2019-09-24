package com.future.function.qa.steps.Batch;

import org.springframework.beans.factory.annotation.Autowired;

import com.future.function.qa.api.batch.BatchAPI;
import com.future.function.qa.data.batch.BatchData;
import com.future.function.qa.steps.BaseSteps;
import net.thucydides.core.annotations.Steps;

public class BatchSteps extends BaseSteps {

  @Steps
  private BatchAPI batchAPI;

  @Autowired
  private BatchData batchData;
}
