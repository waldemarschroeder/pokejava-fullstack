// constants;
const appendMapNameTo = document.getElementById("menuContainer");
//const appendMapContainerTo = document.getElementById("content");

// helper function
function rmElementById(id) {
  document.getElementById(id) && document.getElementById(id).remove();
}

// helper function
function appendMapName(name) {
    
  // update mapName always
  rmElementById("mapName");

  // Create an h2 element
  const h2Element = document.createElement('h2');
  
  // this line is definitely necessary
  h2Element.id = "mapName";
  
  // Set the text content of the h2 element
  h2Element.textContent = name;

  // Append the h2 element to the container
  appendMapNameTo.appendChild(h2Element);

}
  
// Function to display image table
function appendMap(matrix) {
  
  // only one map on the site
  if (document.getElementById("mapContainer")) { return; }

  const appendMapTo = document.createElement('div');
  appendMapTo.className = "mapContainer";
  appendMapTo.id = "mapContainer";
  content.appendChild(appendMapTo);

  // Create a new table if it hasn't been created already
  const table = document.createElement('map');
  // this line is definitely necessary
  table.id = "map";

  for (let i = 0; i < matrix.length; i += 1) {
    const row = document.createElement('tr');

    for (let j = 0; j < matrix[i].length; j++) {
      const cell = document.createElement('td');
      const img = document.createElement('img');
      img.id = "img" + i + "-" + j;

      // Set the image source based on matrix value
      if (fieldToImage(matrix[i][j]) == null) { cell.style.visibility = "hidden"; }
      else { img.src = fieldToImage(matrix[i][j]); }

      // Append the image to the table cell and the cell to the row
      cell.appendChild(img);
      row.appendChild(cell);
    }

    // Append the row to the table
    table.appendChild(row);
  }

  // Append the table to the body
  appendMapTo.appendChild(table);
  
}

function fieldToImage(field, isThereNpc) {
  
  // Your image URLs
  const imageUrls = [
    'https://cdn.pixabay.com/photo/2017/03/29/11/48/meadow-2184989_1280.jpg', // grass
    'https://cdn.pixabay.com/photo/2016/02/27/01/49/concrete-1225038_1280.jpg', // street
    'https://cdn.pixabay.com/photo/2015/11/02/18/32/water-1018808_1280.jpg', // water
    'https://cdn.pixabay.com/photo/2015/12/01/15/43/black-1072366_1280.jpg', // black
    'https://cdn.pixabay.com/photo/2015/03/20/06/31/door-681835_1280.jpg', // entry
    'https://cdn.pixabay.com/photo/2014/10/21/18/26/snow-496875_1280.jpg', // snow
    'images/houseLeftLowerHalf.png',
    'images/houseMiddleLowerHalf.png',
    'images/houseRightLowerHalf.png',
    'images/houseLeftUpperHalf.png',
    'images/houseMiddleUpperHalf.png',
    'images/houseRightUpperHalf.png',
    'https://cdn.pixabay.com/photo/2016/11/23/15/04/wood-1853403_640.jpg', // floor in house
    'images/floorWithNPC.png',
    'images/grassWithNPC.png',
    'images/concreteWithNPC.png',
    'images/snowWithNPC.png',

    // Add more image URLs as needed
  ];
  
  var imageUrl;
  // Set the image source based on matrix value
  switch (field) {
    case 'G': 
      if(isThereNpc) { imageUrl = imageUrls[14]; }
      else { imageUrl = imageUrls[0]; }
      break;
    case 'S': 
      if(isThereNpc) { imageUrl = imageUrls[15]; }
      else { imageUrl = imageUrls[1]; }
      break;
    case 'W': imageUrl = imageUrls[2]; break;
    case 'B': imageUrl = imageUrls[3]; break;
    case 'E': imageUrl = imageUrls[4]; break;
    case 'SNOW': 
      if(isThereNpc) { imageUrl = imageUrls[16]; }
      else { imageUrl = imageUrls[5]; }
      break;
    case 'houseLLH': imageUrl = imageUrls[6]; break;
    case 'houseMLH': imageUrl = imageUrls[7]; break;
    case 'houseRLH': imageUrl = imageUrls[8]; break;
    case 'houseLUH': imageUrl = imageUrls[9]; break;
    case 'houseMUH': imageUrl = imageUrls[10]; break;
    case 'houseRUH': imageUrl = imageUrls[11]; break;
    case 'floor': 
      if(isThereNpc) { imageUrl = imageUrls[13]; }
      else { imageUrl = imageUrls[12]; } 
      break;
    // Add more cases as needed
    default:
      // Handle unexpected values or leave the cell empty
      //cell.style.visibility = "hidden";
      break;
  }

  return imageUrl;
}

