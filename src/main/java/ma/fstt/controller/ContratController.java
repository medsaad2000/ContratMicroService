package ma.fstt.controller;

import ma.fstt.entity.Annonce;
import ma.fstt.entity.AnnonceKey;
import ma.fstt.entity.Categorie;
import ma.fstt.entity.Immobilier;
import ma.fstt.service.AnnonceService;
import ma.fstt.service.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/contrat")
@CrossOrigin(origins = {"http://localhost:4200"})
@EnableFeignClients
public class ContratController {
    @Autowired
    ContratService cs;
    @Autowired
    AnnonceService as ;

//Pour Ajouter un immobilier en utilisant request parametre
//    @PostMapping("/sellArticle")
//    public String sellArticle(@RequestParam String name , @RequestParam String description,@RequestParam String localisation ,@RequestParam long price , @RequestParam long surface ){
//        try {
//            cs.sellImmobilier(name , description ,localisation, BigInteger.valueOf(price), BigInteger.valueOf(surface));
//            System.out.println("Article posted");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "Article Posted ";
   // }
    //Pour Ajouter un immobilier en utilisant request body
    @PostMapping("/sellArticleee/{id}")
    public Immobilier sellArticleee(@RequestBody AnnonceKey annonceKey,@PathVariable String id){
        Categorie cat = as.getCategorieById(id);
        Annonce annonce = new Annonce(null,annonceKey.getImmobilier().getName(),"http://test/studio",annonceKey.getImmobilier().getDescription(),annonceKey.getImmobilier().getPrice().doubleValue(),cat);
        try {

            cs.sellImmobilier(annonceKey.getImmobilier().getName(), annonceKey.getImmobilier().getDescription(), annonceKey.getImmobilier().getLocalisation(), annonceKey.getImmobilier().getPrice(), annonceKey.getImmobilier().getSurface(),annonceKey.getPrivateKey());
            as.addAnnonceC(annonce);
            System.out.println("Article posted");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return annonceKey.getImmobilier();
    }
//Get un immobilier
    @PostMapping("/getarticle")
    public Immobilier getArticles(@RequestParam long id) {

        return cs.getImmobilier(id);
    }
//Get un immobilier
    @GetMapping("/getarticleee/{id}")
    public Immobilier getArticleeees(@PathVariable long id) {

        return cs.getImmobilier(id);
    }
//Acheter un immobilier
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
//Get le nombre des immobilier
    @GetMapping("/getnumarticle")
    public int getNumArticle(){
        try{
            return cs.getNombreImmobilier();
        }catch (Exception e) {
            e.printStackTrace();
            return 0 ;
        }

    }
//Get tous les immobiliers
    @GetMapping("getAllImmobilier")
    public ArrayList<Immobilier> getAllImmobilier(){
        return cs.getAllImmobilier();
    }


}
