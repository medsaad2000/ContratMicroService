package ma.fstt.controller;

import ma.fstt.entity.Immobilier;
import ma.fstt.service.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class ContratController {
    @Autowired
    ContratService cs;

    @PostMapping("/sellArticle")
    public String sellArticle(@RequestParam String name , @RequestParam String description,@RequestParam String localisation ,@RequestParam long price , @RequestParam long surface ){
        try {
            cs.sellImmobilier(name , description ,localisation, BigInteger.valueOf(price), BigInteger.valueOf(surface));
            System.out.println("Article posted");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Article Posted ";
    }

    @PostMapping("/getarticle")
    public Immobilier getArticles(@RequestParam long id) {

        return cs.getImmobilier(id);
    }

    @PostMapping("/buyarticle")
    public String buyArticle(@RequestParam long id) {
        try {
            cs.buyImmobilier(id);
            System.out.println("Article Buyed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Article Buyed";
    }


}
