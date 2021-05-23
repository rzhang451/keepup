const email = require('../funcs/nodemailer');
const validator = require('validator');
const uuid = require('node-uuid');;

var signUpCheck = {};
var forgetPwdCheck = {};
exports.sign_in = (req,res,next)=>{
  //假如已经登录
  if(req.session.usr){
    return res.json({
      msg:'Welcome',
      code: '200',
      data:[{id:req.session.usr}]
    });
  }
  var email = req.body.email;
  var password = req.body.pwd;
  //从profile数据库中查询
  Profile.find({email: email},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
    }
    if(!docs.length){
      //返回用户不存在
      return res.json({
        msg:'User doesn\'t existe!',
        code: '201'
      });
    }else if(docs[0].password == person.password){
      //返回登录成功,返回用户id
      res.session.usr = docs[0].id;
      return res.json({
        msg:'Welcome',
        code: '200',
        data: [{id:docs[0].id}]
      });
    }else{
      //返回密码错误
      return res.json({
        msg:'Password incorrect!',
        code: '202',
      });
    } 
  })
  next();
} 

exports.sign_up_email = (req,res,next)=>{
  var mail = req.body.email;
  var code = parseInt((Math.random(0,1)*0.9+0.1)*10000);
  if(!validator.isEmail(email)){
      return res.json({
        msg:'This is not a email!',
        code: -1
      });
  }   
  Profile.find({email: mail},(err,docs)=>{
    if(!err){
      if(docs.length){
        return res.json({
          msg: 'Email already used!',
          code: -1
        });
      }else{
        signUpCheck[mail]=code;
        email.sendMail(mail,'signup',code,(state)=>{
          if(state){
            console.log('已发送');
            return res.json({
              msg:'Send Code Successful',
              code:200
            });
          }else{
            console.log('发送失败');
            return res.json({
              msg:'Send Code Failed',
              code:-1
            });
          } 
        });
      } 
    }else{
      return res.json({
        msg:console.error(err),
        code:-1
      });
    } 
  })
  next();
} 


exports.sign_up_code = (req,res,next)=>{
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
  next();
}

exports.sign_up_pwd = (req,res,next)=>{
  if(req.body.pwd!=req.body.check){
    return res.json({
      msg:'passwords are not the same!',
      code:'error1'
    })
  }
  if(!validator.matches(password,/(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{5,}/,'g') || !validator.isLength(password,6,12)){
      return res.json({
        msg:'Password Invalide,the length should be between 6 and 12, please enter again',
        code: 'error2'
      });
  }
  var user = new Profile({
    id: uuid.v1,
    email: req.body.email,
    password: req.body.pwd
  });
  user.save(function(err,user){
    if(err){
      console.log(err);
      return res.json({
        msg:'Failed to Save User',
        code:'error3'
      });
    }else{
      res.session.usr = usr.id;
      return res.json({
        msg:'Welcome',
        code:'success',
        data:user.id
      });
    }
  });
  next();
}

exports.forget_pwd = (req,res,next)=>{
  var mail = req.body.email;
  var code = parseInt((Math.random(0,1)*0.9+0.1)*10000);
  if(!validator.isEmail(mail)){
      return res.json({
        msg:'Email Invalide!',
        code: 'error1'
      });
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
              code:'error2',
            });
          }
        });
      }else{
        res.json({
          msg:"Email doesn't existe!",
          code:'error3'
        });
      }
    }else{
      return res.json({
        msg:console.error(err),
        code:'error4'
      });
    }
  })
  next();
}

exports.forget_pwd_code = (req,res,next)=>{
  var mail = req.body.email;
  var code = req.body.code;
  if(!validator.matches(password,/(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[_#@]+$).{5,}/,'g') || !validator.isLength(password,6,12)){
      return res.json({
        msg:'Password Invalide,the length should be between 6 and 12, please enter again',
        code: 'error1'
      });
  }
  if(code == forgetPwdCheck[email]){
    return res.json({
      msg:"Please Enter Your New Password",
      code:'success'
    });
  }else{
    return res.json({
      msg:"Authentification Code Incorrect",
      code:'error2'
    });
  }
  next();
}

exports.forget_pwd_pwd=(req,res,next)=>{
  Profile.update({email:req.body.email},{$set:{password:req.body.pwd}},(err)=>{
    if(!err){
      Profile.findOne({email: mail},(err,docs)=>{
        if(err) return console.error(err);
        res.session.usr = docs.id;
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
      });
    }
  });
  next();
}


