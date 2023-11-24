package uk.ac.le.qasm.job.searching.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seeker_social_media_platforms", schema = "job_searching")
public class SeekerSocialMedia {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Setter
    @Column(name = "platform", unique = true)
    @JsonProperty(value = "platform")
    private String platform;

    @Setter
    @Column(name = "link")
    @JsonProperty(value = "link")
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seeker_id", nullable = false)
    @JsonIgnore
    private JobSeeker seeker;
}
