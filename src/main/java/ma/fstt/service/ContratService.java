package ma.fstt.service;

import lombok.extern.slf4j.Slf4j;
import ma.fstt.contracts.Src_main_resources_solidity_MarketPlace_sol_MarketPlace;
import ma.fstt.entity.Immobilier;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import java.math.BigInteger;

@Slf4j
@Service
public class ContratService {
    Src_main_resources_solidity_MarketPlace_sol_MarketPlace contrat1 ;

    private static String CONTRACT_ADDRESS ="0x24f5c35D78F13AE0823C171454A27a72662B8b8b" ;

    Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:7545"));

    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    String privatekey = "1aae372add4ea4e85fa779d90bdef88e1e2f4406d55421e71a3d31a5b57fd112";
    BigInteger privkey = new BigInteger(privatekey, 16);
    ECKeyPair ecKeyPair = ECKeyPair.create(privkey);
    Credentials credentials = Credentials.create(ecKeyPair);
    TransactionManager transactionManager =new ClientTransactionManager(web3j , "d2601389278084ab26c608563dfb89f6063ed4d13822e607ab1ed7e3e4710290");
    //Deploy contrat
    public String deployContrat(){
        try {
            this.contrat1 = Src_main_resources_solidity_MarketPlace_sol_MarketPlace.deploy(web3j,credentials,GAS_PRICE,GAS_LIMIT).send();
            this.CONTRACT_ADDRESS = contrat1.getContractAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.CONTRACT_ADDRESS;
    }
    //Add Immobilier and sell
    public void sellImmobilier(String _name, String _description,String _localisation, BigInteger _price , BigInteger _surface) throws Exception {
        this.contrat1 = Src_main_resources_solidity_MarketPlace_sol_MarketPlace.load(CONTRACT_ADDRESS,web3j,credentials,GAS_PRICE,GAS_LIMIT);
        this.contrat1.sellImmobilier(_name,_description,_localisation,_price,_surface).send();
    }

    //Afficher le nombre des articles
    public void afficherNombreImmobilier(){
        this.contrat1 = Src_main_resources_solidity_MarketPlace_sol_MarketPlace.load(CONTRACT_ADDRESS,web3j,credentials,GAS_PRICE,GAS_LIMIT);
        try {
            log.warn("Nombre des articles   : "+this.contrat1.getNumberOfArticles().send());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Immobilier getImmobilier(long id){
        Immobilier imm = new Immobilier();
        this.contrat1 = Src_main_resources_solidity_MarketPlace_sol_MarketPlace.load(CONTRACT_ADDRESS,web3j,credentials,GAS_PRICE,GAS_LIMIT);
        try{
            Tuple8 tp =this.contrat1.immobiliers(BigInteger.valueOf(id)).send();
            imm.setId((BigInteger) tp.component1());
            imm.setName((String) tp.component4());
            imm.setDescription((String) tp.component5());
            imm.setLocalisation((String) tp.component6());
            imm.setOwnerAddress((String) tp.component3());
            imm.setPrice((BigInteger) tp.component7());
            imm.setSurface((BigInteger) tp.component8());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return imm;

    }

    public void buyImmobilier(long id) throws Exception {
        this.contrat1 = Src_main_resources_solidity_MarketPlace_sol_MarketPlace.load(CONTRACT_ADDRESS,web3j,credentials,GAS_PRICE,GAS_LIMIT);
        this.contrat1.buyArticle(BigInteger.valueOf(id)).send();
    }



}
