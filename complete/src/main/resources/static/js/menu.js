// dropdown menu list
document.addEventListener("DOMContentLoaded", function () {
    // Array of menu items
    var menuItems = ["PokeJavas", "Badges", "Pokedex", "Bag", "Map", "Save"];
  
    // Get the container where the menu will be generated
    const dropdownMenu = document.getElementById("dropdown")
  
    // Loop through the array of menu items and create list items
    for (var i = 0; i < menuItems.length; i++) {
        var menuItem = document.createElement("a");
        menuItem.textContent = menuItems[i];

        // Icons of list
        var itemIcon = document.createElement("i");
        switch (menuItem.textContent) {
            case "PokeJavas": itemIcon.className = "bx bx-buoy"; break;
            case "Badges": itemIcon.className = "bx bx-id-card"; break;
            case "Pokedex": itemIcon.className = "bx bx-list-ol"; break;
            case "Bag": itemIcon.className = "bx bx-briefcase"; break;
            case "Map": itemIcon.className = "bx bx-map"; break;
            case "Save": itemIcon.className = "bx bx-save"; break;
            default:
                // Handle other cases or do nothing
                break;
        }
        menuItem.appendChild(itemIcon);
  
        // Assign a click event to each menu item (just an example)
        menuItem.addEventListener("click", function () {
            
            // do this if any button is clicked
            rmElementById("mapName");
            rmElementById("npcInteraction");
            rmElementById("pokeSwap");

            // make content empty
            content.replaceChildren();
            appendRtnBtn();

            switch (this.textContent) {
                case "PokeJavas": 
                    fetchAsync("/get-pokejavas")
                        .then(data => { appendPokeInfo(data); appendPokeSwap(data.length); })
                            .catch(()=>{
                            ///Exception occured do something
                    });
                    break;
                case "Badges": alert("You clicked: " + this.textContent); break;
                case "Pokedex": alert("You clicked: " + this.textContent); break;
                case "Bag": alert("You clicked: " + this.textContent); break;
                case "Map": alert("You clicked: " + this.textContent); break;
                case "Save": alert("You clicked: " + this.textContent); break;
                default:
                    // Handle other cases or do nothing
                    alert("You clicked: " + this.textContent);
                    break;
            }
        });
  
        // Append the list item to the list
        dropdownMenu.appendChild(menuItem);
    }

    const dropdownBtn = document.getElementById("btn");
    const toggleArrow = document.getElementById("arrow");

    // Toggle dropdown function
    const toggleDropdown = function () {
    dropdownMenu.classList.toggle("show");
    toggleArrow.classList.toggle("arrow");
    };

    // Toggle dropdown open/close when dropdown button is clicked
    dropdownBtn.addEventListener("click", function (e) {
    e.stopPropagation();
    toggleDropdown();
    });

    // Close dropdown when dom element is clicked
    document.documentElement.addEventListener("click", function () {
    if (dropdownMenu.classList.contains("show")) {
        toggleDropdown();
    }
    });

});