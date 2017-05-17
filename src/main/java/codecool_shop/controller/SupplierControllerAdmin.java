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

    public ModelAndView renderSupplier(Request request, Response response) {
        Map<Object, Object> params = new HashMap<>();
        try {
            params.put("supplierContainer", supplierDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ModelAndView(params, "admin/supplier/index");
    }

    public ModelAndView add(Request request, Response response) {
        Map<Object, Object> params = new HashMap<>();
        return new ModelAndView(params, "admin/supplier/add");
    }

    public ModelAndView addSupplierPost(Request request, Response response) throws SQLException {
        Map params = new HashMap<>();
        String name = request.queryParams("name");
        String address = request.queryParams("address");

        if(!name.isEmpty()) {
            ProductSupplier newSupplier = new ProductSupplier();
            newSupplier.setId(0);
            newSupplier.setName(name);
            newSupplier.setAddress(address);
            supplierDao.add(newSupplier);
            response.redirect("/admin/supplier");
        } else {
            params.put("errorContainer", "Supplier name cannot be empty");
            return new ModelAndView(params, "admin/supplier/add");
        }
        return new ModelAndView(params, "admin/supplier/add");
    }

    public ModelAndView editSupplier(Request request, Response response) throws SQLException  {
        Integer id = Integer.valueOf(request.params("id"));
        ProductSupplier productSupplier = supplierDao.getById(id);
        Map params = new HashMap<>();
        if (!(productSupplier == null)) {
            params.put("supplierContainer", productSupplier);
            return new ModelAndView(params, "admin/supplier/edit");
        }
        return new ModelAndView(params, "404");
    }

    public ModelAndView editSupplierPost(Request request, Response response) throws SQLException {
        String name = request.queryParams("name");
        String address = request.queryParams("address");
        Integer id = Integer.valueOf(request.params("id"));

        ProductSupplier editSupplier = supplierDao.getById(id);
        Map params = new HashMap<>();
        if (!name.isEmpty() && !(editSupplier == null)) {
            editSupplier.setName(name);
            editSupplier.setAddress(address);
            supplierDao.update(editSupplier);
            response.redirect("/admin/supplier");
        } else {
            params.put("supplierContainer", editSupplier);
            params.put("errorContainer", "Category name cannot be empty");
            return new ModelAndView(params, "admin/supplier/edit");
        }
        return new ModelAndView(params, "admin/supplier/edit");

    }
}
