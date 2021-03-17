const mongoose = require('mongoose');

const profile = new mongoose.Schema({
  id: Number,
  name:{
    type: String,
    required: true,
    minlength: 2,
    maxlength: 20
  },
  telephone:{
    type: Number,
    length: 10
  } 
});

const Profile = mongoose.model('Profile',profile);

module.exports = Profile
