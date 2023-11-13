package uk.ac.le.qasm.job.searching.api.controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public JobPost sayHello(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Provider user = (Provider) authentication.getPrincipal();
        return user.getJobPosts().get(0);
    }
}
