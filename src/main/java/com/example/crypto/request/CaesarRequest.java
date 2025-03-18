package com.example.crypto.request;

public class CaesarRequest
{
    private String text;
    private int shift;

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public int getShift()
    {
        return shift;
    }

    public void setShift(int shift)
    {
        this.shift = shift;
    }
}
