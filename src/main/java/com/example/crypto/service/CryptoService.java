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

    public String playFairCipher(String text, String key)
    {
        final int SIZE = 5;
        boolean[] used = new boolean[26];
        char[][] matrix = new char[SIZE][SIZE];
        key = key.toUpperCase().replaceAll("J", "I").replaceAll("[^A-Z]", "");

        int index = 0;
        for (char c : (key + "ABCDEFGHIKLMNOPQRSTUVWXYZ").toCharArray())
        {
            if (!used[c - 'A']) {
                matrix[index / SIZE][index % SIZE] = c;
                used[c - 'A'] = true;
                index++;
            }
        }

        text = text.toUpperCase().replaceAll("J", "I").replaceAll("[^A-Z]", "");
        StringBuilder sb = new StringBuilder(text);
        for (int i = 1; i < sb.length(); i += 2)
        {
            if (sb.charAt(i) == sb.charAt(i - 1))
            {
                sb.insert(i, 'X');
            }
        }
        if (sb.length() % 2 != 0) sb.append('X');
        text = sb.toString();

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
}
