package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.CustomersMst;
import models.Menus;
import models.UserMst;
import play.*;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.util.List;

public class Application extends Controller {
    public static Result index() {
        UserMst users = isLoggedIn();
        int totalUsers = UserMst.findAll().size();
        int totalMembers = CustomersMst.findAll().size();
        int allTerminals= UserMst.find.findRowCount();
//        int totalPayments = Pay.findAll().size();
        if(users!=null){
            Logger.info("LoggedIn### "+isLoggedIn().UserName);
            int menuTotal = Menus.findAll().size();
            Logger.info("###Menu Total "+menuTotal);
            List<Menus> menus = users.roles.menus;
            return ok(index.render("",menus,totalUsers,totalMembers,allTerminals));
//            return ok("here at dash");
        }

        return ok(login.render());
    }

    public static Result login() {
        return ok(login.render());
    }


    public static void setSessions(UserMst users) {
        session().clear();
        session("id", users.ID.toString());
    }

    public static UserMst isLoggedIn() {
        String id = session("id");

        try {
            UserMst users = UserMst.find.byId(Long.parseLong(id));
            return users;
        } catch (Exception e) {
            return null;
        }
    }

    public static Result endSession() {
        session().clear();
        Logger.info("Session Ended####");
        return redirect(routes.Application.login());
    }

    public static Result authenticateUser(){
        DynamicForm form = DynamicForm.form().bindFromRequest();
        String username = form.get("username");
        String password = form.get("password");
        ObjectNode loginRes;
        loginRes = Json.newObject();
        Logger.info("UserName####### "+form.get("username"));

//        Logger.info("UserName### "+getUserIP());
        if (username.trim() !=null){
            UserMst userMst = UserMst.authenticate(username,password);
            if (userMst !=null){
                setSessions(userMst);
                Logger.info("Session Started####");
                Logger.info("#####RoleID "+userMst.rolecode);
                Logger.info("#####SessionID "+ session());
                try{
                    if (userMst.rolecode.equalsIgnoreCase("1000")) {

                        return redirect(routes.Application.index());
//                        return ok("Hello World");
                    }
                }catch (Exception e){
                    Logger.info("#######ex here::"+e);
                    session().clear();
                    Logger.info("Unknown Error!");
                }}
            loginRes.put("responseCode", Json.toJson("200"));
            loginRes.put("responseMessage",Json.toJson("Invalid Username and/or Password"));
            Logger.info(String.valueOf(loginRes));
//            flash().put("error","Invalid Username and/or Password");

            session().clear();
            return redirect(routes.Application.login());

        }
        return null;
    }

}
