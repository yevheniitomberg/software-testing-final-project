package fun.tomberg.swedbankhm.controller;

import fun.tomberg.swedbankhm.repository.RoleRepository;
import fun.tomberg.swedbankhm.repository.UserRepository;
import fun.tomberg.swedbankhm.service.implementation.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomepageController.class)
@AutoConfigureMockMvc(addFilters = false)
class HomepageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void homepagePost() throws Exception {
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void homepageGet() throws Exception {
        mockMvc.perform(post("/")).andDo(print()).andExpect(status().isOk());
    }
}