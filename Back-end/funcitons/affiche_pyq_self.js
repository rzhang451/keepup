const Media=require('../model/mediaModel');
const Profile=require('../model/profileModel');

exports.affiche_pyq_self=(req,res,next)=>{
  Profile.findOne({id:req.params.id},(err,docs)=>{
    if(err){
      return res.json({
        msg:'Failed to connect',
        code: 'error'
      });
    }
    if(!docs){
      return res.json({
        msg:'Username incorrect!',
        code: 'error'
      });
    }
    var profile = {
      username: docs.username,
      sex: docs.sex,
      location: docs.location,
      miniIntro: docs.miniIntro
    };
    var info = [];
    Media.find({id:req.params.id},(err,actus)=>{
      if(err){
        return res.json({
          msg:'Failed to connect',
          code: 'error'
        });
      }
      var i=0;
      while(i<actus.length){
        info.append(actus[i]);
        i++;
      }
    });
    return res.json({
      msg:'Personal media is returned',
      code:'success',
      profile:profile,
      info:info
    });
  });
  next();
}
