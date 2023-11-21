package uk.ac.le.qasm.job.searching.api.controller.provider;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.ac.le.qasm.job.searching.api.Service.JobPostService;
import uk.ac.le.qasm.job.searching.api.controller.Provider.AuthController;
import uk.ac.le.qasm.job.searching.api.controller.Provider.JobPostController;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.request.JobPostRequest;

@ExtendWith(MockitoExtension.class)
public class JobPostControllerTest {
    @Mock
    private MockMvc mockMvc;
    @Mock
    private JobPostService jobPostService;
    @InjectMocks
    private JobPostController jobPostController;

    @Test
    void testCreateJobPost() throws Exception {
        // Mock authentication and SecurityContextHolder
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock the principal (Provider)
        var provider = new Provider();
        when(authentication.getPrincipal()).thenReturn(provider);

        // Mock yourService.saveJobPost method
        doNothing().when(jobPostService).saveJobPost(Mockito.any(JobPost.class));

        // Set up MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();

        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Test Title");
        jobPostRequest.setJobType("FULL_TIME");
        jobPostRequest.setDescription("Test Description");
        jobPostRequest.setSalary("50000");

        // Perform the request and assert the response
        var res = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jobPostRequest))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(jsonPath("$.message")
                        .value("Job Post Created successfully!"));
    }

    @Test
    void testCreateJobPost_MissedTitle_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setJobType("FULL_TIME");
        jobPostRequest.setDescription("Test Description");
        jobPostRequest.setSalary("50000");

        // Perform the request and assert the response
        var res = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jobPostRequest))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(jsonPath("$.errors[0]")
                        .value("The full title is required."));
    }
    @Test
    void testCreateJobPost_TitleIsEmpty_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("");
        jobPostRequest.setJobType("FULL_TIME");
        jobPostRequest.setDescription("Test Description");
        jobPostRequest.setSalary("50000");

        // Perform the request and assert the response
        var res = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jobPostRequest))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(jsonPath("$.errors[0]")
                        .value("The full title is required."));
    }

    @Test
    void testCreateJobPost_JobTypeMissed_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Test title");
        // missed job type here
        jobPostRequest.setDescription("Test Description");
        jobPostRequest.setSalary("50000");

        // Perform the request and assert the response
        var res = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jobPostRequest))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(jsonPath("$.errors[0]")
                        .value("The full jobType is required."));
    }
    @Test
    void testCreateJobPost_JobTypeIsEmpty_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Test title");
        jobPostRequest.setJobType("");
        jobPostRequest.setDescription("Test Description");
        jobPostRequest.setSalary("50000");

        // Perform the request and assert the response
        var res = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jobPostRequest))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(jsonPath("$.errors[0]")
                        .value("The full jobType is required."));
    }
    @Test
    void testCreateJobPost_DescriptionMissed_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Test title");
        jobPostRequest.setJobType("FULL_TIME");
//        jobPostRequest.setDescription("");
        jobPostRequest.setSalary("50000");

        // Perform the request and assert the response
        var res = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jobPostRequest))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(jsonPath("$.errors[0]")
                        .value("The full description is required."));
    }
    @Test
    void testCreateJobPost_DescriptionIsEmpty_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Test title");
        jobPostRequest.setJobType("FULL_TIME");
        jobPostRequest.setDescription("");
        jobPostRequest.setSalary("50000");

        // Perform the request and assert the response
        var res = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jobPostRequest))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                         .andExpect(jsonPath("$.errors[0]")
                        .value("The full description is required."));
    }
    @Test
    void testCreateJobPost_SalaryMissed_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Test title");
        jobPostRequest.setJobType("FULL_TIME");
        jobPostRequest.setDescription("Test Description");
//        jobPostRequest.setSalary("50000");

        // Perform the request and assert the response
        var res = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jobPostRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full salary is required."));
    }
    @Test
    void testCreateJobPost_SalaryIsEmpty_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Test title");
        jobPostRequest.setJobType("FULL_TIME");
        jobPostRequest.setDescription("Test Description");
        jobPostRequest.setSalary("");

        // Perform the request and assert the response
        var res = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jobPostRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full salary is required."));
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
