
function installOnloadHandler(handler)
{
	var oldhandler = window.onload;
	window.onload = function () {
		if (oldhandler) oldhandler();
		handler();
	};
}

function initMap()
{
	var map = new L.Map('map');
	var osm = new L.TileLayer(
		'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
		{minZoom: 8, maxZoom: 22, attribution:
			'Map data from <a href="http://openstreetmap.org">' +
			'OpenStreetMap</a> contributors'});
	var position = new L.LatLng(60.17671, 24.93892);
	if ('geolocation' in navigator) {
		navigator.geolocation.getCurrentPosition(function (curpos) {
			position = new L.LatLng(curpos.coords.latitude,
					curpos.coords.longitude);
		});
	}

	map.setView(position, 15);
	map.addLayer(osm);

	var markers = new L.KML('/api/v1/search?long=' + position.lng +
			'&lat=' + position.lat);
	markers.on("loaded", function(e) {
		map.fitBounds(e.target.getBounds());
	});

	map.addLayer(markers);
}

// installOnloadHandler(initMap);

