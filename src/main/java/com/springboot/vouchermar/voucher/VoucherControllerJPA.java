package com.springboot.vouchermar.voucher;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class VoucherControllerJPA {

    private VoucherRepository voucherRepository;
    private VoucherService voucherService;

    public VoucherControllerJPA(VoucherRepository voucherRepository, VoucherService voucherService) {
        this.voucherRepository = voucherRepository;
        this.voucherService = voucherService;
    }
//-------------------------------------------------------------
    // Manage Vouchers
    @RequestMapping("manage")
    public String listAllVouchers(ModelMap modelMap){
        String username = getLoggedInUsername(modelMap);
        if(username.equals("admin")){
            List<Voucher> vouchers = voucherRepository.findAll();
            modelMap.put("vouchers",vouchers);
            return "manage";
        }
        return "home";
    }
//-------------------------------------------------------------
    // Delete Voucher
    @RequestMapping("delete-voucher")
    public String deleteVoucher(@RequestParam int id){
        voucherRepository.deleteById(id);
        return "redirect:manage";
    }
//-------------------------------------------------------------
    // Disable Voucher
    @RequestMapping("toggle-voucher")
    public String toggleVoucher(@RequestParam int id){
        Voucher voucher = voucherRepository.findById(id).orElse(null);
        if(voucher != null){
            voucher.setActive(!voucher.isActive());
            voucherRepository.save(voucher);
        }
        return "redirect:manage";
    }
//-------------------------------------------------------------
    // Add Voucher
    @RequestMapping(value = "add-voucher",method = RequestMethod.GET)
    public String showAddVoucherPage(ModelMap modelMap){
        String username = getLoggedInUsername(modelMap);
        if(!username.equalsIgnoreCase("admin")){
            return "home";
        }

        Voucher voucher = new Voucher(0,"000000000",1, LocalDate.now().plusYears(1));
        modelMap.put("voucher",voucher);
        return "voucher";
    }

    @RequestMapping(value = "add-voucher",method = RequestMethod.POST)
    public String addNewVoucher(ModelMap modelMap, @Valid Voucher voucher, BindingResult result){
        if(result.hasErrors()){
            return "voucher";
        }

        Voucher existingVoucher = voucherRepository.findByCode(voucher.getCode()).orElse(null);
        if (existingVoucher != null) {
            modelMap.addAttribute("error", "Voucher code already exists!");
            return "voucher";
        }

        voucherRepository.save(voucher);
        //voucherService.addVoucher(voucher.getCode(),voucher.getMaxRedemptions(),voucher.getExpiryDate());
        return "redirect:manage";
    }
//-------------------------------------------------------------
    // Update Voucher
    @RequestMapping(value = "update-voucher",method = RequestMethod.GET)
    public String showUpdateVoucherPage(@RequestParam int id,ModelMap modelMap){
        String username = getLoggedInUsername(modelMap);
        if(!username.equalsIgnoreCase("admin")){
            return "home";
        }

        Voucher voucher = voucherRepository.findById(id).get();
        modelMap.addAttribute("voucher",voucher);
        return "voucher";
    }

    @RequestMapping(value = "update-voucher",method = RequestMethod.POST)
    public String updateVoucher(ModelMap modelMap, @Valid Voucher voucher, BindingResult result){
        if(result.hasErrors()){
            return "voucher";
        }

        Voucher existingVoucher = voucherRepository.findByCode(voucher.getCode()).orElse(null);
        if (existingVoucher != null && existingVoucher.getId() != voucher.getId()) {
            modelMap.addAttribute("error", "Voucher code already exists!");
            return "voucher";
        }

        voucherRepository.save(voucher);
        return "redirect:manage";
    }

//-------------------------------------------------------------
    // Redeem Vouchers
    @RequestMapping(value = "redeem",method = RequestMethod.GET)
    public String showRedeemVoucherPage(){
        return "redeem";
    }

    @RequestMapping(value = "redeem",method = RequestMethod.POST)
    public String redeemVoucher(@RequestParam String code, ModelMap modelMap){
        // Find Voucher by code
        Voucher voucher = voucherRepository.findByCode(code).orElse(null);
        if(voucher == null){
            modelMap.addAttribute("message","Voucher code not found");
            return "redeemResult";
        }

        // Check for activity
        boolean isExpired = voucher.getExpiryDate() != null && voucher.getExpiryDate().isBefore(LocalDate.now());
        boolean isLimitReached = voucher.getMaxRedemptions() <= voucher.getCurrentRedemptions();
        if(!voucher.isActive() || isExpired || isLimitReached){
            modelMap.addAttribute("message","Voucher is not valid");
            return "redeemResult";
        }

        // Redeem Voucher
        voucher.setCurrentRedemptions(voucher.getCurrentRedemptions()+1);

        // Deactivate if needed
        if(voucher.getMaxRedemptions() <= voucher.getCurrentRedemptions()){
            voucher.setActive(false);
        }

        voucherRepository.save(voucher);
        modelMap.addAttribute("message","Voucher redeemed successfully!");
        return "redeemResult";
    }

//-------------------------------------------------------------
    private String getLoggedInUsername(ModelMap model) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();

    }
}
