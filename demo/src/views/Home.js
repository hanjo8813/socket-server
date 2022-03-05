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

                // client.subscribe(
                //     '/topic/change',    // 구독주소
                //     (res) => {  // 콜백
                //     }
                // );
            }
        );
    }, []);

    // state
    const [tables, setTables] = useState([
        { "id": 1, "status": "blue" },
        { "id": 2, "status": "blue" },
        { "id": 3, "status": "blue" },
        { "id": 4, "status": "red" }
    ]);

    // onClick
    const sendStatus = (id) => {
        client.send(
            'change',
            {},
            JSON.stringify({
                "id" : id,
                "tables" : tables
            })
        )
    }


    // const changeStatus = (id, status) => {
    //     var temp = [];
    //     for(var table of tables){
    //         if(table.id === id){
    //             table.status = status;
    //         }
    //         temp.push(table);
    //     }
    //     setTables(temp);
    // }

    return (
        <div>

            <h2>Demo</h2>
            <hr /><br />

            {
                tables.map(v => {
                    if (v.status === "blue") {
                        return (<button className='table_blue' onClick={() => sendStatus(v.id, "red")} key={v.id}>{v.id}</button>);
                    }
                    else {
                        return (<button className='table_red' onClick={() => sendStatus(v.id, "blue")} key={v.id}>{v.id}</button>);
                    }
                })
            }

        </div>
    );
}

export default Home;

