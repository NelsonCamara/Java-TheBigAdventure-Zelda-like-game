package com.TheBigAdventure.mapBuiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Record representing a container for map-related data, including map dimensions and a list of map elements.
 */
record MapContainer(int[] mapDimensions, List<Map<String, String>> listOfElements) {
	
    /**
     * Constructs a MapContainer with the specified map dimensions and list of elements.
     *
     * @param mapDimensions  The dimensions of the map.
     * @param listOfElements The list of elements in the map.
     */
	public MapContainer {
    Objects.requireNonNull(mapDimensions, "mapDimensions cannot be null");
    Objects.requireNonNull(listOfElements, "listOfElements cannot be null");
  }
  
    /**
     * Builds and returns a list of map objects (ObjectFromSkin) from the elements in the map container.
     *
     * @return A list of map objects.
     */
  List<ObjectFromSkin> buildAllMapObjects (){
    List<ObjectFromSkin> mapObjects  = new ArrayList<>();
    String objectSkin;
    String entityOrElement;
    boolean boolEntityOrElement;
    for (int i =0;i<listOfElements.size();i++) {
      ObjectFromSkin tmpObject;
      objectSkin = listOfElements.get(i).get("skin");
      entityOrElement = listOfElements.get(i).get("entityOrElement");
      listOfElements.get(i).remove("entityOrElement");
      if(entityOrElement.equals("entity")) boolEntityOrElement = true;
      else boolEntityOrElement = false;
      tmpObject = new ObjectFromSkin(objectSkin, boolEntityOrElement,listOfElements.get(i));
      mapObjects.add(tmpObject);
    }
    
    return mapObjects;
    
  }
  
}
