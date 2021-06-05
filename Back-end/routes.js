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
//define the storage of avatar
/*var storage_avatar = multer.diskStorage({
  destination:path.resolve(__dirname,'../public/upload/image/profile'),
  filename:function(req,file,cb){
  let extName = file.originalname.slice(file.originalname.lastIndexOf('.'))
  let filename = docs[0].username
  cb(null,filename + extName)
  }
});

var imageUploader_avatar = multer({
  storage_avatar:storage_avatar
});*/
//define the storage of post image
/*var storage_social = multer.diskStorage({
  destination:path.resolve(__dirname,'../public/upload/image/social'),
  filename:function(req,file,cb){
    let extName = file.originalname.slice(file.originalname.lastIndexOf('.'))
    let filename = docs[0].username + Date.now()
    cb(null,filename + extName)
  }
});

var imageUploader_social = multer({
  storage_social:storage_social
});*/


//*******************************************
//log in page: when showing this page, there is no need to demande ressource from backend

router.get('/sign-in/auto',login.sign_in_auto);
//Press on the button: activities
// Sign-in
//F send email address and password to b, b return log in information
router.post('/sign-in/submit',login.sign_in);
//Sign-up -> turn to sign-up page1
//Sign-up page 1: no need to demande ressource from backend
//send verification code
//F send email, b create verification code and send it to email
router.post('/sign-up/email',login.sign_up_email);
//verify verification code
//F send email and code, b retuen status
router.post('/sign-up/code',login.sign_up_code);
//After successful verification, turn sign-up page 1 to sign-up page 2
//Sign-up page 2: no need to demande ressource from backend
//Sign-up
//F send email and password to b, b return status and user id
router.post('/sign-up/pwd',login.sign_up_pwd);
//Forget password -> turn to page
//Forget password page : no need to demande ressource from backend
//send verification code
//F send email and code, b retuen status
router.post('/forgetpwd/email',login.forget_pwd_email);
//After successful verification, turn forget password page to change password page
//Change passwordd page: no need to demande ressource from backend
//Sign-up
//F send email and password to b, b return status and user id
router.post('/forgetpwd/login',login.forget_pwd_login);

//***************************
//Home:when showing this page, f send user id to b, b return recommended and favourite courses

//Return recommend course
//router.get('/mainpage/recommend',home.recommend);

//Return recommend course
router.get('/mainpage/favorite',home.show_favor_course);

//Press the button activities

//search
//conditional search
router.get('/search/label',search.label_search);
//Turn to search page
//F send keyword, b return courses and users
//find courses
router.get('/search',search.search_course);
//find users
router.get('/search',search.search_user);
//find cover
router.get('/cover',search.cover);


//***************************
//agenda
router.get('/getdate',agenda.initialize_agenda);
router.post('/adddate',agenda.add_agenda);
//***************************
//social media
//see other's profile in media
router.get('/profile/user/:id',media.user);
//add a follow
//router.get('/media/subscribe/add/:id',media.add_follow);
//write and share in media*/
router.post('/media/post',media.add_blog);
//Download blog photos
//router.post('media/image',imageUploader_social,function(req, res){
  //console.log(req.files);
//})
//write comment
router.post('/media/comment',media.add_comment);
//show the posts of the people followed by a user
router.get('/media/showposts_follow/:id',media.show_blog_follow);
//show user's own posts
router.get('/media/showposts_self/:id',media.show_blog_self);
//show other users' profile
router.get('/media/showposts_all/:id',media.show_blog_all);
//thumb up for a post
router.post('/media/thumb_up',media.thumb_up);

//***************************
//router.get('/public/image/*',users.image);

//router.get('/course/info/:id',users.info);
//Record after exercises
//router.get('/course/check',users.check);

//Load personal page
//router.get('/profile/:id',users.profile);

//load Health page
/*router.get('profile/health/:id',users.health);
//search user's follower via id
router.get('profile/follower/:id',users.follower);
//search user's follow
router.get('profile/subscribe/:id',users.subscribe);
//upload avatar
router.post('profile/avatar/upload',home.avatar);
//change username/sex/location/introduction
router.post('profile/change',users.change_profile);
//change password
router.post('/profile/changepwd',users.change_pwd);
//change height and weight
router.post('/profile/health',users.change_health);
//add the course to favor course*/
router.get('/search/add_favor',users.favor_course);
//return video information
router.get('/course',users.video);

//set save rules
var storage_avatar = multer.diskStorage({
  //destination：upload path which can be a function
  destination: path.resolve(__dirname, './public/upload/image/profile'),

  //filename：Set the file name to save the file
  filename: function(req, file, cb) {
      let extName = file.originalname.slice(file.originalname.lastIndexOf('.'))
      let fileName = UUID.v1()
      cb(null, fileName + extName)
  }
})

//*******************************更改头像**********************************
//Set filter rules (optional)
var imageFilter = function(req, file, cb){
  var acceptableMime = ['image/jpeg', 'image/png', 'image/jpg', 'image/gif']
  //Only the above four types of pictures are accepted
  if(acceptableMime.indexOf(file.mimetype) !== -1){
      cb(null, true)
  }else{
      cb(null, false)
  }
}
/*
//Set limits (optional)
var imageLimit = {
  fieldSize: '2MB'
}
*/
//create multer example
var imageUploader_avatar = multer({ 
  storage: storage_avatar,
  fileFilter: imageFilter,
  //limits: imageLimit
}).array('photo', 1)    //Define form fields and quantity limits

router.post('/profile/change_avatar', imageUploader_avatar, home.avatar)


module.exports = router;
