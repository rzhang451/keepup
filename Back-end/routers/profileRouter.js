//引入express框架
var express = require('express');
//申明跳转路由
var router = express.Router();
//声明引用数据库
var Profile = require('../database/profileModel');

//sign-in 登陆
router.get('/login/submit',function(req,res){
  var person = new Profile({
    username: req.query.usr,
    password: req.query.pwd
  });
  Profile.find({username: person.username},(err,docs)=>{
    if(err){
      return console.error(err);
    }
    if(!docs.length){
      res.send("Username incorrect!");
    }else if(docs[0].password == person.password){
      res.send("Login successful!");
    }else{
      res.send("Password incorrect!");
    }
  })
});

//sign-up 注册
router.get('/login/sign-up/submit',function(req,res){
  var person = new Profile({
    username: req.query.usr,
    password: req.query.pwd
  });
  Profile.find({username: person.username},(err,docs)=>{
    if(!err){
      if(!docs.length){
        person.save(function(err,person){
          if(err){
            return console.error(err);
          }else{
            res.send("You have successfully signed up!");
          }
        });
      }else{
        res.send("Username already used!");
      }
    }else{
      return console.error(err);
    }
  })
});

//levelTest



module.exports = router;
