package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import uk.ac.le.qasm.job.searching.api.Enumeration.JobType;

import java.util.UUID;


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
    private JobType jobType;

    @Setter
    @Column(name = "isVisible")
    @JsonProperty(value = "isVisible")
    private Boolean isVisible;

    @ManyToOne
    @JoinColumn(name = "provider_uuid")
    private Provider provider;
}
