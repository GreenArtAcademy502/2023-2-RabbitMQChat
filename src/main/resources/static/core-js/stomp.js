const urlParams = new URLSearchParams(window.location.search);
const ichatRoom = urlParams.get('ichatRoom');
console.log('ichatRoom : '+ ichatRoom);

var headers = { Authorization: `Bearer ${localStorage.getItem("access_token")}`};
const url = `ws://${window.location.host}/stomp/chat`;

console.log(`url: ${url}`, url);
let stompClient = null;

const stompConfig = {
    brokerURL: url,
    connectHeaders: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    },
    reconnectDelay: 5000, //자동 재 연결
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    onConnect: () => {
        stompClient.subscribe(`/exchange/chat.topic/room.${ichatRoom}`, message => {
                var data = JSON.parse(message.body);
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
            }
        );
    }
};

stompClient = new StompJs.Client(stompConfig);
stompClient.activate();

function sendMessage() {
    const content = document.getElementById('messageContent');
    const writer = document.getElementById('messageWriter');
    const ChatRequest = {
        ichatRoom: ichatRoom,
        message: content.value,
    };

    stompClient.publish({destination: `/pub/chat.message.${ichatRoom}`, body: JSON.stringify(ChatRequest)});

    // react에서 작업한 레퍼런스
    //https://medium.com/@woal9844/stomp-js-%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-websocket-%EC%97%B0%EB%8F%99-c9f0ef6ab540
}
