// constants;
const appendRtnBtnTo = document.getElementById("menuContainer");
const content = document.getElementById("content");
const appendPokeSwapTo = document.getElementById("menuContainer");

// helper function
function appendPokeStats(poke, appendTo) {
    
    // Create a table
    const table = document.createElement('table');
    
    const headerColumn = ["Specie", "Type", "Level", "HP", "Attack", "Defence", "Speed", "EXP"]
    for (let j = 0; j < headerColumn.length; j++) {
        const row = table.insertRow();
        const property = row.insertCell();
        //property.textContent = Object.keys(poke).at(j);
        property.textContent = headerColumn[j];

        const cell = row.insertCell();
        switch (j) {
            case 0: cell.textContent = poke.specie; break;
            case 1: cell.textContent = poke.type; break;
            case 2: cell.textContent = poke.lvl; break;
            case 3: cell.textContent = poke.isHp + " / " + poke.stats.maxHp; break;
            case 4: cell.textContent = poke.stats.atk; break;
            case 5: cell.textContent = poke.stats.def; break;
            case 6: cell.textContent = poke.stats.speed; break;
            case 7: cell.textContent = poke.exp + " / " + poke.stats.expNextLvl; break;
            default:
                // Handle other cases or do nothing
                cell.textContent = poke.lvl; break;
        }
    }

    // Append the table to the body
    appendTo.appendChild(table);

}

// helper function
function appendPokeImg(pokeSpecie, appendTo, imageID) {
    // Poke Images
    const pokeImages = [
        'https://r2.starryai.com/results/815349878/bb7334bd-7ad1-4290-94de-2ed13f685d1a.webp', // Firely
        'https://images.nightcafe.studio/jobs/IUCQX0iwhRvxArsSvG52/IUCQX0iwhRvxArsSvG52--1--ywff3_7.8125x.jpg?tr=w-1600,c-at_max', // Waterly
        'https://image.cdn2.seaart.ai/2023-09-16/17312534796215301/c8ceb3e615c30706865ccc9fc809ac64681816da.png', // Grassie
        'https://cdn.pixabay.com/photo/2023/01/01/23/10/ai-generated-7690987_1280.jpg', // Normie

        // Add more image URLs as needed
    ];
    const pokeImage = document.createElement('img');
    pokeImage.id = imageID;
    pokeImage.height = 150;
    pokeImage.width = 100;
    pokeImage.style.display = "block";
    switch(pokeSpecie) {
        case "Firely": pokeImage.src = pokeImages[0]; break;
        case "Waterly": pokeImage.src = pokeImages[1]; break;
        case "Grassie": pokeImage.src = pokeImages[2]; break;
        case "Normie": pokeImage.src = pokeImages[3]; break;
        default:
            // Handle other cases or do nothing
            pokeImage.src = pokeImages[0]; break;
    }
    //document.body.appendChild(pokeImage);
    appendTo.appendChild(pokeImage);
}

// helper function
function appendPokeAtks(pokeAttacks, appendTo) {
    // Create a table
    const table = document.createElement('table');

    // Insert a new row
    const newRow = table.insertRow();

    // Create header row
    let rowData = ["Attack", "Type", "Power"];

    // Populate the row with data from the array
    rowData.forEach(data => {
        const cell = newRow.insertCell();
        cell.textContent = data;
    });

    for (let i = 0; i < pokeAttacks.length; i++) {
        // Insert a new row
        const newRow = table.insertRow();
        rowData = Object.values(pokeAttacks[i]);
        rowData.forEach(data => {
            const cell = newRow.insertCell();
            cell.textContent = data;
        });

    }

    appendTo.appendChild(table);
}

// helper function
function appendRtnBtn() {

    // only one returnBtn
    if (document.getElementById("returnBtn")) { return; }

    const newButton = document.createElement('button');
    newButton.className = "returnBtn";
    newButton.id = "returnBtn";
    newButton.textContent = 'Return to map';

    // Append the button element to the container
    appendRtnBtnTo.appendChild(newButton);

    newButton.addEventListener('click', () => {
        // reset back to init
        content.replaceChildren();
        newButton.remove();
        rmElementById("pokeSwap");
        initMap();
    });

}

