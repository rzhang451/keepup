//引入mongoose
const mongoose = require("mongoose");
//链接数据库
const address = "mongodb+srv://nanachi:36070876@cluster0.nfeev.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
mongoose.connect(address);
var db = mongoose.connection;
db.on('error',console.error.bind(console,'connection error:'));
db.once('open',function(){
  console.log('successfully connected to mongoDB');
});

module.exports = mongoose;
