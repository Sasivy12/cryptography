package com.example.crypto.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;

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

    public String routeCipher(String text, int rows, int cols)
    {
        text = text.replaceAll("[^A-Za-z]", "").toUpperCase();
        char[][] grid = new char[rows][cols];
        StringBuilder encrypted = new StringBuilder();

        int index = 0;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                grid[i][j] = index < text.length() ? text.charAt(index++) : 'X';
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
        text = text.replaceAll("[^A-Za-z]", "").toUpperCase();
        int cols = key.length();
        int rows = (int) Math.ceil((double) text.length() / cols);
        char[][] grid = new char[rows][cols];

        int index = 0;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                grid[i][j] = (index < text.length()) ? text.charAt(index++) : 'X';
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


    public String railFenceCipher(String text, int rails) {
        text = text.replaceAll("[^A-Za-z]", "").toUpperCase();
        char[][] rail = new char[rails][text.length()];

        int row = 0, step = 1;

        for (int i = 0; i < text.length(); i++) {
            rail[row][i] = text.charAt(i);

            if (row == 0) step = 1;
            if (row == rails - 1) step = -1;

            row += step;
        }

        StringBuilder encrypted = new StringBuilder();
        for (char[] r : rail) {
            for (char c : r) {
                if (c != 0) encrypted.append(c);
            }
        }

        return encrypted.toString();
    }

}
