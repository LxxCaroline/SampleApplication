var net = require('net'); 
var options = {
        port: 9000,
        host: '10.240.35.18'
    };

var client = net.connect(options, function () {
        client.write('hello beauty');
    });

client.on('data', function (data) {
    console.log(data.toString());
    // client.end();
});