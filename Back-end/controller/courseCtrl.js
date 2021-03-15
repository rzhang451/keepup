var express = require('express');
var app = express();
var URL = require('url');
var mysql = require('mysql');

var connection = mysql.createConnect({
  host : 'localhost',
  user : 'root',
  password :'123456',
  database :'database'
});

connection.connect();

function allCourse(){

}

function myRecommandation(){

}

function courseRecommand(){

}

function video(){

}

function fullscreen(){

}

function comment(){

}

function myCourse(){

}

function favorite(){

}
