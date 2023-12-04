package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import uk.ac.le.qasm.job.searching.api.enums.JobApplicationStatus;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_application", schema = "job_searching")
public class JobApplication {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @ManyToOne
    @JsonProperty("applicant")
    private JobSeeker applicant;

    @ManyToOne
    @JsonProperty("job_post")
    private JobPost jobPost;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonProperty(value = "status")
    private JobApplicationStatus status = JobApplicationStatus.PENDING;
}
