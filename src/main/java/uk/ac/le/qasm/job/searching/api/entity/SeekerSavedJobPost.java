package uk.ac.le.qasm.job.searching.api.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seeker_saved_job_post", schema = "job_searching")
public class SeekerSavedJobPost {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "job_post_id", nullable = false)
    @JsonIgnore
    private JobPost jobPost;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", nullable = false)
    @JsonIgnore
    private JobSeeker jobSeeker;

    @Transient
    private UUID jobPostId;

    @PostLoad
    private void fillJobPostInfo() {
        this.jobPostId = jobPost.getId();
    }

}
