# socket-server

<br>

<table>
<tr>
<td>서버상태</td>
<td><image src="https://img.shields.io/website?down_message=DOWN&up_message=UP&label=server&url=https://kkakdduk.herokuapp.com/health"/> </td>
</tr>

<tr>
<td>데모링크</td>
<td><a href="https://kkakdduk-demo.netlify.app/">https://kkakdduk-demo.netlify.app/</a></td>
</tr>
</table>

<br>

## Socket API

<table>
<tr>
<td>API 도메인</td>
<td><code>https://kkakdduk.herokuapp.com/ws</code></td>
</tr>
</table>

### 연결 시작시 테이블 초기화

`client -> server`

```
Destination URL : /connect

{
    "clientTime" : 클라이언트 시간,
    "tables" : [
        {
            "id" : 테이블 번호,
            "fireStatus" : 불 상태
        },
        ...
    ] 
}
```

- 만약 서버에 연결된 세션이 없는 상태라면 클라이언트의 테이블 상태로 초기화시킴

`server -> client`

```
Subscribe URL : /topic/connect

{
    "serverTime" : 서버시간,
    "sessionCount" : 현재 연결된 세션 수,
    "data" : {
        "clientTime" : 최초 연결 시간,
        "tables" : [
            {
                "id" : 테이블 번호,
                "fireStatus" : 불 상태
            },
            ...
        ]
    }
}
```

- 서버의 테이블 정보로 응답

### 불 상태 변경

`client -> server`

```
Destination URL : /change/fireStatus

{
    "targetId" : 불 상태 바꿀 테이블 ID
}
```

`server -> client`

```
Subscribe URL : /topic/change/fireStatus

{
    "serverTime" : 서버시간,
    "sessionCount" : 현재 연결된 세션 수,
    "data" : {
        "tables" : [
            {
                "id" : 테이블 번호,
                "fireStatus" : 불 상태
            },
            ...
        ]
    }
}
```

### 세션 연결 종료시

`server -> client`

```
Subscribe URL : /topic/disconnect

{
    "serverTime" : 서버시간,
    "sessionCount" : 현재 연결된 세션 수,
    "data" : null
}
```

- 타 세션이 종료되었을때 모두에게 알림

<br>