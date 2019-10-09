import com.avaje.ebean.Ebean;
import models.Menus;
import models.*;
import play.Application;
import play.GlobalSettings;
import play.Logger;

import java.util.ArrayList;
import java.util.List;

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application application) {
        newData.insertData(application);
    }



    static class newData {
        public static void insertData(Application application) {
            Logger.info("Booting Application...");
            Menus menus = null;
            if (Menus.findAll().size() == 0) {

                menus = new Menus();
                menus.menuCode = "40";
                menus.menuName = "Payments";
                menus.menuType = "S";
                menus.menuparentId = "30";
                menus.menuRight = "rcvpayments";
                menus.menuSection = "Finance";
                menus.save();

                menus = new Menus();
                menus.menuCode = "70";
                menus.menuName = "Customer List";
                menus.menuType = "S";
                menus.menuparentId = "10";
                menus.menuRight = "listcustomer";
                menus.menuSection = "Members";
                menus.save();


            }

            Roles roles;
            if (Roles.findAll().size() == 0) {
                roles = new Roles();
                roles.rolename = "Admin";
                roles.rolecode = "1000";

                List<Menus> menusList = new ArrayList<>();
                for (int i = 0; i < Menus.findAll().size(); i++) {

                    menusList.add(Menus.findAll().get(i));
                }
                roles.menus = menusList;
                roles.save();
            }

            if (Roles.findAll().size() == 1) {
                roles = new Roles();
                roles.rolename = "Agent";
                roles.rolecode = "1001";
                List<Menus> allmenus = new ArrayList<>();
                List<Menus> menusList1 = Menus.findmenu("70");
                for (Menus menu : menusList1)
                    allmenus.add(menu);
                List<Menus> menusList10 = Menus.findmenu("10");
                for (Menus menu : menusList10)
                    allmenus.add(menu);
                roles.menus = allmenus;
                roles.save();
            }

            if (Roles.findAll().size() == 2) {
                roles = new Roles();
                roles.rolename = "User";
                roles.rolecode = "1002";

                List<Menus> menususers = new ArrayList<>();
                List<Menus> menusList2 = Menus.findmenu("70");
                for (Menus menu : menusList2)
                    menususers.add(menu);
                List<Menus> menusList11 = Menus.findmenu("40");
                for (Menus menu : menusList11)
                    menususers.add(menu);
                roles.menus = menususers;
                roles.save();
            }


            UserMst users = null;
            if (UserMst.findAll().size() == 0) {
                Logger.info("######Creating Users");
                users = new UserMst();
                users.UserName = "admin";
                users.Password = "1234";
                users.FirstName = "Demo ";
                users.LastName = "user ";
                users.MobileNo = "0700000000";
                users.isActive = "1";
                users.roles = Roles.findrole("1000");
                users.rolecode = "1000";
                users.save();

            }


            CustomersMst customers = null;
            if (CustomersMst.findAll().size() == 0) {
                Logger.info("##Creating Users");
                customers = new CustomersMst();
                customers.FirstName = "sharon";
                customers.LastName = "kandie";
                customers.MobileNo = "0708220000 ";
                customers.account= "1234567890";
                customers.meterno = "2525";
                customers.idnumber = "25263025";
                customers.plotNo = "A8";
                customers.regFee = "1000";
                customers.isActive = "1";
                customers.save();

            }


            PaymentsMst payments;
            if(PaymentsMst.findAll().size()==0){
                Logger.info("##Creating payments record");
                payments = new PaymentsMst();
                payments.previousreading="0";
                payments.currentreading="0";
                payments.consumption="0";
                payments.arrears="0";
                payments.outstanding="0";
                payments.fixedcharge = "20";
                payments.customersMst=CustomersMst.find.byId((long) 1);
                payments.save();
            }

        }
    }
}
