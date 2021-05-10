//This file is the model of transfer videos and images
//express框架
//引入express组件
const express = require('express');
//app 监听函数
const app = express();
const fs = require('fs');
const path = require('path')

app.use(express.static('public'));

app.get('/public/picture/*', function (req, res) {
    var filePath = path.resolve('./'+req.url);
    res.sendFile( filePath );
    console.log("Request for " + req.url + " received.");
})

app.get('/public/video/*', function (req, res) {
    var filePath = path.resolve('./'+req.url);
    res.sendFile( filePath );
    console.log("Request for " + req.url + " received.");
})

app.get('/public/html/index.html', function (req, res) {
   var filePath = path.resolve('./public/html/index.html');
   res.sendFile( filePath);
   console.log("Request for " + req.url + " received.");
})

app.get('/', function(req, res, next){ //require, response
  const filePath = path.resolve('./public/picture/IMG_0165.jpg');
  //给前端返回一个文件流

  //格式必须为binary，否则会出错
  //创建文件可读流
  const stream = fs.createReadStream(filePath);
  stream.on('data',chunk=>{
    res.write(chunk);
  })
  stream.on('end',()=>{
    res.status(200);
    res.end();
  })
  console.log('Running......');
})
app.get('/video',function(req,res,next){
    let filePath = path.resolve('./public/video/IMG_6308.mp4');
    let stat = fs.statSync(filePath);
    let fileSize = stat.size;
    let range = req.headers.range;

    // fileSize 3332038

    if (range) {
        //有range头才使用206状态码

        let parts = range.replace(/bytes=/, "").split("-");
        let start = parseInt(parts[0], 10);
        let end = parts[1] ? parseInt(parts[1], 10) : start + 999999;

        // end 在最后取值为 fileSize - 1
        end = end > fileSize - 1 ? fileSize - 1 : end;

        let chunksize = (end - start) + 1;
        let file = fs.createReadStream(filePath, { start, end });
        let head = {
            'Content-Range': `bytes ${start}-${end}/${fileSize}`,
            'Accept-Ranges': 'bytes',
            'Content-Length': chunksize,
            'Content-Type': 'video/mp4',
        };
        res.writeHead(206, head);
        file.pipe(res);
    } else {
        let head = {
            'Content-Length': fileSize,
            'Content-Type': 'video/mp4',
        };
        res.writeHead(200, head);
        fs.createReadStream(filePath).pipe(res);
    }
})



var server = app.listen(3000, function(){

  var host = server.address().address
  var port = server.address().port
  console.log('应用实例，访问地址为 http://'+ host +':'+ port)
})//在 3000 端口展开服务，如果成功，进入 function
