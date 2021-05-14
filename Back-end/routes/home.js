const Profile = require('../model/profileModel');
const myCourse = require('../model/myCourseModel');
const Course = require('../model/courseModel');

exports.recommand = (req,res,next)=>{
  if(req.cookies.recommand){
    return res.json({
      msg:'Courses Successfully Retured',
      code:'success',
      info:req.cookies.recommand
    })
  }
  var person = new Profile;
  var course = {};
  }
  Profile.find({id:req.params.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:err,
        code:'error'
      });
    }
    if(!docs.length){
      return res.json({
        msg:'User doesn\'t exist',
        code:'error'
      });
    }
    person = docs[0];
    //智能推荐课程
    person.level((goal,level)=>{
      Course.find({$and:[{goal:goal},{difficulty:level}]},(err,docs)=>{
        if(err){
          return res.json({
            msg:err,
            code:'error'
          });
        }
        if(!docs.length){
          var i = 0;
          while((i<2)&&(i<docs.length)){
            course.append(docs[i]);
            i++;
          }
        }
      })
    })
  });
  res.cookie('recommand',course,{maxAge:60*60*100,httpOnly:true});
  return res.json({
    msg:'Courses Successfully Retured',
    code:'success',
    info:course
  });
  next();
}

exports.favorite = (req,res,next)=>{
  if(req.cookies.favorite){
    return res.json({
      msg:'Courses Successfully Retured',
      code:'success',
      info:req.cookies.favorite
    })
  }
  var course = {};
  MyCourse.find({$and:[{usr:req.params.id},{favorite:True}]},(err1,docs)=>{
    if(err1){
      return console.error(err1);
    }
    var i = 0;
    while((i<4)&&(i<docs.length)){
      Course.find({id:docs[i]},(err2,infos)=>{
        if(err2){
          return console.error(err2);
        }
        if(!infos.length){
          return res.send('problem with database!');
        }else{
          course.append(infos[0]);
        }
      });
      i++;
    }
  });
  res.cookie('favorite',course,{maxAge:60*60*1000,httpOnly:true});
  return res.json({
    msg:'Courses Successfully Retured',
    code:'success',
    info:course
  });
  next();
}
