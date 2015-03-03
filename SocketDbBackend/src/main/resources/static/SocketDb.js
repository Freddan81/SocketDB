var stompClient;
var connectType;
function ConnectDB(url, event, type, invoke) {
    var socket = new SockJS(url + "/connect");
    connectType = type;
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
    	stompClient.subscribe('/topic/' + event + '/' + type, function(data){
    		invoke( JSON.parse(data.body));
    	});
    	var sub_old = stompClient.subscribe( '/topic/' + event + '/old/' + type, function(data){
			invoke( JSON.parse(data.body));
    	});
    	stompClient.subscribe( '/topic/' + event + '/stop', function(data){
    		sub_old.unsubscribe();
    	});
    	stompClient.send("/app/connect", {});
    });
}

ConnectDB.prototype.insert = function(val) {
	val['type'] = connectType;
    stompClient.send("/app/save", {}, JSON.stringify(val));
}

ConnectDB.prototype.update = function(id, val) {
	val['type'] = connectType;
	val['id'] = id;
    stompClient.send("/app/update", {}, JSON.stringify(val));
}