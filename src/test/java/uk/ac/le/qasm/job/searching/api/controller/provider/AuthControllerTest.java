package uk.ac.le.qasm.job.searching.api.controller.provider;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.le.qasm.job.searching.api.Service.AuthenticationService;
import uk.ac.le.qasm.job.searching.api.controller.Provider.AuthController;
import uk.ac.le.qasm.job.searching.api.entity.Provider;
import uk.ac.le.qasm.job.searching.api.request.RegisterRequest;


@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Mock
    private AuthenticationService providerService;
    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    // success scenario
    @Test
    public void testRegisterProvider_SuccessfulRegistration_ShouldReturnOk() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUsername");
        request.setPassword("testPassword");
        request.setCompany_name("tech");
        request.setCompany_location("leicester");
        request.setCompany_contact_number("08123131323");
        when(providerService.register(request)).thenReturn("validToken");
        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("validToken"));
        verify(providerService, times(1)).register(any(RegisterRequest.class));
    }
    @Test
    public void testRegisterProvider_WithUsernameAlreadyExists_ShouldReturnForbidden() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existingUsername");
        request.setPassword("testPassword");
        request.setCompany_name("tech");
        request.setCompany_location("leicester");
        request.setCompany_contact_number("08123131323");
        doThrow(RuntimeException.class)
                .when(providerService)
                .register(request);
        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(jsonPath("$.message").value("Provider already exists!"));
        verify(providerService, times(1)).register(request);

    }
    @Test
    public void testRegisterProvider_ShouldReturnBadRequest_UsernameMissed() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        // missed username here
        request.setPassword("testPassword");
        request.setCompany_name("tech");
        request.setCompany_location("leicester");
        request.setCompany_contact_number("08123131323");

        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errors[0]")
                        .value("The full username is required."));
        verify(providerService, never()).register(any(RegisterRequest.class));
    }
    @Test
    public void testRegisterProvider_ShouldReturnBadRequest_UsernameIsEmpty() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        // username is an empty string
        request.setUsername("");
        request.setPassword("testPassword");
        request.setCompany_name("tech");
        request.setCompany_location("leicester");
        request.setCompany_contact_number("08123131323");

        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full username is required."));
        verify(providerService, never()).register(any(RegisterRequest.class));
    }
    @Test
    public void testRegisterProvider_ShouldReturnBadRequest_PasswordMissed() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUsername");
        // missed password here
        request.setCompany_name("tech");
        request.setCompany_location("leicester");
        request.setCompany_contact_number("08123131323");

        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full password is required."));
        verify(providerService, never()).register(any(RegisterRequest.class));
    }
    @Test
    public void testRegisterProvider_ShouldReturnBadRequest_PasswordIsEmpty() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUsername");
        // password is empty here
        request.setPassword("");
        request.setCompany_name("tech");
        request.setCompany_location("leicester");
        request.setCompany_contact_number("08123131323");

        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full password is required."));
        verify(providerService, never()).register(any(RegisterRequest.class));
    }
    @Test
    public void testRegisterProvider_ShouldReturnBadRequest_CompanyNameMissed() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUsername");
        request.setPassword("testPassword");
        // missed company name
        request.setCompany_location("leicester");
        request.setCompany_contact_number("08123131323");

        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full company name is required."));
        verify(providerService, never()).register(any(RegisterRequest.class));
    }
    @Test
    public void testRegisterProvider_ShouldReturnBadRequest_CompanyNameIsEmpty() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUsername");
        request.setPassword("testPassword");
        // company name is empty here
        request.setCompany_name("");
        request.setCompany_location("leicester");
        request.setCompany_contact_number("08123131323");

        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full company name is required."));
        verify(providerService, never()).register(any(RegisterRequest.class));
    }
    @Test
    public void testRegisterProvider_ShouldReturnBadRequest_CompanyLocationMissed() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUsername");
        request.setPassword("testPassword");
        request.setCompany_name("testCompanyName");
        // missed company location
        request.setCompany_contact_number("08123131323");

        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full company location is required."));
        verify(providerService, never()).register(any(RegisterRequest.class));
    }
    @Test
    public void testRegisterProvider_ShouldReturnBadRequest_CompanyLocationIsEmpty() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUsername");
        request.setPassword("testPassword");
        request.setCompany_name("testCompanyName");
        request.setCompany_location("");
        request.setCompany_contact_number("08123131323");

        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full company location is required."));
        verify(providerService, never()).register(any(RegisterRequest.class));
    }
    @Test
    public void testRegisterProvider_ShouldReturnBadRequest_CompanyContactNumberMissed() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUsername");
        request.setPassword("testPassword");
        request.setCompany_name("testCompanyName");
        request.setCompany_location("testCompanyLocation");
        // missed company contact number

        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full company contact number is required."));
        verify(providerService, never()).register(any(RegisterRequest.class));
    }
    @Test
    public void testRegisterProvider_ShouldReturnBadRequest_CompanyContactNumberIsEmpty() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testUsername");
        request.setPassword("testPassword");
        request.setCompany_name("testCompanyName");
        request.setCompany_location("testCompanyLocation");
        // Company location is empty here
        request.setCompany_contact_number("");

        var result = mockMvc.perform(post("/api/v1/auth/provider/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("The full company contact number is required."));
        verify(providerService, never()).register(any(RegisterRequest.class));
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
