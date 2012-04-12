package br.com.vraptor.contrib.jscontroller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.google.common.collect.Sets.*;
import static com.google.common.collect.Lists.*;

import java.util.EnumSet;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.http.route.Route;
import br.com.caelum.vraptor.http.route.RoutesParser;
import br.com.caelum.vraptor.resource.HttpMethod;
import br.com.caelum.vraptor.resource.ResourceClass;

public class ControllerDiscoverTest {
  
  @Mock private ServletContext context;
  @Mock private RoutesParser routesParser;
  private ControllerDiscover discover;
  
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    
    discover = new ControllerDiscover(routesParser, context);
  }
  
  @Test public void 
  givenJsControllerTheDiscoverShouldFind2Methods() throws Exception {
    when(context.getContextPath()).thenReturn("");
    
    Route controllerRoute = mock(Route.class);
    when(controllerRoute.canHandle(JsController.class, JsController.class.getMethod("controller", String.class))).thenReturn(true);
    when(controllerRoute.getOriginalUri()).thenReturn("/js/{controllerName}");
    when(controllerRoute.allowedMethods()).thenReturn(allowedHttpMethods());

    Route minifiedControllerRoute = mock(Route.class);
    when(minifiedControllerRoute.canHandle(JsController.class, JsController.class.getMethod("minifiedController", String.class))).thenReturn(true);
    when(minifiedControllerRoute.getOriginalUri()).thenReturn("/js/min/{controllerName}");
    when(minifiedControllerRoute.allowedMethods()).thenReturn(allowedHttpMethods());

    when(routesParser.rulesFor(any(ResourceClass.class))).thenReturn(newArrayList(controllerRoute,
                                                                                  minifiedControllerRoute));
    
    Controller jsController = new Controller();
    jsController.setName("JsController");
    jsController.setJsRoutes(newArrayList(jsRoute(controllerRoute, "controller"),
                                          jsRoute(minifiedControllerRoute, "minifiedController")));
    
    discover.handle(JsController.class);
    assertEquals(jsController, discover.find(jsController.getName()));
  }

  private EnumSet<HttpMethod> allowedHttpMethods() {
    return newEnumSet(newHashSet(HttpMethod.GET), HttpMethod.class);
  }

  private JsRoute jsRoute(Route route, String name) {
    JsRoute jsRoute = new JsRoute();
    jsRoute.setName(name);
    jsRoute.setUrl(route.getOriginalUri());
    jsRoute.setAllowedMethods(route.allowedMethods());
    return jsRoute;
  }
  
  @Test public void 
  stereotypeShouldAlwaysReturnResourceClass() {
    assertEquals(Resource.class, discover.stereotype());
  }
  
}
