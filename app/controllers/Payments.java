package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Page;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.CustomersMst;
import models.Menus;
import models.PaymentsMst;
import models.UserMst;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.List;
import java.util.Map;

import static controllers.Application.isLoggedIn;

/**
 * Created by Alex on 9/12/2019.
 */
public class Payments extends Controller {

    public static Result paymentPG(){
        UserMst users = isLoggedIn();
        List<Menus> menus = null;
        if (users != null) {
            menus = users.roles.menus;
        }
        System.out.println("User Page#### ");
        return ok(payments.render("user",menus));
    }

    public static Result updateCustomerPG(Long id){
        UserMst users = isLoggedIn();
        if (users != null) {
            List<Menus> menus = users.roles.menus;
            CustomersMst customersMst = CustomersMst.findCustById(id);
            return ok(updateCustomer.render("",customersMst,menus));
        }
        System.out.println("Page#### ");
//        return ok(readings.render("user",menus));
        return redirect(routes.Payments.customerListPage());
    }

    public static Result createCustomerPG(){
        UserMst users = isLoggedIn();
        List<Menus> menus = null;
        if (users != null) {
            menus = users.roles.menus;
        }
        System.out.println("Create customer Page### ");
        return ok(createCustomer.render("customer",menus));
    }

    public static Result updateCustomer(Long id){
        int totalPayments = PaymentsMst.findAll().size();
        System.out.println("get cust data.....");
        UserMst users = isLoggedIn();
        List<Menus> menus = null;
        DynamicForm form = Form.form().bindFromRequest();
        CustomersMst customersMst = CustomersMst.find.byId(id);
        Ebean.update(customersMst);
        System.out.println("Updating Customer "+customersMst.idnumber);
        if (users != null) {
            menus = users.roles.menus;
            System.out.println("Updating Payments table.....");
            DynamicForm form1 = Form.form().bindFromRequest();
            form1.get();
            PaymentsMst pay = new PaymentsMst();
            pay.currentreading=form.get("currreading");
            Ebean.save(pay);
        }
        return redirect(routes.Payments.customerListPage());
    }

    public static Result customerListPage (){
        UserMst userMst = isLoggedIn();
        if (userMst != null) {
            List<Menus> menus = userMst.roles.menus;
            CustomersMst customer = CustomersMst.findCustById(Long.parseLong(session("id")));

            return ok(customerList.render("customers",customer,menus));
        }
        return ok(login.render());
    }


    public static Result customerList() {
        System.out.println("Fetching customers####");
        /**
         * Get needed params
         */
        Map<String, String[]> params = request().queryString();


        Integer totalUsers = CustomersMst.find.findRowCount();
        System.out.println("Total Rows: " +totalUsers);
        String filter = params.get("search[value]")[0];


        Integer pageSize = Integer.valueOf(params.get("length")[0]);
        Integer page = Integer.valueOf(params.get("start")[0]) / pageSize;

        /**
         * Get sorting order and column
         */
        String sortBy = "FirstName";
        String order = params.get("order[0][dir]")[0];

        switch (Integer.valueOf(params.get("order[0][column]")[0])) {
            case 0 :
                sortBy = "FirstName";
                break;
            case 1 :
                sortBy = "LastName";
                break;
            case 2 :
                sortBy = "cid";
                break;
        }
        System.out.println("UserList 1#######");


        /**
         * Get page to show from database
         * It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        Page<CustomersMst> userPage = CustomersMst.find.where(

                Expr.or(
                        Expr.ilike("account", "%" + filter + "%"),
                        Expr.or(
                                Expr.ilike("FirstName", "%" + filter + "%"),
                                Expr.ilike("cid", "%" + filter + "%")
                        )
                )

        )
                .orderBy(sortBy + " " + order + ", cid " + order)
                .findPagingList(pageSize).setFetchAhead(false)
                .getPage(page);

        Integer iTotalDisplayRecords = userPage.getTotalRowCount();
        System.out.println("UserList 2#######");

        /**
         * Construct the JSON to return
         */
        ObjectNode result = Json.newObject();
        System.out.println("UserList 3#######");
        result.put("draw", Integer.valueOf(params.get("draw")[0]));
        result.put("recordsTotal", totalUsers);
        result.put("recordsFilter", iTotalDisplayRecords);

        ArrayNode an = result.putArray("data");
        System.out.println("UserList 4#######");
        for (CustomersMst c : userPage.getList()) {
            ObjectNode row = Json.newObject();
            row.put("0", c.idnumber);
            row.put("1", c.FirstName+" "+c.LastName);
            row.put("2", c.account);
            row.put("3", c.meterno);
            row.put("4", c.plotNo);
            row.put("5", c.MobileNo);
            an.add(row);
            System.out.println("UserTable Json--------------- " +an);
        }
        System.out.println("UserList 5#######");
        return ok(result);
    }

    public static Result createCustomer() {
        UserMst users = isLoggedIn();
        if (users != null) {
            List<Menus> menus = users.roles.menus;

            Logger.info("Creating New Customer.....");
            DynamicForm form = Form.form().bindFromRequest();
            form.get();
            CustomersMst cust = new CustomersMst();
            cust.FirstName = form.get("fname");
            cust.LastName = form.get("lname");
            cust.idnumber = form.get("idno");
            cust.MobileNo = form.get("phone");
            cust.account = form.get("account");
            cust.meterno = form.get("meterno");
            cust.plotNo = form.get("plotno");
            cust.regFee = form.get("regfee");
            Ebean.save(cust);
//            return ok(index.render("home",menus));
            return ok(createCustomer.render("customer",menus));

        }
        return ok(login.render());
    }
    
}
