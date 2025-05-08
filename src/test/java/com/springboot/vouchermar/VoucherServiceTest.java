package com.springboot.vouchermar;

import com.springboot.vouchermar.voucher.Voucher;
import com.springboot.vouchermar.voucher.VoucherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VoucherServiceTest {

	@Autowired
	private VoucherService voucherService = new VoucherService();

	@Test
	void test_addVoucher() {
		int iniSize = VoucherService.getVouchers().size();
		voucherService.addVoucher("666666666",2,LocalDate.now().plusDays(10));

		assertThat(VoucherService.getVouchers()).hasSize(iniSize + 1);
		assertThat(VoucherService.getVouchers()).anyMatch(v -> v.getCode().equals("666666666") && v.getMaxRedemptions() == 2);
	}

	@Test
	void test_findByCode(){
		voucherService.addVoucher("777777777",1,LocalDate.now().plusDays(10));
		Voucher found = voucherService.findByCode("777777777");

		assertEquals(1,found.getMaxRedemptions());
	}
}
