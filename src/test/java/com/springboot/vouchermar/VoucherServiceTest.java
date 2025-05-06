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
		voucherService.addVoucher("111111111",2,LocalDate.now().plusDays(10));

		assertThat(VoucherService.getVouchers()).hasSize(iniSize + 1);
		assertThat(VoucherService.getVouchers()).anyMatch(v -> v.getCode().equals("111111111") && v.getMaxRedemptions() == 2);
	}

	@Test
	void test_findByCode(){
		voucherService.addVoucher("222222222",1,LocalDate.now().plusDays(10));
		Voucher found = voucherService.findByCode("222222222");

		assertEquals(1,found.getMaxRedemptions());
	}
}
