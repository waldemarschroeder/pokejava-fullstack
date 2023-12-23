package com.pokejava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pokejava.maps.*;

public record MapInterfaceDB(MapInterface m1, MapInterface m2) {

    static MapInterfaceDB r1r2 = new MapInterfaceDB(
        new MapInterface("mapExit", "Route 1", new Position(5, 0)),
        new MapInterface("mapExit", "Route 2", new Position(6, 23))
    );
    static MapInterfaceDB r1r3 = new MapInterfaceDB(
        new MapInterface("mapExit", "Route 1", new Position(5, 8)), 
        new MapInterface("mapExit", "Route 3", new Position(6, 0))
    );
    static MapInterfaceDB r1r4 = new MapInterfaceDB(
        new MapInterface("mapExit", "Route 1", new Position(0, 4)), 
        new MapInterface("mapExit","Route 4", new Position(12, 12))
    );
    static MapInterfaceDB r2coa = new MapInterfaceDB(
        new MapInterface("mapExit", "Route 2", new Position(0, 1)),
        new MapInterface("mapExit", "City of Azure", new Position(17, 25))
    );
    static MapInterfaceDB r3m = new MapInterfaceDB(
        new MapInterface("mapExit", "Route 3", new Position(0, 14)), 
        new MapInterface("mapExit", "Mystery Place", new Position(12, 1))
    );
    static MapInterfaceDB r3pc = new MapInterfaceDB(
        new MapInterface("houseEntry", "Route 3", new Position(11, 13)), 
        new MapInterface("mapExit", "PokeCenter Route 3", new Position(9, 4))
    );
    static MapInterfaceDB r4coa = new MapInterfaceDB(
        new MapInterface("mapExit", "Route 4", new Position(1, 0)), 
        new MapInterface("mapExit", "City of Azure", new Position(9, 50))
    );
    static MapInterfaceDB coaGym = new MapInterfaceDB(
        new MapInterface("houseEntry", "City of Azure", new Position(7, 3)), 
        new MapInterface("mapExit", "Gym of Fire", new Position(9, 4))
    );
    static MapInterfaceDB coaPc = new MapInterfaceDB(
        new MapInterface("houseEntry", "City of Azure", new Position(4, 22)), 
        new MapInterface("mapExit", "PokeCenter Azure", new Position(9, 4))
    );
    static MapInterfaceDB coah1 = new MapInterfaceDB(
        new MapInterface("houseEntry", "City of Azure", new Position(14, 19)), 
        new MapInterface("mapExit", "House 1 in Azure", new Position(9, 4))
    );
    static MapInterfaceDB coah2 = new MapInterfaceDB(
        new MapInterface("houseEntry", "City of Azure", new Position(15, 43)), 
        new MapInterface("mapExit", "House 2 in Azure", new Position(9, 4))
    );
    static MapInterfaceDB coah3 = new MapInterfaceDB(
        new MapInterface("houseEntry", "City of Azure", new Position(15, 47)), 
        new MapInterface("mapExit", "House 3 in Azure", new Position(9, 4))
    );
    static MapInterfaceDB coah4 = new MapInterfaceDB(
        new MapInterface("houseEntry", "City of Azure", new Position(7, 43)), 
        new MapInterface("mapExit", "House 4 in Azure", new Position(9, 4))
    );
    static MapInterfaceDB coah5 = new MapInterfaceDB(
        new MapInterface("houseEntry", "City of Azure", new Position(4, 43)), 
        new MapInterface("mapExit", "House 5 in Azure", new Position(9, 4))
    );
    static MapInterfaceDB coah6 = new MapInterfaceDB(
        new MapInterface("houseEntry", "City of Azure", new Position(4, 46)), 
        new MapInterface("mapExit", "House 6 in Azure", new Position(9, 4))
    );


    static MapInterfaceDB[] DBs = {r1r2, r1r3, r1r4, r2coa, r3m, r3pc, r4coa, coaGym, coaPc, coah1, coah2, coah3, coah4, coah5, coah6};
        
    public static MapInterface[] inters(String mapName) {
        
        List<MapInterface> MapInterfaceList = new ArrayList<>();

        for (MapInterfaceDB DB : DBs) {
            if (mapName.equals(DB.m1().mapName())) {
                MapInterfaceList.add(DB.m1());
            }
            if (mapName.equals(DB.m2().mapName())) {
                MapInterfaceList.add(DB.m2());
            }
        }

        // Convert the list to an array
        MapInterface[] inters = MapInterfaceList.toArray(new MapInterface[0]);

        return inters;

    }

    // the super code from chat gpt
    public static Map nextMap(Map initMap) {
        String mapName = initMap.getInitName();
        NPC trainer = initMap.getTrainer();
        Position p = trainer.getPos();

        for (MapInterfaceDB DB : DBs) {
            if (mapName.equals(DB.m1().mapName()) && p.equals(DB.m1().p())) {
                // Entry Field where trainer was -> unblocked
                initMap.fieldSetIsAccessable(p, true);
                
                // trainer has new position in new map
                trainer.setPos(DB.m2().p());
                return createInstance(DB.m2().mapName(), trainer);
            }
            if (mapName.equals(DB.m2().mapName()) && p.equals(DB.m2().p())) {
                // Entry Field where trainer was -> unblocked
                initMap.fieldSetIsAccessable(p, true);
                
                // trainer has new position in new map
                trainer.setPos(DB.m1().p());
                return createInstance(DB.m1().mapName(), trainer);
            }
        }
        return null;
    }

    private static java.util.Map<String, Class<?>> classMapping = new HashMap<>();

    static {
        classMapping.put("Route 1", Route1.class);
        classMapping.put("Route 2", Route2.class);
        classMapping.put("Route 3", Route3.class);
        classMapping.put("Route 4", Route4.class);
        classMapping.put("Mystery Place", Mystery_Place.class);
        classMapping.put("PokeCenter Route 3", PokeCenter1.class);
        classMapping.put("City of Azure", CityOfAzure.class);
        classMapping.put("Gym of Fire", GymOfFire.class);
        classMapping.put("PokeCenter Azure", PokeCenterAzure.class);
        classMapping.put("House 1 in Azure", COAh1.class);
        classMapping.put("House 2 in Azure", COAh2.class);
        classMapping.put("House 3 in Azure", COAh3.class);
        classMapping.put("House 4 in Azure", COAh4.class);
        classMapping.put("House 5 in Azure", COAh5.class);
        classMapping.put("House 6 in Azure", COAh6.class);

    }

    private static Map createInstance(String mapName, NPC trainer) {
        Class<?> clazz = classMapping.get(mapName);
        if (clazz != null) {
            try {
                if (Map.class.isAssignableFrom(clazz)) {
                    // Check if the class is a subclass of MapInterface before creating an instance
                    Class<? extends Map> mapClass = (Class<? extends Map>) clazz;
                    Map map = mapClass.getDeclaredConstructor().newInstance();
                    map.setTrainer(trainer);
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception according to your needs
            }
        }
        return null;
    }
    
}
