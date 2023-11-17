//package com.emgram.kr.dobby;
//
//import com.emgram.kr.dobby.controller.UserController;
//import com.emgram.kr.dobby.dto.login.User;
//import com.emgram.kr.dobby.exception.AppException;
//import com.emgram.kr.dobby.exception.ErrorCode;
//import com.emgram.kr.dobby.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.DisplayName;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithAnonymousUser;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest
////@SpringBootTest
//public class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private UserService userService;
//
////    @Before
////    public void setUp() {
////        MockitoAnnotations.initMocks(this);
////    }
//
//    @Test
//    @DisplayName("로그인 성공!!")
//    @WithMockUser
//    public void login_success() throws Exception{
//        String Name="youbest!!";
//        String password="abc1234";
//
//        when(userService.login(any(), any()))
//                .thenReturn("token을 리턴함");
//
//        mockMvc.perform(post("/api/v1/users/login")
//               // .with(csrf())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(new User(Name, password))))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("Login fail!!- Name없음")
//    @WithMockUser
//    public void login_fail1() throws Exception{
//        String Name="youbest!!";
//        String password="abc1234";
//
//        when(userService.login(any(), any()))
//                .thenThrow(new AppException(ErrorCode.Name_NOT_FOUND, ""));
//
//        mockMvc.perform(post("/api/v1/users/login")
//                .with(csrf())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(new User(Name, password))))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//
//    }
//
//    @Test
//    @DisplayName("Login fail!!- password틀림")
//    @WithMockUser
//    public void login_fail2() throws Exception{
//        String Name="youbest!!";
//        String password="abc1234";
//
//        when(userService.login(any(), any()))
//                .thenThrow(new AppException(ErrorCode.INVALID_PASSWORD, ""));
//
//        mockMvc.perform(post("/api/v1/users/login")
//                .with(csrf())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(new User(Name, password))))
//                .andDo(print())
//                .andExpect(status().isUnauthorized());
//
//    }
//
//}
