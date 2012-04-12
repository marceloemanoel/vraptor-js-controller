package br.com.vraptor.contrib.jscontroller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static br.com.vraptor.contrib.jscontroller.fixtures.Controllers.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.DelegatingServletOutputStream;

import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.util.test.MockResult;

public class JsControllerTest {
  
  private JsController controller;
  private MockResult result;
  @Mock private JsGenerator generator;
  @Mock private ControllerDiscover discover;
  
  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    
    result = new MockResult();
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
  whenTheControllerisFound_thenJsControllerShouldReturnADownloadToTheGeneratedResource() throws IOException{
    generator = new VelocityJsGenerator();
    controller = new JsController(result, discover, generator);
    when(discover.find(anyString())).thenReturn(productsController());
    
    Download jsResource = controller.controller("ProductsController");
    
    ByteArrayOutputStream targetStream = new ByteArrayOutputStream();

    HttpServletResponse mockResponse = mock(HttpServletResponse.class);
    ServletOutputStream out = new DelegatingServletOutputStream(targetStream);
    when(mockResponse.getOutputStream()).thenReturn(out);
    
    try {
      jsResource.write(mockResponse);
    }
    catch (IOException e) {
      fail("Cant write to the mock response!");
    }
    
    assertEquals("text/javascript; charset=UTF-8", mockResponse.getContentType());
    assertTrue(mockResponse.containsHeader("Content-disposition"));
    assertTrue(mockResponse.containsHeader("Content-Length"));
  
  }
  
}