function updateTrainerInMap(matrix, trainerPos, trainerDirection) {

  // update new Field where trainer is
  var trainerCellId = "img" + trainerPos.y + "-" + trainerPos.x;
  var trainerCell = document.getElementById(trainerCellId); 
  var field = matrix[trainerPos.y][trainerPos.x];
  var imgSrc;
  switch(field) {
    case "G": imgSrc = "grassHuman"; break;
    case "S": imgSrc = "streetHuman"; break;
    case "SNOW": imgSrc = "snowHuman"; break;
    case "floor": imgSrc = "floorHuman"; break;
  }
  trainerCell.src = "images/" + imgSrc + trainerDirection + ".png";

  // update the old field where trainer was
  // Convert JSON objects to strings
  if (!(JSON.stringify(currentTrainerPos) === JSON.stringify(trainerPos))) {
    trainerCellId = "img" + currentTrainerPos.y + "-" + currentTrainerPos.x;
    trainerCell = document.getElementById(trainerCellId); 
    field = matrix[currentTrainerPos.y][currentTrainerPos.x];
    trainerCell.src = fieldToImage(field, false);
  }

}

function IOnpcsInMap(matrix, npcsPos, placeORdelete) {
  
  for(i = 0; i < npcsPos.length; i++) {
    npcPos = npcsPos[i];
    npcCellId = "img" + npcPos.y + "-" + npcPos.x;
    npcCell = document.getElementById(npcCellId); 
    field = matrix[npcPos.y][npcPos.x];
    npcCell.src = fieldToImage(field, placeORdelete);

  }

}

function moveNpcsInMap(matrixString, npcsPos) {

  if (!(npcsPos === currentNpcsPos)) {
    // old npc places delete
    IOnpcsInMap(matrixString, currentNpcsPos, false);
  
    // new npc places
    IOnpcsInMap(matrixString, npcsPos, true);
  
    // update currentNpcs
    currentNpcsPos = npcsPos;
  }
}  

function scrollToTarget(trainerPos) {

  const element = document.getElementById('img0-0');
  const cellHeight = parseInt(window.getComputedStyle(element).height, 10);
  const cellWidth = parseInt(window.getComputedStyle(element).width, 10);

  const targetX = trainerPos.x * cellWidth; // Assuming each cell has a fixed width
  const targetY = trainerPos.y * cellHeight; // Assuming each cell has a fixed height

  // Scroll the mapContainer to the target position
  const map = document.getElementById('map');
  if (map) {
    map.scrollTop = targetY - mapContainer.clientHeight / 2 + cellHeight / 2;
    map.scrollLeft = targetX - mapContainer.clientWidth / 2 + cellWidth / 2;
  }

}

// render whole map, Caution: is expensive
function renderMap(data) {
  content.replaceChildren();
  appendMapName(data.name); 
  currentMapName = data.name;
  appendMap(data.matrixString); 
  currentTrainerPos = data.trainerPos;
  updateTrainerInMap(data.matrixString, data.trainerPos, data.trainerDirection); 
  currentNpcsPos = data.npcsPos;
  updateNpcsInMap(data.matrixString, data.npcsPos, true);
  scrollToTarget(data.trainerPos);  
}

