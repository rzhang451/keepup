const mongoose = require('./mongoose');
const uuid = require('node-uuid');

//Social Media database
var mediaSchema = mongoose.Schema({
  id:{
    type:String,
    default:uuid.v1
  },
  usr:String,
  usr_id:String,
  content:String,
  pic:String,
  date:String,
  comment:[{
    usr:String,
    content:String
  }],
  like:{
    type:Number,
    default:0
  },
  like_usr:[String],
  avatar:String
});

module.exports = mongoose.model('Media',mediaSchema);
