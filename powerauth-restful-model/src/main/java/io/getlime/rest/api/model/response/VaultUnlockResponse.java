/*
 * Copyright 2016 Lime - HighTech Solutions s.r.o.
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
package io.getlime.rest.api.model.response;

/**
 * Response object for /pa/vault/unlock end-point.
 *
 * @author Petr Dvorak
 *
 */
public class VaultUnlockResponse {

    private String activationId;
    private String encryptedVaultEncryptionKey;

    /**
     * Get activation ID
     * @return Activation ID
     */
    public String getActivationId() {
        return activationId;
    }

    /**
     * Set activation ID
     * @param activationId Activation ID
     */
    public void setActivationId(String activationId) {
        this.activationId = activationId;
    }

    /**
     * Get encrypted vault encryption key (using a key derived from the master transport key).
     * @return Encrypted vault encryption key.
     */
    public String getEncryptedVaultEncryptionKey() {
        return encryptedVaultEncryptionKey;
    }

    /**
     * Set encrypted vault encryption key (using a key derived from the master transport key).
     * @param encryptedVaultEncryptionKey Encrypted vault encryption key.
     */
    public void setEncryptedVaultEncryptionKey(String encryptedVaultEncryptionKey) {
        this.encryptedVaultEncryptionKey = encryptedVaultEncryptionKey;
    }

}
