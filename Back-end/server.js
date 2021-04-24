//express框架
//引入express组件
const express = require('express');
//引入body-parser组件，用于post请求
const bodyParser = require('body-parser');
//app 监听函数
const app = express();

//引入routes
const profileRouter = require('./routers/profileRouter.js');
//const levelTest = require('./routers/levelTestRouter');
//const search = require('./routers/searchRouter');
//const course = require('./routers/courseRouter');
//const sharedMedia = ('./routers/sharedMediaRouter');
//const setting = require('./routers/settingRouter');
//引入mongodb
//const mongoDB = require('./database/mongoose');

//bodyParser初始化
app.use(bodyParser.urlencoded({extended:false}));
app.use(bodyParser.json())
app.all('*',function(req,res,next){
  res.header('Access-Control-Allow-Origin','*');
  res.header('Access-Control-Allow-Header','X-Request-With,Content-Type');
  res.header('Access-Control-Allow-Method','GET,POST,OPTIONS')
  next();
})

//调用routes
app.use('/',profileRouter);


app.get('/', function(req, res){ //require, response
  res.send('Hello World');
  console.log('Hello World');
})

var server = app.listen(3000, function(){

  var host = server.address().address
  var port = server.address().port
  console.log('应用实例，访问地址为 http://'+ host +':'+ port)
})//在8081端口展开服务，如果成功，进入function
