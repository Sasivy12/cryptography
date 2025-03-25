package com.example.crypto.controller;

import com.example.crypto.request.CaesarRequest;
import com.example.crypto.request.PlayFairRequest;
import com.example.crypto.request.VigenereRequest;
import com.example.crypto.service.CryptoRuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class CryptoRuController
{
    @Autowired
    private CryptoRuService cryptoService;

    @GetMapping("/home/ru")
    public String home(Model model)
    {
        model.addAttribute("title", "Cryptography App");
        return "ru/home_ru";
    }

    @PostMapping("/encrypt/ru")
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
            case "columnartranspos" -> cryptoService.columnarTranspositionCipher(text, key);
            case "railfence" -> cryptoService.railFenceCipher(text, Integer.parseInt(key));
            default -> "Invalid algorithm";
        };
        model.addAttribute("result", result);
        return "ru/home_ru";
    }

    @GetMapping("/caesar/ru")
    public String caesarPage()
    {
        return "ru/caesar_ru";
    }

    @PostMapping("/caesar/ru")
    public String performCaesarCipher(@ModelAttribute CaesarRequest caesarRequest,
                                      @RequestParam(required = false) boolean decrypt,
                                      Model model)
    {
        String result = cryptoService.caesarCipher(caesarRequest.getText(), caesarRequest.getShift(), decrypt);

        model.addAttribute("text", caesarRequest.getText());
        model.addAttribute("shift", caesarRequest.getShift());
        model.addAttribute("decrypt", decrypt);
        model.addAttribute("result", result);

        return "ru/caesar_ru";
    }

    @GetMapping("/vigenere/ru")
    public String vigenerePage()
    {
        return "ru/vigenere_ru";
    }

    @PostMapping("/vigenere/ru")
    public String performVigenereCipher(@ModelAttribute VigenereRequest vigenereRequest,
                                        @RequestParam(required = false) boolean decrypt,
                                        Model model)
    {
        String result = cryptoService.vigenereCipher(vigenereRequest.getText(), vigenereRequest.getKey(), decrypt);

        model.addAttribute("result", result);
        model.addAttribute("text", vigenereRequest.getText());
        model.addAttribute("key", vigenereRequest.getKey());
        model.addAttribute("decrypt", decrypt);

        return "ru/vigenere_ru";
    }

    @GetMapping("/playfair/ru")
    public String playFairPage()
    {
        return "ru/playfaircipher_ru";
    }

    @PostMapping("/playfair/ru")
    public String performPlayFairCipher(@ModelAttribute PlayFairRequest playFairRequest,
                                        @RequestParam(required = false) boolean decrypt,
                                        Model model)
    {
        String result = decrypt ?
                cryptoService.decryptPlayFairCipher(playFairRequest.getText(), playFairRequest.getKey()) :
                cryptoService.playFairCipher(playFairRequest.getText(), playFairRequest.getKey());

        model.addAttribute("text", playFairRequest.getText());
        model.addAttribute("key", playFairRequest.getKey());
        model.addAttribute("decrypt", decrypt);
        model.addAttribute("result", result);

        return "ru/playfaircipher_ru";
    }

}
