//获取连接
const mongoose = require('./mongoose');
const uuid = require('node-uuid');
//Profile database
var profileSchema = mongoose.Schema({
  id:{
    type:String,
    default: uuid.v1
  },
  username:{
    type:String,
    default: uuid.v1
  },
  email: String,
  password: String,
  sexe: {
    type:String,
    default:'secret'
  },
  avatar:{
    type:String,
    default:'/public/upload/image/profile/default.jpg'
  },
  location:{
    type:String,
    default: 'beijing'
  },
  miniIntro: {
    type:String,
    default:'hello'
  },
  Height: {
    type:Number,
    default: 180
  },
  Weight: {
    type:Number,
    default: 70
  },
  Bmi: {
    type:Number,
    default: 21.60
  },
  totalExerciceDay: Number,
  totalExerciceTime: Number,
  totalConsumption: Number,
  favor_course:[String],
  favor_date:[String]
});

//Model
module.exports = mongoose.model('Profile',profileSchema);
