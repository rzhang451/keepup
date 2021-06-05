const mongoose = require('./mongoose');

//My Course database
var followSchema = mongoose.Schema({
  id: String,
  follow: [{
    id: String,
    name: String,
    avatar: String
  }],
  follower: [{
    id: String,
    name: String,
    avatar: String
  }],
  follow_num:{
    type:Number,
    default:0
  },
  follower_num:{
    type:Number,
    default:0
  }
});


module.exports = mongoose.model('Follow',followSchema);
