var express = require('express');
var app = express();
const course = require('../controller/courseCtrl.js');

router.get('/',course.allCourse); //to show all the courses

router.get('/my-recommandation',course.myRecommandation);//to search my course with parameters
router.get('/my-recommandation/course-recommand',course.courseRecommand);

router.get('/video/:vnumber',course.video);//to show video
router.get('/video/:vnumber/fullscreen',course.fullscreen);
router.get('/video/:vnumber/comment/:id',course.comment);

router.get('/my-course',course.myCourse);//to show my agenda and all course I have done.
router.get('/my-course/favorite',course.favorite);


module.exports = router;
