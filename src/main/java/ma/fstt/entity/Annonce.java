package ma.fstt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Annonce {
    private BigInteger id;

    private String titre_anc;
    //private Date date_anc;
    private String url_img;
    private String description;
    private double prix_anc;
}
