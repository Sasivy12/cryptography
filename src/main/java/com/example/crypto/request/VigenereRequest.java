package com.example.crypto.request;

public class VigenereRequest
{
    private String text;
    private String key;

    public VigenereRequest(String text, String key)
    {
        this.text = text;
        this.key = key;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}
