package com.example.crypto.controller;

import com.example.crypto.request.*;
import com.example.crypto.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class CryptoController
{
    @Autowired
    private CryptoService cryptoService;

    @GetMapping("/")
    public String home(Model model)
    {
        model.addAttribute("title", "Cryptography App");
        return "home";
    }

    @PostMapping("/encrypt")
    public String encrypt(@RequestParam String algorithm,
                          @RequestParam String text,
                          @RequestParam(required = false) String key,
                          @RequestParam(required = false) boolean decrypt,
                          Model model)
    {
        String result = switch (algorithm)
        {
            case "caesar" -> cryptoService.caesarCipher(text, Integer.parseInt(key), decrypt);
            case "vigenere" -> cryptoService.vigenereCipher(text, key, decrypt);
            case "playfair" -> cryptoService.playFairCipher(text, key);
            case "route" -> cryptoService.routeCipher(text, 4, 4);
            case "columnar" -> cryptoService.columnarTranspositionCipher(text, key);
            case "railfence" -> cryptoService.railFenceCipher(text, Integer.parseInt(key));
            default -> "Invalid algorithm";
        };
        model.addAttribute("result", result);
        return "home";
    }

    @GetMapping("/caesar")
    public String caesarPage()
    {
        return "caesar";
    }

    @PostMapping("/playfair")
    public String performPlayFairCipher(@RequestParam String text,
                                        @RequestParam String key,
                                        @RequestParam(required = false) boolean decrypt,
                                        Model model)
    {
        String result = decrypt ? cryptoService.decryptPlayFairCipher(text, key) : cryptoService.playFairCipher(text, key);

        model.addAttribute("text", text);
        model.addAttribute("key", key);
        model.addAttribute("decrypt", decrypt);
        model.addAttribute("result", result);

        return "playfaircipher";
    }

    @GetMapping("/vigenere")
    public String vigenerePage()
    {
        return "vigenere";
    }

    @PostMapping("/vigenere")
    public String performVigenereCipher(@RequestParam String text,
                                        @RequestParam String key,
                                        @RequestParam(required = false) boolean decrypt,
                                        Model model)
    {
        String result = decrypt ? cryptoService.decryptPlayFairCipher(text, key) : cryptoService.playFairCipher(text, key);

        model.addAttribute("result", result);
        model.addAttribute("text", text);
        model.addAttribute("key", key);
        model.addAttribute("decrypt", decrypt);

        return "vigenere";
    }

    @GetMapping("/playfair")
    public String playFairPage()
    {
        return "playfaircipher";
    }

//    @PostMapping("/playfair")
//    public ResponseEntity<String> performPlayFairCipher(@RequestBody PlayFairRequest playFairRequest)
//    {
//        return ResponseEntity.ok(cryptoService.playFairCipher(playFairRequest.getText(), playFairRequest.getKey()));
//    }

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