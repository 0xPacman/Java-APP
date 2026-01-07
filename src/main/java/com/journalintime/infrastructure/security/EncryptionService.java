package com.journalintime.infrastructure.security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

/**
 * Service de chiffrement AES-256-GCM pour les données sensibles.
 * Utilise Bouncy Castle pour une sécurité renforcée.
 */
@Component
public class EncryptionService {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_IV_LENGTH = 12;
    private static final int KEY_SIZE = 256;

    private final SecretKey secretKey;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public EncryptionService(@Value("${app.encryption.key:}") String encodedKey) throws Exception {
        if (encodedKey.isEmpty()) {
            // Générer une nouvelle clé si non fournie
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(KEY_SIZE);
            this.secretKey = keyGenerator.generateKey();
        } else {
            byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
            this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        }
    }

    /**
     * Chiffre le texte donné en utilisant AES-256-GCM.
     *
     * @param plainText Le texte à chiffrer
     * @return Texte chiffré encodé en Base64 avec IV préfixé
     */
    public String encrypt(String plainText) throws Exception {
        byte[] iv = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        byte[] encryptedData = cipher.doFinal(plainText.getBytes());
        
        // Préfixer l'IV aux données chiffrées
        byte[] encryptedWithIv = new byte[GCM_IV_LENGTH + encryptedData.length];
        System.arraycopy(iv, 0, encryptedWithIv, 0, GCM_IV_LENGTH);
        System.arraycopy(encryptedData, 0, encryptedWithIv, GCM_IV_LENGTH, encryptedData.length);

        return Base64.getEncoder().encodeToString(encryptedWithIv);
    }

    /**
     * Déchiffre le texte donné en utilisant AES-256-GCM.
     *
     * @param encryptedText Texte chiffré encodé en Base64 avec IV préfixé
     * @return Texte déchiffré
     */
    public String decrypt(String encryptedText) throws Exception {
        byte[] encryptedWithIv = Base64.getDecoder().decode(encryptedText);

        byte[] iv = new byte[GCM_IV_LENGTH];
        System.arraycopy(encryptedWithIv, 0, iv, 0, GCM_IV_LENGTH);

        byte[] encryptedData = new byte[encryptedWithIv.length - GCM_IV_LENGTH];
        System.arraycopy(encryptedWithIv, GCM_IV_LENGTH, encryptedData, 0, encryptedData.length);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData);
    }

    /**
     * Obtient la clé de chiffrement encodée en Base64 pour la configuration.
     */
    public String getEncodedKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
