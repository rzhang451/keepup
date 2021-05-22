const Profile = require('../model/profileModel');
const Media = require('../model/mediaModel');

exports.add_pyq = (req,res,next)=>{
  Profile.find({id: req.params.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: 'error'
      });
      if(!docs.length){
      //返回用户不存在
        return res.json({
          msg:'User doesn\'t existe',
          code: 'error'
        });
      }
    }else{
      var diary = new Media ({
        id:uuid.v1,
        usr:req.usr,
        usr_id:req.usr_id,
        content:req.content,
        pic:req.pic,
        video:req.video,
        date:Date(),
        comment: NULL,
        like:0
      });
      diary.save(function(err,diary){
        if(err){
          console.log(err);
          return res.json({
            msg:'Failed to Save the Diary',
            code:'error3'
          });
        }else{
          res.session.usr = diary.id;
          return res.json({
            msg:'Welcome',
            code:'success',
            data:diary.id
          });
        }
      });
      next();
    }
