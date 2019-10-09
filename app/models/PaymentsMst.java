package models;

import play.Logger;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 9/11/2019.
 */
@Entity
public class PaymentsMst extends Model {

    @Id
    public Long ID ;

    @Constraints.Required
    public String previousreading;

    @Constraints.Required
    public String currentreading;

    @Constraints.Required
    public String consumption;

    @Constraints.Required
    public String arrears;

    @Constraints.Required
    public String outstanding;

    @Constraints.Required
    public String fixedcharge;

    @ManyToOne
    public CustomersMst customersMst;


    @Formats.DateTime(pattern="dd-MM-yyyy")
    public Date CreatedOn = new Date();

    public static Model.Finder<Long, PaymentsMst> find = new Model.Finder<Long, PaymentsMst>(Long.class, PaymentsMst.class);

    public static PaymentsMst authenticate(String UserName, String Password) {
        Logger.info("Checking Login User in Storage###");
        return find.where().eq("UserName", UserName).eq("password", Password).findUnique();
    }

    public static List<PaymentsMst> findAll(){
        return find.all();
    }
}
