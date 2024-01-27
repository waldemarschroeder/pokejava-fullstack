async function getBadges() {
    const data = await fetchAsync("/get-badges");
    console.log(data);
}
