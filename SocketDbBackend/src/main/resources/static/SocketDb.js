var stompClient;
function ConnectDB(url, event, type, invoke) {
    var socket = new SockJS(url + "/connect");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
    	var old = '/topic/' + event + '/' + type + '/old';
    	stompClient.subscribe('/topic/' + event + '/' + type, function(data){
    		invoke( JSON.parse(data.body));
    	});
    	var sub_old = stompClient.subscribe( '/topic/' + event + '/old', function(data){
			invoke( JSON.parse(data.body));
    	});
    	stompClient.subscribe( '/topic/' + event + '/stop', function(data){
    		sub_old.unsubscribe();
    	});
    	stompClient.send("/app/connect", {});
    });
}

ConnectDB.prototype.insert = function(json) {
    stompClient.send("/app/save", {}, JSON.stringify(json));
}