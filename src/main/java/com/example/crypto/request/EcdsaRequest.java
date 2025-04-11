package com.example.crypto.request;

public class EcdsaRequest
{
    private String text;
    private String publicKey;
    private String privateKey;
    private int keySize = 256;
    private String signature;

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

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }
}
