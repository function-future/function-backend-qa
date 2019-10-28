package com.future.function.qa.steps.Core.ActivityBlog;

import com.future.function.qa.api.core.activity_blog.ActivityBlogAPI;
import com.future.function.qa.data.core.activity_blog.ActivityBlogData;
import com.future.function.qa.steps.BaseSteps;
import net.thucydides.core.annotations.Steps;
import org.springframework.beans.factory.annotation.Autowired;

public class ActivityBlogSteps extends BaseSteps {

  @Steps
  private ActivityBlogAPI activityBlogAPI;

  @Autowired
  private ActivityBlogData activityBlogData;
}
