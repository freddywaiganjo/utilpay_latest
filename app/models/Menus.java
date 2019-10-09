package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import views.html.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Entity
public class Menus extends Model {

    @Id
    public long Id;

    @Constraints.Required
    public String menuCode;

    @Constraints.Required
    public String menuName;

    @Constraints.Required
    public String menuType;
    @Constraints.Required
    public String menuparentId;

    @Constraints.Required
    public String menuRight;

    @Constraints.Required
    public String menuSection;



    @Constraints.Required
    public String isActive="1";

    public static Model.Finder<Long, Menus> findMenus = new Model.Finder<Long, Menus>(Long.class, Menus.class);

    public static List<Menus> findAll() {
        return  findMenus.all();}

    /*public String render(String hello, Users user) {
        return null;
    }*/
    public static List findmenu(String menucode) {
        System.out.println("menuCode##");
        return findMenus.where().eq("menuCode", menucode).findList();
    }

}

