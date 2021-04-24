var express = require('express');
var router = express.Router();
var Profile = require('../database/profileModel');
var SharedDiary = require('../database/diaryModel');

//example
/*
var Diary1 = new SharedDiary({
    username：
    context: ['今天天气真好'],
    thumb: 0
});
Diary1.save(function(err,Diary1){
    if(err) return console.error(err);
    console.log("successfully added!");
  });
*/
   
