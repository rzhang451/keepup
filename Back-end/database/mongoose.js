//引入mongoose
const mongoose = require("mongoose");
//链接数据库
const address = "mongodb+srv://nanachi:36070876@cluster0.nfeev.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
//mongoose.connect(address,{useNewUrlParser:true,useUnifiedTopology:true},()=>console.log("success!"));
mongoose.connect(address);
var db = mongoose.connection;
db.on('error',console.error.bind(console,'connection error:'));
db.once('open',function(){
  console.log('successfully connected to mongoDB');
});

//Schema
var profileSchema = mongoose.Schema({
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
  ?"The password is "+this.password
  :"error";
  console.log(forgetpwd);
}
//Model
var Profile = mongoose.model('Profile',profileSchema);
//新增一个实例
var zhangsan = new Profile({
  username: 'zhangsan',
  password: '123456',
  sexe: 'male',
  location: 'Lyon',
  miniIntro: 'Hello World!',
  totalExerciceTime: 20,
  totalExerciceDay: 1,
  totalConsumption: 175,
  exercice: ['back strength'],
  agenda: ['back strength']
});
zhangsan.save(function(err,zhangsan){
  if(err) return console.error(err);
  zhangsan.pwd();
});

//example of deletion
Profile.remove({username:'zhangsan'},(err)=>{
  if (!err){
    console.log("sucessfully deleted!")
  }else{
    throw err
  }
})

