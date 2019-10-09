package models;

import play.Logger;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 9/11/2019.
 */
@Entity
public class CustomersMst extends Model {

    @Id
    public Long cid ;

    @Constraints.Required
    public String FirstName;

    @Constraints.Required
    public String LastName;

    @Constraints.Required
    public String account;

    @Constraints.Required
    public String meterno;

    @Constraints.Required
    public String MobileNo;

    @Constraints.Required
    public String idnumber;

    @Constraints.Required
    public String plotNo;

    @Constraints.Required
    public String regFee;

  /*  @ManyToMany
    public PaymentsMst paymentsMst;*/

    @Constraints.Required
    public String isActive;

    @Formats.DateTime(pattern="dd-MM-yyyy")
    public Date CreatedOn = new Date();

    public static Model.Finder<Long, CustomersMst> find = new Model.Finder<Long, CustomersMst>(Long.class, CustomersMst.class);

    public static CustomersMst authenticate(String UserName, String Password) {
        Logger.info("Checking Login User in Storage###");
        return find.where().eq("UserName", UserName).eq("password", Password).findUnique();
    }

    public static CustomersMst findCustById(Long cid) {
        return find.where().eq("cid", cid).findUnique();
    }

    public static List<CustomersMst> findAll(){
        return find.all();
    }
}
