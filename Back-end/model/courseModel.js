//获取连接
const mongoose = require('./mongoose');
//Profile database
var courseSchema = mongoose.Schema({
  id: String,
  name: String,
  duration: Number,
  type: String,
  difficulty: Number, //(1-easy, 2-medium 3-difficult)
  goal: String,
  way:String
});
//Model
module.exports = mongoose.model('Course',courseSchema);
