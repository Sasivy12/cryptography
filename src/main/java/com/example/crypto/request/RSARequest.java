package com.example.crypto.request;

public class RSARequest
{
    private String text;
    private String publicKey;
    private String privateKey;
    private int keySize = 2048;


    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getPublicKey()
    {
        return publicKey;
    }

    public void setPublicKey(String publicKey)
    {
        this.publicKey = publicKey;
    }

    public String getPrivateKey()
    {
        return privateKey;
    }

    public void setPrivateKey(String privateKey)
    {
        this.privateKey = privateKey;
    }

    public int getKeySize()
    {
        return keySize;
    }

    public void setKeySize(int keySize)
    {
        this.keySize = keySize;
    }
}