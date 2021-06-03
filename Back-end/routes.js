const express = require('express');
const router = express.Router();
const multer = require('multer');
const fs = require('fs');
const gm = require('gm');
const uploadAvatar = multer({dest:__dirname + '/public/upload/image/profile'});
const uploadMedia = multer({dest:__dirname + '/public/upload/image/social'});
const login = require('./routes/login');
const home = require('./routes/home');
const agenda = require('./routes/agenda');
const users = require('./routes/users'); 
const media = require('./routes/media');
const search = require('./routes/search');

//*******************************************
//登录页面：该页面在显示时，不需要向后端请求资源

//触发按钮 activities
//登录 Sign-in
//前端向后端发送邮箱和密码，后端向前端返回登录信息
router.post('/sign-in/submit',login.sign_in);
//注册 Sign-up -> 跳转至注册页面1
//注册页面1: 该页面在显示时，不需要向后端请求资源
//发送验证码
//前端向后端发送邮箱，后端生成验证码发送至邮箱
router.post('/sign-up/email',login.sign_up_email);
//验证验证码
//前端向后端发送email和验证码，后端返回验证状态
router.post('/sign-up/code',login.sign_up_code);
//验证成功后，由注册页面1跳转至注册页面2
//注册页面2：该页面在显示时，不需要向后端请求资源
//注册
//前端向后端发送邮箱和密码，后端返回注册状态和用户id
router.post('/sign-up/pwd',login.sign_up_pwd);
//忘记密码 Forget Password -> 跳转至忘记密码页面
//忘记密码页面:该页面在显示时，不需要向后端请求资源
//发送验证码
//前端向后端发送邮箱，后端生成验证码发送至邮箱
router.post('/forgetpwd/email',login.forget_pwd_email);
//验证验证码
//前端向后端发送email和验证码，后端返回验证状态
router.post('/forgetpwd/login',login.forget_pwd_login);

//***************************
//主页: 该页面在显示时，前端向后端发送用户id，后端返回推荐的和已收藏的课程

//返回推荐课程
router.get('/mainpage/recommand/:id',home.recommand);

//返回收藏课程
router.get('/mainpage/favorite/:id',home.show_favor_course);

//return list of followings
router.get('/profile/follow',home.show_follow);
//触发按钮 activities

//search
//conditional search
router.get('/search/label',search.label_search);
//全局搜索跳转到结果页面
//前端向后端发送关键词，后端向前端返回用户详情，课程详情和动态详情
//find courses
router.get('/search',search.search_course);
//find users
router.get('/search',search.search_user);


//***************************
//agenda
router.get('/agenda/:id',agenda.agenda);

//***************************
//social media
//see other's profile in media
router.get('/profile/user/:id',media.show_otherProfile);
//add a follow
router.get('/media/subscribe/add/:id',media.add_follow);
//write and share in media
router.post('/media/post',media.add_blog);
//write comment
router.post('/media/comment',media.add_comment);
//show the posts of the people followed by a user
router.get('/media/showposts_follow/:id',media.show_blog_follow);
//show user's own posts
router.get('/media/showposts_self/:id',media.show_blog_self);
//show other users' profile
router.get('/media/showothers/:id',media.show_blog_all);
//thumb up for a post
router.post('/media/thumb_up',media.thumb_up);

//***************************
router.get('/public/image/*',users.image);

router.get('/course/info/:id',users.info);
//运动结束后记录
router.get('/course/check',users.check);

//加载个人主页
router.get('/profile/id',users.profile);

//load Health page
router.get('/profile/health',users.health);
//根据用户id查看follower
router.get('/profile/follower/:id',users.follower);
//根据用户id查看已关注到人
router.get('/profile/subscribe/:id',users.subscribe);
//更改头像
router.post('/profile/avatar/upload', uploadAvatar.single('photo'),users.avatar);
//修改用户名/性别/地点/个人简介
router.post('/profile/change',users.change_profile);
//修改密码
router.post('/profile/changepwd',users.change_pwd);
//修改身高体重
router.post('/profile/health',users.change_health);
//add the course to favor course
router.get('/search/add_favor',users.favor_course);
//返回视频信息
router.get('/course',users.video);


module.exports = router;
