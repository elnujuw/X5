package org.junle.common.utils.sign;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密解密
 *
 * @author ruoyi
 **/
public class RsaUtils
{
    // Rsa 私钥
    public static String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDjzeYByhxdVPs9\n" +
            "pOSz4W9lOiaWXq3MELhTQVlo8HERpEfJK07NR8FXA6uqfJIjTCCkfx/8wXPJRms/\n" +
            "OTu99bxyyhPRwVYw242P4aTv83ldVVl8Pwzsjgx4hfVs8sG833yld71dHdoKyNHf\n" +
            "uaesK+DJOftaTOOr+46Ze3g7fw9YzwZonsx7ukgv2DzgxzJeVn2ZvUkQdISjj3qF\n" +
            "Ey3RVUGJSCWsrqOBLXeLiakH1qKwCmBKiCNYO04PQY6DxYT+NX0MhDSyHE35O9As\n" +
            "Pi/ZHrop9U0dlhn45LJWVnlx2Gzh9mOtwdHGfyrzft/XZG7X/0tuJ/8VeIXZLMkj\n" +
            "B36s+Tx5AgMBAAECggEAI8cxybQpkksHjp34QXw1l6tzZgiGuVci7AiGd2HUEFZB\n" +
            "5AmGPd14MydjbcpU1XBebBd/OZ5UhhcmPu7JQEN5DnpIdJgt5kyFRkTFN96AgDUo\n" +
            "ccghSoHg2YkIi8zwuq5LEF3nAtnuZeU2eTHacMMPcetGW99ZnqhKtDZdItMI4QSO\n" +
            "4wtQquWO2k12KysRNnV/zE3ANcgbexqzVd6O/8tv1r0dVz5KMZz3eYfZmkyS+i0u\n" +
            "Md5JfNrm5pPF5uxP7zT/ueZOmMl+vsHT2pKXDuXFMgbuKCpe3ccQl5n9ExdURU9Y\n" +
            "qsqMLWiy6JpJZHtcWXXbQblYU5ZwyxC/xNrLRyPbBQKBgQD+Q6GRF8PKh9HgKSQ3\n" +
            "2k2J69iw0AHHxIfEuXxYy5TcW9bMJYEOYrgLYeDScl4K8bEkClqFw9uFbbyIIhC8\n" +
            "5QfA5sKDVGk1kfxlqOLqT+20Loy9JcS3mxUtE6XkXutVk56BrKLWh98lzO6B9TBc\n" +
            "3/vMtvMpZAqroAKwGCvtjFTPtwKBgQDlXAY3uqiTTD32blXLJdRq16m8jPG/s4aX\n" +
            "qXfrnyWOSQ0RGA/5jTTawL6SiEjuuX6oSlBNV2FOk2BA1jKon49Ofhk6NJXIoobO\n" +
            "aTsMMgv82/4u8Vz2BgRJxtddXDySFGC80YMWzZaWBK81DPz1q3Rq51eagEoNnqAw\n" +
            "1EavJhX1TwKBgQCbkYGvJp0ys7sjrchtK/I5KsYgGISeV47CPMLqCVWBJefcbC30\n" +
            "QU5eGHrYCAWmKI67gGI2aclMcAHkQQOAr5j427ezZtggYLvO73A28MR9c+XEbPFZ\n" +
            "bVedhuH/Qlw2teVLbfcLz4ImvKZJeV0n1htX+6/3aTBmJba/S2rFIxFZLQKBgQC2\n" +
            "1olOW8qOwbSTgpl5/Io9MfbpjCIbg+3DcSFb/95ccverrNbvRRXhXM2O2n3pcI37\n" +
            "cmJZhVLY5LtSdG6l5azEEdnigJD3BDkayuB7dFoCFQ2oNli490rr1UtR7XmLqhsD\n" +
            "6rDpuLJWnR1e2R++aBPCNPGtBKAEA3QH+PDwJxhSGQKBgQCaG8NpRznT6BlUnDNY\n" +
            "axMC9YYcRWS+IASIQEiMWxATGOJn+igogDPfGURMCN0W/zwEyHG7LZSrcLNN1e+M\n" +
            "nM31iBEEaqXcBxpKpLZeQizZJ3I+Fb4HzRwrvBUIr1H5vpQ7fDO/DUSOj2XYvMs0\n" +
            "1C6A0akwrOnK6YOUIjORQ2u//A==";

    /**
     * 私钥解密
     *
     * @param privateKeyString 私钥
     * @param text 待解密的文本
     * @return 解密后的文本
     */
    public static String decryptByPrivateKey(String text) throws Exception
    {
        return decryptByPrivateKey(privateKey, text);
    }

    /**
     * 公钥解密
     *
     * @param publicKeyString 公钥
     * @param text 待解密的信息
     * @return 解密后的文本
     */
    public static String decryptByPublicKey(String publicKeyString, String text) throws Exception
    {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 私钥加密
     *
     * @param privateKeyString 私钥
     * @param text 待加密的信息
     * @return 加密后的文本
     */
    public static String encryptByPrivateKey(String privateKeyString, String text) throws Exception
    {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 私钥解密
     *
     * @param privateKeyString 私钥
     * @param text 待解密的文本
     * @return 解密后的文本
     */
    public static String decryptByPrivateKey(String privateKeyString, String text) throws Exception
    {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 公钥加密
     *
     * @param publicKeyString 公钥
     * @param text 待加密的文本
     * @return 加密后的文本
     */
    public static String encryptByPublicKey(String publicKeyString, String text) throws Exception
    {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * 构建RSA密钥对
     *
     * @return 生成后的公私钥信息
     */
    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }

    /**
     * RSA密钥对对象
     */
    public static class RsaKeyPair
    {
        private final String publicKey;
        private final String privateKey;

        public RsaKeyPair(String publicKey, String privateKey)
        {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey()
        {
            return publicKey;
        }

        public String getPrivateKey()
        {
            return privateKey;
        }
    }
}