document.addEventListener("DOMContentLoaded", function () {
  let defaultLat = 39.0;
  let defaultLng = 35.0;

  const enlemInput = document.querySelector("input[name='enlem']");
  const boylamInput = document.querySelector("input[name='boylam']");

  if (enlemInput && boylamInput && enlemInput.value && boylamInput.value) {
    defaultLat = parseFloat(enlemInput.value);
    defaultLng = parseFloat(boylamInput.value);
  }

  const map = L.map("mapid").setView([defaultLat, defaultLng], 6);

  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution: "© OpenStreetMap Katkıcıları"
  }).addTo(map);

  let marker = L.marker([defaultLat, defaultLng]).addTo(map);

  map.on("click", function (e) {
    const lat = e.latlng.lat;
    const lng = e.latlng.lng;

    marker.setLatLng([lat, lng]);

    enlemInput.value = lat.toFixed(6);
    boylamInput.value = lng.toFixed(6);
  });
});
