package uk.ac.le.qasm.job.searching.api.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "job_posts",schema = "job_searching")
public class JobPost {
    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private UUID id;

    @Column(name = "title")
    @JsonProperty(value = "title")
    @TableField("title")
    private String title;

    @Column(name = "description", columnDefinition="TEXT")
    @JsonProperty(value = "description")
    @TableField("description")
    private String description;

    @Column(name = "salary")
    @JsonProperty(value = "salary")
    @TableField("salary")
    private String salary;

    @Enumerated(EnumType.STRING)
    @TableField("job_Type")
    private JobType jobType;

    @TableField("provider_uuid")
    @Transient
    private String provider_uuid;

    @ManyToOne
    @JoinColumn(name = "provider_uuid")
    @TableField(exist = false)
    private Provider provider;

//    @OneToMany(mappedBy = "jobPost", cascade = CascadeType.ALL)
//    @TableField(exist = false)
//    private List<JobApplication> jobApplications;

    public void setId(String id_String){
        if (id_String != null){
            this.id = UUID.fromString(id_String);
        }else {
            this.id=null;
        }
    }
}
