//引入mongoose
const mongoose = require("mongoose");
//链接数据库
const address = "mongodb+srv://LJT_S:001021@cluster0.2xb7a.mongodb.net/db00?retryWrites=true&w=majority";
//mongoose.connect(address,{useNewUrlParser:true,useUnifiedTopology:true},()=>console.log("success!"));
//"mongodb+srv://LJT_S:001021@cluster0.2xb7a.mongodb.net/db00?retryWrites=true&w=majority" //宋
mongoose.connect(address);
var db = mongoose.connection;
db.on('error',console.error.bind(console,'connection error:'));
db.once('open',function(){
  console.log('successfully connected to mongoDB');
});

//Schema
var diarySchema = mongoose.Schema({
    username: String,
    context: [String],
    thumb: Number,

})

//Model
module.exports = mongoose.model('SharedDiary',diarySchema);
