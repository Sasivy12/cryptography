package com.example.crypto.controller;

import com.example.crypto.request.CaesarRequest;
import com.example.crypto.request.PlayFairRequest;
import com.example.crypto.request.RouteRequest;
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

    @PostMapping("/caesar/{decrypt}")
    public ResponseEntity<String> performCaesarCipher
            (@RequestBody CaesarRequest caesarRequest, @PathVariable boolean decrypt)
    {
        return ResponseEntity.ok(cryptoService.caesarCipher(caesarRequest.getText(), caesarRequest.getShift(), decrypt));
    }

    @PostMapping("/vigenere/{decrypt}")
    public ResponseEntity<String> performVigenereCipher
            (@RequestBody VigenereRequest vigenereRequest, @PathVariable boolean decrypt)
    {
        return ResponseEntity.ok
                (cryptoService.vigenereCipher(vigenereRequest.getText(), vigenereRequest.getKey(), decrypt));
    }

    @PostMapping("/playfair")
    public ResponseEntity<String> performPlayFairCipher(@RequestBody PlayFairRequest playFairRequest)
    {
        return ResponseEntity.ok(cryptoService.playFairCipher(playFairRequest.getText(), playFairRequest.getKey()));
    }

    @PostMapping("/route")
    public ResponseEntity<String> performRouteCipher(@RequestBody RouteRequest routeRequest)
    {
        return ResponseEntity.ok(cryptoService.routeCipher
                (routeRequest.getText(), routeRequest.getRows(), routeRequest.getCols()));
    }
}