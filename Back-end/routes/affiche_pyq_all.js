const Profile=require('../model/profileModel');
const Media=require('../model/mediaModel');

exports.affiche_pyq_all=(req,res,next)=>{
  Profile.find({},(err,docs)=>{
    if(err){
      return res.json({
        msg:'failed to connect',
        code:'error'
      });
    }
    if(!docs){
      return res.json({
        msg:'Cannot find user',
        code:'error'
      });
    })
    var ids = [];
    var i;
    for(i=0;i<docs.length;i++){
      ids[i].append(docs.id);
    }
    var j;
    var info = [];
    for(j=0;j<docs.length;j++){
      Media.find({id:ids[j]},(err,actus)=>{
        if(err){
          return res.json({
            msg:'Failed to connect',
            code: 'error'
          });
        }
        while(j<actus.length){
          info.append(actus[j]);
          j++;
        }
      });
    }
    return res.json({
      msg:'All media is returned',
      code:'success',
      profile:profile,
      info:info
    });
  next();
}
}
