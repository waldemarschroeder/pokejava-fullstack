// speak with API
const host = "http://localhost:8080/rest";

async function fetchAsync (context) {
  url = host + context;
  let response = await fetch(url);
  let data = await response.json();
  return data;
}

async function postAsync (context, postData) {
  url = host + context;
  let response = await fetch(url, {
    method: "POST",
    headers: {
        "Content-Type": "application/json", // Adjust the content type based on your API requirements
        // Add any other headers if needed
    },
    body: JSON.stringify(postData), // Convert your data to JSON format if needed
  });
  let data = await response.json();
  return data;
}

var currentTrainerPos;
var currentNpcsPos;
var currentMapName;

// init map
function initMap() {
  fetchAsync("/get-map")
    .then(data => { 
      renderMap(data);
    })
      .catch(()=>{
        ///Exception occured do something
      });
}
initMap();

// update map
function updateMap() {
  fetchAsync("/get-map")
    .then(data => { 
      moveNpcsInMap(data.matrixString, data.npcsPos);
    })
      .catch(()=>{
        ///Exception occured do something
      });
}

// apiMoving
function apiMoving(direction) {
  // {direction} -> {"direction":"up"}
  postAsync("/move", {direction})
    .then(data => { 
      if (!data.trainerMayMove) { return; }
      rmElementById("npcInteraction");
      // render map only when map has changed, map rendering is expensive
      if (!(JSON.stringify(currentMapName) === JSON.stringify(data.name))) {
        renderMap(data);
      }
      // update trainer position in map always
      updateTrainerInMap(data.matrixString, data.trainerPos, data.trainerDirection); 
      currentTrainerPos = data.trainerPos;
      scrollToTarget(data.trainerPos); 

      //moveNpcsInMap(data.matrixString, data.npcsPos);
      //console.log(data.battle); 
    })
      .catch(()=>{
        ///Exception occured do something
      });
}

// interaction with npc
function interaction(userAnswer) {
  postAsync("/get-interaction", {userAnswer})
    .then(data => { 
      appendDialogBox(data.npcAnswer);
      if (data.possibleUserAnswers !== null) { appendAnswerBtns(data.possibleUserAnswers); }
      if (data.battle) { 
        // wait 2 s
        setTimeout(function() { initBattle(); }, 2000); 
      }
    })
      .catch(()=>{
        ///Exception occured do something
      });
}

//controller
let lastKeyPressTime = 0;

document.onkeydown = function (e) {
  const currentTime = new Date().getTime();

  // Check if 0.1 seconds have passed since the last key press
  if (currentTime - lastKeyPressTime >= 100) {
    checkKey(e);
    lastKeyPressTime = currentTime;
  }
};
function checkKey(e) {
  let mapBefore = document.getElementById("map");
  if (!mapBefore) { return; }
  e = e || window.event;
  
  switch (e.keyCode) {
    // up arrow
    case 38: apiMoving("up"); break;
    
    // down arrow
    case 40: apiMoving("down"); break;

    // left arrow
    case 37: apiMoving("left"); break;

    // right arrow
    case 39: apiMoving("right"); break;

    // Enter key
    case 13: appendNpcInteraction(); interaction("bla"); break;

    default:
      // Handle other cases or do nothing
      break;
  }

}

// Update the map every 0,75 seconds
setInterval(updateMap, 750);
