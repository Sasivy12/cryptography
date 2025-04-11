package com.example.crypto.controller;

import com.example.crypto.request.*;
import com.example.crypto.service.CryptoRuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/route/ru")
    public String routeCipherPage(Model model)
    {
        model.addAttribute("text", "");
        model.addAttribute("rows", "");
        model.addAttribute("cols", "");
        model.addAttribute("result", "");

        return "ru/routecipher_ru";
    }

    @PostMapping("/route/ru")
    public String performRouteCipher(@ModelAttribute RouteRequest routeRequest, Model model)
    {
        String result = cryptoService.routeCipher(routeRequest.getText(), routeRequest.getRows(), routeRequest.getCols());

        model.addAttribute("text", routeRequest.getText());
        model.addAttribute("rows", routeRequest.getRows());
        model.addAttribute("cols", routeRequest.getCols());
        model.addAttribute("result", result);

        return "ru/routecipher_ru";
    }

    @GetMapping("/columnartranspos/ru")
    public String columnarTransposPage(Model model)
    {
        model.addAttribute("text", "");
        model.addAttribute("key", "");
        model.addAttribute("result", "");

        return "ru/columnartranspos_ru";
    }

    @PostMapping("/columnartranspos/ru")
    public String performColumnarTranspositionCipher
            (@ModelAttribute ColumnarTranspositionRequest columnarTranspositionRequest, Model model)
    {
        String result = cryptoService.columnarTranspositionCipher
                (columnarTranspositionRequest.getText(),
                        columnarTranspositionRequest.getKey());

        model.addAttribute("text", columnarTranspositionRequest.getText());
        model.addAttribute("key", columnarTranspositionRequest.getKey());
        model.addAttribute("result", result);

        return "ru/columnartranspos_ru";
    }

    @GetMapping("/railfence/ru")
    public String railFencePage(Model model)
    {
        model.addAttribute("text", "");
        model.addAttribute("rails", "");
        model.addAttribute("result", "");

        return "ru/railfence_ru";
    }

    @PostMapping("/railfence/ru")
    public String performRailFenceCipher(@ModelAttribute RailFenceRequest railFenceRequest, Model model)
    {
        String result = cryptoService.railFenceCipher(railFenceRequest.getText(), railFenceRequest.getRails());

        model.addAttribute("text", railFenceRequest.getText());
        model.addAttribute("rails", railFenceRequest.getRails());
        model.addAttribute("result", result);

        return "ru/railfence_ru";
    }


    @GetMapping("/rsa/ru")
    public String rsaPage(Model model)
    {
        model.addAttribute("publicKey", "");
        model.addAttribute("privateKey", "");
        model.addAttribute("keySize", 2048);
        return "ru/rsa_ru";
    }

    @PostMapping("/rsa/ru/generate-keys")
    public String generateRsaKeys(@ModelAttribute RSARequest rsaRequest, Model model)
    {
        Map<String, String> keys = cryptoService.generateRsaKeys(rsaRequest.getKeySize());

        model.addAttribute("publicKey", keys.get("public"));
        model.addAttribute("privateKey", keys.get("private"));
        model.addAttribute("keySize", rsaRequest.getKeySize());
        model.addAttribute("text", rsaRequest.getText());
        return "ru/rsa_ru";
    }

    @PostMapping("/rsa/ru/encrypt")
    public String performRsaEncryption(@ModelAttribute RSARequest rsaRequest, Model model)
    {
        String encrypted = cryptoService.rsaEncrypt(rsaRequest.getText(), rsaRequest.getPublicKey());

        model.addAttribute("result", encrypted);
        model.addAttribute("text", rsaRequest.getText());
        model.addAttribute("publicKey", rsaRequest.getPublicKey());
        model.addAttribute("privateKey", rsaRequest.getPrivateKey());
        model.addAttribute("keySize", rsaRequest.getKeySize());

        return "ru/rsa_ru";
    }

    @PostMapping("/rsa/ru/decrypt")
    public String performRsaDecryption(@ModelAttribute RSARequest rsaRequest, Model model)
    {
        String decrypted = cryptoService.rsaDecrypt(rsaRequest.getText(), rsaRequest.getPrivateKey());

        model.addAttribute("result", decrypted);
        model.addAttribute("text", rsaRequest.getText());
        model.addAttribute("publicKey", rsaRequest.getPublicKey());
        model.addAttribute("privateKey", rsaRequest.getPrivateKey());
        model.addAttribute("keySize", rsaRequest.getKeySize());

        return "ru/rsa_ru";
    }

    @GetMapping("/ecdsa/ru")
    public String ecdsaPage(Model model)
    {
        model.addAttribute("publicKey", "");
        model.addAttribute("privateKey", "");
        model.addAttribute("keySize", 256);
        return "ru/ecdsa_ru";
    }

    @PostMapping("/ecdsa/ru/generate-keys")
    public String generateEcdsaKeys(@ModelAttribute EcdsaRequest ecdsaRequest, Model model)
    {
        Map<String, String> keys = cryptoService.generateEcdsaKeys(ecdsaRequest.getKeySize());

        model.addAttribute("publicKey", keys.get("public"));
        model.addAttribute("privateKey", keys.get("private"));
        model.addAttribute("keySize", ecdsaRequest.getKeySize());
        model.addAttribute("text", ecdsaRequest.getText());

        return "ru/ecdsa_ru";
    }

    @PostMapping("/ecdsa/ru/sign")
    public String performEcdsaSign(@ModelAttribute EcdsaRequest ecdsaRequest, Model model)
    {
        String signature = cryptoService.ecdsaSign(ecdsaRequest.getText(), ecdsaRequest.getPrivateKey());

        model.addAttribute("result", signature);
        model.addAttribute("text", ecdsaRequest.getText());
        model.addAttribute("publicKey", ecdsaRequest.getPublicKey());
        model.addAttribute("privateKey", ecdsaRequest.getPrivateKey());
        model.addAttribute("keySize", ecdsaRequest.getKeySize());

        return "ru/ecdsa_ru";
    }

    @PostMapping("/ecdsa/ru/verify")
    public String performEcdsaVerify(@ModelAttribute EcdsaRequest ecdsaRequest, Model model)
    {
        boolean isVerified = cryptoService.ecdsaVerify(ecdsaRequest.getText(), ecdsaRequest.getSignature(), ecdsaRequest.getPublicKey());

        model.addAttribute("result", isVerified ? "Signature is valid" : "Signature is invalid");
        model.addAttribute("text", ecdsaRequest.getText());
        model.addAttribute("signature", ecdsaRequest.getSignature());
        model.addAttribute("publicKey", ecdsaRequest.getPublicKey());
        model.addAttribute("privateKey", ecdsaRequest.getPrivateKey());
        model.addAttribute("keySize", ecdsaRequest.getKeySize());

        return "ru/ecdsa_ru";
    }

    @GetMapping("/eddsa/ru")
    public String eddsaPage(Model model)
    {
        model.addAttribute("publicKey", "");
        model.addAttribute("privateKey", "");
        return "ru/eddsa_ru";
    }

    @PostMapping("/eddsa/ru/generate-keys")
    public String generateEdDsaKeys(Model model)
    {
        Map<String, String> keys = cryptoService.generateEd25519Keys();
        model.addAttribute("publicKey", keys.get("public"));
        model.addAttribute("privateKey", keys.get("private"));
        return "ru/eddsa_ru";
    }

    @PostMapping("/eddsa/ru/sign")
    public String signEdDsaMessage(@RequestParam String text,
                                   @RequestParam String privateKey,
                                   @RequestParam String publicKey,
                                   Model model)
    {
        String signature = cryptoService.ed25519Sign(text, privateKey);

        model.addAttribute("text", text);
        model.addAttribute("signature", signature);
        model.addAttribute("privateKey", privateKey);
        model.addAttribute("publicKey", publicKey);
        return "ru/eddsa_ru";
    }

    @PostMapping("/eddsa/ru/verify")
    public String verifyEdDsaSignature(@RequestParam String text,
                                       @RequestParam String signature,
                                       @RequestParam String publicKey,
                                       Model model)
    {
        boolean isValid = cryptoService.ed25519Verify(text, signature, publicKey);

        model.addAttribute("text", text);
        model.addAttribute("signature", signature);
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("result", isValid ? "Signature is valid" : "Signature is invalid");
        return "ru/eddsa_ru";
    }
}
