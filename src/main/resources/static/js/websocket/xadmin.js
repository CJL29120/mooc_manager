var stompClient = null;

/**
 * 连接
 */
function connect() {
    if(stompClient == null) {
        var socket = new SockJS('/endpointWisely');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/callback', function (response) {
                console.log("************************")
                changeCallbackBar();
            });
        });
    }
}

/**
 * 断开连接
 */
function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("断开连接");
}

/**
 * 发送广播信息
 */
function sendMessage() {
    var message = $('#message').val();
    var userId = localStorage.getItem("userId");
    // 向目标发送消息
    stompClient.send("/broadcast", {}, JSON.stringify({'message': message,'userId':userId}));
}

function changeCallbackBar() {
    unresolved++;
    $("#callback-btn").html("");
    $("#callback-btn").append("反馈信息&nbsp;<span style=\"color: #ff4c4d;\">("+unresolved+")</span>");
}
