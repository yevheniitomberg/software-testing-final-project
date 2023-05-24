package fun.tomberg.swedbankhm.controller;

import fun.tomberg.swedbankhm.entity.Role;
import fun.tomberg.swedbankhm.entity.User;
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

import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MockMvc mockMvc;

    @Test
    void adminGet() throws Exception {
        User user = new User(1, "Yevhenii Tomberg", "01-04-2002", "Paul Kerese 14", "yevhenii@tomberg.com", "password", true, "password", new HashSet<Role>());
        mockMvc.perform(get("/admin").with(user(user))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void adminEditPost() throws Exception {
        User user = new User(0, "Yevhenii Tomberg", "01-04-2002", "Paul Kerese 14", "yevhenii@tomberg.com", "password", true, "password", new HashSet<Role>());
        userRepository.save(user);
        mockMvc.perform(post("/admin").with(user(user)).param("userId", "1")).andDo(print()).andExpect(status().is3xxRedirection());
        userRepository.deleteAll();
    }
}