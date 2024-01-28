package com.pokejava.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//import javax.swing.text.Position;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import com.pokejava.Greeting;
import com.pokejava.*;
import com.pokejava.Battle.BattleInfo;
import com.pokejava.Map.MapInfo;
import com.pokejava.NPC.InteractionInfo;
import com.pokejava.NPC.MoveInfo;
import com.pokejava.PokeJava.PokeInfo;
import com.pokejava.maps.*;
import com.pokejava.npcs.Trainer;
import com.pokejava.pokejavas.*;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/rest")
public class WorldController {
    
  PokeJava[] initPokes = new PokeJava[]{
    new Normie(20),
    new Firely(5),
    new Waterly(5),
    new Grassie(5),
  };
  Trainer t1 = new Trainer("Waldemar", new Position(11,4), initPokes);
  Map m1;
  // Create an ArrayList to store maps
  List<Map> mapsList;

  public WorldController() {
    // Initialize m1 and set the trainer within the constructor
    m1 = new Route1();
    m1.setTrainer(t1);
    mapsList = new ArrayList<>(Arrays.asList(m1));
  }
  
  @GetMapping("/get-map")
  public MapInfo initMap() {
		return m1.getMapInfo();
	}

  // true if move sucessful, else false
  @PostMapping("/move")
  public MoveInfo move(@RequestBody java.util.Map<String, String> requestBody) {
    String direction = requestBody.get("direction");

    // do ONE move
    MoveInfo firstMove = t1.move(m1, direction);
    
    // move blocked -> stop
    if (!firstMove.success()) { return firstMove; };
    
    // move was successful, are we now on a new map?
    Map newMap = MapInterfaceDB.nextMap(m1);
    if (newMap != null) {
      
      // does the new map already exists in the list?
      Map updatedMap = handleMapUpdate(newMap);
      if (updatedMap != null) {
        m1 = updatedMap;
        m1.shuffleWildPoke(); // trainer was already on this map, shuffle wild poke
      }

      // do move again to not stay in the entry
      return t1.move(m1, direction);
    } 

    // we are on the same map, return the one move before
    else { return firstMove;}

  }

  private Map handleMapUpdate(Map newMap) {
    boolean listHasMap = mapsList.stream()
            .filter(Objects::nonNull)
            .anyMatch(map -> Objects.equals(newMap.getInitName(), map.getInitName()));

    // if map is not in the list, add it and return it, else return the existing map in the list
    if (!listHasMap) {
      mapsList.add(newMap);
      return newMap;
    } else {
      return mapsList.stream()
              .filter(Objects::nonNull)
              .filter(map -> Objects.equals(newMap.getInitName(), map.getInitName()))
              .findFirst()
              .orElse(null);
    }
  }

  @GetMapping("/get-pokejavas")
  public PokeInfo[] getPokeJavas() {
    PokeJava[] pokeArray = t1.getPokes();  // Assuming getPoke() returns PokeJava[]
        
    // Convert the array to a stream, filter out null elements, map to species, and collect to an array
    PokeInfo[] pokeInfoArray = Arrays.stream(pokeArray)
                                    .filter(Objects::nonNull)  // Filter out null elements
                                    .map(PokeJava::getPokeInfo)
                                    .toArray(PokeInfo[]::new);

    return pokeInfoArray;
	}

  @PostMapping("/change-pokejavas-order")
  public boolean change(@RequestBody java.util.Map<String, Integer> requestBody) { 
    int oldIndex = requestBody.get("oldIndex"); 
    int newIndex = requestBody.get("newIndex"); 
    t1.changePokesOrder(oldIndex, newIndex);
    return true;
  }

  @PostMapping("/get-interaction")
  public InteractionInfo getInteraction(@RequestBody java.util.Map<String, String> requestBody) { 
    // many npcs will read userAnswer, userAnswer = null may be not so good
    String userAnswer = requestBody.get("userAnswer");
    if (userAnswer == null) { userAnswer = ""; }
    InteractionInfo i1 = m1.npcInteraction(userAnswer); 
    // when user press enter anywhere
    if (i1 == null) { return null;}
    //if (i1.battle()) { b1 = m1.getMapInfo().battle(); }
    return i1;
  }

  // if true, a npc wants to interact
  @GetMapping("/update-npcs")
  public boolean updateNpcs() {
    return m1.npcsAutoAction();
  }

  @GetMapping("/get-battleinfo")
  public BattleInfo getBattle() { 
    // if (b1 == null) { return new BattleInfo(null, null, null, false, false); }
    return m1.getBattle().getBattleInfo(); 
  }
  
  @PostMapping("/update-battle")
  public boolean updateBattle(@RequestBody java.util.Map<String, Integer> requestBody) { 
    // if (b1 == null) { return new BattleInfo(null, null, null, false, false); }
    int userChoice = requestBody.get("userChoice");
    m1.getBattle().fight(userChoice);
    return true; 
  }

  @GetMapping("/try-escape")
  public boolean tryEscape() { 
    // if (b1 == null) { return true; }
    return m1.getBattle().tryEscape();
  }

  @GetMapping("/get-badges")
  public int getNumBadges() { 
    return m1.getTrainer().getNumBadges(); 
  }
  
}
