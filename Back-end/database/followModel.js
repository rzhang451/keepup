const mongoose = require('./mongoose');

//My Course database
var followSchema = mongoose.Schema({
  id: String,
  follow: [String],
  follower: [String]
});

module.exports = mongoose.model('Follow',followSchema);
