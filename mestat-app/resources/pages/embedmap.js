
var mestat = mestat || {};

(function () {

var csrfToken = "";

function elem(name)
{
	return document.getElementById(name);
}

function jsonFetch(url, callback) {
	var request = new XMLHttpRequest();
	request.onreadystatechange = function () {
		if (request.readyState === 4 && request.status === 200) {
			callback(JSON.parse(request.responseText));
		}
	};
	request.open("GET", url, true);
	request.setRequestHeader("Accept", "application/json");
	request.send();
}

function makeTagForm(coord)
{
	return '<form method=POST action="add-point">' +
	  '<input type=hidden name=longitude value="' + coord.lng + '">' +
	  '<input type=hidden name=latitude value="' + coord.lat + '">' +
	  '<input type=hidden name="__anti-forgery-token" value="' +
	  csrfToken + '">' +
	  '<p>Add new tag here:<br><input type=text id=tagpop name=tags><br>' +
	  '<input type=submit value="Mark"></p></form>';
}

function addTagPopup(map, click)
{
	var p = L.popup();
	p.setLatLng(click.latlng);
	p.setContent(makeTagForm(click.latlng));
	p.openOn(map);
	elem('tagpop').focus();
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
	elem('messages').appendChild(listItem(description));
}

function updateCoords(coord)
{
	elem('longitude').value = coord.lng;
	elem('latitude').value = coord.lat;
}

function recenterMap(map, lat, lng)
{
	var position = new L.LatLng(lat, lng);
	updateCoords(position);
	var markers = new L.KML('/api/v1/search?long=' + lng + '&lat=' + lat);
	map.addLayer(markers);
	map.setView(position, 15);
}

mestat.recenterMap = recenterMap;

function initMap()
{
	var map = new L.Map('map');
	mestat.map = map;
	var osm = new L.TileLayer(
		'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
		{minZoom: 8, maxZoom: 22, attribution:
			'Map data from <a href="http://openstreetmap.org">' +
			'OpenStreetMap</a> contributors'});
	map.addLayer(osm);
	map.on('click', function (e) { addTagPopup(map, e); });
	map.on('move', function (e) { updateCoords(e.target.getCenter()); });

	mestat.jumpOnMap = function () {
		recenterMap(map, elem('latitude').value,
				elem('longitude').value);
	};

	if ('geolocation' in navigator) {
		navigator.geolocation.getCurrentPosition(function (curpos) {
			recenterMap(map, curpos.coords.latitude,
					curpos.coords.longitude);
		}, function (error) {
			reportError("Could not get your current position: " +
					error.message);
			recenterMap(map, 60.17671, 24.93892);
		});
	} else {
		reportError("Your browser does not support geolocation");
	}
}

mestat.initMestat = function ()
{
	var msg = elem('messages');
	var jsw = elem('jswarning');
	msg.removeChild(jsw);
	jsonFetch("/api/v1/get-csrf-token", function (response) {
		csrfToken = response["csrf-token"];
	});
	initMap();
};

})();

