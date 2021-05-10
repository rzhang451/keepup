const mongoose = require('./mongoose');

//My Course database
var myCourseSchema = mongoose.Schema({
  usr: String,
  course: Number,
  favorite: Boolean,
  schedul: String
});
//Model
module.exports = mongoose.model('MyCourse',myCourseSchema);
