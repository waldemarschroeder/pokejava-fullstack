async function getBadges() {
    const data = await fetchAsync("/get-badges");
    appendBadges(data);
}

// main helper function
function appendBadges(numBadges) {

    const badges = document.createElement('div');
    badges.className = "badges";
    badges.id = "badges";

    // Your image URLs
    const imageUrls = [
        'images/fireBadge.jpg',
        'images/waterBadge.jpg',
        'images/flyBadge.jpg',
        'images/fightBadge.jpg',
        'images/iceBadge.jpg',
        'images/ghostBadge.jpg',
        'images/poisonBadge.jpg',
        'images/dragonBadge.jpg',

    ];

    for (let i = 0; i < 8; i++) {
        // 4 badges in a row
        if (i % 4 === 0) {
            var badgesRow = document.createElement('div');
            badgesRow.className = "badgesRow";
            badges.appendChild(badgesRow);
        }

        const img = document.createElement('img');
        img.src = imageUrls[i];
        // hide the badges that the trainer dont have
        if (i >= numBadges) { 
            // Apply a black filter to the image
            img.style.filter = "brightness(2%)";
        }
        badgesRow.appendChild(img);

    }

    content.appendChild(badges);

}
