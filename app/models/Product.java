package models;


import play.data.validation.Constraints;

import java.util.HashSet;
import java.util.Set;

public class Product {

  private static Set<Product> products;

  static {
    products = new HashSet<Product>();
    products.add(new Product("1111111111111", "Paperclips 1",
        "Paperclips description 1"));
    products.add(new Product("2222222222222", "Paperclips 2",
        "Paperclips description "));
    products.add(new Product("3333333333333", "Paperclips 3",
        "Paperclips description 3"));
    products.add(new Product("4444444444444", "Paperclips 4",
        "Paperclips description 4"));
    products.add(new Product("5555555555555", "Paperclips 5",
        "Paperclips description 5"));
  }

  @Constraints.Required
  public String ean;
  @Constraints.Required
  public String name;
  public String description;

  public Product() {
  }

  public Product(String ean, String name, String description) {
    this.ean = ean;
    this.name = name;
    this.description = description;
  }

  public String toString() {
    return String.format("%s - %s", ean, name);
  }

  public static Set<Product> findAll() {
    return new HashSet<Product>(products);
  }

  public static Product findByEan(String ean) {
    for (Product candidate : products) {
      if (candidate.ean.equals(ean)) {
        return candidate;
      }
    }
    return null;
  }

  public static Set<Product> findByName(String term) {
    final Set<Product> results = new HashSet<Product>();
    for (Product candidate : products) {
      if (candidate.name.toLowerCase().contains(term.toLowerCase())) {
        results.add(candidate);
      }
    }

    return results;
  }

  public static boolean remove(Product product) {
    return products.remove(product);
  }

  public void save() {
    products.remove(findByEan(this.ean));
    products.add(this);
  }

}
