const express = require('express');
const router = express.Router();

//*******************************************
//登录页面：该页面在显示时，不需要向后端请求资源

//触发按钮 activities
//登录 Sign-in
//前端向后端发送邮箱和密码，后端向前端返回登录信息
router.post('/sign-in/submit',login.sign-in);
//注册 Sign-up -> 跳转至注册页面1
//注册页面1: 该页面在显示时，不需要向后端请求资源
//发送验证码
//前端向后端发送邮箱，后端生成验证码发送至邮箱
router.post('/sign-up/email',login.sign-up_email);
//验证验证码
//前端向后端发送email和验证码，后端返回验证状态
router.post('/sign-up/code',login.sign-up_code);
//验证成功后，由注册页面1跳转至注册页面2
//注册页面2：该页面在显示时，不需要向后端请求资源
//注册
//前端向后端发送邮箱和密码，后端返回注册状态和用户id
router.post('/sign-up/pwd',login.sign-up_pwd);
//忘记密码 Forget Password -> 跳转至忘记密码页面
//忘记密码页面:该页面在显示时，不需要向后端请求资源
//发送验证码
//前端向后端发送邮箱，后端生成验证码发送至邮箱
router.post('/forgetpwd',login.forget-pwd_emal);
//验证验证码
//前端向后端发送email和验证码，后端返回验证状态
router.post('/forgetpwd/code',login.forget-pwd_code);
//验证成功后，由忘记密码页面跳转至修改密码页面
//修改密码页面：该页面在显示时，不需要向后端请求资源
//注册
//前端向后端发送邮箱和密码，后端返回注册状态和用户id
router.post('/changepwd',login.forget-pwd_pwd);


module.exports = router;
