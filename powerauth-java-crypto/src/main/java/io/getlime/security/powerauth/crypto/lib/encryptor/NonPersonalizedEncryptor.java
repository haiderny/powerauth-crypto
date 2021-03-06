/*
 * Copyright 2017 Lime - HighTech Solutions s.r.o.
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
package io.getlime.security.powerauth.crypto.lib.encryptor;

import io.getlime.security.powerauth.crypto.lib.config.PowerAuthConfiguration;
import io.getlime.security.powerauth.crypto.lib.encryptor.model.NonPersonalizedEncryptedMessage;
import io.getlime.security.powerauth.crypto.lib.generator.KeyGenerator;
import io.getlime.security.powerauth.crypto.lib.util.AESEncryptionUtils;
import io.getlime.security.powerauth.crypto.lib.util.HMACHashUtilities;
import io.getlime.security.powerauth.provider.CryptoProviderUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for encrypting / decrypting data using non-personalized encryption
 * as documented in PowerAuth 2.0 E2EE documentation.
 *
 * @author Petr Dvorak, petr@lime-company.eu
 */
public class NonPersonalizedEncryptor {

    private static final int MAX_ATTEMPT_COUNT = 1000;

    private byte[] applicationKey;
    private byte[] sessionIndex;
    private byte[] sessionRelatedSecretKey;
    private byte[] ephemeralPublicKey;

    // Create new working objects
    private AESEncryptionUtils aes = new AESEncryptionUtils();
    private KeyGenerator generator = new KeyGenerator();
    private HMACHashUtilities hmac = new HMACHashUtilities();
    private CryptoProviderUtil keyConversion = PowerAuthConfiguration.INSTANCE.getKeyConvertor();

    /**
     * Create a new encryptor using provided applicationKey, application master server public key and session index.
     * @param applicationKey Application key.
     * @param sessionRelatedSecretKey Session related derived key.
     * @param sessionIndex Session index used for key derivation.
     * @param ephemeralPublicKeyString Ephemeral public key
     */
    public NonPersonalizedEncryptor(byte[] applicationKey, byte[] sessionRelatedSecretKey, byte[] sessionIndex, byte[] ephemeralPublicKeyString) {
        this.applicationKey = applicationKey;
        this.sessionIndex = sessionIndex;
        this.sessionRelatedSecretKey = sessionRelatedSecretKey;
        this.ephemeralPublicKey = ephemeralPublicKeyString;
    }

    /**
     * Encrypt original data using components in this encryptor.
     * @param originalData Data to be encrypted.
     * @return Message object with encrypted data.
     */
    public NonPersonalizedEncryptedMessage encrypt(byte[] originalData) {
        try {
            byte[] adHocIndex = generator.generateRandomBytes(16);
            byte[] macIndex = generator.generateRandomBytes(16);

            // make sure the indexes are different
            int attemptCount = 0;
            while (Arrays.equals(adHocIndex, macIndex)) {
                macIndex = generator.generateRandomBytes(16);
                if (attemptCount < MAX_ATTEMPT_COUNT) { // make sure that there is no issue with random data generator
                    attemptCount++;
                } else {
                    return null;
                }
            }

            byte[] nonce = generator.generateRandomBytes(16);

            SecretKey sessionKey = keyConversion.convertBytesToSharedSecretKey(this.sessionRelatedSecretKey);
            SecretKey encryptionKey = generator.deriveSecretKeyHmac(sessionKey, adHocIndex);
            SecretKey macKey = generator.deriveSecretKeyHmac(sessionKey, macIndex);

            byte[] encryptedData = aes.encrypt(originalData, nonce, encryptionKey);
            byte[] mac = hmac.hash(macKey, encryptedData);

            NonPersonalizedEncryptedMessage message = new NonPersonalizedEncryptedMessage();
            message.setApplicationKey(applicationKey);
            message.setEphemeralPublicKey(ephemeralPublicKey);
            message.setSessionIndex(sessionIndex);
            message.setAdHocIndex(adHocIndex);
            message.setMacIndex(macIndex);
            message.setNonce(nonce);
            message.setEncryptedData(encryptedData);
            message.setMac(mac);

            return message;
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(NonPersonalizedEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Decrypt the encrypted message from the message payload using this encryptor.
     * @param message Message object to be decrypted.
     * @return Original decrypted bytes.
     */
    public byte[] decrypt(NonPersonalizedEncryptedMessage message) {

        try {
            byte[] adHocIndex = message.getAdHocIndex();
            byte[] macIndex = message.getMacIndex();

            // make sure the indexes are different
            if (Arrays.equals(adHocIndex, macIndex)) {
                return null;
            }

            byte[] nonce = message.getNonce();

            SecretKey sessionKey = keyConversion.convertBytesToSharedSecretKey(this.sessionRelatedSecretKey);
            SecretKey encryptionKey = generator.deriveSecretKeyHmac(sessionKey, adHocIndex);
            SecretKey macKey = generator.deriveSecretKeyHmac(sessionKey, macIndex);

            byte[] encryptedData = message.getEncryptedData();

            byte[] macExpected = hmac.hash(macKey, encryptedData);
            byte[] mac = message.getMac();

            // make sure the macs are the same
            if (!Arrays.equals(mac, macExpected)) {
                return null;
            }

            return aes.decrypt(encryptedData, nonce, encryptionKey);

        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(NonPersonalizedEncryptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public byte[] getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(byte[] applicationKey) {
        this.applicationKey = applicationKey;
    }

    public byte[] getSessionIndex() {
        return sessionIndex;
    }

    public void setSessionIndex(byte[] sessionIndex) {
        this.sessionIndex = sessionIndex;
    }

    public byte[] getEphemeralPublicKey() {
        return ephemeralPublicKey;
    }

    public void setEphemeralPublicKey(byte[] ephemeralPublicKey) {
        this.ephemeralPublicKey = ephemeralPublicKey;
    }

    public byte[] getSessionRelatedSecretKey() {
        return sessionRelatedSecretKey;
    }

    public void setSessionRelatedSecretKey(byte[] sessionRelatedSecretKey) {
        this.sessionRelatedSecretKey = sessionRelatedSecretKey;
    }
}
