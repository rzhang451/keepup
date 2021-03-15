var express = require('express');
var app = express();

var profileRouter = require('./routers/profileRouter');
var levelTest = require('./routers/levelTestRouter');
var search = require('./routers/searchRouter');
var recommandation = require('./routers/recommandationRouter');
var sharedMedia = require('./routers/sharedMediaRouter');
var setting = require('./routers/settingRouter');

app.get('\login',function(req,res){
  res.send('Welcome to KeepUp');
});


app.listen(3000);
console.log('listening to port 3000');
console.log('listening to port 3000');