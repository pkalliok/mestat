
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
		{minZoom: 4, maxZoom: 16, attribution:
			'Map data from <a href="http://openstreetmap.org">' +
			'OpenStreetMap</a> contributors'});
	var position = new L.LatLng(60.17671, 24.93892);
	if ('geolocation' in navigator) {
		navigator.geolocation.getCurrentPosition(function (curpos) {
			position = new L.LatLng(curpos.coords.latitude,
					curpos.coords.longitude);
		});
	}
	map.setView(position, 9);
	map.addLayer(osm);
}

installOnloadHandler(initMap);

