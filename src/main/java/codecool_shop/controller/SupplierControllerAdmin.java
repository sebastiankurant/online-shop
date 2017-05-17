package codecool_shop.controller;

import codecool_shop.dao.SupplierDao;
import codecool_shop.dao.SupplierInterface;
import codecool_shop.model.ProductSupplier;
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
            params.put("supplierContainer", supplierDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ModelAndView(params, "admin/supplier/index");
    }

    public ModelAndView add(Request req, Response res) {
        Map<Object, Object> params = new HashMap<>();
        return new ModelAndView(params, "admin/supplier/add");
    }

    public ModelAndView editSupplier(Request req, Response res) throws SQLException  {
        Integer id = Integer.valueOf(req.params("id"));
        ProductSupplier productSupplier = supplierDao.getById(id);
        Map params = new HashMap<>();
        if (!(productSupplier == null)) {
            params.put("supplierContainer", productSupplier);
            return new ModelAndView(params, "admin/supplier/edit");
        }
        return new ModelAndView(params, "404");
    }

}
