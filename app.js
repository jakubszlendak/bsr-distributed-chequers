var express = require('express');
var expressWs = require('express-ws');
var expressWs = expressWs(express());
var app = expressWs.app;
var path = require('path');
var net = require('net')


app.use(express.static(path.join(__dirname, 'public')));


app.ws('/', function(ws, req) {
  console.log('Incoming connection...')

  let tcp = new net.Socket()
  tcp.connect(4000)

  ws.on('message', function(msg) {
    console.log('Client message: ', msg)
    tcp.write(msg)
  })

  tcp.on('data', (data) => {
    console.log('Server message: ', data.toString())    
    ws.send(data.toString(), (err)=>{
      console.log('Client write error', err)
    })
  })

  tcp.on('error', (err)=>{
    if(err) console.log('Server connection error', err)
    ws.close()
  })

  ws.on('close', ()=>{
    tcp.end()
  })
});

app.listen(3000)