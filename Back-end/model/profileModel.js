//获取连接
const mongoose = require('./mongoose');
const uuid = require('node-uuid');
//Profile database
var profileSchema = mongoose.Schema({
  id: String,
  username:{
    type:String,
    default: uuid.v1
  },
  email: String,
  password: String,
  sexe: String,
  avatar: String,
  location: String,
  miniIntro: String,
  Height: Number,
  Weight: Number,
  Bmi: Number,
  totalExerciceDay: Number,
  totalExerciceTime: Number,
  totalExerciceDay: Number,
  totalConsumption: Number,
  favor_course:[String]
});

//Model
module.exports = mongoose.model('Profile',profileSchema);
