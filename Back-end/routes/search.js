const Course = require('../model/courseModel');
const Profile = require('../model/profileModel');

exports.search_course = (req,res,next)=>{
    Course.findOne({name:req.params.name},(err,docs)=>{
        if(err){
          return res.json({
            msg:'failed to connect',
            code:'error'
          });
        }
        if(!docs){
          return res.json({
            msg:'Cannot find course',
            code:'error'
          });
        }
        return res.json({
            msg:'Course is returned',
            code:'success',
            Course:docs.id
        });
    });
    next();
}

exports.search_user = (req,res,next)=>{
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
        Profile:docs.username
      });
  });
  next();
}