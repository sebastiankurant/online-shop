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

public class ProductControllerAdmin {
    private ProductInterface productDao = new ProductDao();
    private CategoryInterface categoryDao = new CategoryDao();
    private MetaInterface productMeta = new ProductDao();
    private SupplierInterface supplierDao = new SupplierDao();
    private UtilityClass utilityClass = new UtilityClass();

    public ModelAndView renderProducts(Request req, Response res) throws SQLException {
        //Get products from database by Dao
        Map params = new HashMap<>();
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
        return new ModelAndView(params, "/admin/products/index");
    }

    public ModelAndView addProduct(Request req, Response res) {
        Map params = new HashMap<>();
        try {
            List<ProductCategory> availableCategory = categoryDao.getAll();
            List<ProductSupplier> availableSupplier = supplierDao.getAll();
            params.put("availableCategory", availableCategory);
            params.put("availableSupplier", availableSupplier);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ModelAndView(params, "/admin/products/add");
    }

    public ModelAndView addProductPost(Request req, Response res) throws SQLException {
        String name;
        String description;
        ProductSupplier supplier = null;
        String postDate;
        Integer price = 0;
        Integer supplierId = 0;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Map params = new HashMap<>();
        try {
            List<ProductCategory> availableCategory = categoryDao.getAll();
            params.put("availableCategory", availableCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Map<String, String> formInputs = getParamsFromInputStream(req, res);
        name = formInputs.get("name");
        description = formInputs.get("description");
        postDate = formInputs.get("date");
        String filename = formInputs.get("filename");
        String url = utilityClass.getDomainUrl(req) + filename;
        String[] categoryList = req.queryParamsValues("category");
        try{
            price = Integer.valueOf(req.queryParams("price"));
            supplierId = Integer.valueOf(req.queryParams("supplier"));
            supplier = supplierDao.getById(supplierId);
        }catch (Exception e){
            e.printStackTrace();
        }
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
            Product newProduct = new Product(name, description, date, catList, url, supplier, price);
            productDao.add(newProduct);
            Integer eventId = productDao.getByName(newProduct.getName());
            newProduct.setId(eventId);
            productMeta.addMeta(newProduct);
            res.redirect("/admin/products/");
        } else {
            params.put("errorContainer", "Name or Date is not invalid");
            return new ModelAndView(params, "admin/products/add");
        }
        return new ModelAndView(params, "/admin/products/add");
    }

    public ModelAndView editProduct(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.params("id"));
        Product productToEdit = productDao.getById(id);
        Map params = new HashMap<>();
        if (!(productToEdit == null)) {
            try { // Set attributes which are already checked
                List<ProductCategory> availableCategory = categoryDao.getAll();
                for (ProductCategory availableCat : availableCategory) {
                    for (ProductCategory cat : productToEdit.getCategories()) {
                        if (availableCat.getId() == cat.getId()) {
                            availableCat.setEqual(true);
                        }
                    }
                }
                productToEdit.setCategories(availableCategory);
                params.put("productContainer", productToEdit);
                return new ModelAndView(params, "/admin/products/edit");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return new ModelAndView(params, "404");
    }

    public String editProductPost(Request req, Response res) throws SQLException {
        Integer id = Integer.valueOf(req.params("id"));
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String name = req.queryParams("name");
//        String description = req.queryParams("description");
//        String postDate = req.queryParams("date");
        String[] categoryList = req.queryParamsValues("category");
        Date date = null;
        Product editProduct = productDao.getById(id);
        Map<String, String> formInputs = getParamsFromInputStream(req, res);
        String name = formInputs.get("name");
        String description = formInputs.get("description");
        String postDate = formInputs.get("date");
        String filename = formInputs.get("filename");
        String url = utilityClass.getDomainUrl(req) + filename;
        try {
            date = format.parse(postDate);
            System.out.println(postDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!name.isEmpty() && date != null && editProduct != null) {
            List<ProductCategory> catList = new ArrayList<>();
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
            productDao.update(editProduct);
            productMeta.removeMeta(editProduct);
            productMeta.addMeta(editProduct);
            res.redirect("/admin/products/");
            return "Sucess";
        }
        return "Error";
    }

    public String removeProduct(Request req, Response res) throws SQLException {
        System.out.println("Remove Product");
        Integer id = Integer.valueOf(req.params("id"));
        System.out.println(id);
        Product productToDelete = productDao.getById(id);

        if (!(productToDelete == null)) {
            productDao.remove(productToDelete.getId());
            productMeta.removeMeta(productToDelete);
            res.redirect("/admin/products/");
            return "works";
        }
        res.redirect("/admin/products/");
        return "";
    }

    public ModelAndView pastProducts(Request req, Response res) throws SQLException {
        Map params = new HashMap<>();
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

    public ModelAndView filterCategory(Request req, Response res) throws SQLException {
        Map params = new HashMap<>();
        String category = req.queryParams("cat");
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
            return new ModelAndView(params, "/admin/products/index");
        }
        return new ModelAndView(params, "/admin/products/index");
    }


    public Map<String, String> getParamsFromInputStream(Request req, Response res) {
        Map<String, String> inputsMap = new HashMap<>();
        try {
            File file = new File("src/main/resources");
            String absolutePath = file.getAbsolutePath();
            req.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                    new MultipartConfigElement(absolutePath + "/public/tmp/", 100000000, 100000000, 1024));

            Part partName = req.raw().getPart("name");
            Part partDescription = req.raw().getPart("description");
            Part partDate = req.raw().getPart("date");
            Part uploadedFile = req.raw().getPart("file");
//            Part partCategory = req.raw().getPart("category");
            String filename = req.raw().getPart("file").getSubmittedFileName();
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
