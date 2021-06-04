//获取连接
const mongoose = require('./mongoose');
const uuid = require('node-uuid');
//Profile database
var courseSchema = mongoose.Schema({
  id:{
    type:String,
    default:uuid.v1()
  },
  name: String,
  duration: Number,
  type: String,
  difficulty: Number, //(1-easy, 2-medium 3-difficult)
  goal: String,
  consumption:Number,
  intro:String,
  way:String,
  cover:String,
  duration_check:String
});
//Model
module.exports = mongoose.model('Course',courseSchema);
