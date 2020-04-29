var stompClient = null;
var noticeCount = 0;

/**
 * 连接
 */
function connectSocket() {
    if(stompClient==null){
        // 连接SockJS的endpoint名称为"/endpointWisely"
        var socket = new SockJS('/endpointWisely');
        // 使用STOMP子协议的WebSocket客户端
        stompClient = Stomp.over(socket);
        // 连接WebSocket服务端
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            // ##回调函数## 订阅"/topic/getResponse"目标发送的消息，来显示
            stompClient.subscribe('/topic/callback', function (response) {
                // console.log("response: " + JSON.stringify(response));
                /*发回数据形式为{"command":"MESSAGE","headers":{"content-length":"36","message-id":"3khnmfpx-0","subscription":"sub-0","content-type":"application/json;charset=UTF-8","destination":"/topic/getResponse"},"body":"{\"responseMessage\":\"Welcome, cccc!\"}"}
                showResponse(JSON.parse(response.body).responseMessage);*/
                res = JSON.parse(response.body);
                if (res.code === 200){
                    alert("发送成功！");
                }else {
                    alert("反馈发送失败！");
                }
            });
        });
    }
}

/**
 * 断开连接
 */
function disconnectSocket() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("断开连接");
}

/**
 * 发送广播信息
 */
function sendMessage() {
    var message = $('#callback-message').val();
    var userId = localStorage.getItem("userId");
    console.log(stompClient);
    // 向目标发送消息
    stompClient.send("/callback", {}, JSON.stringify({'message': message,'userId':userId}));
}

/**
 * 显示响应信息
 *
 * @param message
 */
/*function showResponse(message) {
    noticeCount++;
    $('#notice').html("");

    $('#notice').append('公告信息'+noticeCount+'"条!"');
    console.log('********************'+noticeCount)
    $('#response')[0].innerHTML += message + '<br>';
    /!*
     10秒后清屏，
     参数code不能仅仅是一句 "$('#response')[0].innerHTML = "";"
     并发量高的时候，会有bug,可能不是第一个10秒结束
      *!/
    // setTimeout(function(){$('#response')[0].innerHTML = "";}, 10000);
}*/
