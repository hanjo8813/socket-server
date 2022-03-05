import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import StompJs from 'stompjs';

// init socket

const socketUrl = "http://localhost:8080/ws"
const socket = new SockJS(socketUrl);
const client = StompJs.Client = StompJs.over(socket);

function Home() {

    useEffect(() => {
        client.connect(
            {}, // 헤더
            () => { // 연결 시작시 콜백

                var now = new Date();
                
                // 연결확인 송신
                client.send(
                    '/connect',             // 소켓서버주소
                    {},                     // 헤더
                    JSON.stringify(now)    // 바디
                );

                // 연결확인 수신
                client.subscribe(
                    '/topic/connect',    // 구독주소
                    (res) => {  // 콜백
                        console.log(res.body);
                    }
                );

                // 상태변경 수신
                client.subscribe(
                    '/topic/change/fireStatus',
                    (res) => {
                        setTables(JSON.parse(res.body));
                    }
                );
            }
        );
    }, []);

    // state
    const [tables, setTables] = useState([
        { "id": 1, "fireStatus": "OFF" },
        { "id": 2, "fireStatus": "OFF" },
        { "id": 3, "fireStatus": "OFF" },
        { "id": 4, "fireStatus": "ON" }
    ]);

    // onClick
    const changeFireStatus = (targetId) => {
        client.send(
            '/change/fireStatus',
            {},
            JSON.stringify({
                "targetId" : targetId,
                "tables" : tables
            })
        )
    }

    return (
        <div>

            <h2>Demo</h2>
            <hr /><br />

            {
                tables.map(v => {
                    if (v.fireStatus === "OFF") {
                        return (<button className='table_blue' onClick={() => changeFireStatus(v.id, v.fireStatus)} key={v.id}>{v.id}</button>);
                    }
                    else {
                        return (<button className='table_red' onClick={() => changeFireStatus(v.id, v.fireStatus)} key={v.id}>{v.id}</button>);
                    }
                })
            }

        </div>
    );
}

export default Home;

