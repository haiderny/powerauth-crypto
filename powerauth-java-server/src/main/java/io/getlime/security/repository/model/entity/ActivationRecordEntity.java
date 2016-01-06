/**
 * Copyright 2015 Lime - HighTech Solutions s.r.o.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.getlime.security.repository.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.getlime.security.repository.model.ActivationStatus;
import io.getlime.security.repository.model.ActivationStatusConverter;

@Entity(name = "pa_activation")
public class ActivationRecordEntity implements Serializable {

    private static final long serialVersionUID = 7512286634644851705L;

    @Id
    @Column(name = "activation_id", length = 37)
    private String activationId;

    @Column(name = "activation_id_short", nullable = false, updatable = false)
    private String activationIdShort;

    @Column(name = "activation_otp", nullable = false, updatable = false)
    private String activationOTP;

    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;

    @Column(name = "activation_name", nullable = true)
    private String activationName;
    
    @Column(name = "extras", nullable = true)
    private String extras;

    @Column(name = "server_private_key_base64", nullable = false)
    private String serverPrivateKeyBase64;

    @Column(name = "server_public_key_base64", nullable = false)
    private String serverPublicKeyBase64;

    @Column(name = "device_public_key_base64", nullable = true)
    private String devicePublicKeyBase64;

    @Column(name = "counter", nullable = false)
    private Long counter;

    @Column(name = "failed_attempts", nullable = false)
    private Long failedAttempts;

    @Column(name = "timestamp_created", nullable = false)
    private Date timestampCreated;

    @Column(name = "timestamp_last_used", nullable = false)
    private Date timestampLastUsed;

    @Column(name = "activation_status", nullable = false)
    @Convert(converter = ActivationStatusConverter.class)
    private ActivationStatus activationStatus;

    @ManyToOne
    @JoinColumn(name = "master_keypair_id", referencedColumnName = "id", nullable = false)
    private MasterKeyPairEntity masterKeypair;

    protected ActivationRecordEntity() {
    }

    public ActivationRecordEntity(
            String activationId,
            String activationIdShort,
            String activationOTP,
            String userId,
            String activationName,
            String extras,
            String serverPrivateKeyBase64,
            String serverPublicKeyBase64,
            String devicePublicKeyBase64,
            Long counter,
            Long failedAttempts,
            Date timestampCreated,
            Date timestampLastUsed,
            ActivationStatus status,
            MasterKeyPairEntity masterKeypair) {
        this.activationId = activationId;
        this.activationIdShort = activationIdShort;
        this.activationOTP = activationOTP;
        this.userId = userId;
        this.activationName = activationName;
        this.extras = extras;
        this.serverPrivateKeyBase64 = serverPrivateKeyBase64;
        this.serverPublicKeyBase64 = serverPublicKeyBase64;
        this.devicePublicKeyBase64 = devicePublicKeyBase64;
        this.counter = counter;
        this.failedAttempts = failedAttempts;
        this.timestampCreated = timestampCreated;
        this.timestampLastUsed = timestampLastUsed;
        this.activationStatus = status;
        this.masterKeypair = masterKeypair;
    }

    public String getActivationId() {
        return activationId;
    }

    public void setActivationId(String activationId) {
        this.activationId = activationId;
    }

    public String getActivationIdShort() {
        return activationIdShort;
    }

    public void setActivationIdShort(String activationIdShort) {
        this.activationIdShort = activationIdShort;
    }

    public String getActivationOTP() {
        return activationOTP;
    }

    public void setActivationOTP(String activationOTP) {
        this.activationOTP = activationOTP;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivationName() {
		return activationName;
	}
    
    public void setActivationName(String activationName) {
		this.activationName = activationName;
	}
    
    public String getExtras() {
		return extras;
	}
    
    public void setExtras(String extras) {
		this.extras = extras;
	}

    public String getServerPrivateKeyBase64() {
        return serverPrivateKeyBase64;
    }

    public void setServerPrivateKeyBase64(String serverPrivateKeyBase64) {
        this.serverPrivateKeyBase64 = serverPrivateKeyBase64;
    }

    public String getServerPublicKeyBase64() {
        return serverPublicKeyBase64;
    }

    public void setServerPublicKeyBase64(String serverPublicKeyBase64) {
        this.serverPublicKeyBase64 = serverPublicKeyBase64;
    }

    public String getDevicePublicKeyBase64() {
        return devicePublicKeyBase64;
    }

    public void setDevicePublicKeyBase64(String devicePublicKeyBase64) {
        this.devicePublicKeyBase64 = devicePublicKeyBase64;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public Long getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Long failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public Date getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public Date getTimestampLastUsed() {
        return timestampLastUsed;
    }

    public void setTimestampLastUsed(Date timestampLastUsed) {
        this.timestampLastUsed = timestampLastUsed;
    }

    public ActivationStatus getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationStatus activationStatus) {
        this.activationStatus = activationStatus;
    }

    public MasterKeyPairEntity getMasterKeypair() {
        return masterKeypair;
    }

    public void setMasterKeypair(MasterKeyPairEntity masterKeypair) {
        this.masterKeypair = masterKeypair;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.activationId);
        hash = 71 * hash + Objects.hashCode(this.activationIdShort);
        hash = 71 * hash + Objects.hashCode(this.activationOTP);
        hash = 71 * hash + Objects.hashCode(this.userId);
        hash = 71 * hash + Objects.hashCode(this.activationName);
        hash = 71 * hash + Objects.hashCode(this.serverPrivateKeyBase64);
        hash = 71 * hash + Objects.hashCode(this.serverPublicKeyBase64);
        hash = 71 * hash + Objects.hashCode(this.devicePublicKeyBase64);
        hash = 71 * hash + Objects.hashCode(this.counter);
        hash = 71 * hash + Objects.hashCode(this.failedAttempts);
        hash = 71 * hash + Objects.hashCode(this.timestampCreated);
        hash = 71 * hash + Objects.hashCode(this.timestampLastUsed);
        hash = 71 * hash + Objects.hashCode(this.activationStatus);
        hash = 71 * hash + Objects.hashCode(this.masterKeypair);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ActivationRecordEntity other = (ActivationRecordEntity) obj;
        if (!Objects.equals(this.activationIdShort, other.activationIdShort)) {
            return false;
        }
        if (!Objects.equals(this.activationOTP, other.activationOTP)) {
            return false;
        }
        if (!Objects.equals(this.userId, other.userId)) {
            return false;
        }
        if (!Objects.equals(this.activationName, other.activationName)) {
            return false;
        }
        if (!Objects.equals(this.activationId, other.activationId)) {
            return false;
        }
        if (!Objects.equals(this.serverPrivateKeyBase64, other.serverPrivateKeyBase64)) {
            return false;
        }
        if (!Objects.equals(this.serverPublicKeyBase64, other.serverPublicKeyBase64)) {
            return false;
        }
        if (!Objects.equals(this.devicePublicKeyBase64, other.devicePublicKeyBase64)) {
            return false;
        }
        if (!Objects.equals(this.counter, other.counter)) {
            return false;
        }
        if (!Objects.equals(this.failedAttempts, other.failedAttempts)) {
            return false;
        }
        if (!Objects.equals(this.timestampCreated, other.timestampCreated)) {
            return false;
        }
        if (!Objects.equals(this.timestampLastUsed, other.timestampLastUsed)) {
            return false;
        }
        if (this.activationStatus != other.activationStatus) {
            return false;
        }
        if (!Objects.equals(this.masterKeypair, other.masterKeypair)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ActivationRecordEntity{"
                + "activationId=" + activationId
                + ", activationIdShort=" + activationIdShort
                + ", activationOTP=" + activationOTP
                + ", userId=" + userId
                + ", clientName=" + activationName
                + ", serverPrivateKeyBase64=" + serverPrivateKeyBase64
                + ", serverPublicKeyBase64=" + serverPublicKeyBase64
                + ", devicePublicKeyBase64=" + devicePublicKeyBase64
                + ", counter=" + counter
                + ", failedAttempts=" + failedAttempts
                + ", timestampCreated=" + timestampCreated
                + ", timestampLastUsed=" + timestampLastUsed
                + ", status=" + activationStatus
                + ", masterKeypair=" + masterKeypair
                + '}';
    }

}
