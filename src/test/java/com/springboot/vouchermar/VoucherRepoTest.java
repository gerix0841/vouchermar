package com.springboot.vouchermar;

import com.springboot.vouchermar.voucher.Voucher;
import com.springboot.vouchermar.voucher.VoucherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoucherRepoTest {

	@Autowired
	private VoucherRepository repository;

	@Test
	void test_saveAndFindById() {
		Voucher voucher = new Voucher(0,"111111111",3, LocalDate.now().plusDays(30));
		Voucher savedVoucher = repository.save(voucher);

		Optional<Voucher> found = repository.findById(savedVoucher.getId());
		assertEquals("111111111",found.get().getCode());
	}

	@Test
	void test_findByCode(){
		Voucher voucher = new Voucher(0, "222222222", 5, LocalDate.now().plusDays(30));
		repository.save(voucher);

		Optional<Voucher> found = repository.findByCode("222222222");
		assertEquals(5,found.get().getMaxRedemptions());
	}

	@Test
	void test_DeleteVoucher(){
		Voucher voucher = new Voucher(0, "222222222", 5, LocalDate.now().plusDays(30));
		Voucher savedVoucher = repository.save(voucher);

		repository.deleteById(savedVoucher.getId());
		Optional<Voucher> found = repository.findById(savedVoucher.getId());
		assertThat(found).isNotPresent();
	}
}
