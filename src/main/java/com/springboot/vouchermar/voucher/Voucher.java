package com.springboot.vouchermar.voucher;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true,nullable = false)
    @Size(min = 9,max = 9,message = "Enter 9 characters")
    private String code;
    private int maxRedemptions;
    private int currentRedemptions = 0;
    private LocalDate expiryDate;
    private boolean active = true;

    public Voucher(){

    }

    public Voucher(int id, String code, int maxRedemptions, LocalDate expiryDate) {
        this.id = id;
        this.code = code;
        this.maxRedemptions = maxRedemptions;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMaxRedemptions() {
        return maxRedemptions;
    }

    public void setMaxRedemptions(int maxRedemptions) {
        this.maxRedemptions = maxRedemptions;
    }

    public int getCurrentRedemptions() {
        return currentRedemptions;
    }

    public void setCurrentRedemptions(int currentRedemptions) {
        this.currentRedemptions = currentRedemptions;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Voucher{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", maxRedemptions=" + maxRedemptions +
                ", currentRedemptions=" + currentRedemptions +
                ", expiryDate=" + expiryDate +
                ", active=" + active +
                '}';
    }
}
