package uk.ac.le.qasm.job.searching.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPostRequest {
    @NotEmpty(message = "The full title is required.")
    private String title;
    @NotEmpty(message = "The full description is required.")
    private String description;
    @NotEmpty(message = "The full salary is required.")
    private String salary;
    @NotEmpty(message = "The full jobType is required.")
    private String jobType;
}

