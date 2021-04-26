//引入nodemailer
const nodemailer = require('nodemailer');
let transporter = nodemailer.createTransport({
  service:'qq',
  port:465,
  secure:true,
  auth:{
    user:'380353873@qq.com',
    pass:'lwldfarfhgrebjai'
  }
});

function sendMail(mail,code,call){
  let mailOptions = {
    from: '"Keep-Up"<380353873@qq.com>',
    to: mail,
    subject: 'Keep-Up sign-up',
    text: 'authentification code is:' + code,
  }

  //发送函数
  transporter.sendMail(mailOptions,(err,info)=>{
    if(err){
      call(false)
    }else{
      call(true)
    }
  });
}

module.exports = {
  sendMail
}
