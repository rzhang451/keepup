//引入express组件
const express = require('express');
//app 监听函数
app = express();

//引入body-parser组件，用于post请求
const bodyParser = require('body-parser');
//引入cookie组件
const cookie = require('cookie-parser');
//引入session组件
const session = require('express-session');
const path = require('path');
//引入routes路由接口
const routes = require('./routes');


//bodyParser初始化
app.use(bodyParser.urlencoded({extended:false}));
app.use(bodyParser.json())
app.all('*',function(req,res,next){
  res.header('Access-Control-Allow-Origin','*');
  res.header('Access-Control-Allow-Header','X-Request-With,Content-Type');
  res.header('Access-Control-Allow-Method','GET,POST,OPTIONS')
  next();
})

//初始化cookie和session
app.use(cookie('session'));
app.use(session({
  secret:'keepUp',
  resave:false,
  saveUninitialized:true,
  cookie:{
    maxAge: 60*60*1000
  },
  rolling:true
}));


//调用静态资源
//app.use(express.static('public'));
app.use('/',routes);
app.use('/video',express.static('./public/video'));
app.use('/images',express.static('./public/images'));

module.exports = app;
