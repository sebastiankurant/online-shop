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
public class SupplierControllerAdmin extends BaseController{

    private SupplierInterface supplierDao = new SupplierDao();

    public ModelAndView renderSupplier() {
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("supplierContainer", supplierDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return render(params, "admin/supplier/index");
    }

    public ModelAndView add() {
        return render("admin/supplier/add");
    }

    public ModelAndView addSupplierPost(Request request, Response response) throws SQLException {
        Map<String, Object> params = new HashMap<>();
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
            return render(params, "admin/supplier/add");
        }
        return render(params, "admin/supplier/add");
    }

    public ModelAndView editSupplier(Request request) throws SQLException  {
        Integer id = Integer.valueOf(request.params("id"));
        ProductSupplier productSupplier = supplierDao.getById(id);
        Map<String, Object> params = new HashMap<>();
        if (!(productSupplier == null)) {
            params.put("supplierContainer", productSupplier);
            return render(params, "admin/supplier/edit");
        }
        return render(params, "404");
    }

    public ModelAndView editSupplierPost(Request request, Response response) throws SQLException {
        String name = request.queryParams("name");
        String address = request.queryParams("address");
        Integer id = Integer.valueOf(request.params("id"));

        ProductSupplier editSupplier = supplierDao.getById(id);
        Map<String, Object> params = new HashMap<>();
        if (!name.isEmpty() && !(editSupplier == null)) {
            editSupplier.setName(name);
            editSupplier.setAddress(address);
            supplierDao.update(editSupplier);
            response.redirect("/admin/supplier");
        } else {
            params.put("supplierContainer", editSupplier);
            params.put("errorContainer", "Supplier name cannot be empty");
            return render(params, "admin/supplier/edit");
        }
        return render(params, "admin/supplier/edit");

    }

    public String removeSupplier(Request request, Response response) throws SQLException {
        Integer id = Integer.valueOf(request.params("id"));
        ProductSupplier supplierToRemove = supplierDao.getById(id);
        if (null != supplierToRemove) {
            supplierDao.remove(id);
            response.redirect("/admin/supplier/");
            return "Success";
        } else {
            response.redirect("/admin/supplier/");
            return "Mission Failed";
        }
    }
}
