package com.ipsen.spine.controller.vo;

import jakarta.validation.constraints.NotNull;


public class PlatformForm {
    @NotNull
    public String platformName;
    @NotNull
    public boolean status;

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
