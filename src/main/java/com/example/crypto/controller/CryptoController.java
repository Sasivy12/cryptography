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
            case "columnartranspos" -> cryptoService.columnarTranspositionCipher(text, key);
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

    @PostMapping("/caesar")
    public String performCaesarCipher(@ModelAttribute CaesarRequest caesarRequest,
                                      @RequestParam(required = false) boolean decrypt,
                                      Model model)
    {
        String result = cryptoService.caesarCipher(caesarRequest.getText(), caesarRequest.getShift(), decrypt);

        model.addAttribute("text", caesarRequest.getText());
        model.addAttribute("shift", caesarRequest.getShift());
        model.addAttribute("decrypt", decrypt);
        model.addAttribute("result", result);

        return "caesar";
    }

    @GetMapping("/vigenere")
    public String vigenerePage()
    {
        return "vigenere";
    }

    @PostMapping("/vigenere")
    public String performVigenereCipher(@ModelAttribute VigenereRequest vigenereRequest,
                                        @RequestParam(required = false) boolean decrypt,
                                        Model model)
    {
        String result = decrypt ?
                cryptoService.decryptPlayFairCipher(vigenereRequest.getText(), vigenereRequest.getKey()) :
                cryptoService.playFairCipher(vigenereRequest.getText(), vigenereRequest.getKey());

        model.addAttribute("result", result);
        model.addAttribute("text", vigenereRequest.getText());
        model.addAttribute("key", vigenereRequest.getKey());
        model.addAttribute("decrypt", decrypt);

        return "vigenere";
    }

    @GetMapping("/playfair")
    public String playFairPage()
    {
        return "playfaircipher";
    }

    @PostMapping("/playfair")
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

        return "playfaircipher";
    }

    @GetMapping("/route")
    public String routeCipherPage(Model model)
    {
        model.addAttribute("text", "");
        model.addAttribute("rows", "");
        model.addAttribute("cols", "");
        model.addAttribute("result", "");
        return "routecipher";
    }

    @PostMapping("/route")
    public String performRouteCipher(@ModelAttribute RouteRequest routeRequest, Model model)
    {
        String result = cryptoService.routeCipher(routeRequest.getText(), routeRequest.getRows(), routeRequest.getCols());

        model.addAttribute("text", routeRequest.getText());
        model.addAttribute("rows", routeRequest.getRows());
        model.addAttribute("cols", routeRequest.getCols());
        model.addAttribute("result", result);

        return "routecipher";
    }

    @GetMapping("/columnartranspos")
    public String columnarTransposPage(Model model)
    {
        model.addAttribute("text", "");
        model.addAttribute("key", "");
        model.addAttribute("result", "");

        return "columnartranspos";
    }

    @PostMapping("/columnartranspos")
    public String performColumnarTranspositionCipher
            (@ModelAttribute ColumnarTranspositionRequest columnarTranspositionRequest, Model model)
    {
        String result = cryptoService.columnarTranspositionCipher
                (columnarTranspositionRequest.getText(),
                 columnarTranspositionRequest.getKey());

        model.addAttribute("text", columnarTranspositionRequest.getText());
        model.addAttribute("key", columnarTranspositionRequest.getKey());
        model.addAttribute("result", result);

        return "columnartranspos";
    }

    @GetMapping("/railfence")
    public String railFencePage(Model model)
    {
        model.addAttribute("text", "");
        model.addAttribute("rails", "");
        model.addAttribute("result", "");

        return "railfence";
    }

    @PostMapping("/railfence")
    public String performRailFenceCipher(@ModelAttribute RailFenceRequest railFenceRequest, Model model)
    {
        String result = cryptoService.railFenceCipher(railFenceRequest.getText(), railFenceRequest.getRails());

        model.addAttribute("text", railFenceRequest.getText());
        model.addAttribute("rails", railFenceRequest.getRails());
        model.addAttribute("result", result);

        return "railfence";
    }
}