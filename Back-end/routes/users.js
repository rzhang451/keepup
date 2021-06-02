const path = require('path');
const Course = require('../model/courseModel');
const Profile = require('../model/profileModel');
const Follow = require('../model/followModel');

exports.image = (req,res)=>{
  var filePath = path.resolve('./'+req.url);
    res.sendFile( filePath );
    console.log("Request for " + req.url + " received.");
}

//give front-end the detailed infos of the video
exports.video = (req,res)=>{
  /*var filePath = path.resolve('./'+req.url);
    res.sendFile( filePath );*/
    
  Course.find({name:req.query.name},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to fetch video',
        code: '-1'
      });
    }
    if(!docs){
      return res.json({
        msg:'Course doesn\'t existe',
        code: '-1'
      });
    }
    else{
      console.log("Request for " + req.url + " received.");
      return res.json({
        msg:'got video!',
        code:'200',
        name:docs[0].name,
        duration:docs[0].duration,
        type:docs[0].type,
        difficulty:docs[0].difficulty,
        goal:docs[0].goal,
        consumption:docs[0].consumption,
        intro:docs[0].intro,
        way:docs[0].way
      });
    }
  });

}

exports.check = (req,res,next)=>{
  var course = req.query.course;
  var usr = req.query.usr;
  var day = new Date();
  var date = day.getDate()+'/'+day.getMonth()+1+'/'+day.getFullYear();
  var person = new Profile;
  Profile.findOnt({id:usr},(err,docs)=>{
    if(err){
      res.json({
        msg: 'Failed with connection',
        code: '-1'
      });
    }
    if(!docs){
      return res.json({
        msg:'User doesn\'t existe',
        code: '-1'
      });
    }
    person = docs;
  });
  if(person.lastExerciceDay!=date){
    person.lastExerciceDay = date;
    person.totalExerciceDay = person.totalExerciceDay + 1;
  }
  Course.findOne({id:course},(err,docs)=>{
    if(err){
      res.json({
        msg: 'Failed with connection',
        code: '-1'
      });
    }
    if(!docs){
      return res.json({
        msg:'Course doesn\'t existe',
        code: '-1'
      });
    }
    person.totalConsumption = person.totalConsumption + docs.consumption;
    person.totalExerciceTime = person.totalExerciceTime + docs.duration;
  });
  Profile.update({id:usr},{$set:{
    totalConsumption:person.totalConsumption,
    totalExerciceDay:person.totalExerciceDay,
    totalExerciceTime:person.totalExerciceTime}},(err)=>{
    if(err){
      res.json({
        msg: 'Failed with connection',
        code: '-1'
      });
    }else{
      return res.json({
        msg: 'profile is updated',
        code: '200',
        totalDay: person.totalExerciceDay
      });
    }
  });
  next();
}

exports.profile = (req,res)=>{
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
    return res.json({
      msg:'Profile is returned',
      code:'200',
      username:docs[0].username,
      email:docs[0].email,
      password:docs[0].password,
      sexe:docs[0].sexe,
      avatar:docs[0].avatar,
      location:docs[0].location,
      miniIntro:docs[0].miniIntro,
      totalExerciceDay:docs[0].totalExerciceDay,
      totalExerciceTime:docs[0].totalExerciceTime,
      totalConsumption:docs[0].totalConsumption
    });
  });
}

exports.health = (req,res,next)=>{
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
    return res.json({
      msg:'Health is returned',
      code:'200',
      Height:docs[0].Height,
      Weight:docs[0].Weight,
      Bmi:docs[0].Bmi
    });
  });
  next();
}

exports.follower = (req,res,next)=>{
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
    return res.json({
      msg:'Profile is returned',
      code:'200',
      follower:docs.follower
    });
  });
  next();
}

//已经在media里写了add_follow
/*exports.subscribe = (req,res,next)=>{     
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
    return res.json({
      msg:'Profile is returned',
      code:'success',
      follower:docs.follow
    });
  });
  next();
}
*/
exports.change_profile = (req,res)=>{
  Profile.update({id:req.body.id},{$set:{username = req.body.name,
                                         sexe = req.body.sexe,
                                         location = req.body.location,
                                         miniIntro = req.body.intro,}},(err)=>{
    if(err){
      res.json({
          msg: 'Failed to update',
          code: '-1'
      });
    }else{
      res.json({
        msg: 'Update ok',
        code: '200'
      })
    }
  });
}

exports.change_pwd = (req,res)=>{
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
    if(req.params.old_pwd!=docs.password){
      return res.json({
        msg:'Your old password is incorrect',
        code: 'illegal'
      });
    }
  });
  if(req.params.new_pwd==req.params.check_pwd){
    Profile.update({id:req.params.id},{$set:{password:req.params.new_pwd}},(err)=>{
      if(err){
        return res.json({
          msg:'Failed to connect',
          code: '-1'
        });
      }
    });
  }
  return res.json({
    msg:'Successfully Modified',
    code:'200'
  });
}

exports.change_health = (req,res)=>{
  Profile.update({id:req.body.id},{$set:{Height = req.body.height,
                                         Weight = req.body.weight,
                                         Bmi = req.body.bmi,}},(err)=>{
    if(err){
      res.json({
          msg: 'Failed to update',
          code: '-1'
      });
    }else{
      res.json({
        msg: 'Update ok',
        code: '200'
      })
    }
  });
}

//add new favor course
exports.favor_course = (req,res)=>{
  Profile.find({id: req.body.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
      if(!docs.length){
      //返回user不存在
        return res.json({
          msg:'User doesn\'t existe',
          code: '-1'
        });
      }
    }else{
     //add new favor course
      var course_id = req.body.id;
      favor_course.add(course_id);
      return res.json({
        msg:'favor course is added',
        code: '200'
      });
    }
  })
}
