package com.pokejava.controller;

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
import com.pokejava.PokeJava.PokeInfo;
import com.pokejava.maps.*;
import com.pokejava.npcs.Trainer;
import com.pokejava.pokejavas.*;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/rest")
public class WorldController {
    
  PokeJava[] initPokes = new PokeJava[]{
    new Normie(5),
    new Firely(5),
    new Waterly(5),
    new Grassie(5),
  };
  Trainer t1 = new Trainer("Waldemar", new Position(11,4), initPokes);
  Map m1 = new Route1(t1);
  
  @GetMapping("/get-map")
  public MapInfo initMap() {
		return m1.getMapInfo();
	}

  @PostMapping("/move")
  // curl localhost:8080/rest/move?direction=right
  public MapInfo move(@RequestBody java.util.Map<String, String> requestBody) {
    String direction = requestBody.get("direction");
    t1.move(m1, direction);
    Map m2 = MapInterfaceDB.nextMap(m1.getName(), t1);
    if ( m2 != null) { m1 = m2; t1.move(m1, direction); }
		return m1.getMapInfo();
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

  Battle b1;

  @PostMapping("/get-interaction")
  public InteractionInfo getInteraction(@RequestBody java.util.Map<String, String> requestBody) { 
    String userAnswer = requestBody.get("userAnswer");
    InteractionInfo i1 = m1.npcInteraction(userAnswer); 
    // when user press enter anywhere
    if (i1 == null) { return null;}
    if (i1.battle() != null) { b1 = i1.battle(); }
    return i1;
  }

  @GetMapping("/update-npcs")
  public InteractionInfo updateNpcs() {
    InteractionInfo i1 = m1.npcsAutoAction();
    if (i1 == null) { return null; }
    if (i1.battle() != null) { b1 = i1.battle(); }
    return i1;
  }
  

  @PostMapping("/change-pokejavas-order")
  public PokeInfo[] change(@RequestBody java.util.Map<String, Integer> requestBody) { 
    int oldIndex = requestBody.get("oldIndex"); 
    int newIndex = requestBody.get("newIndex"); 
    t1.changePokesOrder(oldIndex, newIndex);
    return getPokeJavas();
  }

  @GetMapping("/get-battleinfo")
  public BattleInfo getBattle() { 
    // if (b1 == null) { return new BattleInfo(null, null, null, false, false); }
    return b1.getBattleInfo(); 
  }
  
  @PostMapping("/update-battle")
  public BattleInfo updateBattle(@RequestBody java.util.Map<String, Integer> requestBody) { 
    // if (b1 == null) { return new BattleInfo(null, null, null, false, false); }
    int userChoice = requestBody.get("userChoice");
    b1.fight(userChoice);
    if (!b1.getBattleInfo().active()) {
      m1.setBattle(false);
      m1.setTrainerMayMove(true);
    }
    return b1.getBattleInfo(); 
  }

  @GetMapping("/try-escape")
  public boolean tryEscape() { 
    // if (b1 == null) { return true; }
    boolean tmp = b1.tryEscape();
    if (tmp) {
      m1.setBattle(false);
      m1.setTrainerMayMove(true);
    }
    return tmp;
  }
  
}
