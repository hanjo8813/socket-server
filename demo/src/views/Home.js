import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import StompJs from 'stompjs';

// init socket

const socketUrl = "http://localhost:8080/ws"
const socket = new SockJS(socketUrl);
const client = StompJs.Client = StompJs.over(socket);

function Home() {

    // state
    const [tables, setTables] = useState([
        { "id": 1, "fireStatus": "OFF" },
        { "id": 2, "fireStatus": "OFF" },
        { "id": 3, "fireStatus": "OFF" },
        { "id": 4, "fireStatus": "ON" }
    ]);

    // effect
    useEffect(() => {
        client.connect(
            // header
            {},
            // callback
            () => { 
                
                // 연결확인 송신
                client.send(
                    // host
                    '/connect', 
                    // header
                    {}, 
                    // body
                    JSON.stringify({
                        "clientTime" : new Date(),
                        "tables" : tables
                    })
                );

                // 연결확인 수신
                client.subscribe(
                    // 구독 host
                    '/topic/connect',
                    // callback
                    (res) => {
                        console.log(res.body);
                        res = JSON.parse(res.body);
                        setTables(res.tables);
                    }
                );

                // 상태변경 수신
                client.subscribe(
                    '/topic/change/fireStatus',
                    (res) => {
                        res = JSON.parse(res.body);
                        setTables(res.tables);
                    }
                );
            }
        );
    }, []);

    // onClick
    const changeFireStatus = (targetId) => {
        client.send(
            '/change/fireStatus',
            {},
            JSON.stringify({
                "targetId" : targetId,
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

