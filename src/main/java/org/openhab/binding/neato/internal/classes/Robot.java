/**
 * Copyright (c) 2014-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.neato.internal.classes;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Patrik Wimnell - Initial contribution
 * @author Florian Dietrich - Vendor added
 */

public class Robot {

    @SerializedName("serial")
    @Expose
    private String serial;
    @SerializedName("prefix")
    @Expose
    private Object prefix;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("secret_key")
    @Expose
    private String secretKey;
    @SerializedName("purchased_at")
    @Expose
    private Object purchasedAt;
    @SerializedName("linked_at")
    @Expose
    private String linkedAt;
    @SerializedName("traits")
    @Expose
    private List<Object> traits = null;
    @SerializedName("proof_of_purchase_url")
    @Expose
    private Object proofOfPurchaseUrl;
    @SerializedName("proof_of_purchase_url_valid_for_seconds")
    @Expose
    private Integer proofOfPurchaseUrlValidForSeconds;
    @SerializedName("proof_of_purchase_generated_at")
    @Expose
    private Object proofOfPurchaseGeneratedAt;
    @SerializedName("mac_address")
    @Expose
    private String macAddress;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Object getPrefix() {
        return prefix;
    }

    public void setPrefix(Object prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Object getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(Object purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public String getLinkedAt() {
        return linkedAt;
    }

    public void setLinkedAt(String linkedAt) {
        this.linkedAt = linkedAt;
    }

    public List<Object> getTraits() {
        return traits;
    }

    public void setTraits(List<Object> traits) {
        this.traits = traits;
    }

    public Object getProofOfPurchaseUrl() {
        return proofOfPurchaseUrl;
    }

    public void setProofOfPurchaseUrl(Object proofOfPurchaseUrl) {
        this.proofOfPurchaseUrl = proofOfPurchaseUrl;
    }

    public Integer getProofOfPurchaseUrlValidForSeconds() {
        return proofOfPurchaseUrlValidForSeconds;
    }

    public void setProofOfPurchaseUrlValidForSeconds(Integer proofOfPurchaseUrlValidForSeconds) {
        this.proofOfPurchaseUrlValidForSeconds = proofOfPurchaseUrlValidForSeconds;
    }

    public Object getProofOfPurchaseGeneratedAt() {
        return proofOfPurchaseGeneratedAt;
    }

    public void setProofOfPurchaseGeneratedAt(Object proofOfPurchaseGeneratedAt) {
        this.proofOfPurchaseGeneratedAt = proofOfPurchaseGeneratedAt;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
