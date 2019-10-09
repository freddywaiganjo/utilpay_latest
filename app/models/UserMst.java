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
public class UserMst extends Model {

    @Id
    public Long ID ;

    @Constraints.Required
    public String UserName;

    @Constraints.Required
    public String FirstName;

    @Constraints.Required
    public String LastName;

    @Constraints.Required
    public String MobileNo;

    @Constraints.Required
    public String Password;

    @Constraints.Required
    public String isActive;

    @Constraints.Required
    public String rolecode;

    @ManyToOne
    public Roles roles;

    @Formats.DateTime(pattern="dd-MM-yyyy")
    public Date CreatedOn = new Date();

    public static Model.Finder<Long, UserMst> find = new Model.Finder<Long, UserMst>(Long.class, UserMst.class);

    public static UserMst authenticate(String UserName, String Password) {
        Logger.info("Checking Login User in Storage###");
        return find.where().eq("UserName", UserName).eq("password", Password).findUnique();
    }

    public static List<UserMst> findAll(){
        return find.all();
    }
}
