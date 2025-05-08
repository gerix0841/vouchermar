package com.springboot.vouchermar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.vouchermar.voucher.Voucher;
import com.springboot.vouchermar.voucher.VoucherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
class VoucherRestTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private VoucherRepository voucherRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void test_getAllVouchers() throws Exception {
		mockMvc.perform(get("/api/vouchers")).andExpect(status().isOk());
	}

	@Test
	void test_createVoucher() throws Exception {
		Voucher voucher = new Voucher(0,"444444444",3,LocalDate.now().plusDays(10));

		mockMvc.perform(post("/api/vouchers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(voucher)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("444444444"));
	}

	@Test
	void test_RedeemVoucher() throws Exception {
		Voucher voucher = new Voucher(0,"555555555",1,LocalDate.now().plusDays(10));
		voucherRepository.save(voucher);

		mockMvc.perform(post("/api/vouchers/redeem")
						.param("code", "555555555"))
				.andExpect(status().isOk())
				.andExpect(content().string("Voucher redeemed successfully!"));
	}
}
