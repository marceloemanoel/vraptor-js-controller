package com.github.marceloemanoel.vraptor.jscontroller;

import java.util.List;

import com.google.common.base.Objects;
/**
 * This class represents a jsController with all the routes needed to contact vraptor controllers.
 * @author marceloemanoel
 */
public class Controller {
  private String name;
  private List<JsRoute> jsRoutes;
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<JsRoute> getJsRoutes() {
    return jsRoutes;
  }

  public void setJsRoutes(List<JsRoute> jsRoutes) {
    this.jsRoutes = jsRoutes;
  }

  @Override
  public int hashCode() {
      return Objects.hashCode(jsRoutes, name);
  }

  @Override
  public boolean equals(Object obj) {
      Controller other = (Controller) obj;
      return Objects.equal(jsRoutes, other.jsRoutes) &&
             Objects.equal(name, other.name);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
                  .add("name", name)
                  .add("jsRoutes", jsRoutes)
                  .toString();
  }

}
