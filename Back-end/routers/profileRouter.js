//引入express框架
const express = require('express');
//声明跳转路由
const router = express.Router();
//声明引用数据库
const Profile = require('../database/profileModel');
//引入sendEmail
const email = require('./functions/nodemailer');
const uuid = require('node-uuid');

//*******************************************
//登录页面：该页面在显示时，不需要向后端请求资源

//触发按钮 activities
//登录 Sign-in
//前端向后端发送邮箱和密码，后端向前端返回登录信息
router.get('/sign-in/submit',function(req,res){
  var person = new Profile({
    email: req.query.email,
    password: req.query.pwd
  });
  //从profile数据库中查询
  Profile.find({email: person.email},(err,docs)=>{
    if(err){
      return console.error(err);
    }
    if(!docs.length){
      //返回用户不存在
      res.send('User doesn\'t existe!');
    }else if(docs[0].password == person.password){
      //返回登录成功,返回用户id
      res.send('Welcome User: '+ docs[0].id);
    }else{
      //返回密码错误
      res.send('Password incorrect!');
    }
  })
});
//注册 Sign-up -> 跳转至注册页面1
//注册页面1: 该页面在显示时，不需要向后端请求资源
//发送验证码
//前端向后端发送邮箱，后端生成验证码发送至邮箱
var signUpCheck = {};
router.get('/sign-up/email/:email',function(req,res){
  var mail = req.params.email;
  var code = parseInt((Math.random(0,1)*0.9+0.1)*10000);
  Profile.find({email: mail},(err,docs)=>{
    if(!err){
      if(!docs.length){
        res.send('Email already used!');
      }else{
        signUpCheck[mail]=code;
        email.sendMail(mail,'signup',code,(state)=>{
          if(state){
            return res.send('Send Code Successful')
          }else{
            return res.send('Send Code Failed')
          }
        });
      }
    }else{
      return console.error(err);
    }
  })
});
//验证验证码
//前端向后端发送email和验证码，后端返回验证状态
router.get('/sign-up/code',function(req,res){
  var mail = req.query.email;
  var code = req.query.code;
  if(code == signUpCheck[mail]){
    return res.send('Please Enter Your Password');
  }else{
    return res.send('Authentification Code Incorrect');
  }
});
//验证成功后，由注册页面1跳转至注册页面2
//注册页面2：该页面在显示时，不需要向后端请求资源
//注册
//前端向后端发送邮箱和密码，后端返回注册状态和用户id
router.get('/sign-up/pwd',function(req,res){
  var user = new Profile({
    id = uuid.v1,
    email = req.query.email,
    password = req.query.pwd
  });
  user.save(function(err,user){
    if(err){
      console.log(err);
      return res.send('Failed to Save User');
    }else{
      return res.send('Welcome User: '+user.id);
    }
  });
});
//忘记密码 Forget Password -> 跳转至忘记密码页面
//忘记密码页面:该页面在显示时，不需要向后端请求资源
//发送验证码
//前端向后端发送邮箱，后端生成验证码发送至邮箱
var forgetPwdCheck = {};
router.get('/forgetpwd/:email',function(req,res){
  var mail = req.params.email;
  var code = parseInt((Math.random(0,1)*0.9+0.1)*10000);
  Profile.find({email: mail},(err,docs)=>{
    if(!err){
      if(!docs.length){
        forgetPwdCheck[mail]=code;
        email.sendMail(mail,code,(state)=>{
          if(state){
            return res.send("Send Code Successful")
          }else{
            return res.send("Send Code Failed")
          }
        });
      }else{
        res.send("Email doesn't existe!");
      }
    }else{
      return console.error(err);
    }
  })
});
//验证验证码
//前端向后端发送email和验证码，后端返回验证状态
router.get('/forgetpwd/code',function(req,res){
  var mail = req.query.email;
  var code = req.query.code;
  if(code == forgetPwdCheck[email]){
    return res.send("Please Enter Your New Password")
  }else{
    return res.send("Authentification Code Incorrect")
  }
});
//验证成功后，由忘记密码页面跳转至修改密码页面
//修改密码页面：该页面在显示时，不需要向后端请求资源
//注册
//前端向后端发送邮箱和密码，后端返回注册状态和用户id
router.get('/changepwd',function(req,res){
  Profile.update({email:req.query.email},{$set:{password:req.query.pwd}},(err)=>{
    if(!err){
      Profile.findOne({email: mail},(err,docs)=>{
        if(err) return console.error(err);
      })
      return res.send("Password Modified "+ docs.id)
    }else{
      return res.send("problem with modification")
    }
  })
});

module.exports = router;

