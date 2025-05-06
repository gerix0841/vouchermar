package com.springboot.vouchermar.voucher;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher,Integer> {
    Optional<Voucher> findByCode(String code);
}
