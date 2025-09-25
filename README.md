# 💳 Real-Time Payment Status POC (Kotlin + Quarkus + SSE)

This project demonstrates a simple **real-time payment status tracker** using:

* **Backend**: Kotlin + Quarkus (JDK 21, Gradle 8.14.x)
* **Frontend**: React (uses native `EventSource` API)
* **Communication**: Server-Sent Events (**SSE**) via `SseEventSink`

The goal: instead of repeatedly polling the server for updates, the client keeps one open connection, and the server pushes status changes in **real-time**.

---

## 🚀 Features

* Exposes an SSE endpoint `/payment/events/status`
* Server emits events sequentially: `PENDING → PROCESSING → SUCCESSFUL`
* React UI displays: `Your latest payment status is - <STATUS>`
* Uses `Sse` + `SseEventSink` API (Jakarta standard) instead of coroutines `Flow`

## 📊 How SSE Works

```mermaid
sequenceDiagram
    participant C as Client
    participant S as Server

    C->>S: HTTP GET /events<br/>(Accept: text/event-stream)
    S-->>C: HTTP 200 OK<br/>(Content-Type: text/event-stream)

    S-->>C: event: message<br/>data: {"msg": "Update 1"}<br/>id: 1<br/><br/>
    Note over C: Client processes event

    S-->>C: event: message<br/>data: {"msg": "Update 2"}<br/>id: 2<br/><br/>

    Note over S,C: Connection stays open
    S-->>C: : keepalive<br/><br/>

    Note over C: Optional: Server closes connection
    C->>S: EventSource.close()
    Note over C,S: Connection closed
```



* Client opens **one HTTP connection** using `EventSource`.
* Server keeps connection open and **pushes events** when ready.
* Client updates UI live without polling.

---

## ✅ Advantages of SSE

* Native browser support (`EventSource` → no extra libs)
* Lightweight compared to polling
* One persistent connection per client
* Perfect for **status updates, notifications, dashboards**

---
