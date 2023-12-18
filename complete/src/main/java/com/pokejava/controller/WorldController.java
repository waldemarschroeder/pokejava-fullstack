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
  Trainer t1 = new Trainer("Waldemar", initPokes);
  Map m1 = new Route1(new Position(11,4));
  
  @GetMapping("/get-map")
  public MapInfo initMap() {
		return m1.getMapInfo();
	}

  @PostMapping("/move")
  // curl localhost:8080/rest/move?direction=right
  public MapInfo move(@RequestBody java.util.Map<String, String> requestBody) {
    String direction = requestBody.get("direction");
    m1.moveTrainer(direction);
    Map m2 = MapInterfaceDB.nextMap(m1.getName(), m1.getPosition());
    if ( m2 != null) { m1 = m2; m1.moveTrainer(direction); }
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
    InteractionInfo i1 = m1.npcInteraction(userAnswer, t1.getPokes()); 
    if (i1.battle()) { b1 = new Battle(t1, i1.npc()); }
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
    if (b1 == null) { return new BattleInfo(null, null, null, false, false); }
    return b1.getBattleInfo(); 
  }
  
  @PostMapping("/update-battle")
  public BattleInfo updateBattle(@RequestBody java.util.Map<String, Integer> requestBody) { 
    if (b1 == null) { return new BattleInfo(null, null, null, false, false); }
    int userChoice = requestBody.get("userChoice");
    if (b1.getBattleInfo().active()) { b1.fight(userChoice); }
    return b1.getBattleInfo(); 
  }

  @GetMapping("/try-escape")
  public boolean tryEscape() { 
    if (b1 == null) { return true; }
    boolean tmp = b1.tryEscape();
    if (tmp) {b1 = null;};
    return tmp;
  }
  
}
