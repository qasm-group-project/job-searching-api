package uk.ac.le.qasm.job.searching.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.ac.le.qasm.job.searching.api.Enumeration.ApplicationStatus;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "job_application", schema = "job_searching")
@TableName(value = "job_application",schema = "job_searching")
public class JobApplication {

    @Id
    @GeneratedValue
    @Column(name = "id")
    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    @TableId
    private UUID id;

    @Enumerated(EnumType.STRING)
    @TableField("job_Type")
    private ApplicationStatus applicationStatus;



    @TableField("jobpost_uuid")
    private UUID jobpost_uuid;

    @TableField("seeker_uuid")
    private UUID seeker_uuid;

//    public void setJobpost_uuid(String jobpost_uuid) {
//        if (jobpost_uuid != null){
//            this.id = UUID.fromString(jobpost_uuid);
//        }else {
//            this.id=null;
//        }
//    }
//
//    public void setSeeker_uuid(String seeker_uuid) {
//        if (seeker_uuid != null){
//            this.id = UUID.fromString(seeker_uuid);
//        }else {
//            this.id=null;
//        }
//    }

//    @ManyToOne
//    @JoinColumn(name = "jobpost_uuid")
//    @TableField(exist = false)
//    private JobPost jobPost;


}
