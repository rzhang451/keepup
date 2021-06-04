const Profile = require('../model/profileModel');
const MyCourse = require('../model/myCourseModel');
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

exports.avatar = (req,res)=>{
  Profile.find({id: req.body.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
      if(!docs.length){
      //返回用户不存在
        return res.json({
          msg:'Profile doesn\'t existe',
          code: '-1'
        });
      }
    }else{
      //upload avatar
      var storage_avatar = multer.diskStorage({
        destination:path.resolve(__dirname,'../public/upload/image/profile'),
        filename:function(req,file,cb){
        let extName = file.originalname.slice(file.originalname.lastIndexOf('.'))
        let filename = docs[0].username
        cb(null,filename + extName)
        }
      })

      var imageUploader_avatar = multer({
        storage_avatar:storage_avatar
      })
      router.post('/profile/avatar/upload/image', imageUploader_avatar.single('photo'),function(req,res){
        console.log(req.files);
      });
      Profile.update({id:docs[0].id},{$set:{avatar:req.files.path}},(err)=>{
        if (!err){
          console.log('soccessfully modified')
        }
        else{
          throw err
        }
      });
      return res.json({
      msg:'avatar is uploaded',
      code:'200'
      });
    }
  })
}
exports.show_favor_course=(req,res)=>{
  Profile.find({id:req.query.id},(err,docs)=>{
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
      var favor_liste=docs.favor_course;
    }
  });
  var k;
  var favor_result=[];
  for(k=0;k<favor_liste.length;k++){
    Course.find({id:favor_liste[k]},(err,courses)=>{
      if(err){
        return res.json({
          msg:'Failed to connect',
          code: '-1'
        });
      }
      if(!docs){
        return res.json({
          msg:'Course not found!',
          code: '-1'
        });
      }
      else{
        var data={
          id:courses.id,
          name:courses.name,
          duration:courses.duration,
          type:courses.type,
          difficulty:courses.difficulty,
          goal:courses.goal,
          consumption:courses.consemption,
          intro:courses.intro,
          way:courses.way
        };
        favor_result.push(data);
      }
    });
  }
  return res.json({
    msg:'All favour got',
    code:'200',
    data:favor_result
  });
  next();
}

exports.show_myhealth = (req,res)=>{
  Profile.findOne({id:req.query.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'failed to connect',
        code:'-1'
      });
    }
    if(!docs){
      return res.json({
        msg:'user does not exist',
        code:'-1'
      });
    }
    else{
      return res.json({
        msg:'info got',
        code:'200',
        height:docs[0].Height,
        weight:docs[0].Weight,
        bmi:docs[0].Bmi
      });
    }
  });
}

exports.show_follow=(req,res)=>{
  Follow.find({id:req.query.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: '-1'
      });
    }
    if(!docs){
      return res.json({
        msg:'User id incorrect!',
        code: '-1'
      });
    }
    else{
      return res.json({
        msg:'Follow list get!',
        code:'200',
        follow:docs[0].follow,
        follower:docs[0].follower
      })
    }
  });
}
