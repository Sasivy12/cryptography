package com.example.crypto.controller;

import com.example.crypto.request.*;
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

    @PostMapping("/columnartranspos")
    public ResponseEntity<String> performColumnarTranspositionCipher
            (@RequestBody ColumnarTranspositionRequest columnarTranspositionRequest)
    {
        return ResponseEntity.ok
                (cryptoService.
                        columnarTranspositionCipher(columnarTranspositionRequest.getText(),
                        columnarTranspositionRequest.getKey())
                );
    }

    @PostMapping("/railfence")
    public ResponseEntity<String> performRailFenceCipher(@RequestBody RailFenceRequest railFenceRequest)
    {
        return ResponseEntity.ok
                (cryptoService.railFenceCipher(railFenceRequest.getText(), railFenceRequest.getRails()));
    }
}