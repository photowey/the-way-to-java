/*
 * Copyright © 2021 photowey (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.crypto.in.action.aes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.photowey.crypto.in.action.base64.Base64Utils;
import com.photowey.crypto.in.action.property.SecurityConfigProperties;
import com.photowey.crypto.in.action.reader.ClassPathReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * {@code DataSecurityHandler}
 * {@code AES256} 处理器
 *
 * @author photowey
 * @date 2022/03/03
 * @since 1.0.0
 */
@Slf4j
public class DataSecurityHandler {

    protected final SecurityConfigProperties securityConfigProperties;

    public DataSecurityHandler(SecurityConfigProperties securityConfigProperties) {
        this.securityConfigProperties = securityConfigProperties;
    }

    protected static final String CIPHER_AES_ECB_PKCS7 = "AES/ECB/PKCS7Padding";
    protected static final String SHA_WITH_RSA = "SHA1withRSA";
    protected static final String RSA = "RSA";
    protected static final String X_509 = "X.509";
    protected static final String AES = "AES";
    protected static final String PROVIDER_BC = "BC";
    protected static final String JKS = "JKS";

    /**
     * 初始化
     */
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    // ================================================= ORDER

    /**
     * 将报文参数进行排序
     *
     * @param parameters 参数
     * @return 签名原文
     */
    public String order(JSONObject parameters) {
        Map<String, Object> paramMap = this.sortJsonObject(parameters);
        String sign = paramMap.toString();
        sign = sign.substring(1, sign.length() - 1).replaceAll(", ", "&");
        if (log.isDebugEnabled()) {
            log.debug("encrypt::the sign order result is:[{}]", sign);
        }

        return sign;
    }

    // ================================================= PRIVATE-KEY

    /**
     * 从 KeyStore 中加载私钥 自己也可以保存密钥
     * <p>
     * 生成证书 KeyStore
     * keytool -genkey -alias mykey -keyalg RSA -keystore ${your.path}/mykeystore.keystore -keysize 2048 -validity 36500
     * 查看
     * keytool -list -keystore ${your.path}/mykeystore.keystore
     * 导出公钥证书
     * keytool -export -alias mykey -keystore ${your.path}/mykeystore.keystore -file mypublickey.crt
     * <p>
     * keytool -genkey -alias encrypt -keypass encrypt -keyalg RSA -keysize 2048 -validity 36500 -keystore ${your.path}/encrypt_server.keystore -storepass encrypt
     * keytool -export -alias encrypt -keystore ${your.path}/encrypt_server.keystore -file encrypt_server.crt
     * <p>
     * # 示例
     * <pre>
     * $ keytool -genkey -alias encrypt -keypass encrypt -keyalg RSA -keysize 2048 -validity 36500 -keystore ${your.path}/encrypt_server.jks -storepass encrypt
     * $ keytool -export -alias encrypt -keystore ${your.path}/encrypt_server.jks -file encrypt_server.crt
     *
     * # 格式处理
     * keytool -importkeystore -srckeystore ${your.path}/encrypt_server.jks -destkeystore ${your.path}/encrypt_server.jks -deststoretype pkcs12
     * </pre>
     *
     * @return 私钥
     * @throws SecurityException
     */
    @Deprecated
    public PrivateKey loadPrivateKeyFromKeyStore() throws SecurityException {
        return loadPrivateKeyFromKeyStore("encrypt", "encrypt", "encrypt", this.securityConfigProperties.getKeyStorePath());
    }

