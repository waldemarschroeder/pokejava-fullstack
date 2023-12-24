// speak with API
const host = "http://localhost:8080/rest";

async function fetchAsync(context) {
  const url = host + context;

  try {
    let response = await fetch(url);

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    // Check if the response has content before parsing as JSON
    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
      let data = await response.json();
      return data;
    } else {
      // Handle cases where the response is not JSON
      //console.warn("Response does not contain JSON data");
      return null;
    }
  } catch (error) {
    console.error("An error occurred during fetchAsync:", error);
    throw error; // Rethrow the error to handle it in the calling code
  }
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

// update npcs in map
async function updateNpcsInMap() {
  try {
    // do not if no map there
    if (!document.getElementById("mapContainer")) { return; }

    const updateNpcsData = await fetchAsync("/update-npcs");
    if (updateNpcsData) { interaction() }

    // update npc position in map always
    const getMapData = await fetchAsync("/get-map");
    moveNpcsInMap(getMapData.matrixString, getMapData.npcsPos);
  } catch (error) {
    // Handle exceptions
    console.error("An error occurred:", error);
  }
}

// apiMoving
async function apiMoving(direction) {
  // {direction} -> {"direction":"up"}
  const move = await postAsync("/move", {direction});
  if (!move) { 
    console.log("blocked");
    //return; 
  }

  const data = await fetchAsync("/get-map")
  if (data.trainerMayMove) { rmElementById("npcInteraction"); }
  // render map only when map has changed, map rendering is expensive
  if (!(JSON.stringify(currentMapName) === JSON.stringify(data.name))) {
    renderMap(data);
  }
  // update trainer position in map always
  updateTrainerInMap(data.matrixString, data.trainerPos, data.trainerDirection); 
  currentTrainerPos = data.trainerPos;
  scrollToTarget(data.trainerPos); 

  if (data.wildPoke !== null ) { console.log(data.wildPoke); }
}

// interaction with npc
function interaction(userAnswer) {
  postAsync("/get-interaction", {userAnswer})
    .then(data => { 
      appendNpcInteraction(); 
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
    case 13: interaction(); break;

    default:
      // Handle other cases or do nothing
      break;
  }

}

// Update the npcs in map every 0,75 seconds
setInterval(updateNpcsInMap, 750);
