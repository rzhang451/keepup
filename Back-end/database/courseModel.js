//获取连接
const mongoose = require('./mongoose');
//Profile database
var courseSchema = mongoose.Schema({
  id: Number,
  name: String,
  duration: Number,
  type: String,
  difficulty: Number,
  goal: String
});
//Model
module.exports = mongoose.model('Course',profileSchema);
