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
package io.getlime.security.powerauth.crypto.lib.util;

import com.google.common.io.BaseEncoding;
import io.getlime.security.powerauth.crypto.lib.config.PowerAuthConfiguration;
import io.getlime.security.powerauth.crypto.lib.generator.KeyGenerator;
import io.getlime.security.powerauth.provider.CryptoProviderUtil;
import io.getlime.security.powerauth.provider.CryptoProviderUtilFactory;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.ECPrivateKey;

import static org.junit.Assert.assertEquals;

/**
 * Test for key conversion utilities
 * 
 * @author Petr Dvorak
 * 
 */
public class KeyConversionUtilsTest {

	/**
	 * Default constructor
	 */
	public KeyConversionUtilsTest() {
	}

	/**
	 * Set up crypto providers
	 */
	@Before
	public void setUp() {
		// Add Bouncy Castle Security Provider
		Security.addProvider(new BouncyCastleProvider());
		PowerAuthConfiguration.INSTANCE.setKeyConvertor(CryptoProviderUtilFactory.getCryptoProviderUtils());
	}

	/**
	 * Test of convertPublicKey method, of class KeyConversionUtils.
	 * 
	 * @throws Exception In case test fails 
	 *
	 */
	@Test
	public void testConvertPublicKey() throws Exception {
		System.out.println("convertPublicKeyToBytes");
		KeyGenerator keyGenerator = new KeyGenerator();
		CryptoProviderUtil instance = PowerAuthConfiguration.INSTANCE.getKeyConvertor();

		PublicKey key = instance.convertBytesToPublicKey(BaseEncoding.base64().decode("AsUaehWpuZseHUprd9immCELf62TTtHUGlTIXyCxY7h2"));

		for (int i = 0; i < 1000; i++) {
			KeyPair kp = keyGenerator.generateKeyPair();

			PublicKey publicKey = kp.getPublic();
			byte[] originalBytes = instance.convertPublicKeyToBytes(publicKey);
			String originalBase64 = BaseEncoding.base64().encode(originalBytes);
			byte[] decodedBytes = BaseEncoding.base64().decode(originalBase64);
			PublicKey decodedPublicKey = instance.convertBytesToPublicKey(decodedBytes);
			assertEquals(publicKey, decodedPublicKey);

			PrivateKey privateKey = kp.getPrivate();
			byte[] originalPrivateBytes = instance.convertPrivateKeyToBytes(privateKey);
			String originalPrivateBase64 = BaseEncoding.base64().encode(originalPrivateBytes);
			byte[] decodedPrivateBytes = BaseEncoding.base64().decode(originalPrivateBase64);
			PrivateKey decodedPrivateKey = instance.convertBytesToPrivateKey(decodedPrivateBytes);
			assertEquals(privateKey, decodedPrivateKey);

			KeyFactory kf = KeyFactory.getInstance("ECDH", PowerAuthConfiguration.INSTANCE.getKeyConvertor().getProviderName());
			BigInteger keyInteger = new BigInteger("" + (12 * i));
			ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
			ECPrivateKeySpec pubSpec = new ECPrivateKeySpec(keyInteger, ecSpec);
			ECPrivateKey privateKey2 = (ECPrivateKey) kf.generatePrivate(pubSpec);
			originalPrivateBytes = instance.convertPrivateKeyToBytes(privateKey2);
			originalPrivateBase64 = BaseEncoding.base64().encode(originalPrivateBytes);
			decodedPrivateBytes = BaseEncoding.base64().decode(originalPrivateBase64);
			PrivateKey decodedPrivateKey2 = instance.convertBytesToPrivateKey(decodedPrivateBytes);
			assertEquals(privateKey2, decodedPrivateKey2);
		}

	}

}
