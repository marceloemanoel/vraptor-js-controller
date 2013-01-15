package br.com.vraptor.contrib.jscontroller.web;

import org.apache.tools.ant.filters.StringInputStream;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.vraptor.contrib.jscontroller.Controller;
import br.com.vraptor.contrib.jscontroller.JsGenerator;
import br.com.vraptor.contrib.jscontroller.generators.MinifiedJsGenerator;
import br.com.vraptor.contrib.jscontroller.vraptor.VRaptorControllerDiscover;

/**
 * This controller generates all the javascript needed to use the other controllers via javascript.
 * It should be injected on the pages.
 * @author marceloemanoel
 */
@Resource
@Path("/js")
public class JsController {
  
  private static final String JS_EXTENSION = ".js";
  private static final String MINIFIED_JS_EXTENSION = "-min" + JS_EXTENSION;
  private static final String CONTENT_TYPE = "text/javascript; charset=UTF-8";
  
  private Result result;
  private VRaptorControllerDiscover discover;
  private JsGenerator generator;
  
  public JsController(Result result, VRaptorControllerDiscover discover, JsGenerator generator) {
    this.result = result;
    this.discover = discover;
    this.generator = generator;
  }
  
  @Get
  @Path("/{controllerName}")
  public Download controller(String controllerName){
    Controller controller = discover.find(controllerName);
    if(controller == null){
      result.notFound();
      return null;
    }
    return download(generator.generate(controller), controller.getName()+JS_EXTENSION);
  }

  private Download download(String javascript, String fileName) {
    StringInputStream inputstream = new StringInputStream(javascript);
    return new InputStreamDownload(inputstream, CONTENT_TYPE, fileName, false, javascript.length());
  } 
  
  @Get
  @Path("/min/{controllerName}")
  public Download minifiedController(String controllerName){
    Controller controller = discover.find(controllerName);
    if(controller == null){
      result.notFound();
      return null;
    }
    MinifiedJsGenerator minifiedJsGenerator = new MinifiedJsGenerator(generator);
    return download(minifiedJsGenerator.generate(controller), controller.getName()+MINIFIED_JS_EXTENSION);
  }
}
