package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import uk.ac.le.qasm.job.searching.api.enums.JobStatus;
import uk.ac.le.qasm.job.searching.api.enums.JobType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_posts", schema = "job_searching")
public class JobPost {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Setter
    @Column(name = "title")
    @JsonProperty(value = "title")
    private String title;

    @Setter
    @Column(name = "description", columnDefinition="TEXT")
    @JsonProperty(value = "description")
    private String description;

    @Setter
    @Column(name = "salary")
    @JsonProperty(value = "salary")
    private String salary;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "job_type")
    @JsonProperty("job_type")
    private JobType jobType;

    @Setter
    @Column(name = "is_visible")
    @JsonProperty(value = "is_visible")
    private Boolean isVisible;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @JsonProperty(value = "status")
    private JobStatus status;

    @Setter
    @Column(name = "category")
    @JsonProperty(value = "category")
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Provider provider;

    @Column(name = "deadline")
    @JsonProperty(value = "deadline")
    private LocalDateTime deadline;


    @OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL)
    @JsonProperty("applications")
    private List<JobPostApplication> jobPostApplications;

    @Transient
    @JsonProperty(value = "number_of_applicants")
    private int numberOfApplicants;

    @PostLoad
    private void calculateNumberOfApplicants() {
        this.numberOfApplicants = (jobPostApplications != null) ? jobPostApplications.size() : 0;
    }
}
