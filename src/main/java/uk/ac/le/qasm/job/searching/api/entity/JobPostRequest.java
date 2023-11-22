package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.le.qasm.job.searching.api.enums.JobStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostRequest {

    @NotEmpty(message = "The full title is required.")
    @JsonProperty("title")
    private String title;
    @NotEmpty(message = "The full description is required.")
    @JsonProperty("description")
    private String description;
    @NotEmpty(message = "The full salary is required.")
    @JsonProperty("salary")
    private String salary;
    @NotEmpty(message = "The full jobType is required.")
    @JsonProperty("job_type")
    private String jobType;
    @NotNull(message = "The status of visibility is required.")
    @JsonProperty("is_visible")
    private Boolean isVisible;
    @JsonProperty("status")
    private JobStatus jobStatus = JobStatus.PENDING;


}

