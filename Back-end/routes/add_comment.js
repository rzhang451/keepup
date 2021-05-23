const Profile = require('../model/profileModel');
const Media = require('../model/mediaModel');

exports.add_comment = (req,res,next)=>{
  Media.find({id: req.body.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: 'error'
      });
      if(!docs.length){
      //返回动态不存在
        return res.json({
          msg:'Diary doesn\'t existe',
          code: 'error'
        });
      }
    }else{
     //add new comment
      var comment_usr = req.body.usr;
      var comment_content = req.body.content;
      var comment_date = Date();
      comment.add(comment_usr,comment_content);
    }
  })
}
