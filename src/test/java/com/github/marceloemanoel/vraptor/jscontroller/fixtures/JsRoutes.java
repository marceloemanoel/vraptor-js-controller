package com.github.marceloemanoel.vraptor.jscontroller.fixtures;

import static com.google.common.collect.Sets.*;

import com.github.marceloemanoel.vraptor.jscontroller.JsRoute;

import br.com.caelum.vraptor.resource.HttpMethod;

public class JsRoutes {
  
  private JsRoutes(){}
  
  public static JsRoute products_GET_list(){
    JsRoute jsRoute = new JsRoute();
    jsRoute.setName("list");
    jsRoute.setUrl("/list");
    jsRoute.setAllowedMethods(newEnumSet(newHashSet(HttpMethod.GET), HttpMethod.class));
    return jsRoute;
  }
  
}
