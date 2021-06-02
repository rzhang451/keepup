const Course = require('../model/courseModel');
const Profile = require('../model/profileModel');

exports.search_course = (req,res)=>{
      //find the course that has the key word given by the user
    Course.find({name:{$regex:req.query.key,$options:'i'}},(err,docs)=>{
        if(err){
          return res.json({
            msg:'failed to connect',
            code:'-1'
          });
        }
        if(!docs){
          return res.json({
            msg:'Cannot find course',
            code:'-1'
          });
        }
        return res.json({
            msg:'Course is returned',
            code:'200',
            course:docs
        });
    });
}

exports.search_user = (req,res)=>{
    //find the users that has the key word given by the user
  Profile.find({name:{$regex:req.query.key,$options:'i'}},(err,docs)=>{
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
        Profile:docs
      });
  });
}

exports.label_search=(req,res)=>{
  Course.find({duration:req.query.key},(err,docs)=>{
    if(err){
          return res.json({
            msg:'failed to connect',
            code:'error'
          });
          }
        if(!docs){
         return res.json({
            msg:'Cannot find user',
            code:'-1'});
        }
        else{
          return res.json({
            msg:'research found',
            code:'200',
            docs:docs
          });
        }
  });
  Course.find({type:req.query.key},(err,docs)=>{
    if(err){
          return res.json({
            msg:'failed to connect',
            code:'error'
          });
          }
    if(!docs){
         return res.json({
            msg:'Cannot find user',
            code:'-1'});
        }
        else{
          return res.json({
            msg:'research found',
            code:'200',
            docs:docs
          });
        }
  });
  Course.find({difficulty:req.query.key},(err,docs)=>{
    if(err){
          return res.json({
            msg:'failed to connect',
            code:'error'
          });
          }
        if(!docs){
         return res.json({
            msg:'Cannot find user',
            code:'-1'});
        }
        else{
          return res.json({
            msg:'research found',
            code:'200',
            docs:docs
          });
        }
  });
  Course.find({goal:req.query.key},(err,docs)=>{
    if(err){
          return res.json({
            msg:'failed to connect',
            code:'error'
          });
          }
        if(!docs){
         return res.json({
            msg:'Cannot find user',
            code:'-1'});
        }
        else{
          return res.json({
            msg:'research found',
            code:'200',
            docs:docs
          });
        }
  });

}
