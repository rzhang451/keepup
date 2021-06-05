const Profile = require('../model/profileModel');
const MyCourse = require('../model/myCourseModel');
const Course = require('../model/courseModel');

//Agenda page: Frontend send user it to backend and receive today's courses and persistence time（days）
exports.agenda = (req,res,next)=>{
  var courseList = [];
  Profile.find({id: req.params.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: 'error'
      });
      if(!docs.length){
      //if user doesn't existe
        return res.json({
          msg:'User doesn\'t existe',
          code: 'error'
        });
      }
    }else{
      Mycourse.find({usr: req.params.id},(err,course)=>{
       if(err){
         return res.json({
           msg:'Failed to connect',
           code: 'error'
         });
       }else{
          if(!course.length){
            return res.json({
              plan:'You don\'t have course yet',
              day: docs[0].totalExerciceDay,
            });
          }else{
            var date = new Date();
            var day = date.getDay();
            for(i=0;i<course.length;i++){
              for(j=0;j<course[i].schedul.length;j++){
                if(day==course[i].schedul[j]){
                  courseList.append(course[i].course);
                 }
               }
             }
             for(var i=0;i<courseList.length;i++){
               Course.findOne({id:courseList[i]},(err,docs)=>{
                 if(err){
                   return res.json({
                     msg:'Failed to connect',
                     code: 'error'
                   });
                 }
                 if(!docs){
                   return res.json({
                     msg:'Error with the database',
                     code: 'error'
                   });
                 }
                 courseList[i]={id:courseList[i],name:docs.name};
               });
             }
          }
       }
     });
   }
    return res.json({
     plan: courseList,
     totalExerciceDay: docs[0].totalExerciceDay,
    });
  })
}


exports.initialize_agenda=(req,res)=>{
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
      var personaldate=[];
      personaldate=docs[0].favor_date;
      return res.json({
        msg:'agenda get ok!',
        code:'200',
        checked:personaldate
      });
    }
  });
}


exports.add_agenda=(req,res)=>{
  Profile.findOne({id:req.body.id},(err,docs)=>{
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
      var agenda=docs.personal_date;
    }
  });
  agenda.push(req.body.date);
  Profile.updateOne({id:req.body.id},{$set:{personal_date:agenda}},(err)=>{
    if(err){
      res.json({
          msg: 'Failed to update',
          code: '-1'
      });
    }else{
      res.json({
        msg: 'Update ok',
        code: '200'
      });
    }
  });
}
