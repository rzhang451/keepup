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
  if(req.query.duration=='5-10'||req.query.duration=='10-20'||req.query.duration=='20-40'|req.query.duration=='40以上')
  {
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
  }
  if(req.query.type=='腿'||req.query.type=='胳膊'||req.query.type=='腹'|req.query.type=='肩背')
  {
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
  }
  if(req.query.difficulty=='1'||req.query.difficulty=='2'||req.query.difficulty=='3')
  {
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
  }
  if(req.query.goal=='练肌'||req.query.goal=='减肥')
  {
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

}

