
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
router.post('/sign-in/submit',function(req,res){
  var person = new Profile({
    email: req.body.email,
    password: req.body.pwd
  });
  if(!validator.isEmail(email)){
      return res.json({
        msg:'邮箱格式不合法,请重新输入',
        code: 'error2'
      });'
  }
   if(!validator.matches(password,/(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{5,}/,'g') || !validator.isLength(password,6,12)){
      return res.json({
        msg:'密码不合法,长度在5-12位,请重新输入',
        code: 'error1'
      });'
  }
  //从profile数据库中查询
  Profile.find({email: person.email},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: 'error2'
      });
    }
    if(!docs.length){
      //返回用户不存在
      return res.json({
        msg:'User doesn\'t existe!',
        code: 'error1'
      });
    }else if(docs[0].password == person.password){
      //返回登录成功,返回用户id
      return res.json({
        msg:'Welcome',
        code: 0,
        info: docs[0].id
      });
    }else{
      //返回密码错误
      return res.json({
        msg:'Password incorrect!',
        code: 'error2',
      });
    }
  })
});
//注册 Sign-up -> 跳转至注册页面1
//注册页面1: 该页面在显示时，不需要向后端请求资源
//发送验证码
//前端向后端发送邮箱，后端生成验证码发送至邮箱
var signUpCheck = {};
router.post('/sign-up/email',function(req,res){
  var mail = req.body.email;
  var code = parseInt((Math.random(0,1)*0.9+0.1)*10000);
  if(!validator.isEmail(email)){
      return res.json({
        msg:'邮箱格式不合法,请重新输入',
        code: 'error2'
      });'
  }
  Profile.find({email: mail},(err,docs)=>{
    if(!err){
      if(!docs.length){
        return res.json({
          msg: 'Email already used!',
          code: 'error1'
        });
      }else{
        signUpCheck[mail]=code;
        email.sendMail(mail,'signup',code,(state)=>{
          if(state){
            return res.json({
              msg:'Send Code Successful',
              code:'success'
            });
          }else{
            return res.json({
              msg:'Send Code Failed',
              code:'error2'
            });
          }
        });
      }
    }else{
      return res.json({
        msg:console.error(err),
        code:'error3'
      });
    }
  })
});
//验证验证码
//前端向后端发送email和验证码，后端返回验证状态
router.post('/sign-up/code',function(req,res){
  var mail = req.body.email;
  var code = req.body.code;
  if(code == signUpCheck[mail]){
    return res.json({
      msg:'Please Enter Your Password',
      code: 'success'
    });
  }else{
    return res.json({
      msg:'Authentification Code Incorrect',
      code:'error'
    });
  }
});
//验证成功后，由注册页面1跳转至注册页面2
//注册页面2：该页面在显示时，不需要向后端请求资源
//注册
//前端向后端发送邮箱和密码，后端返回注册状态和用户id
router.post('/sign-up/pwd',function(req,res){
  var user = new Profile({
    id = uuid.v1,
    email = req.body.email,
    password = req.body.pwd
  });
  if(!validator.matches(password,/(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{5,}/,'g') || !validator.isLength(password,6,12)){
      return res.json({
        msg:'密码不合法,长度在5-12位,请重新输入',
        code: 'error1'
      });'
  }
  user.save(function(err,user){
    if(err){
      console.log(err);
      return res.json{(
        msg:'Failed to Save User',
        code:'error'
      )};
    }else{
      return res.json({
        msg:'Welcome',
        code:'success',
        info:user.id
      });
    }
  });
});
//忘记密码 Forget Password -> 跳转至忘记密码页面
//忘记密码页面:该页面在显示时，不需要向后端请求资源
//发送验证码
//前端向后端发送邮箱，后端生成验证码发送至邮箱
var forgetPwdCheck = {};
router.post('/forgetpwd',function(req,res){
  var mail = req.body.email;
  var code = parseInt((Math.random(0,1)*0.9+0.1)*10000);
  if(!validator.isEmail(email)){
      return res.json({
        msg:'邮箱格式不合法,请重新输入',
        code: 'error2'
      });'
  }
  Profile.find({email: mail},(err,docs)=>{
    if(!err){
      if(!docs.length){
        forgetPwdCheck[mail]=code;
        email.sendMail(mail,code,(state)=>{
          if(state){
            return res.json({
              msg:"Send Code Successful",
              code:'success',
            });
          }else{
            return res.json({
              msg:"Send Code Failed",
              code:'error1',
            });
          }
        });
      }else{
        res.json({
          msg:"Email doesn't existe!",
          code:'error2'
        });
      }
    }else{
      return res.json({
        msg:console.error(err),
        code:'error3'
      });
    }
  })
});
//验证验证码
//前端向后端发送email和验证码，后端返回验证状态
router.post('/forgetpwd/code',function(req,res){
  var mail = req.body.email;
  var code = req.body.code;
  if(!validator.matches(password,/(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{5,}/,'g') || !validator.isLength(password,6,12)){
      return res.json({
        msg:'密码不合法,长度在5-12位,请重新输入',
        code: 'error1'
      });'
  }
  if(code == forgetPwdCheck[email]){
    return res.json({
      msg:"Please Enter Your New Password",
      code:'success'
    });
  }else{
    return res.json({
      msg:"Authentification Code Incorrect",
      code:'error'
    });
  }
});
//验证成功后，由忘记密码页面跳转至修改密码页面
//修改密码页面：该页面在显示时，不需要向后端请求资源
//注册
//前端向后端发送邮箱和密码，后端返回注册状态和用户id
router.post('/changepwd',function(req,res){
  Profile.update({email:req.body.email},{$set:{password:req.body.pwd}},(err)=>{
    if(!err){
      Profile.findOne({email: mail},(err,docs)=>{
        if(err) return console.error(err);
      })
      return res.json({
        msg:"Password Modified ",
        code:'succss',
        info:docs.id
      });
    }else{
      return res.json({
        msg:"problem with modification",
        code:'error'
      )
    }
  });
});
module.exports = router;

