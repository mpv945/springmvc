package org.haijun.study.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)*/
// 使用mvc测试参数是否正确
public class ValidatorMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    //@Test
    public void submitRegistrationPasswordNotValid() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .param("firstName", "Memory")
                                .param("lastName", "Not Found")
                                .param("email", "new@memorynotfound.com")
                                .param("confirmEmail", "new@memorynotfound.com")
                                .param("password", "password")
                                .param("confirmPassword", "password")
                                .param("terms", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(2))// 错误的个数
                .andExpect(model().attributeHasFieldErrors("user", "password", "confirmPassword"))
                .andExpect(status().isOk());
    }

    //@Test
    public void submitRegistrationPasswordNotMatching() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .param("firstName", "Memory")
                                .param("lastName", "Not Found")
                                .param("email", "new@memorynotfound.com")
                                .param("confirmEmail", "new@memorynotfound.com")
                                .param("password", "xjD1!3djk4")
                                .param("confirmPassword", "xjD1!3djk3")
                                .param("terms", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(status().isOk());
    }


    //@Test
    public void submitRegistrationSuccess() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .param("firstName", "Memory")
                                .param("lastName", "Not Found")
                                .param("email", "new@memorynotfound.com")
                                .param("confirmEmail", "new@memorynotfound.com")
                                .param("password", "xjD1!3djk4")
                                .param("confirmPassword", "xjD1!3djk4")
                                .param("terms", "on")
                )
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/registration?success"))
                .andExpect(status().is3xxRedirection());
    }
}
