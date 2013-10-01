package controllers;

import models.Product;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;
import views.html.products.*;

import java.util.List;

public class Products extends Controller {

  private static final Form<Product> productForm = Form.form(Product.class);

  public static Result list() {
    List<Product> products = Product.findAll();
    return ok(list.render(products));
  }

  public static Result newProduct() {
    return ok(details.render(productForm));
  }

  public static Result details(String ean) {
    final Product product = Product.findByEan(ean);
    if (product == null) {
      return notFound(String.format("Product %s does not exist.", ean));
    }

    Form<Product> filledForm = productForm.fill(product);
    return ok(details.render(filledForm));
  }

  public static Result save() {
    Form<Product> boundForm = productForm.bindFromRequest();
    if(boundForm.hasErrors()) {
      flash("error", "Please correct the form below.");
      return badRequest(details.render(boundForm));
    }

    Product product = boundForm.get();
    product.save();
    flash("success",
        String.format("Successfully added product %s", product));

    return redirect(routes.Products.list());
  }

  public static Result delete(String ean) {
    final Product product = Product.findByEan(ean);
    if(product == null) {
        return notFound(String.format("Product %s does not exists.", ean));
    }
    Product.remove(product);
    return redirect(routes.Products.list());
  }
}
