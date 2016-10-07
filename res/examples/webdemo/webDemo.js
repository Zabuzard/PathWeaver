/**
 * Client of a demo web application which shows how
 * the API can be used as the server which solves shortest
 * path computation tasks.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
 
 /**
 * The map object to use.
 */
var map;
 /**
 * The source marker to use.
 */
var source;
 /**
 * The destination marker to use.
 */
var destination;
 /**
 * The polyline object to use which represents the
 * shortest path between source and destination.
 */
var line;

 /**
 * Callback method which is used by the Google Maps API
 * once the API has been loaded. It initializes the service.
 */
function initMap() {
	// Create the map object and hook it to the canvas in the DOM
	var centerPos = new google.maps.LatLng(49.23299, 6.97633);
	var mapOptions = {
		zoom: 14,
		center: centerPos,
		mapTypeId: google.maps.MapTypeId.SATELLITE
	};
	map = new google.maps.Map(document.getElementById('mapCanvas'), mapOptions);
	
	// Create the initial source and destination markers
	source = new google.maps.Marker({
		map: map,
		draggable: true,
		position: new google.maps.LatLng(49.24134, 6.96354)
	});
	destination = new google.maps.Marker({
		map : map,
		draggable: true,
		position: new google.maps.LatLng(49.22364, 6.99260)
	});
	
	// Create the line object which is used to represent the shortest path
	var path = [source.getPosition(), destination.getPosition()];
	line = new google.maps.Polyline({
		map: map,
		path: path,
		strokeColor: 'blue',
		strokeWeight: 8,
		strokeOpacity: 0.5
	});
	
	// Add listeners to the marker which invoke the computation of the shortest path
	google.maps.event.addListener(source, 'drag', redrawLine);
	google.maps.event.addListener(destination, 'drag', redrawLine);
	
	// Invoke the first computation
	redrawLine();
}

 /**
 * Redraws the line between the source and destination marker.
 */
function redrawLine() {
	redrawLineServer();
}

 /**
 * Locally redraws the line between the source and destination marker by
 * creating a straight line between both marker.
 */
function redrawLineClient() {
	var path = [source.getPosition(), destination.getPosition()];
	line.setPath(path);
}

 /**
 * Redraws the line between the source and destination marker by calling
 * the server application which computes the shortest path between source
 * and destination. The line then represents this path.
 */
function redrawLineServer() {
	var url = 'http://localhost:8888/?request='
		+ 'sLat:' + source.getPosition().lat() + ','
		+ 'sLng:' + source.getPosition().lng() + ','
		+ 'dLat:' + destination.getPosition().lat() + ','
		+ 'dLng:' + destination.getPosition().lng();
	$.ajax(url, {dataType: 'jsonp'});
}

 /**
 * Callback used by the server application to accept the computed
 * shortest path and actually drawing it.
 */
function redrawLineServerCallback(json) {
	var path = [];
	
	for (var i = 0; i < json.path.length; i++) {
		var nodePos = new google.maps.LatLng(json.path[i][0], json.path[i][1]);
		path.push(nodePos);
	}
	
	line.setPath(path);
}