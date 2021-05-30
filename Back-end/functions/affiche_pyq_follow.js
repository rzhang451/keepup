const Follow=require('../model/followModel');
const Media=require('../model/mediaModel');

exports.affiche_pyq_follow=(req,res,next)=>{
  Follow.findOne({id:req.params.id},(err,docs)=>{
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
    }
    var follow={
      follow:docs.follow,
      follower:docs.follower,
    };
    var info = [] ;
    var i;
    var j;
    for(i=0;i<follow[follow].length;i++){
      Media.find({id:follow[follow][i]},(err,actus)=>{
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
      msg:'Follower media is returned',
      code:'success',
      follow:follow[follow],
      info:info
    });
  next();
  });
}
