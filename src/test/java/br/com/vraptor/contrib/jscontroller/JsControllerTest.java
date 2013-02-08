package br.com.vraptor.contrib.jscontroller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static br.com.vraptor.contrib.jscontroller.fixtures.Controllers.*;

import java.io.IOException;

import javax.servlet.ServletContext;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;

import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.util.test.MockResult;

public class JsControllerTest {
  
  private JsController controller;
  private MockResult result;
  @Mock private JsGenerator generator;
  @Mock private ControllerDiscover discover;
  @Mock private ServletContext context;
  private VelocityConfiguration configuration;
  
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    
    result = new MockResult();
    configuration = new VelocityConfiguration(context);
    controller = new JsController(result, discover, generator);    
  }
  
  @Test public void 
  whenTheControllerIsNotFound_theJsControllerShouldReturnNull() {
    assertNull(controller.controller("inexistentController"));
  }
  
  @Test public void
  whenTheControllerIsNotFound__theJsControllerShouldReturnAHttp404(){
    result = mock(MockResult.class);
    controller = new JsController(result, discover, generator);
    controller.controller("inexistentController");
    verify(result).notFound();
  }

  @Test public void
  whenTheControllerIsNotFound__theMinifiedJsControllerShouldReturnAHttp404(){
    result = mock(MockResult.class);
    controller = new JsController(result, discover, generator);
    controller.minifiedController("inexistentController");
    verify(result).notFound();
  }
  
  @Test public void
  whenTheControllerisFound_thenJsControllerShouldReturnADownloadToTheGeneratedResource() throws IOException{
    generator = new VelocityJsGenerator(configuration);
    controller = new JsController(result, discover, generator);
    when(discover.find(anyString())).thenReturn(productsController());
    
    Download jsResource = controller.controller("ProductsController");
    
    MockHttpServletResponse response = new MockHttpServletResponse();
    
    try {
      jsResource.write(response);
    }
    catch (IOException e) {
      fail("Cant write to the mock response!");
    }
    
    assertEquals("text/javascript; charset=UTF-8", response.getContentType());
    assertEquals("inline; filename=ProductsController.js", response.getHeader("Content-disposition"));
    assertEquals(generator.generate(productsController()).length() + "", response.getHeader("Content-Length"));
  }
  
  @Test public void
  whenTheControllerIsFound_thenMinifiedJsControllerShouldReturnADownloadToTheGenerateResource() {
    generator = new MinifiedJsGenerator(new VelocityJsGenerator(configuration));
    controller = new JsController(result, discover, generator);
    when(discover.find(anyString())).thenReturn(productsController());
    
    Download jsResource = controller.minifiedController("ProductsController");
    
    MockHttpServletResponse response = new MockHttpServletResponse();
    
    try {
      jsResource.write(response);
    }
    catch (IOException e) {
      fail("Cant write to the mock response!");
    }
    
    int generatedLength = generator.generate(productsController()).length();

    assertEquals("text/javascript; charset=UTF-8", response.getContentType());
    assertEquals("inline; filename=ProductsController-min.js", response.getHeader("Content-disposition"));
    assertEquals(generatedLength + "", response.getHeader("Content-Length"));
  }
  
}
