package uk.ac.le.qasm.job.searching.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationInterviewRequestUpdate {
    private LocalDateTime interview;
}
