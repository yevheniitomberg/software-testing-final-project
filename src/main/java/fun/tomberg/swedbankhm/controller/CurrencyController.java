package fun.tomberg.swedbankhm.controller;

import fun.tomberg.swedbankhm.service.implementation.ApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final ApiServiceImpl apiService;

    @GetMapping("")
    public String getCurrency(Model model) throws IOException {
        model.addAttribute("currencies", apiService.getAllCurrencies());
        return "currency-view";
    }

    @PostMapping("")
    public String postCurrency(@RequestParam("currencyFromSelect") String currencyFrom,
                               @RequestParam("currencyToSelect") String currencyTo,
                               @RequestParam("amount") double amount,
                               Model model) throws IOException {
        model.addAttribute("currencies", apiService.getAllCurrencies());
        model.addAttribute("from", currencyFrom);
        model.addAttribute("to", currencyTo);
        model.addAttribute("amount", amount);
        model.addAttribute("rate", String.format("%.2f", apiService.getSpecificExchangeRate(currencyFrom, currencyTo, amount)));
        return "currency-view";
    }
}
