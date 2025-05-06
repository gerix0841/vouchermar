package com.springboot.vouchermar.voucher;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class VoucherService {
    private static List<Voucher> vouchers = new ArrayList<>();
    private static int vouchersCount = 0;
    private VoucherRepository voucherRepository;

    public VoucherService() {

    }

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    static {
        vouchers.add(new Voucher(++vouchersCount,"000000001",1,null));
        vouchers.add(new Voucher(++vouchersCount,"000000002",5,null));
        vouchers.add(new Voucher(++vouchersCount,"000000003",1, LocalDate.now().plusYears(1)));
    }

    public static List<Voucher> getVouchers() {
        return vouchers.stream().toList();
    }

    public void deleteById(int id){
        Predicate<? super Voucher> predicate = voucher -> voucher.getId() == id;
        vouchers.removeIf(predicate);
    }

    public void addVoucher(String code,int maxRedemptions,LocalDate expiryDate){
        Voucher voucher = new Voucher(++vouchersCount,code,maxRedemptions,expiryDate);
        boolean isLimitReached = voucher.getMaxRedemptions() <= voucher.getCurrentRedemptions();
        boolean isExpired = voucher.getExpiryDate() != null && voucher.getExpiryDate().isBefore(LocalDate.now());
        if(isLimitReached || isExpired){
            voucher.setActive(false);
        }
        vouchers.add(voucher);
    }

    public Voucher findById(int id){
        Predicate<? super Voucher> predicate = voucher -> voucher.getId() == id;
        return vouchers.stream().filter(predicate).findFirst().get();
    }

    public void updateVoucher(@Valid Voucher voucher){
        deleteById(voucher.getId());
        boolean isLimitReached = voucher.getMaxRedemptions() <= voucher.getCurrentRedemptions();
        boolean isExpired = voucher.getExpiryDate() != null && voucher.getExpiryDate().isBefore(LocalDate.now());
        if(isLimitReached || isExpired){
            voucher.setActive(false);
        }
        vouchers.add(voucher);
    }

    public Voucher findByCode(String code){
        return vouchers.stream().filter(v -> v.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
    }

    public boolean isCodeUnique(String code) {
        return voucherRepository.findByCode(code) == null;
    }
}
