package uk.ac.le.qasm.job.searching.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.ac.le.qasm.job.searching.api.entity.JobPost;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.enums.JobType;
import uk.ac.le.qasm.job.searching.api.request.JobPostRequest;
import uk.ac.le.qasm.job.searching.api.service.JobPostService;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        JobPost jobPost = new JobPost();
        jobPost.setId(UUID.randomUUID());
        when(jobPostService.saveJobPost(Mockito.any(JobPost.class))).thenReturn(jobPost);

        // Set up MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();

        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Test Title");
        jobPostRequest.setJobType("FULL_TIME");
        jobPostRequest.setDescription("Test Description");
        jobPostRequest.setSalary("50000");
        jobPostRequest.setIsVisible(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
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
        jobPostRequest.setIsVisible(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
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
        jobPostRequest.setIsVisible(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
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
        jobPostRequest.setIsVisible(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
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
        jobPostRequest.setIsVisible(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
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
        jobPostRequest.setIsVisible(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
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
        jobPostRequest.setIsVisible(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
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
        jobPostRequest.setIsVisible(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
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
        jobPostRequest.setIsVisible(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(asJsonString(jobPostRequest))
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest())
               .andExpect(jsonPath("$.errors[0]")
                                  .value("The full salary is required."));
    }

    @Test
    void testCreateJobPost_IsVisibleMissed_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Test title");
        jobPostRequest.setJobType("FULL_TIME");
        jobPostRequest.setDescription("Test Description");
        jobPostRequest.setSalary("8000");
//        jobPostRequest.setIsVisible(true);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(asJsonString(jobPostRequest))
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest())
               .andExpect(jsonPath("$.errors[0]")
                                  .value("The status of visibility is required."));
    }

    @Test
    void testCreateJobPost_IsVisibleIsEmpty_ShouldReturnBadRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Create a JobPostRequest object
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Test title");
        jobPostRequest.setJobType("FULL_TIME");
        jobPostRequest.setDescription("Test Description");
        jobPostRequest.setSalary("80000");
        jobPostRequest.setIsVisible(null);

        // Perform the request and assert the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/provider/job-post/create")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(asJsonString(jobPostRequest))
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest())
               .andExpect(jsonPath("$.errors[0]")
                                  .value("The status of visibility is required."));
    }

    @Test
    public void getAllJobPosts() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Mock authentication and principal
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock provider
        Provider mockProvider = new Provider();
        when(authentication.getPrincipal()).thenReturn(mockProvider);

        // Mock job posts
        Page<JobPost> mockJobPosts = new PageImpl<>(Collections.emptyList()); // You can customize this as needed
        when(jobPostService.getAllJobPostsByProvider(eq(mockProvider), any())).thenReturn(mockJobPosts);

        // Perform the request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/provider/job-post")
                                              .param("page", "0")
                                              .param("size", "10")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray());

        // Verify that the service method was called with the correct arguments
        verify(jobPostService, times(1)).getAllJobPostsByProvider(eq(mockProvider), any());
    }

    @Test
    void getAllJobPostsFailure() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Mock authentication and principal
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mock provider
        Provider mockProvider = new Provider();
        when(authentication.getPrincipal()).thenReturn(mockProvider);

        // Mock job posts service to throw an exception
        when(jobPostService.getAllJobPostsByProvider(eq(mockProvider), any()))
                .thenThrow(new RuntimeException("Simulated exception"));

        // Perform the request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/provider/job-post")
                                              .param("page", "0")
                                              .param("size", "10")
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isInternalServerError());

        // Verify that the service method was called with the correct arguments
        verify(jobPostService, times(1)).getAllJobPostsByProvider(eq(mockProvider), any());
    }

    @Test
    void updateJobPostSuccess() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Arrange
        UUID jobPostId = UUID.randomUUID();
        Provider provider = new Provider(); // Create a provider instance

        // Create a JobPostRequest with sample data
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Updated Title");
        jobPostRequest.setDescription("Updated Description");
        jobPostRequest.setSalary("50000");
        jobPostRequest.setIsVisible(true);
        jobPostRequest.setJobType("FULL_TIME"); // Assuming "FULL_TIME" is a valid JobType

        // Mock authentication and principal
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(provider);

        // Mock service to return an existing job post
        JobPost existingJobPost = new JobPost();
        existingJobPost.setId(jobPostId);
        existingJobPost.setProvider(provider);
        existingJobPost.setTitle(jobPostRequest.getTitle());
        existingJobPost.setDescription(jobPostRequest.getDescription());
        existingJobPost.setSalary(jobPostRequest.getSalary());
        existingJobPost.setIsVisible(jobPostRequest.getIsVisible());
        existingJobPost.setJobType(JobType.valueOf(jobPostRequest.getJobType()));
        when(jobPostService.updateJobPost(provider, jobPostId, jobPostRequest))
                .thenReturn(existingJobPost);

        // Perform the request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/provider/job-post/{jobPostId}", jobPostId)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(asJsonString(jobPostRequest))
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Title")) // Add assertions for other fields
               .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Description"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(50000))
               .andExpect(MockMvcResultMatchers.jsonPath("$.isVisible").value(true))
               .andExpect(MockMvcResultMatchers.jsonPath("$.jobType").value("FULL_TIME"));

        // Verify that the service method was called with the correct arguments
        verify(jobPostService, times(1)).updateJobPost(provider, jobPostId, jobPostRequest);
    }

    @Test
    void updateJobPostUnauthorized() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Arrange
        UUID jobPostId = UUID.randomUUID();
        Provider provider = new Provider(); // Create a provider instance

        // Create a JobPostRequest with sample data
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Updated Title");
        jobPostRequest.setDescription("Updated Description");
        jobPostRequest.setSalary("50000");
        jobPostRequest.setIsVisible(true);
        jobPostRequest.setJobType("FULL_TIME"); // Assuming "FULL_TIME" is a valid JobType

        // Mock authentication and principal
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(provider);

        // Mock service to return an existing job post
        when(jobPostService.updateJobPost(provider, jobPostId, jobPostRequest))
                .thenThrow(new RuntimeException("You are not authorized to update this job post"));

        // Perform the request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/provider/job-post/{jobPostId}", jobPostId)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(asJsonString(jobPostRequest))
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isForbidden());

        // Verify that the service method was called with the correct arguments
        verify(jobPostService, times(1)).updateJobPost(provider, jobPostId, jobPostRequest);
    }

    @Test
    void updateJobPostNotFound() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Arrange
        UUID jobPostId = UUID.randomUUID();
        Provider provider = new Provider(); // Create a provider instance
        JobPostRequest jobPostRequest = new JobPostRequest();
        jobPostRequest.setTitle("Updated Title");
        jobPostRequest.setDescription("Updated Description");
        jobPostRequest.setSalary("50000");
        jobPostRequest.setIsVisible(true);
        jobPostRequest.setJobType("FULL_TIME"); // Assuming "FULL_TIME" is a valid JobType
        // Mock authentication and principal
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(provider);

        // Mock service to throw JobPostNotFoundException
        when(jobPostService.updateJobPost(provider, jobPostId, jobPostRequest))
                .thenThrow(new RuntimeException("Job post not found"));

        // Perform the request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/provider/job-post/{jobPostId}", jobPostId)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(asJsonString(jobPostRequest))
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isForbidden());

        // Verify that the service method was called with the correct arguments
        verify(jobPostService, times(1)).updateJobPost(provider, jobPostId, jobPostRequest);
    }

    @Test
    void deleteJobPostSuccess() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Arrange
        UUID jobPostId = UUID.randomUUID();
        Provider provider = new Provider(); // Create a provider instance

        // Mock authentication and principal
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(provider);

        // Mock service to return an existing job post
        JobPost existingJobPost = new JobPost();
        existingJobPost.setId(jobPostId);
        existingJobPost.setProvider(provider);
        when(jobPostService.deleteJobPost(provider, jobPostId)).thenReturn(ResponseEntity.ok("Job post deleted successfully"));

        // Perform the request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/provider/job-post/{jobPostId}", jobPostId)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                                               .value("Job Post Deleted successfully!"));

        // Verify that the service method was called with the correct arguments
        verify(jobPostService, times(1)).deleteJobPost(provider, jobPostId);
    }

    @Test
    void deleteJobPostNotFound() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Arrange
        UUID jobPostId = UUID.randomUUID();
        Provider provider = new Provider(); // Create a provider instance

        // Mock authentication and principal
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(provider);

        // Mock service to throw JobPostNotFoundException
        when(jobPostService.deleteJobPost(provider, jobPostId))
                .thenThrow(new RuntimeException("Job post not found"));

        // Perform the request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/provider/job-post/{jobPostId}", jobPostId)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isForbidden());

        // Verify that the service method was called with the correct arguments
        verify(jobPostService, times(1)).deleteJobPost(provider, jobPostId);
    }

    @Test
    void deleteJobPostUnauthorized() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(jobPostController).build();
        // Arrange
        UUID jobPostId = UUID.randomUUID();
        Provider provider = new Provider(); // Create a provider instance

        // Mock authentication and principal
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(provider);

        // Mock service to throw UnauthorizedAccessException
        when(jobPostService.deleteJobPost(provider, jobPostId))
                .thenThrow(new RuntimeException("You are not authorized to delete this job post"));

        // Perform the request using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/provider/job-post/{jobPostId}", jobPostId)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isForbidden());

        // Verify that the service method was called with the correct arguments
        verify(jobPostService, times(1)).deleteJobPost(provider, jobPostId);
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
