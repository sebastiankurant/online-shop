package codecool_shop.controller;

import codecool_shop.dao.*;
import codecool_shop.model.Product;
import codecool_shop.model.ProductCategory;
import codecool_shop.model.ProductSupplier;
import codecool_shop.utilities.UtilityClass;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;

public class ProductControllerAdmin extends BaseController {
    private ProductInterface productDao = new ProductDao();
    private CategoryInterface categoryDao = new CategoryDao();
    private MetaInterface productMeta = new ProductDao();
    private SupplierInterface supplierDao = new SupplierDao();
    private UtilityClass utilityClass = new UtilityClass();

    public ModelAndView renderProducts(Request request, Response response) throws SQLException {
        //Get products from database by Dao
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("productContainer", productDao.getAll());
            params.put("categoryAvailable", categoryDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();
        UtilityClass utilityClass = new UtilityClass();
        params.put("UtilityClass", utilityClass);
        params.put("currentDate", currentDate);
        return render(params, "/admin/products/index");
    }

    public ModelAndView addProduct(Request request, Response response) {
        Map<String, Object> params = new HashMap<>();
        try {
            List<ProductCategory> availableCategory = categoryDao.getAll();
            List<ProductSupplier> availableSupplier = supplierDao.getAll();
            params.put("availableCategory", availableCategory);
            params.put("availableSupplier", availableSupplier);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return render(params, "/admin/products/add");
    }

    public ModelAndView addProductPost(Request request, Response response) throws SQLException {
        String name;
        String description = null;
        String postDate;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> params = new HashMap<>();
        try {
            List<ProductCategory> availableCategory = categoryDao.getAll();
            params.put("availableCategory", availableCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Map<String, String> formInputs = getParamsFromInputStream(request, response);
        name = formInputs.get("name");
        description = formInputs.get("description");
        postDate = formInputs.get("date");
        String filename = formInputs.get("filename");
        String url = utilityClass.getDomainUrl(request)+filename;
        String[] categoryList = request.queryParamsValues("category");
        Integer price = Integer.valueOf(request.queryParams("price"));
        Integer supplierId = Integer.valueOf(request.queryParams("supplier"));
        ProductSupplier supplier = supplierDao.getById(supplierId);
        Date date = null;
        try {
            date = format.parse(postDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!name.isEmpty() && date != null) {
            List<ProductCategory> catList = new ArrayList<>();
            if (categoryList != null) {
                for (String category_slug : categoryList) {
                    ProductCategory tempCat = categoryDao.getBySlug(category_slug);
                    catList.add(tempCat);
                }
            }
            Product newProduct = new Product(name, description, date, catList,url,supplier, price);
            productDao.add(newProduct);
            Integer eventId = productDao.getByName(newProduct.getName());
            newProduct.setId(eventId);
            productMeta.addMeta(newProduct);
            response.redirect("/admin/products/");
        } else {
            params.put("errorContainer", "Name or Date is not invalid");
            return render(params, "admin/products/add");
        }
        return render(params, "/admin/products/add");
    }

    public ModelAndView editProduct(Request request, Response response) throws SQLException {
        Integer id = Integer.valueOf(request.params("id"));
        Product productToEdit = productDao.getById(id);
        Map<String, Object> params = new HashMap<>();
        if (!(productToEdit == null)) {
            try { // Set attributes which are already checked
                List<ProductCategory> availableCategory = categoryDao.getAll();
                List<ProductSupplier> availableSupplier = supplierDao.getAll();
                for (ProductCategory availableCat : availableCategory) {
                    for (ProductCategory cat : productToEdit.getCategories()) {
                        if (availableCat.getId() == cat.getId()) {
                            availableCat.setEqual(true);
                        }
                    }
                }
                productToEdit.setCategories(availableCategory);
                params.put("productContainer", productToEdit);
                params.put("availableSupplier", availableSupplier);

                return render(params, "/admin/products/edit");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return render(params, "404");
    }

    public String editProductPost(Request request, Response response) throws SQLException {
        Integer id = Integer.valueOf(request.params("id"));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String name = req.queryParams("name");
//        String description = req.queryParams("description");
//        String postDate = req.queryParams("date");
        String[] categoryList = request.queryParamsValues("category");
        Date date = null;
        Product editProduct = productDao.getById(id);
        Map<String, String> formInputs = getParamsFromInputStream(request, response);
        String name = formInputs.get("name");
        String description = formInputs.get("description");
        String postDate = formInputs.get("date");
        String filename = formInputs.get("filename");
        Integer supplierId = Integer.valueOf(request.queryParams("supplier"));
        String url = utilityClass.getDomainUrl(request)+filename;

        try {
            date = format.parse(postDate);
            System.out.println(postDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!name.isEmpty() && date != null && editProduct != null) {
            List<ProductCategory> catList = new ArrayList<>();
            ProductSupplier supplier = new SupplierDao().getById(supplierId);
            if (categoryList != null) {
                for (String category_slug : categoryList) {
                    ProductCategory tempCat = categoryDao.getBySlug(category_slug);
                    catList.add(tempCat);
                }
            }
            System.out.println(catList);
            editProduct.setName(name);
            editProduct.setDescription(description);
            editProduct.setDate(date);
            editProduct.setCategories(catList);
            editProduct.setUrl(url);
            editProduct.setSupplier(supplier);
            productDao.update(editProduct);
            productMeta.removeMeta(editProduct);
            productMeta.addMeta(editProduct);
            response.redirect("/admin/products/");
            return "Sucess";
        }
        return "Error";
    }

    public String removeProduct(Request request, Response response) throws SQLException {
        System.out.println("Remove Product");
        Integer id = Integer.valueOf(request.params("id"));
        System.out.println(id);
        Product productToDelete = productDao.getById(id);

        if (!(productToDelete == null)) {
            productDao.remove(productToDelete.getId());
            productMeta.removeMeta(productToDelete);
            response.redirect("/admin/products/");
            return "works";
        }
        response.redirect("/admin/products/");
        return "";
    }

    public ModelAndView pastProducts(Request request, Response response) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        try {
            params.put("productContainer", productDao.getAllPast());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();
        UtilityClass utilityClass = new UtilityClass();
        params.put("UtilityClass", utilityClass);
        params.put("currentDate", currentDate);
        return new ModelAndView(params, "/admin/products/index");
    }

    public ModelAndView filterCategory(Request request, Response response) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        String category = request.queryParams("cat");
        ProductCategory catObject = null;
        try {
            catObject = categoryDao.getById(Integer.valueOf(category));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (catObject != null) {
            try {
                List<ProductCategory> availableCategory = categoryDao.getAll();
                params.put("categoryAvailable", availableCategory);
                params.put("productContainer", productDao.getByAllCategory(catObject));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            params.put("UtilityClass", utilityClass);
            params.put("currentDate", new Date());
            return render(params, "/admin/products/index");
        }
        return render(params, "/admin/products/index");
    }


    public Map<String, String> getParamsFromInputStream(Request request, Response response) {
        Map<String, String> inputsMap = new HashMap<>();
        try {
            File file = new File("src/main/resources");
            String absolutePath = file.getAbsolutePath();
            request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement(absolutePath + "/public/tmp/", 100000000, 100000000, 1024));

            Part partName = request.raw().getPart("name");
            Part partDescription = request.raw().getPart("description");
            Part partDate = request.raw().getPart("date");
            Part uploadedFile = request.raw().getPart("file");
//            Part partCategory = req.raw().getPart("category");
            String filename = request.raw().getPart("file").getSubmittedFileName();
            inputsMap.put("name", utilityClass.getStringFromInputStream(partName));
            inputsMap.put("description", utilityClass.getStringFromInputStream(partDescription));
            inputsMap.put("date", utilityClass.getStringFromInputStream(partDate));
            String fileName = filename;
            inputsMap.put("filename", fileName);
            if (!fileName.equals("") && fileName != null) {
                try (final InputStream in = uploadedFile.getInputStream()) {
                    File f = new File(absolutePath + "/public/images/" + filename);
                    if (f.exists() && !f.isDirectory()) {
                        file.delete();
                    } else {
                        Files.copy(in, Paths.get(absolutePath + "/public/images/" + fileName));
                        uploadedFile.delete();
                    }
                }
            }

            return inputsMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
