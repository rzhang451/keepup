//引入mongoose
const mongoose = require("mongoose");
//链接数据库
const address = "mongodb+srv://nanachi:36070876@cluster0.nfeev.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"; //刘
//mongoose.connect(address,{useNewUrlParser:true,useUnifiedTopology:true},()=>console.log("success!"));
//"mongodb+srv://LJT_S:001021@cluster0.2xb7a.mongodb.net/db00?retryWrites=true&w=majority" //宋
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
//add an example
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
Profile.remove({username:'zhangsan'},(err)=>{ //delete the person whose name is "zhangsan"
  if (!err){
    console.log("sucessfully deleted!")
  }else{
    throw err
  }
})

//exemple of modification
Profile.update({location:"Lyon"},{$set:{username:"lisi"}},(err)=>{  //change the name of the people live in Lyon to "lisi"
  if (!err){
    console.log("successfully modified")
  }else{
    throw err
  }
})

// 2 examples of finding
Profile.find({username:"lisi"},(err,docs)=>{ //find the person whose name is "lisi"
  if(!err){
    console.log(docs) //afficher le resultat
  }else{
    throw err
  }
})

Profile.find({},"_id username sexe location",{skip:2,limit:2},(err,docs)=>{ //skip 2 mess.(skip:2) and show 2 mess.(limit:2)
  if(!err){
    console.log(docs)
  }else{
    throw err
  }
})


