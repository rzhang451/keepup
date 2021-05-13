const express = require('express');
const router = express.Router();
const Profile = require('../database/profileModel');
const MyCourse = require('../database/myCourseModel');

//日程表页面: 该页面在显示时，前端向后端发送用户id，返回今日课程和打卡天数。
router.get('/agenda/:id',function(req,res){
  var day = new Date().getDay();
  Profile.find({id: req.params.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: 'error2'
      });
    }else{Mycourse.find({usr: req.params.id},(err,course)=>{
       if(err){
         return res.json({
           msg:'Failed to connect',
           code: 'error2'
         });
       }
       if(!docs.length){
       //返回用户不存在
         return res.json({
           msg:'User doesn\'t existe!',
           code: 'error1'
         });
       }else{
          if(!course.length){
            return res.json({
              plan:'You don\'t have course yet',
              continueExerciceDay: docs[0].continueExerciceDay,
            });
          }else{
             courseList=[];
             for(i=0;i<course.length;i++){
               for(j=0;j<course[i].schedul.length;j++){
                 if(day==course[i].schedul){
                    courseList.append({class:course[i].course,
                                       favo:course[i].favorite,
                                      })
                 }
               }
             }
             return res.json({
               plan: courseList,
               continueExerciceDay: docs[0].continueExerciceDay,
             });
          };
       }
    })
   }
  })
}
