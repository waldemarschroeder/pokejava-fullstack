// helper fucntion append battle Container
function appendBattleContainer() {
  
    // only one map on the site
    if (document.getElementById("battleContainer")) { return; }
  
    const battleContainer = document.createElement('div');
    battleContainer.className = "battleContainer";
    battleContainer.id = "battleContainer";
    content.appendChild(battleContainer);

    const battleRow = document.createElement('div');
    battleRow.id = "battleRow";
    battleContainer.appendChild(battleRow);

    const battleField = document.createElement('div');
    battleField.className = "battleField";
    battleField.id = "battleField";
    battleRow.appendChild(battleField);

    const battleMenu= document.createElement('div');
    battleMenu.className = "battleMenu";
    battleMenu.id = "battleMenu";
    battleRow.appendChild(battleMenu);
   
}

// helper function append battle text
function appendBattleText(message, appendTo) {

    // only one battle text, always updating
    rmElementById("battleText");

    // battleText
    const battleText = document.createElement('div');
    battleText.id = "battleText";
    battleText.textContent = message;
    
    // Append the battleText to the container
    appendTo.appendChild(battleText);

}

// helper function append battlePokeInfo
function appendBattlePokeInfo(poke, appendTo, isTrainerPoke) {
    // only one battlePokeInfo, always updating
    // rmElementById("battlePokeInfo");

    // battlePokeInfo specie + lvl
    const battlePokeInfo = document.createElement('div');
    battlePokeInfo.id = "battlePokeInfo";
    battlePokeInfo.textContent = poke.specie + "     Lvl. " + poke.lvl;
    appendTo.appendChild(battlePokeInfo);

    // dynamic green health bar
    const battlePokeHpBar = document.createElement('div');
    battlePokeHpBar.id = "battlePokeHpBar";
    battlePokeHpBar.style.height = "10px"; // Set the height of the health bar
    battlePokeHpBar.style.background = "linear-gradient(to right, #4caf50 " + ((poke.isHp / poke.stats.maxHp) * 100) + "%, transparent " + ((poke.isHp / poke.stats.maxHp) * 100) + "%)";
    battlePokeInfo.appendChild(battlePokeHpBar);

    // Create a child element for the actual health bar value
    // isHp / maxHp
    const battlePokeHpBarValue = document.createElement("div");
    battlePokeHpBarValue.id = "battlePokeHpBarValue";
    battlePokeInfo.appendChild(battlePokeHpBarValue);

    if (isTrainerPoke) {
        // Set the text content with current HP and max HP
        battlePokeHpBarValue.textContent = "HP " + poke.isHp + " / " + poke.stats.maxHp;
    }

}


async function appendTextsWithDelay(textArray, appendTo) {
    for (let texts of textArray) {
        if (Array.isArray(texts)) {
            for (let text of texts) {
                await new Promise(resolve => setTimeout(resolve, 2000)); // Wait for 2 seconds
                appendBattleText(text, appendTo);
            }
        } else {
            console.error("Invalid text array format:", texts);
        }
    }
}

// helper function
function appendBattleField(appendTo, data) {

    // always updating
    appendTo.replaceChildren();

    const npcPokeContainer = document.createElement('div');
    npcPokeContainer.className = "battlePokeContainer";
    npcPokeContainer.id = "npcPokeContainer";
    battleField.appendChild(npcPokeContainer);

    const trainerPokeContainer = document.createElement('div');
    trainerPokeContainer.className = "battlePokeContainer";
    trainerPokeContainer.id = "trainerPokeContainer";
    battleField.appendChild(trainerPokeContainer);

    appendTextsWithDelay(data.text, appendTo);

    appendBattlePokeInfo(data.npcPoke, npcPokeContainer, false);
    appendPokeImg(data.npcPoke.specie, npcPokeContainer, "enemyPokeImg");
    document.getElementById("npcPokeContainer").style.alignSelf = "flex-end";

    appendPokeImg(data.trainerPoke.specie, trainerPokeContainer, "trainerPokeImg");
    appendBattlePokeInfo(data.trainerPoke, trainerPokeContainer, true);
    document.getElementById("trainerPokeContainer").style.alignSelf = "flex-start";
}

// helper function
function appendBattleMenu(appendTo, attacks) {

    // always updating
    appendTo.replaceChildren();

    appendAttackBtns(attacks, appendTo);
    appendEscapeBtn(appendTo);

    
}

// init battle
function initBattle() {
    fetchAsync("/get-battleinfo")
      .then(data => { 
        // if battle not active, do nothing
        if (!data.active) { return; } 
        content.replaceChildren(); 
        rmElementById("mapName");
        rmElementById("npcInteraction");
        
        // hide menu
        const menu = document.getElementById("btn");
        menu.style.display = "none";

        // bigger content, bc menu is now hidden
        content.style.maxHeight = "90vh";

        appendBattleContainer();
        
        // build battleField
        const battleField = document.getElementById("battleField");
        appendBattleField(battleField, data);

        // build battleMenu
        const battleMenu = document.getElementById("battleMenu");
        appendBattleMenu(battleMenu, data.trainerPoke.attacks);

      })
        .catch(()=>{
          ///Exception occured do something
        });
}

// helper function
function appendEscapeBtn(appendEscBtnTo) {

    // only one returnBtn
    if (document.getElementById("escapeBtnDiv")) { return; }

    const div = document.createElement('div');
    div.id = "escapeBtnDiv";
    appendEscBtnTo.appendChild(div)

    const newButton = document.createElement('button');
    newButton.className = "returnBtn";
    newButton.id = "escapeBtn";
    newButton.textContent = 'Escape';

    // Append the button element to the container
    div.appendChild(newButton);

    newButton.addEventListener('click', () => {
        // reset back to init
        fetchAsync("/try-escape")
            .then(data => { 
                if (data) {
                    newButton.remove();
                    back2Map();
                }
            })
                .catch(()=>{
                    ///Exception occured do something
                });
        
    });

}

function back2Map() {
    content.replaceChildren();
    const menu = document.getElementById("btn");
    menu.style.display = "block";
    initMap();

}

// helper function
function appendAttackBtns(pokeAttacks, appendAttBtnsTo) {

    // only one returnBtn
    if (document.getElementById("attackBtnsDiv")) { return; }

    const div = document.createElement('div');
    div.id = "attackBtnsDiv";
    appendAttBtnsTo.appendChild(div)

    // Create header row
    let rowData = ["Attack", "Type", "Power"];
    
    for (let i = 0; i < pokeAttacks.length; i++) {
        const newButton = document.createElement('button');
        newButton.className = "attackBtn";
        newButton.id = "attackBtn" + i;
        

        columnData = Object.values(pokeAttacks[i]);
        for (let j = 0; j < columnData.length; j++) {
            const line = document.createElement('div');
            line.textContent = `${rowData[j]}: ${columnData[j]}`;
            newButton.appendChild(line);
        }

        // Use an IIFE to capture the current value of i
        (function (index) {
            newButton.addEventListener('click', async () => {
                try {
                    const update = await postAsync("/update-battle", { "userChoice": index });
        
                    const data = await fetchAsync("/get-battleinfo");
                    if (!data.active) { back2Map(); }
        
                    // build battleField
                    const battleField = document.getElementById("battleField");
                    appendBattleField(battleField, data);
        
                    // build battleMenu
                    const battleMenu = document.getElementById("battleMenu");
                    appendBattleMenu(battleMenu, data.trainerPoke.attacks);
                } catch (error) {
                    // Handle errors
                    console.error(error);
                }
            });
        })(i);
        

        // Append the button element to the container
        div.appendChild(newButton);
    }


}