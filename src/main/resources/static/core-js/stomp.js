const urlParams = new URLSearchParams(window.location.search);
const ichatRoom = urlParams.get('ichatRoom');
console.log('ichatRoom : '+ ichatRoom);

var stompClient = null;
var headers = { Authorization: `Bearer ${localStorage.getItem("access_token")}`};

var socket = new SockJS(`/stomp/chat`);

stompClient = Stomp.over(socket);

let page = 1;
function getMsgList() {

}

stompClient.connect(headers, (frame) => {
    console.log('Connected: ' + frame);
    
    stompClient.subscribe(`/exchange/chat.topic/room.${ichatRoom}`, function (receivedMesssage) {
        var data = JSON.parse(receivedMesssage.body);
        //console.log(receivedMesssage.body + "  서버에서 날아온 메시지 ");

        const now = new Date();
        const year = now.getFullYear();
        const month = now.getMonth() + 1;
        const day = now.getDate();
        const hour = now.getHours();
        const minute = now.getMinutes();
        const meridiem = hour >= 12 ? "오후" : "오전";
        const formattedTime = `${year}년 ${month}월 ${day}일 ${meridiem} ${hour % 12}:${minute < 10 ? "0" + minute : minute}`;


        const container = document.querySelector('.chatbox');
        const input = document.getElementById('messageContent');

        const div = document.createElement('div');

        /*if (memberId == message.memberId) {
          div.style.backgroundColor = '#c4e8ed';
        }*/


        div.className = 'message';
        div.innerHTML = `
          <span class="name">${data.sendUserNm} :</span>
          <span class="text">${data.message}</span>
          <span class="time">${formattedTime}</span>
          `;
        container.appendChild(div);
        input.value = '';
        container.scrollTop = container.scrollHeight;
    }, {Authorization: localStorage.getItem('access_token')})
  }, (error) => {
      console.log('연결실패');
      console.log(error)
    });


function sendMessage() {
  const content = document.getElementById('messageContent');
  const writer = document.getElementById('messageWriter');
  const ChatRequest = {
      ichatRoom: ichatRoom,
      message: content.value,
  };

  /*var headers = {
  'header1': 'value1',
  'header2': 'value2',
  };*/

  stompClient.send(`/pub/chat.message.${ichatRoom}`, { Authorization: `Bearer ${localStorage.getItem("access_token")}`}, JSON.stringify(ChatRequest));

}