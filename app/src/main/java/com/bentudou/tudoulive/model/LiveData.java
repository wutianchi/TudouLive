package com.bentudou.tudoulive.model;

/**
 * Created by lzz on 2016/9/20.
 */
public class LiveData {
    private int liveStreamServiceId;
    private int warehouseId;
    private int isEnable;
    private String cameraLinkAddress;
    private String cameraCode;
    private String cameraCoverage;
    private String warehouseName;
    private String cameraIcon;

    public String getCameraIcon() {
        return cameraIcon;
    }

    public void setCameraIcon(String cameraIcon) {
        this.cameraIcon = cameraIcon;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getLiveStreamServiceId() {
        return liveStreamServiceId;
    }

    public void setLiveStreamServiceId(int liveStreamServiceId) {
        this.liveStreamServiceId = liveStreamServiceId;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public String getCameraLinkAddress() {
        return cameraLinkAddress;
    }

    public void setCameraLinkAddress(String cameraLinkAddress) {
        this.cameraLinkAddress = cameraLinkAddress;
    }

    public String getCameraCode() {
        return cameraCode;
    }

    public void setCameraCode(String cameraCode) {
        this.cameraCode = cameraCode;
    }

    public String getCameraCoverage() {
        return cameraCoverage;
    }

    public void setCameraCoverage(String cameraCoverage) {
        this.cameraCoverage = cameraCoverage;
    }
}
