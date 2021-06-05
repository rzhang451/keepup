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
  pic:{
    type:String,
    default:null
  },
  date:String,
  time:{
    type: Date,
    default:Date.now
  },
  comment:[{
    usr:String,
    content:String
  }],
  like:{
    type:Number,
    default:0
  },
  like_user:{
    type:[String],
    default:[]
  },
  avatar:{
    type:String,
    default:null
  }
});

module.exports = mongoose.model('Media',mediaSchema);