// main helper function
function appendPokeInfo (arrPokeInfo) {

    // always updating pokeinfos
    rmElementById("pokeinfos");

    const appendPokeInfosTo = document.createElement('div');
    appendPokeInfosTo.className = "pokeinfos";
    appendPokeInfosTo.id = "pokeinfos";

    // one table for every poke
    for (let i = 0; i < arrPokeInfo.length; i++) {

        var poke = arrPokeInfo[i];

        // two pokejavas in a row
        if (i % 2 === 0) {
            var pokeRow = document.createElement('div');
            pokeRow.className = "pokeRow";
        }

        var pokeContainer = document.createElement('div');
        pokeContainer.className = "pokeContainer";
        
        const topCorner = document.createElement('topCorner');
        topCorner.textContent = i+1;
        pokeContainer.appendChild(topCorner)
        
        var color;
        switch (poke.type) {
            case "normal": color = 'burlywood'; break;
            case "fire": color = 'tomato'; break;
            case "water": color = 'lightblue'; break;
            case "grass": color = 'lightgreen'; break;
            default: color = 'lightgrey'; break;
        } 
        if (poke.isHp == 0) { color = 'grey' }
        pokeContainer.style.backgroundColor = color;

        appendPokeStats(poke, pokeContainer);
        appendPokeImg(poke.specie, pokeContainer);
        appendPokeAtks(poke.attacks, pokeContainer)
        
        pokeRow.appendChild(pokeContainer);

        if (i % 2 === 0) {
            appendPokeInfosTo.appendChild(pokeRow);
        }

    }
    content.appendChild(appendPokeInfosTo);

}

// helper function
function appendPokeSwap(lengthPokeArray) {

    // only one pokeSwap on the site
    if (document.getElementById("pokeSwap")) { return; }
    
    const pokeSwap = document.createElement('div');
    pokeSwap.id = "pokeSwap";
    appendPokeSwapTo.appendChild(pokeSwap);

    // Text 1
    const text1 = document.createElement('div');
    text1.textContent = "Change PokeJava";
    pokeSwap.appendChild(text1);

    // oldIndex
    const selectElementOld = document.createElement('select');
    selectElementOld.id = "selectOldIndex";
    pokeSwap.appendChild(selectElementOld);

    // Text 2
    const text2 = document.createElement('div');
    text2.textContent = "with";
    pokeSwap.appendChild(text2);

    // newIndex
    const selectElementNew = document.createElement('select');
    selectElementNew.id = "selectNewIndex";
    pokeSwap.appendChild(selectElementNew);

    for (let i = 0; i < lengthPokeArray; i++) {
        // Create separate option elements for each select element
        const optionOld = document.createElement("option");
        optionOld.value = i;
        optionOld.text = i+1;
        selectElementOld.add(optionOld);

        const optionNew = document.createElement("option");
        optionNew.value = i;
        optionNew.text = i+1;
        selectElementNew.add(optionNew);
    }

    let selectedOldIndex; // Declare variables outside event listener functions
    let selectedNewIndex;

    // Adding event listener for the old index select
    selectElementOld.addEventListener('change', function () {
        selectedOldIndex = parseInt(selectElementOld.value, 10);
        // You can use selectedOldIndex as needed in your application
    });

    // Adding event listener for the new index select
    selectElementNew.addEventListener('change', function () {
        selectedNewIndex = parseInt(selectElementNew.value, 10);
        // You can use selectedOldIndex as needed in your application
    });

    // ok button
    const newButton = document.createElement('button');
    newButton.className = "answerBtn";
    newButton.id = "okAnswerBtn";
    newButton.textContent = "ok";

    // Append the button element to the container
    pokeSwap.appendChild(newButton);

    newButton.addEventListener('click', () => {
        // Check if both indices are defined before proceeding
        if (selectedOldIndex == undefined) { selectedOldIndex = 0; }
        if (selectedNewIndex == undefined) { selectedNewIndex = 0; }
        changePokeJavasOrder(selectedOldIndex, selectedNewIndex);
    });


}

// API change pokejavas order

function changePokeJavasOrder(oldIndex, newIndex) {
    postAsync("/change-pokejavas-order", {oldIndex, newIndex})
      .then(data => { 
        appendPokeInfo(data);
      })
        .catch(()=>{
          ///Exception occured do something
        });
}
  