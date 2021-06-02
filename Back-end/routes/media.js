const Profile = require('../model/profileModel');
const Course = require('../model/courseModel');
const Media = require('../model/mediaModel');
const Follow=require('../model/followModel');
const multer = require ('multer');
const UUId = require('uuid');
var path = ('path');

exports.add_blog = (req,res)=>{
  Profile.find({id: req.params.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
      if(!docs.length){
      //返回用户不存在
        return res.json({
          msg:'User doesn\'t existe',
          code: '-1'
        });
      }
    }else{
      var storage = multer.diskStorage({
        destination:path.resolve(__dirname,'../public/upload/image/social'),
        filename:function(req,file,cb){
          let extName = file.originalname.slice(file.originalname.lastIndexOf('.'))
          let filename = docs[0].username + Date.now()
          cb(null,filename + extName)
        }
      })
      
      var imageUploader = multer({
        storage:storage
      })
      
      var diary = new Media ({
        id:uuid.v1,
        usr:req.body.usr,
        usr_id:req.body.usr_id,
        content:req.body.content,
        //pic:req.body.pic,
        pic:'../public/upload/image/social/'+ imageUploader.storage.filename + imageUploader.storage.extName,
        date:Date(),
        comment: [],
        like_usr:[],
        like:0
      });
      diary.save(function(err,diary){
        if(err){
          console.log(err);
          return res.json({
            msg:'Failed to Save the Diary',
            code:'-1'
          });
        }else{
          res.session.usr = diary.id;
          return res.json({
            msg:'diary is added',
            code:'200',
          });
        }
      });

    }
  })
}

exports.add_comment = (req,res,next)=>{
  Media.find({id: req.body.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
      if(!docs.length){
      //返回动态不存在
        return res.json({
          msg:'Diary doesn\'t existe',
          code: '-1'
        });
      }
    }else{
     //add new comment
      var comment_usr = req.body.usr;
      var comment_content = req.body.content;
      var comment_date = Date();
      comment.add(comment_usr,comment_content,comment_date);
      return res.json({
        msg:'comment is added',
        code:'200'
      });
    }
  })
}

exports.show_blog_self=(req,res,next)=>{
  Profile.findOne({id:req.params.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
    }
    if(!docs){
      return res.json({
        msg:'Username incorrect!',
        code: '-1'
      });
    }
    var profile = {
      username: docs.username,
      sex: docs.sex,
      location: docs.location,
      miniIntro: docs.miniIntro
    };
    var info = [];
    Media.find({id:req.params.id},(err,actus)=>{
      if(err){
        return res.json({
          msg:'Failed to connect',
          code: '-1'
        });
      }
      var i=0;
      while(i<actus.length){
        info.append(actus[i]);
        i++;
      }
    });
    return res.json({
      msg:'Personal media is returned',
      code:'200',
      profile:profile,
      info:info
    });
  });
  next();
}

exports.show_blog_follow=(req,res,next)=>{
  Follow.findOne({id:req.params.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'failed to connect',
        code:'-1'
      });
    }
    if(!docs){
      return res.json({
        msg:'Cannot find user',
        code:'-1'
      });
    }
    var follow={
      follow:docs.follow,
      follower:docs.follower,
    };
    var info = [] ;
    var i;
    var j;
    for(i=0;i<follow[follow].length;i++){
      Media.find({id:follow[follow][i]},(err,actus)=>{
        if(err){
          return res.json({
            msg:'Failed to connect',
            code: '-1'
          });
        }
        while(j<actus.length){
          info.append(actus[j]);
          j++;
        }
      });
    }
    return res.json({
      msg:'Follower media is returned',
      code:'200',
      follow:follow[follow],
      info:info
    });
  next();
  });
}

exports.show_blog_all=(req,res,next)=>{
  const date = new Date();
  var j;
  var datas = [];
  for(j=0;j<30;j++){
      Media.find({date:{$gte:date.setDate(date.getDate()-5)}},(err,actus)=>{
        if(err){
          return res.json({
            msg:'Failed to connect',
            code: '-1'
          });
        }
        if(j==30){
          req.session.date=date;
        }
      });
      Profile.find({id:actus.id},(err,docs)=>{
        if(err){
          return res.json({
            msg:'failed to connect',
            code:'-1'
          });
          }
        if(!docs){
         return res.json({
            msg:'Cannot find user',
            code:'-1'});
        }
      });
      var data={
          id:actus.id,
          username:actus.usr,
          avatar:docs.avatar,
          date:actus.date,
          message:actus.content,
          like:actus.like,
          like_user:actus.like_user,
          photo:actus.pic
      };
      datas.push(data);
      }
      res.json{        
        msg:'All media is returned',
        code:'200',
        data: datas}      
  next();
}
}
               
exports.show_otherProfile = (req,res,next)=>{
  Profile.findOne({id:req.params.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
    }
    if(!docs){
      return res.json({
        msg:'Username incorrect!',
        code: '-1'
      });
    }
  })
  Follow.findOne({id:req.params.id},(err,files)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
    }
    if(!files){
      return res.json({
        msg:'Username incorrect!',
        code: '-1'
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
          code: '-1'
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
      code:'200',
      profile:profile,
      info:info
    });
  });
  next();
}

exports.add_follow = (req,res)=>{
  Profile.find({id: req.body.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
      if(!docs.length){
      //返回用户不存在
        return res.json({
          msg:'User doesn\'t existe',
          code: '-1'
        });
      }
    }else{
     //add new follow
      var follow_id = req.body.id;
      follow.add(follow_id);
      return res.json({
        msg:'follow is added',
        code:'200'
      });
      Profile.find({id: follow_id},(err,docs)=>{
        if(err){
          return res.json({
            msg:'Failed to connect',
            code: '-1'
          });
          if(!docs.length){
          //返回用户不存在
            return res.json({
              msg:'User doesn\'t existe',
              code: '-1'
            });
          }
        }else{
         //add new Follower
          follower.add(docs[0].id);
          return res.json({
            msg:'follower is added',
            code:'200'
          });

        }
      })
    }
  })
}

//function thumb up
exports.thumb_up = (req,res)=>{
  Media.find({id: req.body.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
      if(!docs.length){
      //返回动态不存在
        return res.json({
          msg:'Diary doesn\'t existe',
          code: '-1'
        });
      }
    }else{
     //add person who thumbed up the post
      var person_thumbup = req.body.id;
      docs[0].like_usr.append(person_thumbup);
      docs[0].like+=1;
      return res.json({
        msg:'person who thumbed up is added',
        code:'200'
      });
    }
  })
}