    public PrivateKey loadPrivateKeyFromKeyStore(String storePassword, String alias, String keyPassword, String path) throws SecurityException {
        try {
            InputStream secret = ClassPathReader.read(path);
            KeyStore keyStore = KeyStore.getInstance(JKS);
            keyStore.load(secret, storePassword.toCharArray());
            secret.close();

            return (PrivateKey) keyStore.getKey(alias, keyPassword.toCharArray());
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    public PrivateKey privateKey(String privateKeyPath) throws Exception {
        String keyStr = ClassPathReader.joinRead(privateKeyPath);
        return this.loadPrivateKeyForString(keyStr);
    }

    public PrivateKey loadPrivateKeyForString(String keyStr) throws Exception {
        byte[] keyBytes = Base64Utils.decrypt(keyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);

        return keyFactory.generatePrivate(keySpec);
    }

    // ================================================= PUBLIC-KEY

    public PublicKey publicKeyFromCert(String path) throws CertificateException {
        path = StringUtils.hasText(path) ? path : "";
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X_509);
        InputStream inputStream = ClassPathReader.read(path);
        Certificate x509certificate = certificateFactory.generateCertificate(inputStream);

        return x509certificate.getPublicKey();
    }

    public PublicKey publicKey(String publicKeyPath) throws Exception {
        String keyStr = ClassPathReader.joinRead(publicKeyPath);
        byte[] keyBytes = Base64Utils.decrypt(keyStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey privateKey = keyFactory.generatePublic(keySpec);

        return privateKey;
    }

    // ================================================= SHA256

    public String sha256(final String text) {
        return DigestUtils.sha256Hex(text.getBytes(StandardCharsets.UTF_8)).toUpperCase();
    }

    // ================================================= SIGN

    public String sign(final PrivateKey privateKey, final String text) {
        try {
            Signature signature = Signature.getInstance(SHA_WITH_RSA);
            signature.initSign(privateKey);
            signature.update(text.getBytes(StandardCharsets.UTF_8));
            return Base64Utils.encrypt(signature.sign());
        } catch (Exception e) {
            throw new SecurityException(String.format("the input param have error,maybe:[%s]", text));
        }
    }

    // ================================================= VERIFY

    public boolean verify(final PublicKey publicKey, final String original, final String sign) throws Exception {
        Signature signature = Signature.getInstance(SHA_WITH_RSA);
        signature.initVerify(publicKey);
        signature.update(original.getBytes(StandardCharsets.UTF_8));

        return signature.verify(Base64Utils.decrypt(sign));
    }

    public void verify(String publicCertPath, String sign, JSONObject bizData) throws Exception {
        String original = this.order(bizData);
        PublicKey publicKey = this.publicKeyFromCert(publicCertPath);
        boolean failed = this.failed(publicKey, this.sha256(original), sign);
        if (failed) {
            throw new SecurityException("the data verify the sign failed!");
        }
    }

    public void verifyPlatformResponse(String sign, JSONObject data) throws Exception {
        String original = this.order(data);
        PublicKey publicKey = this.publicKey(this.securityConfigProperties.getPlatformPublicKey());
        boolean failed = this.failed(publicKey, this.sha256(original), sign);
        if (failed) {
            throw new SecurityException("the data verify sign failed!");
        }
    }

    public boolean failed(final PublicKey publicKey, final String original, final String sign) throws Exception {
        return !this.verify(publicKey, this.sha256(original), sign);
    }

    // ================================================= ENCRYPT

    public String encryptAes256(final String text, final String key) throws SecurityException {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_AES_ECB_PKCS7, PROVIDER_BC);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] buffer = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return Base64Utils.encrypt(buffer);
        } catch (Exception e) {
            throw new SecurityException("handle aes 256 encrypt exception!", e);
        }
    }

    public final String decryptAes256(final String text, final String key) throws SecurityException {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_AES_ECB_PKCS7, PROVIDER_BC);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = cipher.doFinal(Base64Utils.decrypt(text));
            return new String(decoded, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SecurityException("handle aes 256 decrypt exception!", e);
        }
    }

    // ================================================= UUID

    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // ================================================= SORT

    /**
     * 对 JSON 数据进行排序.
     *
     * @param param JSON 数据
     * @return 排序后的数据
     */
    private TreeMap<String, Object> sortJsonObject(JSONObject param) {
        TreeMap<String, Object> paramMap = new TreeMap<>();
        Set<Map.Entry<String, Object>> set = param.entrySet();
        for (Map.Entry<String, Object> entry : set) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            if (value instanceof JSONObject) {
                paramMap.put(key, this.sortJsonObject((JSONObject) value));
            } else if (value instanceof JSONArray) {
                paramMap.put(key, this.sortJsonArray((JSONArray) value));
            } else {
                paramMap.put(key, value);
            }
        }

        return paramMap;
    }

    /**
     * 对 JSON 数组进行排序.
     *
     * @param parameters JSON 数组
     * @return 排序后的数据
     */
    private List<Object> sortJsonArray(JSONArray parameters) {
        List<Object> data = new ArrayList<>();

        for (Object parameter : parameters) {
            if (null == parameter) {
                continue;
            }
            if (parameter instanceof JSONObject) {
                data.add(this.sortJsonObject((JSONObject) parameter));
            } else if (parameter instanceof JSONArray) {
                data.add(this.sortJsonArray((JSONArray) parameter));
            } else {
                data.add(parameter);
            }
        }

        data.sort(Comparator.comparing(Object::toString));

        return data;
    }
}

