package uk.ac.le.qasm.job.searching.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import uk.ac.le.qasm.job.searching.api.Enumeration.JobType;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "provider")
@Table(name = "job_posts", schema = "job_searching")
@TableName(value = "job_posts", schema = "job_searching")
public class JobPost {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @TableId("id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @Setter
    @Column(name = "title")
    @TableField(value = "title")
    @JsonProperty(value = "title")
    private String title;

    @Setter
    @Column(name = "description", columnDefinition="TEXT")
    @TableField(value = "description")
    @JsonProperty(value = "description")
    private String description;

    @Setter
    @Column(name = "salary")
    @TableField(value = "salary")
    @JsonProperty(value = "salary")
    private String salary;

    @Setter
    @Enumerated(EnumType.STRING)
    @TableField("job_type")
    private JobType jobType;

    @Setter
    @Column(name = "isVisible")
    @TableField(value = "isVisible")
    @JsonProperty(value = "isVisible")
    private Boolean isVisible;

    @ManyToOne
    @JoinColumn(name = "provider_uuid")
    @TableField(value = "provider_uuid")
    @JsonIgnore
    private Provider provider;


}
