package com.example.crypto.service;

import org.springframework.stereotype.Service;

@Service
public class CryptoService
{
    public String caesarCipher(String text, int shift, boolean decrypt)
    {
        if (decrypt)
        {
            shift = 26 - (shift % 26);
        }

        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray())
        {
            if (Character.isLetter(character))
            {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                result.append((char) ((character - base + shift) % 26 + base));
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
            if (Character.isLetter(character))
            {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                int keyShift = key.charAt(j % keyLength) - 'a';
                if (decrypt)
                {
                    keyShift = 26 - keyShift;
                }
                result.append((char) ((character - base + keyShift) % 26 + base));
                j++;
            }
            else
            {
                result.append(character);
            }
        }

        return result.toString();
    }
}
