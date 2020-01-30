package com.future.function.qa.model.response.scoring.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentWebResponse {

    private String id;

    private String title;

    private String description;

    private long deadline;

    private String batchCode;

    private String file;

    private String fileId;

    private long uploadedDate;

}
