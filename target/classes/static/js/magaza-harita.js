document.addEventListener("DOMContentLoaded", async function () {
  const map = L.map("mapid").setView([39.0, 35.0], 6);

  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    maxZoom: 19,
    attribution: "© OpenStreetMap Katkıcıları"
  }).addTo(map);

  const ilSec = document.getElementById("ilSec");
  const ilceSec = document.getElementById("ilceSec");

  let all = [];
  let markers = [];

  function clearMarkers() {
    markers.forEach(m => map.removeLayer(m));
    markers = [];
  }

  function fillSelects(data) {
    const iller = [...new Set(data.map(x => (x.il || "").trim()).filter(Boolean))].sort();
    ilSec.innerHTML = `<option value="">Tümü</option>` + iller.map(i => `<option value="${i}">${i}</option>`).join("");
    ilceSec.innerHTML = `<option value="">Tümü</option>`;
  }

  function fillIlceler(data, il) {
    const ilceler = [...new Set(data
      .filter(x => (x.il || "").trim() === il)
      .map(x => (x.ilce || "").trim())
      .filter(Boolean))].sort();

    ilceSec.innerHTML = `<option value="">Tümü</option>` + ilceler.map(i => `<option value="${i}">${i}</option>`).join("");
  }

  function render(data) {
    clearMarkers();
    if (data.length === 0) return;

    data.forEach(m => {
      const marker = L.marker([m.enlem, m.boylam]).addTo(map)
        .bindPopup(`<b>${m.ad}</b><br>${m.il || ""} ${m.ilce || ""}<br>${m.adres || ""}`);
      markers.push(marker);
    });

    // ekrandaki mağazalara göre yakınlaştır
    const group = new L.featureGroup(markers);
    map.fitBounds(group.getBounds().pad(0.2));
  }

  // 1) mağazaları çek
  const res = await fetch("/api/magazalar");
  all = await res.json();

  fillSelects(all);
  render(all);

  // 2) filtreler
  ilSec.addEventListener("change", () => {
    const il = ilSec.value;
    if (il) fillIlceler(all, il);
    else ilceSec.innerHTML = `<option value="">Tümü</option>`;

    const filtered = all.filter(x => !il || (x.il || "").trim() === il);
    render(filtered);
  });

  ilceSec.addEventListener("change", () => {
    const il = ilSec.value;
    const ilce = ilceSec.value;

    const filtered = all.filter(x =>
      (!il || (x.il || "").trim() === il) &&
      (!ilce || (x.ilce || "").trim() === ilce)
    );
    render(filtered);
  });
});
