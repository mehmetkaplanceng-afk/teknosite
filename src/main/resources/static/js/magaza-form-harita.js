document.addEventListener("DOMContentLoaded", function () {
  const enlemInput = document.getElementById("enlem");
  const boylamInput = document.getElementById("boylam");

  const initLat = enlemInput.value ? parseFloat(enlemInput.value) : 39.0;
  const initLng = boylamInput.value ? parseFloat(boylamInput.value) : 35.0;

  const map = L.map("map").setView([initLat, initLng], enlemInput.value ? 13 : 6);

  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
  }).addTo(map);

  const marker = L.marker([initLat, initLng], { draggable: true }).addTo(map);

  function setInputs(lat, lng) {
    enlemInput.value = lat.toFixed(6);
    boylamInput.value = lng.toFixed(6);
  }

  setInputs(initLat, initLng);

  map.on("click", function (e) {
    marker.setLatLng(e.latlng);
    setInputs(e.latlng.lat, e.latlng.lng);
  });

  marker.on("dragend", function () {
    const p = marker.getLatLng();
    setInputs(p.lat, p.lng);
  });
});
