const mongoose = require('./mongoose');

//My Course database
var myCourseSchema = mongoose.Schema({
  usr: String,
  course: Number,
  favorite: Boolean,
  schedul: [Number] //0-6代表周日到周六
});

//Methods



//Model
module.exports = mongoose.model('MyCourse',myCourseSchema);
