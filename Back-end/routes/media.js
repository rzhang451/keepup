var express = require('express');
var router = express.Router();
var responseData;

//var Profile = require('../model/profileModel');
//var Follow = require('../model/followModel');
var Media = require('../model/mediaModel');

router.use(function(req,res,next){
  var content = req.body.content;){
  responseData = {
    code:0,
    message: ''
  }
});

router.get('/',function(req,res,next){
    var content = req.body.content;

    if(content = ''){
      responseData.code = 1;
      responseData.message = 'Content cannot be empty.';
      res.json(responseData);
      return;
    }

    responseData.message = 'Success to post'
});
module.exports = router;





/*const Profile = require('../model/profileModel');
const Follow = require('../model/followModel');
const Media = require('../model/mediaModel');




exports.user = (req,res,next)=>{
  Profile.findOne({id:req.params.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: 'error'
      });
    }
    if(!docs){
      return res.json({
        msg:'Username incorrect!',
        code: 'error'
      });
    }
    Follow.findOne({id:req.params.id},(err,files)=>{
      if(err){
        return res.json({
          msg:'Failed to connect',
          code: 'error'
        });
      }
      if(!files){
        return res.json({
          msg:'Username incorrect!',
          code: 'error'
        });
      }
    var profile = {
      username: docs.username,
      sex: docs.sex,
      location: docs.location,
      miniIntro: docs.miniIntro
    };
    var follow = {
      follow: files.follow,
      follower: files.follower
    };
    var info = [];
    Media.find({id:req.params.id},(err,actus)=>{
      if(err){
        return res.json({
          msg:'Failed to connect',
          code: 'error'
        });
      }
      var i=0;
      while(i<3&&i<actus.length){
        info.append(actus[i]);
        i++;
      }
    });
    return res.json({
      msg:'Profile is returned',
      code:'success',
      profile:profile,
      info:info
    });
  });
  next();
}
*/