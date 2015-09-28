// 在前者的基础上，实现 Client --> Sever 的通讯，如此一来便是双向通讯  
var net = require('net');  
var chatServer = net.createServer(),      
    clientList = [];  
      
chatServer.on('connection', function(client) {  
	console.log("new client has join");
  // JS 可以为对象自由添加属性。这里我们添加一个 name 的自定义属性，用于表示哪个客户端（客户端的地址+端口为依据）  
  client.name = client.remoteAddress + ':' + client.remotePort;    
  client.write('Helloworld ' + client.name + '!\n');    
  client.write("test\n");
  clientList.push(client);    
  client.on("data",function(data) {
        broadcast(data, client);// 接受来自客户端的信息    
    });
  client.on('end',function(){
  	console.log("a client has exited");
  	clientList.splice(clientList.indexOf(client), 1);
  });

});  

// function broadcast(message, client) { 
// 	var cleanup = []  
// 	  for(var i=0;i<clientList.length;i++) {  
// 	    if(client != clientList[i]) {  
	  
// 	      // if(clientList[i].writable) { // 先检查 sockets 是否可写  
// 	        clientList[i].write(client.name + " says " + message)  
// 	      // } else {  
// 	        // cleanup.push(clientList[i]) // 如果不可写，收集起来销毁。销毁之前要 Socket.destroy() 用 API 的方法销毁。  
// 	        // clientList[i].destroy()  
// 	      // }  
	  
// 	    }  
// 	  }  //Remove dead Nodes out of write loop to avoid trashing loop index   
// 	  for(i=0;i<cleanup.length;i+=1) {  
// 	    clientList.splice(clientList.indexOf(cleanup[i]), 1)  
// 	  }     
// } 

function broadcast(message, client) {    
    for(var i=0;i<clientList.length;i++) {  
    console.log("broadcast:"+i+"---"+clientList[i].name);    
      if(client !== clientList[i]) {        
        clientList[i].write(client.name + " says " + message+"\n");      
      }    
    }  
}  

chatServer.listen(9000,"10.240.35.18");  