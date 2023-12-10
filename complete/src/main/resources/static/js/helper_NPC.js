// constants;
const appendNpcInteractionTo = document.getElementById("menuContainer");

function appendNpcInteraction() {
    
    // only one npcInteraction on the site
    if (document.getElementById("npcInteraction")) { return; }
    
    const npcInteraction = document.createElement('div');
    npcInteraction.id = "npcInteraction";
    npcInteraction.className = "container";
    appendNpcInteractionTo.appendChild(npcInteraction);
}

// helper function
function appendDialogBox(message) {

    // only one dialogBox, always updating
    rmElementById("dialogBox");

    // dialogbox
    const dialogBox = document.createElement('div');
    dialogBox.id = "dialogBox";
    dialogBox.innerHTML += `<p>${message}</p>`;
    
    // Append the button element to the container
    const npcInteraction = document.getElementById("npcInteraction");
    npcInteraction.appendChild(dialogBox);

}

// helper function
function appendAnswerBtns(answerArray) {

    // No answerBtns without dialogBox
    if (!(document.getElementById("dialogBox"))) { return; }

    // only one answerBtns, always updating
    rmElementById("answerBtns");

    const answerBtns = document.createElement('div');
    answerBtns.id = "answerBtns";

    const npcInteraction = document.getElementById("npcInteraction");
    npcInteraction.appendChild(answerBtns);

    // Using for...of loop for arrays
    for (let answer of answerArray) {
        var newButton = document.createElement('button');
        newButton.className = "answerBtn";
        newButton.id = answer + "AnswerBtn";
        newButton.textContent = answer;

        // Append the button element to the container
        answerBtns.appendChild(newButton);

        newButton.addEventListener('click', () => {
            // reset back to init
            interaction(answer);
            rmElementById("answerBtns");
        });
    }

}
