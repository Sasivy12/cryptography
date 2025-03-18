package com.example.crypto.request;

public class RailFenceRequest
{
    private String text;
    private int rails;

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getRails()
    {
        return rails;
    }

    public void setRails(int rails)
    {
        this.rails = rails;
    }
}
