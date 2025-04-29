package org.student.banking_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.student.banking_api.entity.Account;
import org.student.banking_api.service.AccountService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setAccountHolderName("Max Mustermann");
        testAccount.setBalance(500.0);
    }

    @Test
    void testCreateAccount() throws Exception {
        when(accountService.createAccount(any(Account.class))).thenReturn(testAccount);

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountHolderName\":\"Max Mustermann\",\"balance\":500.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountHolderName").value("Max Mustermann"))
                .andExpect(jsonPath("$.balance").value(500.0));
    }

    @Test
    void testGetAccount() throws Exception {
        when(accountService.getAccount(1L)).thenReturn(Optional.of(testAccount));

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountHolderName").value("Max Mustermann"))
                .andExpect(jsonPath("$.balance").value(500.0));
    }

    @Test
    void testDeposit() throws Exception {
        when(accountService.deposit(1L, 200.0)).thenReturn(testAccount);

        mockMvc.perform(post("/api/accounts/1/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":200.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountHolderName").value("Max Mustermann"))
                .andExpect(jsonPath("$.balance").value(500.0));
    }

    @Test
    void testWithdraw() throws Exception {
        when(accountService.withdraw(1L, 100.0)).thenReturn(testAccount);

        mockMvc.perform(post("/api/accounts/1/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountHolderName").value("Max Mustermann"))
                .andExpect(jsonPath("$.balance").value(500.0));
    }
}