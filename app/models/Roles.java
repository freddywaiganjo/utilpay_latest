package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.*;



@Entity
public class Roles extends Model {

    @Id
    public Long Id;

    @Constraints.Required
    public String rolename;

    @Constraints.Required
    public String rolecode;

    @Constraints.Required
    public String status;

    @Constraints.Required
    public String date;

    @Constraints.Required
    public String isActive="1";

    @ManyToMany
    public List<Menus> menus = new ArrayList<Menus>();

    public static Model.Finder<Long,Roles> find = new Model.Finder<Long,Roles>(Long.class,Roles.class);


    public static List<Roles> findAll(){

        return find.all();
    }
    public static Roles findRoleExists(String rolename,String rolecode){

        return find.where().eq("rolename",rolename)
                .eq("rolecode",rolecode)
                .findUnique();
    }
    public static Roles findrole(String rolecode) {

        return find.where().eq("rolecode",rolecode).findUnique();
    }

    public static Map<String, String> options(){
        LinkedHashMap<String,String> options=new LinkedHashMap<String,String>();
        for(Roles c:Roles.find.
                where().eq("isActive","1")
                .orderBy("rolename").findList()){
            options.put(Long.toString(c.Id),c.rolename );
        }

        return options;
    }



}
