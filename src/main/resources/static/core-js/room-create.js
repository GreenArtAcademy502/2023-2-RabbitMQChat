function createRoom() {
    const otherPersonIuser = window.document.getElementById("other_person_iuser").value;

    if (otherPersonIuser.trim() !== "") {

        const param = {
            otherPersonIuser
        }

        fetch(`/api/chat/room`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization' : `Bearer ${localStorage.getItem("access_token")}`
            },
            body: JSON.stringify(param)
        })
            .then(response => {
                if (response.status == 401 || response.status == 403) {
                    alert('로그인을 해주세요.');
                    console.log(response);
                } else if (!response.ok) {
                    response.json().then(error => {
                        alert(error.message);
                    });
                } else {
                    alert('채팅방을 생성하였습니다.');
                    opener.parent.location.reload();
                    window.close();
                }
            }).catch(error => console.error(error));

        
    } else {
        alert("올바른 값을 입력해주세요.");
    }
}