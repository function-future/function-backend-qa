package com.future.function.qa.model.request.scoring.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentWebRequest {

    private String title;

    private String description;

    private Long deadline;

    private List<String> files;

}
