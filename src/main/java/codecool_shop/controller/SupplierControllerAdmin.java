package codecool_shop.controller;

import codecool_shop.dao.SupplierDao;
import codecool_shop.dao.SupplierInterface;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by monika on 17.05.17.
 */
public class SupplierControllerAdmin {

    private SupplierInterface supplierDao = new SupplierDao();

    public ModelAndView renderSupplier(Request req, Response res) {
        Map<Object, Object> params = new HashMap<>();
        try {
            params.put("categoryContainer", supplierDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ModelAndView(params, "admin/supplier/index");
    }

}
