package com.example.crypto.request;

public class DSARequest
{
    private String text;

    private String publicKey;

    private String privateKey;

    private String signature;

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

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
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
