package com.example.crypto.controller;

import com.example.crypto.request.VigenereRequest;
import com.example.crypto.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class CryptoController
{
    @Autowired
    private CryptoService cryptoService;

    @GetMapping("/caesar/{shift}/{decrypt}")
    public ResponseEntity<String> performCaesarCipher(
            @RequestBody String text,
            @PathVariable int shift,
            @PathVariable boolean decrypt)
    {
        return ResponseEntity.ok(cryptoService.caesarCipher(text, shift, decrypt));
    }

    @GetMapping("/vigenere/{decrypt}")
    public ResponseEntity<String> performVigenereCipher
            (@RequestBody VigenereRequest vigenereRequest, @PathVariable boolean decrypt)
    {
        return ResponseEntity.ok
                (cryptoService.vigenereCipher(vigenereRequest.getText(), vigenereRequest.getKey(), decrypt));
    }
}