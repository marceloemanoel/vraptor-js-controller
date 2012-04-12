package br.com.vraptor.contrib.jscontroller.fixtures;

import static br.com.vraptor.contrib.jscontroller.fixtures.JsRoutes.*;

import java.util.ArrayList;

import br.com.vraptor.contrib.jscontroller.Controller;
import br.com.vraptor.contrib.jscontroller.JsRoute;

import com.google.common.collect.Lists;

public class Controllers {
  private Controllers(){}
  
  public static Controller productsController() {
    Controller productsController = new Controller();
    productsController.setName("ProductsController");
    
    ArrayList<JsRoute> jsRoutes = Lists.newArrayList(products_GET_list());
    productsController.setJsRoutes(jsRoutes);
    
    return productsController;
  }
}
