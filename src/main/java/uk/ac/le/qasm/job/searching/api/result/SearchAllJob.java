package uk.ac.le.qasm.job.searching.api.result;
import lombok.Data;
import uk.ac.le.qasm.job.searching.api.Enumeration.JobType;
import java.util.UUID;
@Data
public class SearchAllJob {

    private String id;

    private String title;

    private String description;

    private String salary;

    private JobType jobType;

    private String company_name;

    private String company_contact_number;

    private String company_location;

//    public void setId(String id_String){
//        if (id_String != null){
//            this.id = UUID.fromString(id_String);
//        }else {
//            this.id=null;
//        }
//    }

}
