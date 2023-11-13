package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.le.qasm.job.searching.api.Enumeration.JobType;

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

    @Column(name = "title")
    @JsonProperty(value = "title")
    private String title;

    @Column(name = "description", columnDefinition="TEXT")
    @JsonProperty(value = "description")
    private String description;

    @Column(name = "salary")
    @JsonProperty(value = "salary")
    private String salary;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @ManyToOne
    @JoinColumn(name = "provider_uuid")
    private Provider provider;
}
