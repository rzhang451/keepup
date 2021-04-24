//获取连接
const mongoose = require('./mongoose');
const uuid = require('node-uuid');
//Profile database
var profileSchema = mongoose.Schema({
  id:{
    type:String,
    default: uuid.v1
  },
  username: String,
  password: String,
  sexe: String,
  location: String,
  miniIntro: String,
  totalExerciceTime: Number,
  totalExerciceDay: Number,
  totalConsumption: Number,
  exercice: [String],
  agenda: [String]
});
//Methods
profileSchema.methods.pwd = function(){
  var forgetpwd = this.password
    ? "The password is " + this.password
    : "error";
  console.log(forgetpwd);
}
//Model
module.exports = mongoose.model('Profile',profileSchema);
