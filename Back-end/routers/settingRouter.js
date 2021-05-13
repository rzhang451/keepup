const express = require('express');
const router = express.Router();
const Profile = require('../database/profileModel');

router.post('/modify',function(req,res){
  Profile.Update({id:req.body.id},{$set:{username = req.body.name,
                                         password = req.body.pwd,
                                         sexe = req.body.sexe,
                                         location = req.body.location,
                                         miniIntro = req.body.intro,
                                         Height =req.body.height,
                                         Weight = req.body.weight,
                                         Bmi = req.body.bmi,
                                         }},(err)=>{
                                           if(err){
                                             res.json({
                                               msg: 'Failed to update',
                                               code: 'error1'
                                             });
                                           }else{
                                             res.json({
                                               msg: 'Update ok',
                                               code: 'success'
                                             })
                                           }
                                         });
})
