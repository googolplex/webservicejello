package jello.schema;

import java.util.ArrayList;
import java.util.List;

public class JelloNS implements NSProvider {

  @Override
  public List<String> getRootNamespaces() {
    List<String> allNS =  new ArrayList<String>();
    allNS.add( "app");
    return allNS;
  }

}
