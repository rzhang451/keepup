const express = require('express');
const router = express.Router();
const login = require('./routes/login');
const home = require('./routes/home');
const agenda = require('./routes/agenda');
const media = require('./routes/media');
const users = require('./routes/users'); 
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
router.post('/forgetpwd/email',login.forget-pwd_emal);
//验证验证码
//前端向后端发送email和验证码，后端返回验证状态
router.post('/forgetpwd/code',login.forget-pwd_code);
//验证成功后，由忘记密码页面跳转至修改密码页面
//修改密码页面：该页面在显示时，不需要向后端请求资源
//注册
//前端向后端发送邮箱和密码，后端返回注册状态和用户id
router.post('/forgetpwd/pwd',login.forget-pwd_pwd);

//***************************
//主页: 该页面在显示时，前端向后端发送用户id，后端返回推荐的和已收藏的课程

//返回推荐课程
router.get('/mainpage/recommand/:id',home.recommand);

//返回收藏课程
router.get('/mainpage/favorite/:id',home.favorite);

//触发按钮 activities

//全局搜索 global search
//全局搜索跳转到结果页面
//前端向后端发送关键词，后端向前端返回用户详情，课程详情和动态详情
router.post('/search/global/:key',home.global_search);

//条件搜索 conditional search
router.post('/search/condition',home.conditional_search);

//***************************
//agenda
router.get('/agenda/:id',agenda.agenda);

//***************************
//social media
//看他人的个人主页
router.get('/profile/user/:id',media.user);

//***************************
router.get('/public/image/*',users.image);
router.get('/public/video/*',users.video);
router.get('/course/info/:id',users.info);
//运动结束后记录
router.get('/course/check',users.check);

//加载个人主页
router.get('/profile/:id',user.profile);
//根据用户id查看follower
router.get('profile/follower/:id',users.follower);
//根据用户id查看已关注到人
router.get('profile/subscribe/:id',users.subscribe);
//更改头像
router.post('profile/upload/',users.avatar);
//修改用户名/性别/地点/个人简介
router.post('profile/change',users.change_profile);
//修改密码
router.post('/profile/changepwd',users.change_pwd);
//修改身高体重
router.post('/profile/health',users.change_health);


module.exports = router;
