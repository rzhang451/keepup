const Profile=require('../model/profileModel');
const Media=require('../model/mediaModel');
exports.affiche_pyq_all=(req,res,next)=>{
  const date = new Date();
  var j;
  var datas = [];
  for(j=0;j<30;j++){
      Media.find({date:{$gte:date.setDate(date.getDate()-5)}},(err,actus)=>{
        if(err){
          return res.json({
            msg:'Failed to connect',
            code: '-1'
          });
        }
        if(j==30){
          req.session.date=date;
        }
      });
      Profile.find({id:actus.id},(err,docs)=>{
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
      });
      var data={
          id:actus.id,
          username:actus.usr,
          avatar:docs.avatar,
          date:actus.date,
          message:actus.content,
          like:actus.like,
          like_user:actus.like_user,
          photo:actus.pic
      };
      datas.push(data);
      }
      res.json{        
        msg:'All media is returned',
        code:'200',
        data: datas}
    
      
  next();
}
}
