package br.com.vraptor.contrib.jscontroller.fixtures;

import static com.google.common.collect.Sets.*;

import br.com.caelum.vraptor.resource.HttpMethod;
import br.com.vraptor.contrib.jscontroller.JsRoute;

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
