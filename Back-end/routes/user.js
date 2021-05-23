const path = require('path');
const Course = require('../model/courseModel');
const Profile = require('../model/profileModel');
const Follow = require('../model/followModel');

exports.image = (req,res,next)=>{
  var filePath = path.resolve('./'+req.url);
    res.sendFile( filePath );
    console.log("Request for " + req.url + " received.");
}

exports.video = (req,res,next)=>{
  var filePath = path.resolve('./'+req.url);
    res.sendFile( filePath );
    console.log("Request for " + req.url + " received.");
}

exports.info = (req,res,next)=>{
  Course.findOne({id:req.params.id},(err,docs)=>{
    if(err){
      res.json({
        msg: 'Failed with connection',
        code: 'error'
      });
    }
    if(!docs){
      return res.json({
        msg:'Course doesn\'t existe',
        code: 'error'
      });
    }
    return res.json({
      msg:'info of course is returned',
      code:'success',
      info:docs
    });
  });
  next();
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
        code: 'error'
      });
    }
    if(!docs){
      return res.json({
        msg:'User doesn\'t existe',
        code: 'error'
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
        code: 'error'
      });
    }
    if(!docs){
      return res.json({
        msg:'Course doesn\'t existe',
        code: 'error'
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
        code: 'error'
      });
    }else{
      return res.json({
        msg: 'profile is updated',
        code: 'success',
        totalDay: person.totalExerciceDay
      });
    }
  });
  next();
}

exports.profile = (req,res,next)=>{
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
      profile:docs
    });
  });
  next();
}

exports.follower = (req,res,next)=>{
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
      follower:docs.follower
    });
  });
  next();
}

exports.subscribe = (req,res,next)=>{
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

exports.change_profile = (req,res)=>{
  Profile.update({id:req.body.id},{$set:{username = req.body.name,
                                         sexe = req.body.sexe,
                                         location = req.body.location,
                                         miniIntro = req.body.intro,}},(err)=>{
    if(err){
      res.json({
          msg: 'Failed to update',
          code: 'error'
      });
    }else{
      res.json({
        msg: 'Update ok',
        code: 'success'
      })
    }
  });
}

exports.change_pwd = (req,res)=>{
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
          code: 'error'
        });
      }
    });
  }
  return res.json({
    msg:'Successfully Modified',
    code:'success'
  });
}

exports.change_health = (req,res)=>{
  Profile.update({id:req.body.id},{$set:{Height = req.body.height,
                                         Weight = req.body.weight,
                                         Bmi = req.body.bmi,}},(err)=>{
    if(err){
      res.json({
          msg: 'Failed to update',
          code: 'error'
      });
    }else{
      res.json({
        msg: 'Update ok',
        code: 'success'
      })
    }
  });
}
