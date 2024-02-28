fetch(`/api/chat/room`, {
    method: 'GET',
    //credentials: 'include',
    headers: {
        'Content-Type': 'application/json',
         'Authorization' : `Bearer ${localStorage.getItem("access_token")}`
    },
})
    .then(response => {
        if (!response.ok) {
            response.json().then(error => {
                alert(error.message);
            });
        } else {
            return response.json();
        }
    })
    .then(rooms => {

        //console.log(rooms[0].roomName);
        const roomlist = document.querySelector('#roomlist');
        roomlist.innerHTML = "";


        for (let i = 0; i < rooms.length; i++) {
            const room = rooms[i];
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${room.ichatRoom}</td>
                <td>${room.otherPersonNm}</td>
            `;
            roomlist.append(tr);
            tr.addEventListener('click', e => {
                window.location.href = `/websocket.html?ichatRoom=${room.ichatRoom}`;
            })
        }
    });


