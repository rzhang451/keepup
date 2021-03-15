var express = require('express');
var app = express();

var profileRouter = require('./routes/profileRouter');
var levelTest = require('./routes/levelTestRouter');
var search = require('./routes/searchRouter');
var recommandation = require('./routes/recommandationRouter');
var sharedMedia = require('./routes/sharedMediaRouter');
var setting = require('./routes/settingRouter');

app.get('\login',function(req,res){

});


app.listen(3000);
console.log('listening to port 3000');
console.log('listening to port 3000');
