package com.example.crypto.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Service
public class CryptoRuService
{

    private static final int RUS_ALPHABET_SIZE = 33;
    private static final char UPPER_A = 'А', UPPER_YA = 'Я';
    private static final char LOWER_A = 'а', LOWER_YA = 'я';

    public String caesarCipher(String text, int shift, boolean decrypt)
    {
        if (decrypt)
        {
            shift = RUS_ALPHABET_SIZE - (shift % RUS_ALPHABET_SIZE);
        }

        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray())
        {
            if (Character.UnicodeBlock.of(character) == Character.UnicodeBlock.CYRILLIC)
            {
                char base = Character.isUpperCase(character) ? UPPER_A : LOWER_A;
                result.append((char) ((character - base + shift) % RUS_ALPHABET_SIZE + base));
            }
            else
            {
                result.append(character);
            }
        }
        return result.toString();
    }

    public String vigenereCipher(String text, String key, boolean decrypt)
    {
        StringBuilder result = new StringBuilder();
        key = key.toLowerCase();
        int keyLength = key.length();

        for (int i = 0, j = 0; i < text.length(); i++)
        {
            char character = text.charAt(i);
            if (Character.UnicodeBlock.of(character) == Character.UnicodeBlock.CYRILLIC)
            {
                char base = Character.isUpperCase(character) ? UPPER_A : LOWER_A;
                int keyShift = key.charAt(j % keyLength) - LOWER_A;
                if (decrypt)
                {
                    keyShift = RUS_ALPHABET_SIZE - keyShift;
                }
                result.append((char) ((character - base + keyShift) % RUS_ALPHABET_SIZE + base));
                j++;
            }
            else
            {
                result.append(character);
            }
        }
        return result.toString();
    }

    public String playFairCipher(String text, String key)
    {
        final int SIZE = 6;
        boolean[] used = new boolean[RUS_ALPHABET_SIZE];
        char[][] matrix = new char[SIZE][SIZE];

        key = key.toUpperCase().replaceAll("[^А-ЯЁ]", "");
        String alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

        int index = 0;
        for (char c : (key + alphabet).toCharArray())
        {
            if (!used[c - UPPER_A])
            {
                matrix[index / SIZE][index % SIZE] = c;
                used[c - UPPER_A] = true;
                index++;
            }
        }

        text = text.toUpperCase().replaceAll("[^А-ЯЁ]", "");
        if (text.length() % 2 != 0) text += "Х";

        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2)
        {
            int[] pos1 = findPosition(text.charAt(i), matrix, SIZE);
            int[] pos2 = findPosition(text.charAt(i + 1), matrix, SIZE);

            if (pos1[0] == pos2[0])
            {
                encrypted.append(matrix[pos1[0]][(pos1[1] + 1) % SIZE]);
                encrypted.append(matrix[pos2[0]][(pos2[1] + 1) % SIZE]);
            }
            else if (pos1[1] == pos2[1])
            {
                encrypted.append(matrix[(pos1[0] + 1) % SIZE][pos1[1]]);
                encrypted.append(matrix[(pos2[0] + 1) % SIZE][pos2[1]]);
            }
            else
            {
                encrypted.append(matrix[pos1[0]][pos2[1]]);
                encrypted.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        return encrypted.toString();
    }

    public String decryptPlayFairCipher(String text, String key)
    {
        final int SIZE = 6;
        boolean[] used = new boolean[RUS_ALPHABET_SIZE];
        char[][] matrix = new char[SIZE][SIZE];

        key = key.toUpperCase().replaceAll("[^А-ЯЁ]", "");
        String alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

        int index = 0;
        for (char c : (key + alphabet).toCharArray())
        {
            if (!used[c - UPPER_A])
            {
                matrix[index / SIZE][index % SIZE] = c;
                used[c - UPPER_A] = true;
                index++;
            }
        }

        text = text.toUpperCase().replaceAll("[^А-ЯЁ]", "");

        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < text.length(); i += 2)
        {
            int[] pos1 = findPosition(text.charAt(i), matrix, SIZE);
            int[] pos2 = findPosition(text.charAt(i + 1), matrix, SIZE);

            if (pos1[0] == pos2[0])
            {
                decrypted.append(matrix[pos1[0]][(pos1[1] + SIZE - 1) % SIZE]);
                decrypted.append(matrix[pos2[0]][(pos2[1] + SIZE - 1) % SIZE]);
            }
            else if (pos1[1] == pos2[1])
            {
                decrypted.append(matrix[(pos1[0] + SIZE - 1) % SIZE][pos1[1]]);
                decrypted.append(matrix[(pos2[0] + SIZE - 1) % SIZE][pos2[1]]);
            }
            else
            {
                decrypted.append(matrix[pos1[0]][pos2[1]]);
                decrypted.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        return decrypted.toString();
    }

    private static int[] findPosition(char c, char[][] matrix, int SIZE)
    {
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                if (matrix[i][j] == c) return new int[]{i, j};
            }
        }
        return null;
    }

    public String routeCipher(String text, int rows, int cols)
    {
        text = text.replaceAll("[^А-Яа-я]", "").toUpperCase();
        char[][] grid = new char[rows][cols];
        StringBuilder encrypted = new StringBuilder();

        int index = 0;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                grid[i][j] = index < text.length() ? text.charAt(index++) : 'Х';
            }
        }

        for (int j = 0; j < cols; j++)
        {
            for (int i = 0; i < rows; i++)
            {
                encrypted.append(grid[i][j]);
            }
        }
        return encrypted.toString();
    }

    public String columnarTranspositionCipher(String text, String key)
    {
        text = text.replaceAll("[^А-Яа-я]", "").toUpperCase();
        int cols = key.length();
        int rows = (int) Math.ceil((double) text.length() / cols);
        char[][] grid = new char[rows][cols];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
            {
                grid[i][j] = (index < text.length()) ? text.charAt(index++) : 'Х';
            }
        }

        Integer[] order = new Integer[cols];
        for (int i = 0; i < cols; i++) order[i] = i;
        Arrays.sort(order, Comparator.comparingInt(key::charAt));

        StringBuilder encrypted = new StringBuilder();
        for (int col : order)
        {
            for (int row = 0; row < rows; row++)
            {
                encrypted.append(grid[row][col]);
            }
        }
        return encrypted.toString();
    }

    public String railFenceCipher(String text, int rails)
    {
        text = text.replaceAll("[^А-Яа-я]", "").toUpperCase();
        char[][] rail = new char[rails][text.length()];

        int row = 0, step = 1;

        for (int i = 0; i < text.length(); i++)
        {
            rail[row][i] = text.charAt(i);

            if (row == 0) step = 1;
            if (row == rails - 1) step = -1;

            row += step;
        }

        StringBuilder encrypted = new StringBuilder();
        for (char[] r : rail)
        {
            for (char c : r)
            {
                if (c != 0) encrypted.append(c);
            }
        }
        return encrypted.toString();
    }

    public Map<String, String> generateRsaKeys(int keySize)
    {
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(keySize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            Map<String, String> keys = new HashMap<>();
            keys.put("public", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            keys.put("private", Base64.getEncoder().encodeToString(privateKey.getEncoded()));

            return keys;
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Error generating RSA keys", e);
        }
    }

    public String rsaEncrypt(String text, String publicKeyStr)
    {
        try
        {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error encrypting with RSA", e);
        }
    }

    public String rsaDecrypt(String encryptedText, String privateKeyStr)
    {
        try
        {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error decrypting with RSA", e);
        }
    }

    public Map<String, String> generateEcdsaKeys(int keySize)
    {
        try
        {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(keySize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            Map<String, String> keys = new HashMap<>();
            keys.put("public", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            keys.put("private", Base64.getEncoder().encodeToString(privateKey.getEncoded()));

            return keys;
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Error generating ECDSA keys", e);
        }
    }

    public String ecdsaSign(String text, String privateKeyStr)
    {
        try
        {
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Signature signature = Signature.getInstance("SHA256withECDSA");
            signature.initSign(privateKey);
            signature.update(text.getBytes());

            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error signing with ECDSA", e);
        }
    }


    public boolean ecdsaVerify(String text, String signatureStr, String publicKeyStr)
    {
        try
        {
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            Signature signature = Signature.getInstance("SHA256withECDSA");
            signature.initVerify(publicKey);
            signature.update(text.getBytes());

            byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
            return signature.verify(signatureBytes);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error verifying signature with ECDSA", e);
        }
    }

    public Map<String, String> generateEd25519Keys()
    {
        try
        {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("Ed25519");
            KeyPair keyPair = keyGen.generateKeyPair();

            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

            Map<String, String> keys = new HashMap<>();
            keys.put("public", publicKey);
            keys.put("private", privateKey);
            return keys;
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Error generating Ed25519 keys", e);
        }
    }

    public String ed25519Sign(String message, String base64PrivateKey)
    {
        try
        {
            byte[] privateKeyBytes = Base64.getDecoder().decode(base64PrivateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("Ed25519");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Signature signature = Signature.getInstance("Ed25519");
            signature.initSign(privateKey);
            signature.update(message.getBytes());

            byte[] sigBytes = signature.sign();
            return Base64.getEncoder().encodeToString(sigBytes);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error signing with Ed25519", e);
        }
    }

    public boolean ed25519Verify(String message, String base64Signature, String base64PublicKey)
    {
        try
        {
            byte[] publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("Ed25519");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            Signature signature = Signature.getInstance("Ed25519");
            signature.initVerify(publicKey);
            signature.update(message.getBytes());

            byte[] sigBytes = Base64.getDecoder().decode(base64Signature);
            return signature.verify(sigBytes);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error verifying Ed25519 signature", e);
        }
    }
}
