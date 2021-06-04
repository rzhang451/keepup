const Course = require('../model/courseModel');
const Profile = require('../model/profileModel');

exports.search_course = (req,res)=>{
  //var courses = [];
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
    else{
      var datas =[];
      for( i=0;i<docs.length;i++){
        var data={
          name:docs[i].name,
          duration:docs[i].duration,
          cover:docs[i].cover,
          miniIntro:docs[i].miniIntro,
        }
        datas.push(data)
    }
      console.log("Request for " + req.url + " received.");
      return res.json({
          msg:'Course is returned',
          code:'200',
          result_number:docs.length,
          data:datas
        });
  }
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
      else{
        console.log("Request for " + req.url + " received.");
        return res.json({
          msg:'Profile is returned',
          code:'200',
          Profile:[{username:docs[0].username,sexe:docs[0].sexe}]
      });
    }
  });
}

exports.label_search=(req,res)=>{
  console.log("Request for " + req.url + " received.");
  if(req.query.duration=='5-10'||req.query.duration=='10-20'||req.query.duration=='20-40'|req.query.duration=='40+')
  {
    Course.find({duration:req.query.duration},(err,docs)=>{
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
          else{
            return res.json({
              msg:'research found',
              code:'200',
              data:[{name:docs[0].name,duration:docs[0].duration,cover:docs[0].cover}]
            });
          }
    });
  }
  if(req.query.type=='leg'||req.query.type=='arm'||req.query.type=='abdominal'|req.query.type=='back')
  {
    Course.find({type:req.query.type},(err,docs)=>{
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
      else{
          return res.json({
              msg:'research found',
              code:'200',
              data:[{name:docs[0].name,duration:docs[0].duration,cover:docs[0].cover}]
            });
          }
    });
  }
  if(req.query.difficulty=='1'||req.query.difficulty=='2'||req.query.difficulty=='3')
  {
    Course.find({difficulty:req.query.difficulty},(err,docs)=>{
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
          else{
            return res.json({
              msg:'research found',
              code:'200',
              data:[{name:docs[0].name,duration:docs[0].duration,cover:docs[0].cover}]
            });
          }
    });
  }
  if(req.query.goal=='muscle'||req.query.goal=='lose weight')
  {
    Course.find({goal:req.query.goal},(err,docs)=>{
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
          else{
            return res.json({
              msg:'research found',
              code:'200',
              data:[{name:docs[0].name,duration:docs[0].duration,cover:docs[0].cover}]
            });
          }
    });
  }

}
