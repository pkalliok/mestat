
function recenterMap(map, lat, lng)
{
	var position = new L.LatLng(lat, lng);
	document.getElementById('longitude').value = lng;
	document.getElementById('latitude').value = lat;
	var markers = new L.KML('/api/v1/search?long=' + lng + '&lat=' + lat);
	map.addLayer(markers);
	map.setView(position, 15);

	//markers.on("loaded", function(e) {
	//	map.fitBounds(e.target.getBounds());
	//});
}

function listItem(content)
{
	var text = document.createTextNode(content);
	var node = document.createElement("LI");
	node.appendChild(text);
	return node;
}

function reportError(description)
{
	document.getElementById('messages').appendChild(listItem(description));
}

function initMestat()
{
	var msg = document.getElementById('messages');
	var jsw = document.getElementById('jswarning');
	msg.removeChild(jsw);
	initMap();
}

function initMap()
{
	var map = new L.Map('map');
	var osm = new L.TileLayer(
		'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
		{minZoom: 8, maxZoom: 22, attribution:
			'Map data from <a href="http://openstreetmap.org">' +
			'OpenStreetMap</a> contributors'});
	mestatMap.addLayer(osm);

	if ('geolocation' in navigator) {
		navigator.geolocation.getCurrentPosition(function (curpos) {
			recenterMap(map, curpos.coords.latitude,
					curpos.coords.longitude);
		}, function (error) {
			reportError("Could not get your current position: " +
					error.message);
			recenterMap(map, 60.17671, 24.93892);
		});
	}
}

