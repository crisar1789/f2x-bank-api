package com.f2x.bank.interfaces.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.f2x.bank.TestUtils;
import com.f2x.bank.application.service.TransactionServiceI;
import com.f2x.bank.domain.model.Transaction;

@AutoConfigureMockMvc
@AutoConfigureWebMvc
@SpringBootTest
class TransactionControllerTest {

	@Autowired
    protected MockMvc mvc;
	
	@MockBean
    private TransactionServiceI transactionService;
	
	@Test
	public void createTransactionTest() throws Exception {
		Transaction transaction = TestUtils.createValidTransactionTransfer();
		
		final MvcResult mvcResult = createTransaction(transaction)
	            .andExpect(MockMvcResultMatchers.content().json(TestUtils.asJsonString(transaction)))
	            .andReturn();

	    assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	private ResultActions createTransaction(Transaction transaction) throws Exception {
		 when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

	        return mvc.perform(MockMvcRequestBuilders.get("")
	            .contentType(MediaType.APPLICATION_JSON)
	            .characterEncoding(StandardCharsets.UTF_8.name()));
	}

}
