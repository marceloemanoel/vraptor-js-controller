VRAPTOR-JS-CONTROLLER [![Build Status](https://travis-ci.org/marceloemanoel/vraptor-js-controller.png)](https://travis-ci.org/marceloemanoel/vraptor-js-controller)
======================

The plugin's main idea is to help the interaction between ajax clients and vraptor.
As for now, it depends on jQuery to do ajax communication.

Installation
-----------

The plugin is self contained and doesn't require any special configuration on your project.
Just put the jar in your classpath and all should be ready to go. If you use dependency management
systems like Maven or Gradle you're just a few lines from joy!

Maven Dependecy
----------------

Add the following to your `pom.xml`:

```xml
  <dependency>
    <groupId>com.github.marceloemanoel</groupId>
    <artifactId>vraptor-js-controller</artifactId>
    <version>0.2</version>
  </dependency>
```

Gradle Dependency
-----------------

As shown below, just add the these lines to your `build.gradle` file:

```groovy
  repositories {
    mavenCentral()
  }

  dependencies {
    compile "com.github.marceloemanoel:vraptor-js-controller:0.2"
  }
```

As for last, but not the least, you can always build the project from source and
add `vraptor-js-controller.jar` file on your `WEB-INF/lib` folder.  Instructions on
how to build the project are listed bellow.

On the web page you want to use the controller just add the following line:

```html
    <script type="text/javascript" src="<c:url value='/js/ControllerName'/>"></script>
```

to use a minified version of it include the following line:

```html
    <script type="text/javascript" src="<c:url value='/js/min/ControllerName'/>"></script>
```

For instance suppose that exist a ProductsController than you should insert the following snippet:

```html
    <script type="text/javascript" src="<c:url value='/js/ProductsController'/>"></script>
```

Usage
----------

Suppose you have the following controller on your application: 

```java
    //package and imports ommited..
    
    @Resource
    @Path("products")
    public class ProductsController {
  
      private Result result;
      private Products products;
      
      public ProductsController(Result result, Products products) {
        this.result = result;
        this.products = products;
      }
      
      @Get
      @Path("/")
      public void index(){
      }
       
      @Get
      @Path("/list")
      public void list() {
        result.use(json()).withoutRoot().from(products.all()).recursive().serialize();
      }
      
      @Get
      @Path("/{id}")
      public void show(int id) {
        result.use(json()).withoutRoot().from(products.select(id)).recursive().serialize();
      }
      
      @Post
      @Path("/new")
      public void newProduct(Product product){
        products.add(product);
        result.use(status()).ok();
      }
      
      @Put
      @Path("/{id}")
      public void update(int id, Product product){
        products.update(id, product);
        result.use(status()).ok();
      }
      
      @Delete
      @Path("/{id}")
      public void remove(int id) {
        products.remove(id);
        result.use(status()).ok();
      }
    }

```

Given that controller your client code could be something like this:

`index.jsp`:

include the js controller:

```html
     <script type="text/javascript" src="<c:url value='/js/ProductsController'/>"></script>
```
     
and then create another script block with client code:

```html
    <script type="text/javascript">
        var controller = ProductsController();    
        controller.list({
                onSuccess : function(results) {
                              var console = $("#console");
                              console.text('');
                              $.each(results, function(index, product) {
                                  console.append("<p>product.description: " + product.description + " - product.price: " + product.price + "</p>");
                              });
                }
        });
    </script>
```

All js controller methods can receive an object with the following properties:

    onSuccess: Callback to treat the successful result from VRaptor controller
    onError: Callback to treat an unsuccessful result from VRaptor controller
    data: An object with the parâmeters that will be delivered to VRaptor controller
             
Compile from source code
------------------------

* open the command line
* cd to the root folder
* type           

        ./gradlew jar
   
Build with Eclipse
------------------

To use eclipse just do the following:

* open the command line
* cd to the root folder
* type: 

        ./gradlew eclipse
    
* open eclipse
* import project to the workspace
* I use the following [code conventions](https://github.com/marceloemanoel/Code-Conventions), when contributing, please use it.

Run the sample
--------------

To run the sample code:

* open the command line
* cd to the sample folder
* type:

        ../gradlew jettyRunWar
        
It's also possible to run from eclipse WTP.

Thanks!
------

To [Caelum](http://www.caelum.com.br/), thank you so much for this great framework! 

To [Douglas Crockford](http://www.crockford.com/), thank you for the wonderful book [JavaScript: The Good Parts](http://www.amazon.com/exec/obidos/ASIN/0596517742/wrrrldwideweb)
it helped me a lot.

To [Cristhiano Milfont](http://www.milfont.org/tech/), some of his ideas from the course [Javascript Fundamental](http://www.milfont.org/javascriptfundamental.html) were
used to build this plugin.

To [Handerson Frota](http://www.handersonfrota.com.br/), after a talk with him some functionalities came to light.
