package com.github.marceloemanoel.vraptor.jscontroller.fixtures;

import static com.github.marceloemanoel.vraptor.jscontroller.fixtures.JsRoutes.*;

import java.util.ArrayList;


import com.github.marceloemanoel.vraptor.jscontroller.Controller;
import com.github.marceloemanoel.vraptor.jscontroller.JsRoute;
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
