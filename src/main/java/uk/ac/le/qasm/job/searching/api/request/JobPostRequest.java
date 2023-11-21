package uk.ac.le.qasm.job.searching.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotNull(message = "The status of visibility is required.")
    private Boolean isVisible;
}

