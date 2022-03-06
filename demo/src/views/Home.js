import React, { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import StompJs from 'stompjs';

// init socket

const socketUrl = "https://kkakdduk.herokuapp.com/ws"
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
    const [sessionCount, setSessionCount] = useState(0);

    // effect
    useEffect(() => {

        client.connect(
            // header
            {},
            // callback
            () => {

                // 연결확인 수신
                client.subscribe(
                    // 구독 host
                    '/topic/connect',
                    // callback
                    (res) => {
                        console.log(res.body);
                        res = JSON.parse(res.body);
                        setSessionCount(res.sessionCount);
                        setTables(res.data.tables);
                    }
                );

                // 연결확인 송신
                client.send(
                    // host
                    '/connect',
                    // header
                    {},
                    // body
                    JSON.stringify({
                        "clientTime": new Date(),
                        "tables": tables
                    })
                );

                // 타 세션 연결종료 수신
                client.subscribe(
                    '/topic/disconnect',
                    (res) => {
                        console.log(res.body);
                        res = JSON.parse(res.body);
                        setSessionCount(res.sessionCount);
                    }
                );

                // 불 상태변경 수신
                client.subscribe(
                    '/topic/change/fireStatus',
                    (res) => {
                        res = JSON.parse(res.body);
                        setSessionCount(res.sessionCount);
                        setTables(res.data.tables);
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
                "targetId": targetId,
            })
        )
    }

    return (
        <div>

            <h2>Demo</h2>
            <div className='info'>
                <ul>
                    <li>탭을 여러개 열어서 테스트한다.</li>
                    <li>세션 수가 1 이상이 되어야 정상적으로 연결 완료</li>
                    <li>03:00 ~ 13:00 사이에는 heroku 서버가 sleep 상태이므로, 최초 연결시까지 최대 15초 소요</li>
                </ul>

            </div>

            <br /><br /><br />

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

            <br /><br /><br />

            {(
                () => {
                    if (sessionCount === 0) {
                        return (<div><b>소켓 연결중 .....</b></div>);
                    }
                    else {
                        return (<div><b>현재 연결 세션 수 : {sessionCount}</b></div>);
                    }
                }
            )()}



        </div>
    );
}

export default Home;

