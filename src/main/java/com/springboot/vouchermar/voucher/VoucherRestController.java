package com.springboot.vouchermar.voucher;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherRestController {

    private final VoucherRepository voucherRepository;

    public VoucherRestController(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }
//-------------------------------------------------------------
    // List Vouchers
    @GetMapping
    public List<Voucher> getAllVouchers(){
        return voucherRepository.findAll();
    }

    // Get Voucher by ID
    @GetMapping("/{id}")
    public Optional<Voucher> getVoucherById(@PathVariable int id){
        return voucherRepository.findById(id);
    }

    // Create new Voucher
    @PostMapping
    public Voucher createVoucher(@RequestBody Voucher voucher){
        return voucherRepository.save(voucher);
    }

    // Update Voucher
    @PutMapping("/{id}")
    public Voucher updateVoucher(@PathVariable int id, @RequestBody Voucher voucherDetails){
        Voucher voucher = voucherRepository.findById(id).orElseThrow();
        voucher.setCode(voucherDetails.getCode());
        voucher.setMaxRedemptions(voucherDetails.getMaxRedemptions());
        voucher.setExpiryDate(voucherDetails.getExpiryDate());
        voucher.setActive(voucherDetails.isActive());
        return voucherRepository.save(voucher);
    }

    // Delete Voucher
    @PostMapping("/{id}")
    public void deleteVoucher(@PathVariable int id){
        voucherRepository.deleteById(id);
    }

    // Toggle Voucher
    @PostMapping("/{id}/toggle")
    public Voucher disableVoucher(@PathVariable int id){
        Voucher voucher = voucherRepository.findById(id).orElseThrow();
        voucher.setActive(!voucher.isActive());
        return voucherRepository.save(voucher);
    }

    // Redeem Voucher
    @PostMapping("/redeem")
    public String redeemVoucher(@RequestParam String code){
        Voucher voucher = voucherRepository.findByCode(code).orElse(null);
        if(voucher == null){
            return "Voucher code not found";
        }

        boolean isExpired = voucher.getExpiryDate().isBefore(LocalDate.now());
        boolean isLimitReached = voucher.getMaxRedemptions() <= voucher.getCurrentRedemptions();
        if(!voucher.isActive() || isExpired || isLimitReached){
            return "Voucher is not valid";
        }

        voucher.setCurrentRedemptions(voucher.getCurrentRedemptions()+1);
        if(voucher.getMaxRedemptions() <= voucher.getCurrentRedemptions()){
            voucher.setActive(false);
        }

        voucherRepository.save(voucher);
        return "Voucher redeemed successfully!";
    }
}
