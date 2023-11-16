package uk.ac.le.qasm.job.searching.api.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobApplicationRequest {

    private String jobpost_uuid;

    private String seeker_uuid;
}
